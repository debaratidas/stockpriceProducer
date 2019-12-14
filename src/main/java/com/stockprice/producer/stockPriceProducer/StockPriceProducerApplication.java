package com.stockprice.producer.stockPriceProducer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class StockPriceProducerApplication {

	static final String topicExchangeName = "stock-quote-exchange";
	static final String queueName = "stock-quote-queue";

	// Creates an AMQP queue.
	@Bean
	Queue queue() {
		return new Queue(queueName, false);
	}

	// Creates a topic exchange
	@Bean
	TopicExchange topicExchange() {
		return new TopicExchange(topicExchangeName);
	}

	// Bind the queue to the exchange
	// Topic exchange and the queue is bound with routing key "stock.quote.#"
	// Any message sent with a routing key beginning with stock.quote. will be routed to the queue.
	@Bean
	Binding binding(Queue queue, TopicExchange exchange) {
		return BindingBuilder.bind(queue).to(exchange).with("stock.quote.#");
	}


	// The bean defined in the listenerAdapter() method is registered as a message listener in the container defined
	// in container(). It will listen for messages on the "stock-quote-queue" queue. Because the StockQuoteReceiver class is a POJO,
	// it needs to be wrapped in the MessageListenerAdapter, where you specify it to invoke receiveMessage.

//	@Bean
//	SimpleMessageListenerContainer container(ConnectionFactory connectionFactory, MessageListenerAdapter listenerAdapter) {
//		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
//		container.setConnectionFactory(connectionFactory);
//		container.setQueueNames(queueName);
//		container.setMessageListener(listenerAdapter);
//
//		return container;
//	}

//	@Bean
//	MessageListenerAdapter listenerAdapter(StockQuoteReceiver receiver) {
//		return new MessageListenerAdapter(receiver, "receiveMessage");
//	}

	public static void main(String[] args) {
		SpringApplication.run(StockPriceProducerApplication.class, args).close();
	}

}
