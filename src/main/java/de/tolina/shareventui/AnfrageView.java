package de.tolina.shareventui;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.addons.autocomplete.AutocompleteExtension;

import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Button;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import de.tolina.sharevent.api.hacon.location.name.StopLocation;
import de.tolina.sharevent.api.hacon.route.Trip;
import de.tolina.sharevent.client.AnfrageClient;

@SpringView(name = AnfrageView.VIEWNAME)
public class AnfrageView extends VerticalLayout implements View {

	protected static final String VIEWNAME = "anfrage";

	private transient AnfrageClient anfrageClient;

	private Navigator navigator;

	@Autowired
	public void setNavigator(Navigator navigator) {
		this.navigator = navigator;
	}

	@Autowired
	public void setAnfrageClient(AnfrageClient anfrageClient) {
		this.anfrageClient = anfrageClient;
	}

	@Override
	public void enter(ViewChangeListener.ViewChangeEvent event) {
		setSizeFull();

		TextField start = new TextField("Start");
		AutocompleteExtension<String> autocompleteExtensionStart = new AutocompleteExtension<>(start);
		autocompleteExtensionStart.setSuggestionGenerator((query, limit) -> {
			return returnSuggestionList(query);
		});
		autocompleteExtensionStart.addSuggestionSelectListener(entry -> start.setValue(entry.getSelectedValue()));
		addComponent(start);

		TextField ziel = new TextField("Ziel");
		AutocompleteExtension<String> autocompleteExtensionZiel = new AutocompleteExtension<>(ziel);
		autocompleteExtensionZiel.setSuggestionGenerator((query, limit) -> {
			return returnSuggestionList(query);
		});
		autocompleteExtensionZiel.addSuggestionSelectListener(entry -> ziel.setValue(entry.getSelectedValue()));
		addComponent(ziel);

		TextField freitext = new TextField("Freitext");
		addComponent(freitext);

		Button button = new Button("Suchen");
		addComponent(button);

		button.addClickListener(clickEvent -> {
			List<StopLocation> startHaltestellen = getHaltestellen(start.getValue());
			List<StopLocation> endHaltestellen = getHaltestellen(ziel.getValue());

			String startExtId = getStartExtId(startHaltestellen);
			String zielExtId = getEndExtId(endHaltestellen);

			Date now = new Date();
			String nowDateAsString = new SimpleDateFormat("yyyy-MM-dd").format(now);
			String nowTimeAsString = new SimpleDateFormat("hh:mm").format(now);
			List<Trip> trips = anfrageClient.lookupTrips(startExtId, zielExtId, nowDateAsString, nowTimeAsString);

			getSession().setAttribute("trip", trips.get(0));
			navigator.navigateTo(MapView.VIEWNAME);
		});
	}

	private List<String> returnSuggestionList(String query) {
		if (query != null) {
			List<StopLocation> haltestellen = getHaltestellen(query);
			return haltestellen.stream().limit(10).map(StopLocation::getName).collect(Collectors.toList());
		} else {
			return Collections.emptyList();
		}
	}

	private String getEndExtId(List<StopLocation> endHaltestellen) {
		String zielExtId = "";
		if (!endHaltestellen.isEmpty()) {
			zielExtId = endHaltestellen.get(0).getExtId();
		}
		return zielExtId;
	}

	private String getStartExtId(List<StopLocation> startHaltestellen) {
		String startExtId = "";

		if (!startHaltestellen.isEmpty()) {
			startExtId = startHaltestellen.get(0).getExtId();
		}
		return startExtId;
	}


	private List<StopLocation> getHaltestellen(String input) {
		if (input == null || "".equals(input))
			input = "spandau";
		return anfrageClient.lookupHaltestellen(input);
	}
}
