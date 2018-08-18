package de.tolina.shareventui;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

@SpringView(name = AnfrageView.VIEWNAME)
public class AnfrageView extends VerticalLayout implements View {

    protected static final String VIEWNAME = "anfrage";

    @Override
	public void enter(ViewChangeListener.ViewChangeEvent event) {
		setSizeFull();
		addComponent(new TextField("Start"));
		addComponent(new TextField("Ziel"));
		addComponent(new TextField("Freitext"));
	}
}
