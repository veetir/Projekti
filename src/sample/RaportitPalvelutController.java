package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RaportitPalvelutController implements Initializable {

    @FXML
    private BorderPane varausNaytto;

    @FXML
    private TableView<Raportit2> tablePalvelut;

    @FXML
    private TableColumn<Raportit2, Integer> columnPalvelunumero;

    @FXML
    private TableColumn<Raportit2, Integer> columnVarausnumeroP;

    @FXML
    private TableColumn<Raportit2, Integer> columnLukumaara;

    public void raportitButtonOnAction(ActionEvent event) {

    }

    public void varausButtonOnAction(ActionEvent actionEvent) throws IOException {
        Tyokalu.vaihdaIkkuna("varaus.fxml", actionEvent);
    }

    public void hallintaButtonOnAction(ActionEvent actionEvent) throws IOException {
        Tyokalu.vaihdaIkkuna("hallinta.fxml", actionEvent);
    }

    public void varauksetButtonOnAction(ActionEvent actionEvent) throws IOException {
        Tyokalu.vaihdaIkkuna("raportit.fxml", actionEvent);
    }

    ObservableList<Raportit2> palvelulista = FXCollections.observableArrayList();


    public void initialize(URL location, ResourceBundle resources) {

        try {
            Connection conn = SQL_yhteys.getYhteys();

            ResultSet rs = conn.createStatement().executeQuery("select * from varauksen_palvelut");

            while (rs.next()) {
                palvelulista.add(new Raportit2(rs.getInt("palvelu_id"),rs.getInt("varaus_id"),rs.getInt("lkm")));
            }
        } catch (SQLException ex) {
            Logger.getLogger(RaportitController.class.getName()).log(Level.SEVERE, null, ex);
        }

        columnPalvelunumero.setCellValueFactory(new PropertyValueFactory<>("palvelu_id"));
        columnVarausnumeroP.setCellValueFactory(new PropertyValueFactory<>("varaus_id"));
        columnLukumaara.setCellValueFactory(new PropertyValueFactory<>("lkm"));

        tablePalvelut.setItems(palvelulista);
    }

}

