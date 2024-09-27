package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import java.io.IOException;

public class MainController {

    @FXML
    private AnchorPane tab1AnchorPane;

    @FXML
    private AnchorPane tab2AnchorPane;

    @FXML
    private TabPane tabPane;

    @FXML
    public void initialize() {
        loadTab1();
        loadTab2();
    }

    private void loadTab1() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Tab1.fxml"));
            AnchorPane tabContent = loader.load();
            tab1AnchorPane.getChildren().setAll(tabContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadTab2() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Tab2.fxml"));
            AnchorPane tabContent = loader.load();
            tab2AnchorPane.getChildren().setAll(tabContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

