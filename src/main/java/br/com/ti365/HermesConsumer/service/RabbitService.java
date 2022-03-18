package br.com.ti365.HermesConsumer.service;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConnectionFactory;

public interface RabbitService {

	/*
	 * Create connection with RabbitMQ Server
	 */
	Channel connectionFactory(ConnectionFactory factory);
	
	
	/*
	 * Publish new menssage on Queue
	 */
	void publish(Channel channel, String message);
}
