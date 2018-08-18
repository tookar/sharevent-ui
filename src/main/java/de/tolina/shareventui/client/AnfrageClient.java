package de.tolina.shareventui.client;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import de.tolina.sharevent.api.StringListDto;
import de.tolina.sharevent.route.Routes;

@Component
public class AnfrageClient {

	private RestTemplateBuilder restTemplateBuilder;
	private String backendUrl;

	@Autowired
	public AnfrageClient(RestTemplateBuilder restTemplateBuilder, @Value("${backend}") String backendUrl) {
		this.restTemplateBuilder = restTemplateBuilder;
		this.backendUrl = backendUrl;
	}

	public StringListDto lookupSites(String input) {
		URI uri = UriComponentsBuilder.fromUriString(backendUrl)
				.path(Routes.ANFRAGE_FIND_ROUTE)
				.queryParam(Routes.PARAM_ORT, input)
				.build()
				.encode()
				.toUri();
		ResponseEntity<StringListDto> entity = restTemplateBuilder.build().getForEntity(uri, StringListDto.class);
		return entity.getBody();
	}


}
