package br.com.ti365.HermesConsumer;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConnectionFactory;

import br.com.ti365.HermesConsumer.service.POMKafkaService;
import br.com.ti365.HermesConsumer.service.RabbitService;
import br.com.ti365.HermesConsumer.service.impl.POMKafkaServiceImpl;
import br.com.ti365.HermesConsumer.service.impl.RabbitServiceImpl;

public class Application {

	public static void main(String[] args) {
		POMKafkaService consumirdorEventoKafka = new POMKafkaServiceImpl();
		RabbitService rabbitmq = new RabbitServiceImpl();
		Channel rabbitMQChannel= rabbitmq.connectionFactory(new ConnectionFactory());
		consumirdorEventoKafka.startConsumerKafka(rabbitMQChannel);

	}

}
