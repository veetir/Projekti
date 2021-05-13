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
    private TableColumn<Raportit2, String> columnNimi;

    @FXML
    private TableColumn<Raportit2, Integer> columnPalvelunumero;

    @FXML
    private TableColumn<Raportit2, Integer> columnVarausnumeroP;

    @FXML
    private TableColumn<Raportit2, Integer> columnLukumaara;

    @FXML
    private TableColumn<Raportit2, Integer> columnToimintaAlue;

    @FXML
    private TableColumn<Raportit2, Double> columnHinta;

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

            ResultSet rs = conn.createStatement().executeQuery("select p.nimi as nimi, vp.palvelu_id as palvelu_id, vp.varaus_id as varaus_id, vp.lkm as lkm, p.hinta as hinta, p.toimintaalue_id as toimintaalue_id " + "from varauksen_palvelut vp join palvelu p " + "on p.palvelu_id = vp.palvelu_id;");

            while (rs.next()) {
                palvelulista.add(new Raportit2(rs.getString("nimi"),rs.getInt("palvelu_id"),rs.getInt("varaus_id"),rs.getInt("lkm"),rs.getDouble("hinta"),rs.getInt("toimintaalue_id")));
            }
        } catch (SQLException ex) {
            Logger.getLogger(RaportitController.class.getName()).log(Level.SEVERE, null, ex);
        }

        columnNimi.setCellValueFactory(new PropertyValueFactory<>("nimi"));
        columnPalvelunumero.setCellValueFactory(new PropertyValueFactory<>("palvelu_id"));
        columnVarausnumeroP.setCellValueFactory(new PropertyValueFactory<>("varaus_id"));
        columnLukumaara.setCellValueFactory(new PropertyValueFactory<>("lkm"));
        columnHinta.setCellValueFactory(new PropertyValueFactory<>("hinta"));
        columnToimintaAlue.setCellValueFactory(new PropertyValueFactory<>("toimintaalue_id"));


        tablePalvelut.setItems(palvelulista);
    }

}

