package de.tolina.shareventui;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import de.tolina.shareventui.client.AnfrageClient;

@SpringBootApplication(scanBasePackages = { //
		"de.tolina.shareventui", //
})
public class ShareventUiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShareventUiApplication.class, args);
	}

	@Bean
	public CommandLineRunner doIt(AnfrageClient anfrageClient) {
		return args -> {
			System.err.println(anfrageClient.lookupSites("foo"));
			;
		};
	}
}
