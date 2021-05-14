package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

public class uusiAsiakasController {
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    boolean varma = false;
    long id = -1;

    public void initData(Asiakas asiakas) {
        etunimiTextField.setText(asiakas.getEtunimi());
        sukunimiTextField.setText(asiakas.getSukunimi());
        lahiosoiteTextField.setText(asiakas.getLahiosoite());
        postinroTextField.setText(String.valueOf(asiakas.getPostinro()));
        puhelinnroTextField.setText(String.valueOf(asiakas.getPuhelinnro()));
        emailTextField.setText(asiakas.getEmail());
        id = asiakas.getAsiakasId();
        poistaButton.setDisable(false);
        poistaButton.setOpacity(1);
    }

    @FXML
    private TextField etunimiTextField;
    @FXML
    private TextField sukunimiTextField;
    @FXML
    private TextField lahiosoiteTextField;
    @FXML
    private TextField postinroTextField;
    @FXML
    private TextField puhelinnroTextField;
    @FXML
    private TextField emailTextField;
    @FXML
    private Button muokkaaAsiakasButton;
    @FXML
    private Button poistaButton;
    @FXML
    private Button poistaVarmaButton;
    @FXML
    private Label asiakasIDLabel;
    @FXML
    private Label errorLabel;

    @FXML
    void muokkaaAsiakasButtonOnAction(ActionEvent event) throws SQLException {
        errorLabel.setText("");
        String etunimi = etunimiTextField.getText();
        String sukunimi = sukunimiTextField.getText();
        String osoite = lahiosoiteTextField.getText();
        String postinro = postinroTextField.getText();
        String puhelinnro = puhelinnroTextField.getText();
        String email = emailTextField.getText();
        if (!SQL_yhteys.hasPostinro(postinro)) {
            SQL_yhteys.insertPostinro(postinro, osoite); // TODO: korjaa osoite postitoimipaikaksi
        }
        SQL_yhteys.muokkaaAsiakas(String.valueOf(id), etunimi, sukunimi, osoite, postinro, puhelinnro, email, true, false, 0);
        errorLabel.setText("Asiakas päivitetty. Päivitä.");
        alert.setTitle("Asiakkaan päivitys");
        alert.setHeaderText(null);
        alert.setContentText("Asiakkaan  '" + etunimi + " " + sukunimi + "'  tiedot päivitettiin tietokannassa.");
        alert.showAndWait();
    }

    @FXML
    void poistaButtonOnAction(ActionEvent event) throws SQLException {
        errorLabel.setText("");
        if (!varma) {
            etunimiTextField.setVisible(false);
            sukunimiTextField.setVisible(false);
            lahiosoiteTextField.setVisible(false);
            postinroTextField.setVisible(false);
            puhelinnroTextField.setVisible(false);
            emailTextField.setVisible(false);

            poistaButton.setText("Peru");
            varma = true;
            errorLabel.setText("Oletko varma? Poistetaanko asiakas, jolla on tämä asiakasID?");

            poistaVarmaButton.setDisable(false);
            poistaVarmaButton.setOpacity(1);
            poistaVarmaButton.setVisible(true);
        } else {
            varma = false;

            etunimiTextField.setVisible(true);
            sukunimiTextField.setVisible(true);
            lahiosoiteTextField.setVisible(true);
            postinroTextField.setVisible(true);
            puhelinnroTextField.setVisible(true);
            emailTextField.setVisible(true);

            poistaVarmaButton.setDisable(true);
            poistaVarmaButton.setOpacity(0.1);
            poistaVarmaButton.setVisible(false);
            poistaButton.setText("Poista");

        }
    }

    @FXML
    void poistaVarmaButtonOnAction(ActionEvent event) throws SQLException {

        if (!SQL_yhteys.asiakasHasVaraus(String.valueOf(id))){
            SQL_yhteys.muokkaaAsiakas(String.valueOf(id), null,
                    null, null, null,
                    null, null,
                    false, true, 0);
            errorLabel.setText("Asiakas poistettu. Päivitä.");
            poistaButton.setVisible(false);
            poistaVarmaButton.setVisible(false);
            alert.setTitle("Asiakkaan poisto");
            alert.setHeaderText(null);
            alert.setContentText("Asiakkaan tiedot poistettiin tietokannasta.");
            alert.showAndWait();
        } else{
            errorLabel.setText("Asiakkaalla on varaus.");
            poistaButton.setVisible(false);
            poistaVarmaButton.setVisible(false);
            alert.setTitle("Asiakasta ei voida poistaa");
            alert.setHeaderText(null);
            alert.setContentText("Asiakkaalla on olemassa oleva varaus, joten asiakasta ei voida poistaa. ");
            alert.showAndWait();
        }
    }

}
