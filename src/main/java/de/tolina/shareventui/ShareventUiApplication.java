package de.tolina.shareventui;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = { //
		"de.tolina.shareventui", //
})
public class ShareventUiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShareventUiApplication.class, args);
	}
}
