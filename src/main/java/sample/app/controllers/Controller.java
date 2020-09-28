package sample.app.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import sample.Main;

import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    //  @FXML
    //private Pane
    @FXML
    private Button home_b, config_b, sett_b;

    //mouse evt
    @FXML
    public void handleButtonClicks(javafx.event.ActionEvent actionEvent) throws Throwable {
        String fxml;

        if (actionEvent.getSource() == config_b) {
            fxml = "/app/scene/config_stage.fxml";
            try {loadStage(fxml, actionEvent);} catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        } else if (actionEvent.getSource() == sett_b) {
            fxml ="/app/scene/sett_stage.fxml";
            try { loadStage(fxml, actionEvent);
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        } else if (actionEvent.getSource() == home_b) {
            fxml ="/app/scene/homeb_stage.fxml";
            try {loadStage(fxml, actionEvent);
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
            //  home_pane.toFront();
        }
    }

    /*WHen method called it'll change the scene*/

    private void loadStage(String fxml, ActionEvent event) throws Throwable, InvocationTargetException {

        // System.out.println(fxml);
        System.out.println(getClass());
        System.out.println(getClass().getResource(fxml));
        AnchorPane newscene = (AnchorPane) FXMLLoader.load(Main.class.getResource(fxml));
        Scene scene = new Scene(newscene);

        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();


    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


    }


    public void menumouseEnter(javafx.scene.input.MouseEvent mouseEvent) {
        ((Node)mouseEvent.getSource()).setStyle("-fx-background-color: #6aa0a6; ");
    }

    public void menumouseExit(javafx.scene.input.MouseEvent mouseEvent) {
        ((Node)mouseEvent.getSource()).setStyle("-fx-background-color: #2F575D; ");
    }


    public void menumouseClick(MouseEvent mouseEvent) {
        ((Node)mouseEvent.getSource()).setStyle("-fx-text-fill: #000000; ");
    }

    public void menumousePressed(MouseEvent mouseEvent) {
        ((Node)mouseEvent.getSource()).setStyle("-fx-background-color: #ceffff; ");
        ((Node)mouseEvent.getSource()).setStyle("-fx-text-fill: #000000; ");
    }

    public void menumouseReleased(MouseEvent mouseEvent) {
        ((Node)mouseEvent.getSource()).setStyle("-fx-background-color: #71ff37; ");
        ((Node)mouseEvent.getSource()).setStyle("-fx-text-fill: #ffffff; ");
    }
}