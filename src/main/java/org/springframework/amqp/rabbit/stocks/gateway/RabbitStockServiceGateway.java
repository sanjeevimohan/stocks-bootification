/*
 * Copyright 2002-2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.amqp.rabbit.stocks.gateway;

import java.io.UnsupportedEncodingException;
import java.util.UUID;

import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitGatewaySupport;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.stocks.domain.TradeRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * Rabbit implementation of {@link StockServiceGateway} to send trade requests to an external process.
 *
 * @author Mark Pollack
 * @author Gary Russell
 */

@Configuration
public class RabbitStockServiceGateway implements StockServiceGateway {

	@Value("${defaultReplyTo}")
	private String defaultReplyTo;

	@Autowired
	private FanoutExchange fanout;

	public void setDefaultReplyTo(String defaultReplyTo) {
		this.defaultReplyTo = defaultReplyTo;
	}

	@Autowired
	private RabbitTemplate template;

	public void send(TradeRequest tradeRequest) {
		System.out.println("*****Stock purchase request*****");
		System.out.println(defaultReplyTo);
		template.convertAndSend(fanout.getName(), "", tradeRequest, new MessagePostProcessor() {
			public Message postProcessMessage(Message message) throws AmqpException {
				message.getMessageProperties().setReplyTo(defaultReplyTo);
				message.getMessageProperties().setCorrelationIdString(UUID.randomUUID().toString());
				return message;
			}
		});
		//template.convertAndSend(fanout.getName(), "", tradeRequest);
	}

}
