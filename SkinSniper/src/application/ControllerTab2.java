package application;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.AudioClip;
import skinportApp.CalculatedSkin;
import skinportApp.SkinBot;
import filters.*;

public class ControllerTab2 implements Initializable {

	@FXML
	private ListView<String> savedSearches;

	@FXML
	private ListView<String> savedListView;

	@FXML
	private Button skinPortLinkButton;

	@FXML
	private Label topLabel;

	private String currentHTML;

	@FXML
	private Button createNewButton;

	@FXML
	private Button deleteSelectedButton;

	@FXML
	private Button editSelectedButton;

	@FXML
	private AnchorPane darkendBackground;

	// botPanel

	@FXML
	private Button startBotButton;

	@FXML
	private Button resetBotButton;

	@FXML
	private Button pauseBotButton;

	@FXML
	private TextField updateEveryMinField;

	@FXML
	private RadioButton playSoundRadioButton;

	private AudioClip notificationSound;

	@FXML
	private RadioButton notifyDiscordRadioButton;

	private boolean isBotRunning = false;
	private ScheduledExecutorService scheduler;
	private SkinBot sB;
	private FilterHandler fH;
	


	// new panel

	@FXML
	private AnchorPane createNewPanel;

	@FXML
	private Button saveNewSearchButton;

	@FXML
	private TextField saveSearchName;

	@FXML
	private TextField saveSearchMax;

	@FXML
	private TextField saveSearchMin;

	@FXML
	private TextField saveSearchCheckLast;

	@FXML
	private TextField saveSearchVolume;

	@FXML
	private RadioButton hideUnreliableSkins;

	@FXML
	private RadioButton saveAlsoYellow;
	// Grid

	@FXML
	private Button twentyFour;
	@FXML
	private Button sevenDays;
	@FXML
	private Button thirtyDays;
	@FXML
	private Button ninetyDays;
	@FXML
	private Button current;
	@FXML
	private Label twntyFrHourPrice;
	@FXML
	private Label twntyFrHourEarn;
	@FXML
	private Label twntyFrHourPercent;
	@FXML
	private Label sevDaysPrice;
	@FXML
	private Label sevDaysEarn;
	@FXML
	private Label sevDaysPercent;
	@FXML
	private Label thirtyDaysPrice;
	@FXML
	private Label thirtyDaysEarn;
	@FXML
	private Label thirtyDaysPercent;
	@FXML
	private Label ninetyDaysPrice;
	@FXML
	private Label ninetyDaysEarn;
	@FXML
	private Label ninetyDaysPercent;
	@FXML
	private Label currPrice;

	private FilterSettings selectedSearch;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		fH = new FilterHandler();
		updateSavedSearches(fH);

		sB = new SkinBot(fH);
		scheduler = Executors.newScheduledThreadPool(1);
		notificationSound = new AudioClip(getClass().getResource("/notifySound.mp3").toString());

		EventHandler<ActionEvent> closeSearchPanel = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {

				createNewPanel.setVisible(false);
				darkendBackground.setVisible(false);
				String saveSearchNameValue = saveSearchName.getText().isBlank() ? "Unnamed Search"
						: saveSearchName.getText();
				Double saveSearchMaxValue = saveSearchMax.getText().isBlank() ? Double.MAX_VALUE
						: Double.valueOf(saveSearchMax.getText());
				Double saveSearchMinValue = saveSearchMin.getText().isBlank() ? 0.0
						: Double.valueOf(saveSearchMin.getText());
				int saveSearchCheckLastValue = saveSearchCheckLast.getText().isBlank() ? 24
						: Integer.valueOf(saveSearchCheckLast.getText());
				int saveSearchVolumeValue = saveSearchVolume.getText().isBlank() ? 0
						: Integer.valueOf(saveSearchVolume.getText());
				fH.addFilter(new FilterSettings(saveSearchNameValue, saveSearchMaxValue, saveSearchMinValue,
						saveSearchCheckLastValue, saveSearchVolumeValue, hideUnreliableSkins.isSelected(),
						saveAlsoYellow.isSelected()));
				updateSavedSearches(fH);
				saveSearchName.setText("");
				saveSearchMax.setText("");
				saveSearchMin.setText("");
				saveSearchCheckLast.setText("");
				saveSearchVolume.setText("");
				hideUnreliableSkins.setSelected(false);
				saveAlsoYellow.setSelected(false);
				System.out.println(fH.getFilters().size());
			}
		};

		EventHandler<ActionEvent> closeSearchPanelEdited = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {

				String saveSearchNameValue = saveSearchName.getText().isBlank() ? "Unnamed Search"
						: saveSearchName.getText();
				Double saveSearchMaxValue = saveSearchMax.getText().isBlank() ? Double.MAX_VALUE
						: Double.valueOf(saveSearchMax.getText());
				Double saveSearchMinValue = saveSearchMin.getText().isBlank() ? 0.0
						: Double.valueOf(saveSearchMin.getText());
				int saveSearchCheckLastValue = saveSearchCheckLast.getText().isBlank() ? 24
						: Integer.valueOf(saveSearchCheckLast.getText());
				int saveSearchVolumeValue = saveSearchVolume.getText().isBlank() ? 0
						: Integer.valueOf(saveSearchVolume.getText());
				fH.updateFilter(selectedSearch.getTag(),
						new FilterSettings(saveSearchNameValue, saveSearchMaxValue, saveSearchMinValue,
								saveSearchCheckLastValue, saveSearchVolumeValue, hideUnreliableSkins.isSelected(),
								saveAlsoYellow.isSelected()));
				updateSavedSearches(fH);
				saveSearchName.setText("");
				saveSearchMax.setText("");
				saveSearchMin.setText("");
				saveSearchCheckLast.setText("");
				saveSearchVolume.setText("");
				hideUnreliableSkins.setSelected(false);
				saveAlsoYellow.setSelected(false);
				// hide the panels
				createNewPanel.setVisible(false);
				darkendBackground.setVisible(false);
				System.out.println(fH.getFilters().size());
			}
		};

		EventHandler<ActionEvent> openSearchPanel = new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				createNewPanel.setVisible(true);
				darkendBackground.setVisible(true);
				saveNewSearchButton.setOnAction(closeSearchPanel);

				saveNewSearchButton.setText("Save Search");
				System.out.println(fH.getFilters().size());
			}
		};

		EventHandler<ActionEvent> deleteSelectedSearch = new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				int index = savedSearches.getSelectionModel().getSelectedIndex();
				if (index != -1) {
					fH.deleteFilter(fH.getFilters().get(index).getTag());
				}
				updateSavedSearches(fH);
				System.out.println(fH.getFilters().size());

			}
		};
		EventHandler<ActionEvent> editSelectedSearch = new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {

				int index = savedSearches.getSelectionModel().getSelectedIndex();
				if (index != -1) {
					FilterSettings filter = fH.getFilters().get(index);
					createNewPanel.setVisible(true);
					darkendBackground.setVisible(true);
					saveNewSearchButton.setOnAction(closeSearchPanelEdited);

					saveSearchName.setText(filter.getName());
					saveSearchMax.setText(filter.getMax().toString());
					saveSearchMin.setText(filter.getMin().toString());
					saveSearchCheckLast.setText("" + filter.getCheckLast());
					saveSearchVolume.setText("" + filter.getVolume());
					hideUnreliableSkins.setSelected(filter.isHideUnrelSkins());
					saveAlsoYellow.setSelected(filter.isSaveYellow());

					saveNewSearchButton.setText("Save Edited Search");

					selectedSearch = filter;
				}
				System.out.println(fH.getFilters().size());
			}
		};

		createNewButton.setOnAction(openSearchPanel);
		deleteSelectedButton.setOnAction(deleteSelectedSearch);
		editSelectedButton.setOnAction(editSelectedSearch);

		EventHandler<ActionEvent> goListingWebsite = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {

				int index = savedListView.getSelectionModel().getSelectedIndex();
				if (index != -1) {
					currentHTML = sB.getLatestSavedSkins().get(index).getItem_page();
//					sB.getLatestSavedSkins().get(index).getItem_page().substring(26);
					if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
						try {
							if (!currentHTML.equals("")) {
								Desktop.getDesktop().browse(new URI(currentHTML));
							}
						} catch (IOException e1) {
							e1.printStackTrace();
						} catch (URISyntaxException e1) {
							e1.printStackTrace();
						}
					}
				}
			}
		};
		skinPortLinkButton.setOnAction(goListingWebsite);

		EventHandler<ActionEvent> startBotEvent = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				if (!isBotRunning) {
					isBotRunning = true;
					handleBotStart();
				}
			}
		};

		// Set up the event handler for pausing the bot
		EventHandler<ActionEvent> pauseBotEvent = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				if (isBotRunning) {
					pauseBot();
				}
			}
		};

		EventHandler<ActionEvent> resetBotEvent = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				resetBot();
			}
		};
		startBotButton.setOnAction(startBotEvent);
		pauseBotButton.setOnAction(pauseBotEvent);
		resetBotButton.setOnAction(resetBotEvent);

		// For selecting an item. Get ItemColorCode and change the grids text.
		savedListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
				int selectedIndex = savedListView.getSelectionModel().getSelectedIndex();
				// Ensure the selected index is valid
				if (selectedIndex >= 0) {
					String skinName = "";
					skinName = savedListView.getSelectionModel().getSelectedItem();
					topLabel.setText(skinName);
					CalculatedSkin skin = sB.getLatestSavedSkins().get(selectedIndex);
					String colorCode = skin.getColorCode();

					// Update background color based on the color code
					updateBackgroundColor(twentyFour, colorCode.charAt(0));
					updateBackgroundColor(sevenDays, colorCode.charAt(1));
					updateBackgroundColor(thirtyDays, colorCode.charAt(2));
					updateBackgroundColor(ninetyDays, colorCode.charAt(3));

					if (skin.getPrice24Hours() != null) {
						double skin24HourPrice = skin.getPrice24Hours();
						twntyFrHourPrice.setText(Double.toString(skin24HourPrice));
						twntyFrHourEarn.setText(Double.toString((skin24HourPrice * 0.88) - skin.getMin_price()));
						twntyFrHourPercent.setText(
								Double.toString((skin24HourPrice * 0.88 - skin.getMin_price()) / skin.getMin_price()));
					} else {
						twntyFrHourPrice.setText("-");
						twntyFrHourEarn.setText("-");
						twntyFrHourPercent.setText("-");
					}

					if (skin.getPrice7Days() != null) {
						double skin7DaysPrice = skin.getPrice7Days();
						sevDaysPrice.setText(Double.toString(skin7DaysPrice));
						sevDaysEarn.setText(Double.toString((skin7DaysPrice * 0.88) - skin.getMin_price()));
						sevDaysPercent.setText(
								Double.toString((skin7DaysPrice * 0.88 - skin.getMin_price()) / skin.getMin_price()));
					} else {
						sevDaysPrice.setText("-");
						sevDaysEarn.setText("-");
						sevDaysPercent.setText("-");
					}

					if (skin.getPrice30Days() != null) {
						double skin30DaysPrice = skin.getPrice30Days();
						thirtyDaysPrice.setText(Double.toString(skin30DaysPrice));
						thirtyDaysEarn.setText(Double.toString((skin30DaysPrice * 0.88) - skin.getMin_price()));
						thirtyDaysPercent.setText(
								Double.toString((skin30DaysPrice * 0.88 - skin.getMin_price()) / skin.getMin_price()));
					} else {
						thirtyDaysPrice.setText("-");
						thirtyDaysEarn.setText("-");
						thirtyDaysPercent.setText("-");
					}

					if (skin.getPrice90Days() != null) {
						double skin90DaysPrice = skin.getPrice90Days();
						ninetyDaysPrice.setText(Double.toString(skin90DaysPrice));
						ninetyDaysEarn.setText(Double.toString((skin90DaysPrice * 0.88) - skin.getMin_price()));
						ninetyDaysPercent.setText(
								Double.toString((skin90DaysPrice * 0.88 - skin.getMin_price()) / skin.getMin_price()));
					} else {
						ninetyDaysPrice.setText("-");
						ninetyDaysEarn.setText("-");
						ninetyDaysPercent.setText("-");
					}
					if (skin.getMin_price() != null) {
						currPrice.setText("" + skin.getMin_price());
					} else {
						currPrice.setText("-");
					}
				}
			}

			private void updateBackgroundColor(Button button, char code) {
				String color = switch (code) {
				case 'G' -> "#00802b"; // Green
				case 'Y' -> "#ffcc00"; // Yellow
				case 'R' -> "#ff0000"; // Red
				default -> "#666666"; // Default gray
				};
				button.setStyle("-fx-background-color: " + color + ";");
			}
		});

	}

	private void updateSavedSearches(FilterHandler fH) {
		List<String> newSavedSearches = new ArrayList<String>();
		for (FilterSettings filter : fH.getFilters()) {
			newSavedSearches.add(filter.getName());
		}
		savedSearches.getItems().clear();
		if (newSavedSearches != null && newSavedSearches.size() != 0) {
			savedSearches.getItems().addAll(newSavedSearches);
		}
	}

	private void handleBotStart() {

		if (scheduler == null || scheduler.isShutdown()) {
			scheduler = Executors.newScheduledThreadPool(1);
		}

		String inputText = updateEveryMinField.getText().trim();

		int intervalInMinutes = 5;
		if (!inputText.isEmpty()) {
			try {
				intervalInMinutes = Integer.parseInt(inputText);
			} catch (NumberFormatException e) {
				System.out.println("Invalid input: Please provide a valid number.");
				return;
			}
		}

		checkForDeals();

		scheduler.scheduleAtFixedRate(() -> {
			Platform.runLater(() -> checkForDeals());
		}, intervalInMinutes, intervalInMinutes, TimeUnit.MINUTES);

		System.out.println("Bot started and will check for deals every " + intervalInMinutes + " minute(s).");

	}

	private void checkForDeals() {
		
		// Your logic to perform the bot's deal checking
		System.out.println("Checking for deals...");
		sB.checkForDeals(); // Assuming this checks for deals
		updateSkinList();
	}

	private void pauseBot() {
		System.out.println("Pausing the bot...");
		if (scheduler != null && !scheduler.isShutdown()) {
			scheduler.shutdown();
			isBotRunning = false;
			System.out.println("Bot paused.");
			
		}
	}

	private void resetBot() {
		sB = new SkinBot(fH);
		pauseBot(); // Pause any ongoing tasks
		isBotRunning = false;
		updateSkinList();
		System.out.println("Bot reset.");
		
	}

	private void updateSkinList() {
		savedListView.getItems().clear();
		List<String> savedSkins = new ArrayList<String>();
		List<String> savedSkinsLinks = new ArrayList<String>();
		if (sB.getLatestSavedSkins() != null && !sB.getLatestSavedSkins().isEmpty()) {
			for (CalculatedSkin skin : sB.getLatestSavedSkins()) {
				savedSkins.add(skin.getMarket_hash_name());
				if(!sB.getAllNotifiedSkins().contains(skin.getMarket_hash_name())) {
					savedSkinsLinks.add(skin.getItem_page() + " \r\n");
					sB.setAllNotifiedSkins(sB.getAllNotifiedSkins() + skin.getMarket_hash_name());
				}
			}
			if (savedSkins != null && !savedSkins.isEmpty()) {
				savedListView.getItems().addAll(savedSkins);

				if (playSoundRadioButton.isSelected() && sB.isNewSkins()) {
					notificationSound.play();
				}
				if (notifyDiscordRadioButton.isSelected() && !savedSkinsLinks.isEmpty()) {
					sB.sendNotification(savedSkinsLinks.toString());
				}
			}
		}
	}
}
