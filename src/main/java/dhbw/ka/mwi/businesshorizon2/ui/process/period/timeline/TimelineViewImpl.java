package dhbw.ka.mwi.businesshorizon2.ui.process.period.timeline;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.themes.Reindeer;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.NativeButton;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

import dhbw.ka.mwi.businesshorizon2.models.Period.Period;

/**
 * Diese Klasse implementiert das GUI fuer den Prozessschritt "Methoden" in
 * Vaadin.
 * 
 * @author Julius Hacker
 * 
 */
public class TimelineViewImpl extends VerticalLayout implements
		TimelineViewInterface {
	private static final long serialVersionUID = 1L;

	@Autowired
	private TimelinePresenter presenter;

	GridLayout layout = new GridLayout(2, 1);

	NativeButton delPast, delFuture;

	Button past, future;
	
	Panel p = new Panel();

	PeriodButton pB;

	Logger logger = Logger.getLogger(TimelineViewImpl.class);

	/**
	 * Dies ist der Konstruktor, der von Spring nach der Initialierung der
	 * Dependencies aufgerufen wird. Er registriert sich selbst beim Presenter
	 * und initialisiert die View-Komponenten.
	 * 
	 * @author Julius Hacker
	 */
	@PostConstruct
	public void init() {
		presenter.setView(this);
		generateUi();
	}

	/**
	 * Erstelle das GUI zum Prozessschritt "Perioden"
	 * 
	 * @author Julius Hacker
	 */
	private void generateUi() {
		p.setScrollable(true);
		p.setStyleName(Reindeer.PANEL_LIGHT);
		delPast = new NativeButton("X", new Button.ClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				layout.getComponent(0, 0);
				presenter.removeLastPastPeriod(((PeriodButton) layout
						.getComponent(0, 0)).getPeriod());
			}
		});
		delFuture = new NativeButton("X", new Button.ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				presenter.removeLastFuturePeriod(((PeriodButton) layout
						.getComponent(0, layout.getRows() - 1)).getPeriod());
			}
		});

		past = new Button("+", new Button.ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				presenter.addPastPeriod();
			}
		});
		this.p.addComponent(past);
		this.p.addComponent(layout);
		future = new Button("+", new Button.ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				presenter.addFuturePeriod();
			}
		});

		this.p.addComponent(future);
		this.addComponent(p);
	}

	@Override
	public void removeFuturePeriod() {
		logger.debug("" + layout.getRows());
		layout.removeRow(layout.getRows() - 1);
		logger.debug("" + layout.getRows());
		if (((PeriodButton) layout.getComponent(0, layout.getRows() - 1))
				.getCaption().startsWith("Basis")) {

		} else
			layout.addComponent(delFuture, 1, layout.getRows() - 1);
	}

	@Override
	public void removePastPeriod() {
		layout.removeRow(0);
		if (layout.getComponent(0, 0).getCaption().startsWith("Basis")) {

		} else
			layout.addComponent(delPast, 1, 0);
	}

	/*
	 * @Override public void setPeriodValid(int year, boolean isValid) { // TODO
	 * Auto-generated method stub
	 * 
	 * }
	 */
	@Override
	public void setPastButtonAccess(boolean usable) {
		past.setEnabled(usable);

	}

	@Override
	public void setFutureButtonAccess(boolean usable) {
		future.setEnabled(usable);
	}

	@Override
	public void addBasePeriod(Period period) {

		layout.removeComponent(pB);
		pB = new PeriodButton("Basisjahr: " + period.getYear(),
				new Button.ClickListener() {

					@Override
					public void buttonClick(ClickEvent event) {
						// TODO Auto-generated method stub
						presenter.periodClicked(((PeriodButton) event
								.getButton()).getPeriod());

					}
				});
		pB.setPeriod(period);

		layout.addComponent(pB);

	}

	@Override
	public void addFuturePeriod(Period period) {
		PeriodButton pB = new PeriodButton("" + period.getYear(),
				new Button.ClickListener() {

					@Override
					public void buttonClick(ClickEvent event) {
						// TODO Auto-generated method stub
						presenter.periodClicked(((PeriodButton) event
								.getButton()).getPeriod());

					}
				});
		pB.setPeriod(period);
		layout.setRows(layout.getRows() + 1);
		layout.addComponent(pB, 0, layout.getRows() - 1);
		layout.removeComponent(delFuture);
		layout.addComponent(delFuture, 1, layout.getRows() - 1);

	}

	@Override
	public void addPastPeriod(Period period) {
		// TODO Auto-generated method stub
		PeriodButton pB = new PeriodButton("" + period.getYear(),
				new Button.ClickListener() {

					@Override
					public void buttonClick(ClickEvent event) {
						// TODO Auto-generated method stub
						presenter.periodClicked(((PeriodButton) event
								.getButton()).getPeriod());

					}
				});
		pB.setPeriod(period);
		layout.insertRow(0);
		layout.addComponent(pB, 0, 0);
		layout.removeComponent(delPast);
		layout.addComponent(delPast, 1, 0);

	}
}
