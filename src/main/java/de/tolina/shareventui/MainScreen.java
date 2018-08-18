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
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;


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
		VerticalLayout layout = new VerticalLayout();
		layout.setSpacing(false);
		layout.setMargin(false);
		layout.setSizeFull();
		setCompositionRoot(layout);
		setSizeFull();

		HorizontalLayout viewContainer = new HorizontalLayout();
		viewContainer.setSizeFull();
		viewContainer.setMargin(true);
		layout.addComponent(viewContainer);
		layout.setExpandRatio(viewContainer, 1f);

		navigator.init(UI.getCurrent(), viewContainer);
		//
		//		final String viewName = Optional.of(navigator)//
		//				.map(SpringNavigator::getState)//
		//				.orElse(AnfrageView.VIEWNAME);
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
