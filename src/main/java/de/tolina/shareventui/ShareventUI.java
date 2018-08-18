/*
 * (c) tolina GmbH, 2016
 */

package de.tolina.shareventui;

import static com.vaadin.shared.ui.ui.Transport.WEBSOCKET_XHR;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import com.vaadin.annotations.PreserveOnRefresh;
import com.vaadin.annotations.Push;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Widgetset;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.UI;

/**
 * This UI is the application entry point. A UI may either represent a browser window
 * (or tab) or some part of a html page where a Vaadin application is embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be
 * overridden to add component to the user interface and initialize non-component functionality.
 */
@SpringUI
@PreserveOnRefresh
@Push(transport = WEBSOCKET_XHR)
@Theme("sharevent")
@Widgetset("de.tolina.shareventui.WidgetSet")
public class ShareventUI extends UI {
	private final transient Logger log = LoggerFactory.getLogger(ShareventUI.class);

	private transient ApplicationContext applicationContext;

	@Override
	protected void init(VaadinRequest request) {
		getPage().setTitle("Sharevent");
		showMainScreen();
	}


	@Override
	public void attach() {
		super.attach();
	}

	@Override
	public void detach() {
		super.detach();
	}

	private void showMainScreen() {
		setContent(applicationContext.getBean(MainScreen.class));
	}
}
