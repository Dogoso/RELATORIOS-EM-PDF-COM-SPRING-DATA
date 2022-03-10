package com.doglab.relatorio;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.doglab.relatorio.pdf.PDFGenerator;

@SpringBootApplication
public class RelatorioPdfApplication implements CommandLineRunner {

	private final PDFGenerator GENERATOR;
	
	public RelatorioPdfApplication(PDFGenerator generator)
	{
		this.GENERATOR = generator;
	}
	
	public static void main(String[] args) {
		SpringApplication.run(RelatorioPdfApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		GENERATOR.Generate("funcionarios", "Mercadinho Leite");
	}

}
