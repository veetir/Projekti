package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class AsiakasController {
    @FXML
    // Asiakas
    public TextField etunimiTextField, sukunimiTextField, zipTextField, emailTextField, puhnroTextField, osoiteTextField;

    public void varausButtonOnAction(ActionEvent actionEvent) throws SQLException {
        // https://www.mysqltutorial.org/mysql-jdbc-insert/
        String etunimi = etunimiTextField.getText();
        String sukunimi = sukunimiTextField.getText();
        String osoite = osoiteTextField.getText();
        String zip = zipTextField.getText();
        String email = emailTextField.getText();
        String puhnro = puhnroTextField.getText();
        String kysely = "INSERT INTO asiakas(postinro, etunimi, sukunimi, lahiosoite, email, puhelinnro) "
                + "VALUES(?, ?, ?, ?, ?, ?);";

        SQL_yhteys.setAsiakkaat(kysely, zip, etunimi, sukunimi, osoite, email, puhnro);
        SQL_yhteys.getAsiakkaat();
    }


    public void takaisinButtonOnAction(ActionEvent actionEvent) throws IOException {
        Parent toiseenNakymaan = FXMLLoader.load(getClass().getResource("varaushallinta.fxml"));
        Scene toinenScene = new Scene(toiseenNakymaan);

        Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        window.setScene(toinenScene);
        window.show();
    }
}
