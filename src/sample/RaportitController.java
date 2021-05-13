package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.fxml.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;


public class RaportitController implements Initializable {

    @FXML
    private BorderPane varausNaytto;

    @FXML
    private TableView<Raportit> tableVaraukset;


    @FXML
    private TableColumn<Raportit, Integer> columnVarausnumero;

    @FXML
    private TableColumn<Raportit, Integer> columnAsiakasnumero;

    @FXML
    private TableColumn<Raportit, Integer> columnMokki;

    @FXML
    private TableColumn<Raportit, Date> columnVarauspaiva;



    public void raportitButtonOnAction(ActionEvent event) {

    }

    public void varausButtonOnAction(ActionEvent actionEvent) throws IOException {
        Tyokalu.vaihdaIkkuna("varaus.fxml", actionEvent);
    }

    public void hallintaButtonOnAction(ActionEvent actionEvent) throws IOException {
        Tyokalu.vaihdaIkkuna("hallinta.fxml", actionEvent);
    }

    public void palvelutButtonOnAction(ActionEvent actionEvent) throws IOException {
        Tyokalu.vaihdaIkkuna("raportitPalvelut.fxml", actionEvent);
    }

    ObservableList<Raportit> varauslista = FXCollections.observableArrayList();


    public void initialize(URL location, ResourceBundle resources) {

        try {
            Connection conn = SQL_yhteys.getYhteys();

            ResultSet rs = conn.createStatement().executeQuery("select * from varaus");

            while (rs.next()) {
                varauslista.add(new Raportit(rs.getInt("varaus_id"), rs.getInt("asiakas_id"), rs.getInt("mokki_mokki_id"), rs.getDate("varattu_pvm")));
            }
        } catch (SQLException ex) {
            Logger.getLogger(RaportitController.class.getName()).log(Level.SEVERE, null, ex);
        }

        columnVarausnumero.setCellValueFactory(new PropertyValueFactory<>("varaus_id"));
        columnAsiakasnumero.setCellValueFactory(new PropertyValueFactory<>("asiakas_id"));
        columnMokki.setCellValueFactory(new PropertyValueFactory<>("mokki_mokki_id"));
        columnVarauspaiva.setCellValueFactory(new PropertyValueFactory<>("varattu_pvm"));

        tableVaraukset.setItems(varauslista);
    }


}
