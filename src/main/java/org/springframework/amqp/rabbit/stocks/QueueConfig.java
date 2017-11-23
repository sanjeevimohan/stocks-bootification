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
	public Queue autoDeleteQueue1() {
		return new AnonymousQueue();
	}

	@Bean
	public Binding binding1(FanoutExchange fanout, Queue autoDeleteQueue1) {
		return BindingBuilder.bind(autoDeleteQueue1).to(fanout);
	}

	@Bean
	public TopicExchange topic() {
		return new TopicExchange("app.stock.marketdata");
	}

	@Bean
	public Queue autoDeleteQueue2() {
		return new AnonymousQueue();
	}

	@Bean
	public Binding binding2(TopicExchange topic, Queue autoDeleteQueue2) {
		return BindingBuilder.bind(autoDeleteQueue2).to(topic).with("app.stock.quotes.nasdaq.*");
	}

	@Bean
	public MessageConverter jsonMessageConverter() {
		return new Jackson2JsonMessageConverter();
	}
}
