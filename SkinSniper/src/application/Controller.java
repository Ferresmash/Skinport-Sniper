
package application;

import java.awt.Desktop;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import skinportApp.APITest;
import skinportApp.CalculatedSkin;
import skinportApp.SkinHandler;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;

public class Controller implements Initializable {

	@FXML
	private ListView<String> myListView;

	@FXML
	private Label myLabel;

	String skin;

	@FXML
	private TextField maxLabel;
	@FXML
	private TextField minLabel;
	@FXML
	private TextField updatedAtField;

	@FXML
	private TextField volumeField;
	
	@FXML
	private RadioButton unrelRadButt;
	
	@FXML
	private Button priceEnter;

	private String currentHTML;

	// grid

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
	private Button skinPortLinkButton;

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

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		

		

		SkinHandler skinHandler = new SkinHandler(APITest.callAPI("https://api.skinport.com/v1/sales/history"),
				APITest.callAPI("https://api.skinport.com/v1/items"));
		List<String> marketNamesList = new ArrayList<String>();
		for (CalculatedSkin skin : skinHandler.getFilteredSkins()) {
			marketNamesList.add(skin.getMarket_hash_name());
		}
		
		unrelRadButt.setSelected(true);
		myListView.getItems().addAll(marketNamesList);

		// Update the skinlist
		EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {

				skinHandler.setSettings(Double.valueOf(minLabel.getText()), Double.valueOf(maxLabel.getText()),
						Integer.valueOf(updatedAtField.getText()),Integer.valueOf(volumeField.getText()),unrelRadButt.isSelected());
				skinHandler.filterList();
				List<String> newMarketNamesList = new ArrayList<String>();
				if (skinHandler.getFilteredSkins().size() != 0) {
					for (CalculatedSkin skin : skinHandler.getFilteredSkins()) {
						newMarketNamesList.add(skin.getMarket_hash_name());
					}
				}
				myListView.getItems().clear();
				myListView.getItems().addAll(newMarketNamesList);
			}
		};

		EventHandler<ActionEvent> goListingWebsite = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {

				int index = myListView.getSelectionModel().getSelectedIndex();
				if (index != -1) {
					currentHTML = skinHandler.getFilteredSkins().get(index).getItem_page();
					skinHandler.getFilteredSkins().get(index).getItem_page().substring(26);

				}

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
		};

		priceEnter.setOnAction(event);
		skinPortLinkButton.setOnAction(goListingWebsite);



		// For selecting an item. Get ItemColorCode and change the grids text.
		myListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
				int selectedIndex = myListView.getSelectionModel().getSelectedIndex();
				// Ensure the selected index is valid
				if (selectedIndex >= 0) {
					skin = myListView.getSelectionModel().getSelectedItem();
					myLabel.setText(skin);
					CalculatedSkin skin = skinHandler.getFilteredSkins().get(selectedIndex);
					String colorCode = skin.getColorCode();

					// Update background color based on the color code
					updateBackgroundColor(twentyFour, colorCode.charAt(0));
					updateBackgroundColor(sevenDays, colorCode.charAt(1));
					updateBackgroundColor(thirtyDays, colorCode.charAt(2));
					updateBackgroundColor(ninetyDays, colorCode.charAt(3));
					
					if (skin.getPrice24Hours() != null) {
						double skin24HourPrice = skin.getPrice24Hours();
						twntyFrHourPrice.setText(Double.toString(skin24HourPrice));
						twntyFrHourEarn.setText(Double.toString(skin24HourPrice - skin.getMin_price()));
						twntyFrHourPercent.setText(
								Double.toString((skin24HourPrice - skin.getMin_price()) / skin.getMin_price()));
					} else {
						twntyFrHourPrice.setText("-");
						twntyFrHourEarn.setText("-");
						twntyFrHourPercent.setText("-");
					}

					if (skin.getPrice7Days() != null) {
						double skin7DaysPrice = skin.getPrice7Days();
						sevDaysPrice.setText(Double.toString(skin7DaysPrice));
						sevDaysEarn.setText(Double.toString(skin7DaysPrice - skin.getMin_price()));
						sevDaysPercent
								.setText(Double.toString((skin7DaysPrice - skin.getMin_price()) / skin.getMin_price()));
					} else {
						sevDaysPrice.setText("-");
						sevDaysEarn.setText("-");
						sevDaysPercent.setText("-");
					}

					if (skin.getPrice30Days() != null) {
						double skin30DaysPrice = skin.getPrice30Days();
						thirtyDaysPrice.setText(Double.toString(skin30DaysPrice));
						thirtyDaysEarn.setText(Double.toString(skin30DaysPrice - skin.getMin_price()));
						thirtyDaysPercent.setText(
								Double.toString((skin30DaysPrice - skin.getMin_price()) / skin.getMin_price()));
					} else {
						thirtyDaysPrice.setText("-");
						thirtyDaysEarn.setText("-");
						thirtyDaysPercent.setText("-");
					}

					if (skin.getPrice90Days() != null) {
						double skin90DaysPrice = skin.getPrice90Days();
						ninetyDaysPrice.setText(Double.toString(skin90DaysPrice));
						ninetyDaysEarn.setText(Double.toString(skin90DaysPrice - skin.getMin_price()));
						ninetyDaysPercent.setText(
								Double.toString((skin90DaysPrice - skin.getMin_price()) / skin.getMin_price()));
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

		currentHTML = "";
	}

}
