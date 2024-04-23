package com.optimagrowth.license;

import java.util.Locale;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

@SpringBootApplication
public class LicenseServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(LicenseServiceApplication.class, args);
	}

	// Diese Bean ist für die Auflösung der bevorzugten Locale (Sprache)
	//verantwortlich
	@Bean
	public LocaleResolver localeResolver() {
		//SessionLocaleResolver ist eine Implementierung von LocaleResolver,
		// die die bevorzugte Locale in der Sitzung speichert.
		SessionLocaleResolver localeResolver = new SessionLocaleResolver();
		localeResolver.setDefaultLocale(Locale.US);
		return localeResolver;
	}
	//ResourceBundleMessageSource die eine Implementierung von MessageSource ist
	// und Nachrichten aus Ressourcenbündeln (z. B. .properties-Dateien) lädt.
	@Bean
	public ResourceBundleMessageSource messageSource() {
		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
		// der Nachrichtenschlüssel als Standardnachricht verwendet wird
		messageSource.setUseCodeAsDefaultMessage(true);
		//Hier wird der Basisname (oder die Basisnamen) der Ressourcenbündel festgelegt,
		// aus denen die Nachrichten geladen werden sollen
		messageSource.setBasenames("responseMessages");
		messageSource.setBasenames("messages");
		return messageSource;
	}

}
