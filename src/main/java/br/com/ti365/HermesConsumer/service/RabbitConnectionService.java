package br.com.ti365.HermesConsumer.service;

import com.rabbitmq.client.Channel;

public interface RabbitConnectionService {

	Channel connectionFactory();
	
	void publish(Channel channel, String message);
}
