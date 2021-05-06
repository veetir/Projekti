package sample;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class MokkiController implements Initializable {
    ObservableList<String> list = FXCollections.observableArrayList(); // Alueet
    @FXML
    // Mökki
    public TextField moknimiTextField, mokzipTextField, mokhloTextField, mokosoiteTextField, mokkiHintaTextField;
    public TextArea mokvarusteluTextArea, mokkuvausTextArea;
    public ChoiceBox<String> mokkialueChoiceBox;


    @Override // https://www.youtube.com/watch?v=cQK2Yh_kayY
    public void initialize(URL url, ResourceBundle rb) {
        loadData();
    }

    private void loadData() {
        String a = "1", b = "2", c = "3";
        list.addAll(a, b, c);
        mokkialueChoiceBox.getItems().addAll(list);
    }

    public void lisaamokkiButtonOnAction(ActionEvent actionEvent) throws SQLException, IOException {
        // https://www.mysqltutorial.org/mysql-jdbc-insert/ <- idea otettu suoraan täältä
        // Mökin lisäys kantaan onnistuu, mutta tällä hetkellä pitää olla jo olemassa postinumero, muutetaan myöhemmin
        String nimi = moknimiTextField.getText();
        String osoite = mokosoiteTextField.getText();
        String zip = mokzipTextField.getText();
        String kuvaus = mokkuvausTextArea.getText();
        String hlolkm = mokhloTextField.getText();
        String varustelu = mokvarusteluTextArea.getText();
        String alueid = mokkialueChoiceBox.getValue();
        String hinta = mokkiHintaTextField.getText();
        String kysely = "INSERT INTO mokki(toimintaalue_id, postinro, mokkinimi, katuosoite, kuvaus, henkilomaara, varustelu, hinta) "
                + "VALUES(?, ?, ?, ?, ?, ?, ?, ?);";
        SQL_yhteys.setMokit(kysely, alueid, zip, nimi, osoite, kuvaus, hlolkm, varustelu, hinta);
        SQL_yhteys.getMokit();
    }

    // ks. https://www.youtube.com/watch?v=XCgcQTQCfJQ
    // "Using SceneBuilder and Controller class to change scenes in Javafx"
    // versiopäivityksessä käytetään metodia vaihdaIkkuna
    public void moklisaustakaisinButtonOnAction(ActionEvent actionEvent) throws IOException {
        Tyokalu.vaihdaIkkuna("hallinta.fxml", actionEvent);

    }
}
