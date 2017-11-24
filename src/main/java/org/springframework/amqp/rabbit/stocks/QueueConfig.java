package org.springframework.amqp.rabbit.stocks;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QueueConfig {

	@Bean
	public FanoutExchange fanout() {
		return new FanoutExchange("broadcast.responses");
	}

	@Bean
	public Queue stockRequestQueue() {
		return new AnonymousQueue();
	}

	@Bean
	public Binding bindingStockRequestQueueToFanout(FanoutExchange fanout, Queue stockRequestQueue) {
		return BindingBuilder.bind(stockRequestQueue).to(fanout);
	}

	@Bean
	public TopicExchange topic() {
		return new TopicExchange("app.stock.marketdata");
	}

	@Bean
	public Queue marketDataQueue() {
		return new AnonymousQueue();
	}

	@Bean
	public Binding bindingMarketDataQueueWithTopic(TopicExchange topic, Queue marketDataQueue) {
		return BindingBuilder.bind(marketDataQueue).to(topic).with("app.stock.quotes.nasdaq.*");
	}

	@Bean
	public MessageConverter jsonMessageConverter() {
		return new Jackson2JsonMessageConverter();
	}
}
