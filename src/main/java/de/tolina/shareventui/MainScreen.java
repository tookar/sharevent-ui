/*
 * (c) tolina GmbH, 2018
 */
package de.tolina.shareventui;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.shared.Registration;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.spring.navigator.SpringNavigator;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.UI;


@SuppressWarnings("javadoc")
@UIScope
@SpringComponent
public class MainScreen extends CustomComponent {
	private final transient Logger log = LoggerFactory.getLogger(MainScreen.class);

	private final SpringNavigator navigator;

	private List<ViewChangeListener> viewChangeListeners = new ArrayList<>();
	private List<Registration> listenerRegistrations = new ArrayList<>();

	@Autowired
	public MainScreen(SpringNavigator navigator) {
		this.navigator = navigator;
		CssLayout layout = new CssLayout();
		layout.setSizeFull();
		setCompositionRoot(layout);
		setSizeFull();

		CssLayout viewContainer = new CssLayout();
		viewContainer.setId("sharevent-container");
		viewContainer.setWidth(360, Unit.PIXELS);
		viewContainer.setHeight(619, Unit.PIXELS);

		layout.addComponent(viewContainer);

		navigator.init(UI.getCurrent(), viewContainer);
		navigator.navigateTo(AnfrageView.VIEWNAME);
	}

	@Override
	public void attach() {
		super.attach();
		viewChangeListeners.forEach(listener -> listenerRegistrations.add(navigator.addViewChangeListener(listener)));
	}

	@Override
	public void detach() {
		listenerRegistrations.forEach(Registration::remove);
		super.detach();
	}

}
