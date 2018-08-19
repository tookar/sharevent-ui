package de.tolina.shareventui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import de.tolina.sharevent.client.AnfrageClient;

@SpringBootApplication(scanBasePackages = { //
		"de.tolina.shareventui", //
		"de.tolina.sharevent.client", //
})
public class ShareventUiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShareventUiApplication.class, args);
	}

	@Bean
	@Autowired
	public CommandLineRunner doIt(AnfrageClient anfrageClient) {
		return args -> {
			System.err.println(anfrageClient.lookupSites("foo"));
			;
		};
	}
}