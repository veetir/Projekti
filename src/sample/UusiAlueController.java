package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.sql.SQLException;
import java.util.ArrayList;


public class UusiAlueController {

    @FXML
    public Label alueIdLabel, errorLabel;
    public TextField uusiToimAlueTextField;

    public void lisaaUusiAlueButtonOnAction(ActionEvent actionEvent) throws SQLException {
        errorLabel.setText("");
        String uusiAlue = uusiToimAlueTextField.getText();

        // Ei lisätä tyhjää aluetta
        if (uusiAlue == "") {
            errorLabel.setText("Tyhjää aluetta ei voi lisätä!");
            return;
        } else {
            ArrayList<ToimintaAlue> alueet; // Tähän taulukkolistaan ladataan olemassa olevat toim.alueet
            alueet = SQL_yhteys.getToimintaAlueetX();
            // Ei lisätä olemassa olevaa aluetta
            /**
             * Huom. alla oleva silmukka on teoriassa hieman hidas ratkaisu (O(n)), jos toiminta-alueita olisi
             * tuhansittain: parempi idea voisi olla tehdä uusi get-metodi toiminta-alueille, jossa alueet tallennetaan
             * hajautustauluun, josta voitaisiin tehokkaasti (O(1)) hakea onko jo samaa toiminta-aluetta. Käytännössä ongelmaa
             * tuskin kohdataan.
             */
            for (int i = 0; i < alueet.size(); i++) {
                if (alueet.get(i).get_nimi().equalsIgnoreCase(uusiAlue)) {
                    errorLabel.setText("Alue on jo olemassa!");
                    return;
                }
            }
            SQL_yhteys.insertToiminta(uusiAlue);
        }
    }
}