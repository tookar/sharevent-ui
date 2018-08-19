package de.tolina.shareventui;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import de.tolina.sharevent.client.AnfrageClient;

@SpringView(name = AnfrageView.VIEWNAME)
public class AnfrageView extends VerticalLayout implements View {

	protected static final String VIEWNAME = "anfrage";

	private AnfrageClient anfrageClient;

	@Autowired
	public void setAnfrageClient(AnfrageClient anfrageClient) {
		this.anfrageClient = anfrageClient;
	}

	@Override
	public void enter(ViewChangeListener.ViewChangeEvent event) {
		setSizeFull();
		addComponent(new TextField("Start"));
		addComponent(new TextField("Ziel"));
		addComponent(new TextField("Freitext"));
	}
}
