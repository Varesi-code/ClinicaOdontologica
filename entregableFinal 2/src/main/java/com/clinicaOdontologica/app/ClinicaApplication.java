package com.clinicaOdontologica.app;
// import org.apache.log4j.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ClinicaApplication {

	public static void main(String[] args) {
		 //PropertyConfigurator.configure("log4j2.xml");
		SpringApplication.run(ClinicaApplication.class, args);
	}

}
