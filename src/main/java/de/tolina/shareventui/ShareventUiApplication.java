package de.tolina.shareventui;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = { //
		"de.tolina.shareventui", //
		"de.tolina.sharevent.client", //
})
public class ShareventUiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShareventUiApplication.class, args);
	}

	//	@Bean
	//	@Autowired
	//	public CommandLineRunner doIt(AnfrageClient anfrageClient) {
	//		return args -> {
	//			List<StopLocation> stopLocations = anfrageClient.lookupHaltestellen("spandau");
	//			for (StopLocation stopLocation : stopLocations) {
	//				System.err.println(stopLocation.getName());
	//				System.err.println(stopLocation.getExtId());
	//			}
	//		};
	//	}

}