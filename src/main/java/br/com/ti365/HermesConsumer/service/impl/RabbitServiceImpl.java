package br.com.ti365.HermesConsumer.service.impl;

import java.io.IOException;
import java.util.Properties;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import br.com.ti365.HermesConsumer.service.RabbitService;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class RabbitServiceImpl implements RabbitService {

	private PropertyServiceImpl propertyService = new PropertyServiceImpl();
	Properties properties = propertyService.loadFileProperties();
	private String queue = properties.getProperty("rabbitmq.queue");

	public Channel connectionFactory(ConnectionFactory factory) {
		try {
			log.info("Criando o objeto Rabbit ConnectionFactory");
			
			factory = propertyService.loadRabbitProperties(factory, properties);
			
			log.info("Conexão com o Server RabbitMq estabelecida com sucesso");

			Connection connection = factory.newConnection();
			Channel channel = connection.createChannel();
			channel.queueDeclare(queue, true, false, false, null);
			log.info(" [*] Conexões abertas para publicação de mensagens.");
			return channel;
		} catch (Exception e) {
			log.error("Falha ao criar o objeto ConnectionFactory", e);
		}
		return null;
	}

	public void publish(Channel channel, String message) {
		try {
			channel.basicPublish("", queue, null, message.getBytes());
			log.info("[ * ] Mensagem publicada na fila Rabbit: \n" + message);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
