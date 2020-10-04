package sample.app.controllers;
import java.text.SimpleDateFormat;
import java.util.Date;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import sample.Main;
import sample.TestDBCon;
import sample.app.fun.java.ProtocolEntity;
import sample.app.fun.java.TDBInterface;

import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

public class StartController implements Initializable {
    TestDBCon tdbc;
    ProtocolEntity pe;
   // private TDBInterface pr = new StartController();

  //  @FXML
    //private Pane
   // private CurrentDateTime cdt;

    @FXML
    private Button home_b, config_b, sett_b, refresh_b, seemore_b, start_b;

    @FXML
    private TableView<ProtocolEntity> data_tab = new TableView<>();


    @FXML
    private TableColumn<String, ProtocolEntity> app_name;

    @FXML
    private TableColumn<String, ProtocolEntity> ip_client;

    @FXML
    private TableColumn<String, ProtocolEntity> ip_server;

    @FXML
    private TableColumn<String, ProtocolEntity> prot_name;

    @FXML
    private TableColumn<String, ProtocolEntity> date_d;

    @FXML
    private TableColumn<String, ProtocolEntity> time_d;

    @FXML
    private TableColumn<String, ProtocolEntity> byte_d;

    @FXML
    private PieChart talk_pie = new PieChart();

    @FXML
    private PieChart prot_pie = new PieChart();

    @FXML
    private Text last_scan;

    public void setpiecartdata1(){
        String query = "ipCount";
        String query1 = "SELECT COUNT(prot) FROM ProtocolEntity pe GROUP BY prot LIMIT 10";
        ObservableList <PieChart.Data> prot_pie_data = FXCollections.observableArrayList();
        ObservableList <PieChart.Data> talk_pie_data = FXCollections.observableArrayList();

        prot_pie_data.addAll(new PieChart.Data("IPVX",100));
        prot_pie.setData(prot_pie_data);

        //tdbc.getPieChartdata(talk_pie_data, query);

        talk_pie.getData().addAll(new PieChart.Data("Telegramm",90),
                new PieChart.Data("Chrome",10));
    }

/** mouse event handler*/
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
            fxml = "/app/scene/homeb_stage.fxml";
            try {
                loadStage(fxml, actionEvent);
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }else if (actionEvent.getSource() == seemore_b){
            fxml = "/app/scene/more_info.fxml";
            try {
                loadStage(fxml, actionEvent);
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }else  if  (actionEvent.getSource() == refresh_b){
            try {refreshaction();

            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }else if (actionEvent.getSource() == start_b){
            fxml = "/app/scene/start_stage.fxml";
            try {
                loadStage(fxml, actionEvent);
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }

        }
    }

    /** resfresh_button action - refreshes the table data*/
    public void refreshaction(){
        last_scan = new Text();

        System.out.println(java.time.LocalDateTime.now() +"  " +java.time.LocalTime.now());
        last_scan.setText(java.time.LocalDateTime.now() +"  " +java.time.Clock.systemUTC().instant());
        data_tab.getItems().clear();
        String query = "FROM ProtocolEntity pe";
        getdatafortable(query);
    }


    /**WHen method called it'll change the scene*/
    private void loadStage(String fxml, ActionEvent event) throws Throwable, InvocationTargetException {
        if (((Node)event.getSource()) == seemore_b){
            AnchorPane root = (AnchorPane) FXMLLoader.load(getClass().getResource(fxml));
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(fxml));
            // GridPane mainLayout = loader.load();
            Scene scene = new Scene(root, 1200,800);
            // scene.getStylesheets().add(getClass().getResource("fullpackstyling.css").toExternalForm());
            Stage window = new Stage();
            window.setScene(scene);
            window.show();
        }else{
            // System.out.println(fxml);
           // System.out.println(getClass());
          //  System.out.println(getClass().getResource(fxml));
            AnchorPane newscene = (AnchorPane) FXMLLoader.load(Main.class.getResource(fxml));
            Scene scene = new Scene(newscene);

            Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();

        }

    }


    /**gets data from DB and puts it into table on home_page*/
    public void getdatafortable(String query){
        //set connection

        ObservableList <ProtocolEntity> protocols = FXCollections.observableArrayList();
        tdbc.getRecord(protocols,query);

        app_name.setCellValueFactory(new PropertyValueFactory<>("appName"));
        ip_client.setCellValueFactory(new PropertyValueFactory<>("ipClient"));
        ip_server.setCellValueFactory(new PropertyValueFactory<>("ipServer"));
        byte_d.setCellValueFactory(new PropertyValueFactory<>("bytes"));
        prot_name.setCellValueFactory(new PropertyValueFactory<>("prot"));

        data_tab.setItems(protocols);
}

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        refreshaction();
        setpiecartdata1();

    }

/**menu animations(?)*/
    public void menumouseEnter(javafx.scene.input.MouseEvent mouseEvent) {
        ((Node)mouseEvent.getSource()).setStyle("-fx-background-color: #6aa0a6; ");
    }

    public void menumouseExit(javafx.scene.input.MouseEvent mouseEvent) {
        ((Node)mouseEvent.getSource()).setStyle("-fx-background-color: #213c42; ");
    }


    public void menumouseClick(MouseEvent mouseEvent) {
        ((Node)mouseEvent.getSource()).setStyle("-fx-text-fill: #000000; ");
    }

    public void menumousePressed(MouseEvent mouseEvent) {
        ((Node)mouseEvent.getSource()).setStyle("-fx-background-color: #ceffff; ");
        ((Node)mouseEvent.getSource()).setStyle("-fx-text-fill: #000000; ");
    }

    public void menumouseReleased(MouseEvent mouseEvent) {
        ((Node)mouseEvent.getSource()).setStyle("-fx-background-color: #213c42; ");
        ((Node)mouseEvent.getSource()).setStyle("-fx-text-fill: #ffffff; ");
    }


}

