package br.com.ti365.HermesConsumer.model;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class EnrichedAttempt {

	public void name() {
		log.error("Error occurred", new RuntimeException("Planned"));
		
	}

	
	
}
