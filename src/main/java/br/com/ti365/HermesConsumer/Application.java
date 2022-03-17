package br.com.ti365.HermesConsumer;

import br.com.ti365.HermesConsumer.service.impl.POMKafkaConsumer;

public class Application {

	public static void main(String[] args) {

		Application application = new Application();
		application.iniciar();
		
	}
	
	private void iniciar() {
		POMKafkaConsumer consumirdorEvento = new POMKafkaConsumer();
		consumirdorEvento.executar();
	}

}
