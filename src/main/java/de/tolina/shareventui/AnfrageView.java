package de.tolina.shareventui;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

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

	@Autowired
	public void setAnfrageClient(AnfrageClient anfrageClient) {
		this.anfrageClient = anfrageClient;
	}

	@Override
	public void enter(ViewChangeListener.ViewChangeEvent event) {
		setSizeFull();
		TextField start = new TextField("Start");
		addComponent(start);
		TextField ziel = new TextField("Ziel");
		addComponent(ziel);
		TextField freitext = new TextField("Freitext");
		addComponent(freitext);

		Button button = new Button("Suchen");
		addComponent(button);

		button.addClickListener(clickEvent -> {
			List<StopLocation> startHaltestellen = getStartHaltestellen(start);
			List<StopLocation> endHaltestellen = getEndHaltestellen(ziel);

			String startExtId = getStartExtId(startHaltestellen);
			String zielExtId = getEndExtId(endHaltestellen);

			Date now = new Date();
			String nowDateAsString = new SimpleDateFormat("yyyy-MM-dd").format(now);
			String nowTimeAsString = new SimpleDateFormat("hh:mm").format(now);
			List<Trip> trips = anfrageClient.lookupTrips(startExtId, zielExtId, nowDateAsString, nowTimeAsString);

			if (!trips.isEmpty())
				System.err.println(trips.get(0).getDuration());
			else
				System.err.println("No trip found");
		});
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

	private List<StopLocation> getEndHaltestellen(TextField ziel) {
		String zielString = ziel.getValue();
		if (zielString == null || "".equals(zielString))
			zielString = "zoolog";
		return anfrageClient.lookupHaltestellen(zielString);
	}

	private List<StopLocation> getStartHaltestellen(TextField start) {
		String startString = start.getValue();
		if (startString == null || "".equals(startString))
			startString = "spandau";
		return anfrageClient.lookupHaltestellen(startString);
	}
}
