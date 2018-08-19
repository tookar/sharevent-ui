package de.tolina.shareventui;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;

import com.vaadin.event.MouseEvents;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.StreamResource;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.Image;

@SpringView(name = ImageView.VIEWNAME)
public class ImageView extends AbsoluteLayout implements View {

	protected static final String VIEWNAME = "image";

	private Navigator navigator;

	@Override
	public void enter(ViewChangeListener.ViewChangeEvent event) {
		setSizeFull();
		int pageNumber = Integer.parseInt(event.getParameters());
		final String filename = "screens/" + pageNumber + ".png";
		Image image = new Image(null, new StreamResource((StreamResource.StreamSource) () -> {
			try {
				return new ClassPathResource(filename).getInputStream();
			} catch (IOException e) {
				return null;
			}
		}, filename));

		addComponent(image);
		image.setSizeFull();
		image.addClickListener((MouseEvents.ClickListener) clickEvent -> {
			if (pageNumber == 1) {
				navigator.navigateTo(AnfrageView.VIEWNAME);
			} else {
				navigator.navigateTo(VIEWNAME + "/" + (pageNumber + 1));
			}
		});
	}

	@Autowired
	public void setNavigator(Navigator navigator) {
		this.navigator = navigator;
	}
}
