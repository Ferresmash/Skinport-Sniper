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
import skinportApp.Skin2;
import skinportApp.SkinHandler;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;

public class Controller implements Initializable{

	@FXML
	private ListView<String> myListView;
	
	@FXML
	private Label myLabel;
	
	String currentFood;
	
	
	//WebPanel
	
	@FXML
	private WebView webView;
	
	private WebEngine engine;
	
	@FXML
	private TextField maxLabel;
	@FXML
	private TextField minLabel;
	@FXML
	private TextField updatedAtField;
	
	@FXML
	private Button priceEnter;
	
	private String currentHTML;
	private String priceEmpireURL = "https://pricempire.com/item/cs2/skin/";
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
			
		SkinHandler skinHandler = new SkinHandler(APITest.callAPI("https://api.skinport.com/v1/sales/history"),APITest.callAPI("https://api.skinport.com/v1/items"));
		List<String> marketNamesList = new ArrayList<String>();
		for (Skin2 skin : skinHandler.getListedSkins()) {
			marketNamesList.add(skin.getMarket_hash_name());
		}
		
		EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() { 
			@Override
			public void handle(ActionEvent e) {
				System.out.println("Button is pressed");
				for (Skin2 skin : skinHandler.getListedSkins()) {
					skin.setSettings(Double.valueOf(minLabel.getText()), Double.valueOf(maxLabel.getText()), Integer.valueOf(updatedAtField.getText()));
					
				}
				skinHandler.sortList();
				List<String> newMarketNamesList = new ArrayList<String>();
				for (Skin2 skin : skinHandler.getListedSkins()) {
					newMarketNamesList.add(skin.getMarket_hash_name());
				}
				myListView.getItems().clear();
				myListView.getItems().addAll(newMarketNamesList);
			}
		};
		
		priceEnter.setOnAction(event);

		myListView.getItems().addAll(marketNamesList);
		
		myListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
				
				System.out.println("changed gets called");
				
				currentFood = myListView.getSelectionModel().getSelectedItem();
				
				myLabel.setText(currentFood);
				
				int index = myListView.getSelectionModel().getSelectedIndex();
				if(index != -1) {
					currentHTML = skinHandler.getListedSkins().get(index).getItem_page();
					priceEmpireURL = "https://pricempire.com/item/cs2/skin/" + skinHandler.getListedSkins().get(index).getItem_page().substring(26);
					loadPage();
				}
				
				
				if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
				    try {
						Desktop.getDesktop().browse(new URI(currentHTML));
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (URISyntaxException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
			}	
		});
		
		currentHTML = "https://pricempire.com/item/cs2/skin/bayonet-doppler";
		
		engine = webView.getEngine();
		loadPage();
		
	}
	public void loadPage(){
		engine.load(priceEmpireURL);
	}
	
}
