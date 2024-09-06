//package application;
//
//import java.net.URL;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.ResourceBundle;
//
//import javafx.fxml.FXML;
//import javafx.fxml.Initializable;
//import javafx.scene.control.Label;
//import javafx.scene.control.ListView;
//import skinportApp.APITest;
//import skinportApp.Skin2;
//import skinportApp.SkinHandler;
//
//public class Controller implements Initializable{
//
//	@FXML
//	private ListView<String> skinList;
//	
//	@FXML
//	private Label marketName;
//	
//	private String[] randomArray = {"Hi","JI"};
//
//	@Override
//	public void initialize(URL arg0, ResourceBundle arg1) {
//		
////		SkinHandler skinHandler = new SkinHandler(APITest.callAPI("https://api.skinport.com/v1/sales/history"),APITest.callAPI("https://api.skinport.com/v1/items"));
////		
////		List<String> marketNameList = new ArrayList<String>();
////		
////		for(Skin2 skin : skinHandler.getListedSkins()) {
////			marketNameList.add(skin.getMarket_hash_name());
////		}
//		
//		skinList.getItems().addAll(randomArray);
//		
//	}
//	
//
//	
//}
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
import javafx.scene.control.TextField;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
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

	String currentFood;

	@FXML
	private TextField maxLabel;
	@FXML
	private TextField minLabel;
	@FXML
	private TextField updatedAtField;

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

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		SkinHandler skinHandler = new SkinHandler(APITest.callAPI("https://api.skinport.com/v1/sales/history"),
				APITest.callAPI("https://api.skinport.com/v1/items"));
		List<String> marketNamesList = new ArrayList<String>();
		for (CalculatedSkin skin : skinHandler.getListedSkins()) {
			marketNamesList.add(skin.getMarket_hash_name());
		}

		// Update the skinlist
		EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				System.out.println("Button is pressed");
				for (CalculatedSkin skin : skinHandler.getListedSkins()) {
					skin.setSettings(Double.valueOf(minLabel.getText()), Double.valueOf(maxLabel.getText()),
							Integer.valueOf(updatedAtField.getText()));

				}
				skinHandler.sortList();
				List<String> newMarketNamesList = new ArrayList<String>();
				for (CalculatedSkin skin : skinHandler.getListedSkins()) {
					newMarketNamesList.add(skin.getMarket_hash_name());
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
					currentHTML = skinHandler.getListedSkins().get(index).getItem_page();
					skinHandler.getListedSkins().get(index).getItem_page().substring(26);

				}

				if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
					try {
						if(!currentHTML.equals("")) {
							Desktop.getDesktop().browse(new URI(currentHTML));
						}
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (URISyntaxException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		};

		priceEnter.setOnAction(event);
		skinPortLinkButton.setOnAction(goListingWebsite);

		myListView.getItems().addAll(marketNamesList);

		
		//For selecting an item. Get ItemColorCode and change the grids text.
		myListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
		    @Override
		    public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
		        int selectedIndex = myListView.getSelectionModel().getSelectedIndex();
		        
		        // Ensure the selected index is valid
		        if (selectedIndex >= 0) {
		            currentFood = myListView.getSelectionModel().getSelectedItem();
		            myLabel.setText(currentFood);
		            CalculatedSkin skin = skinHandler.getListedSkins().get(selectedIndex);		            
		            String colorCode = skin.getColorCode();

		            // Update background color based on the color code
		            updateBackgroundColor(twentyFour, colorCode.charAt(0));
		            updateBackgroundColor(sevenDays, colorCode.charAt(1));
		            updateBackgroundColor(thirtyDays, colorCode.charAt(2));
		            updateBackgroundColor(ninetyDays, colorCode.charAt(3));
		        }
		    }

		    // Helper method to update the background color based on the code
		    private void updateBackgroundColor(Button button, char code) {
		        String color = switch (code) {
		            case 'G' -> "#00802b"; // Green
		            case 'Y' -> "#ffcc00"; // Yellow
		            case 'R' -> "#ff0000"; // Red
		            default -> "#666666";  // Default gray
		        };
		        button.setStyle("-fx-background-color: " + color + ";");
		    }
		});

		currentHTML = "";
	}

}
