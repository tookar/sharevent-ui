package de.tolina.shareventui;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;

import com.vaadin.event.MouseEvents;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.StreamResource;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Image;
import com.vaadin.ui.VerticalLayout;

import de.tolina.sharevent.api.hacon.route.Trip;
import de.tolina.sharevent.client.AnfrageClient;

@SpringView(name = MapView.VIEWNAME)
public class MapView extends VerticalLayout implements View {

	protected static final String VIEWNAME = "map";

	private Navigator navigator;
	private AnfrageClient anfrageClient;

	@Override
	public void enter(ViewChangeListener.ViewChangeEvent event) {
		setSizeFull();
		Image header = new Image(null, new StreamResource((StreamResource.StreamSource) () -> {
			try {
				return new ClassPathResource("screens/bar_top_android.png").getInputStream();
			} catch (IOException e) {
				return null;
			}
		}, "screens/header.png"));

		Trip trip = (Trip) getSession().getAttribute("trip");
		anfrageClient.getMapImage(trip);

		Image mapImage = new Image(null,
				new StreamResource((StreamResource.StreamSource) () -> new ByteArrayInputStream(anfrageClient.getMapImage(trip)), "map" + UUID.randomUUID().toString() + ".png"));

		Image footer = new Image(null, new StreamResource((StreamResource.StreamSource) () -> {
			try {
				return new ClassPathResource("screens/bar_bottom_search.png").getInputStream();
			} catch (IOException e) {
				return null;
			}
		}, "screens/footer.png"));

		addComponent(header);
		addComponent(mapImage);
		addComponent(footer);
		MouseEvents.ClickListener clickListener = e -> navigator.navigateTo(ImageView.VIEWNAME + "/2");
		header.addClickListener(clickListener);
		mapImage.addClickListener(clickListener);
		footer.addClickListener(clickListener);
	}

	@Autowired
	public void setNavigator(Navigator navigator) {
		this.navigator = navigator;
	}

	@Autowired
	public void setAnfrageClient(AnfrageClient anfrageClient) {
		this.anfrageClient = anfrageClient;
	}
}
