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
             * Huom. pahimmassa tilanteessa verrataan kaikkia alue-listan alkioita lisättävään
             * (itseasiassa aina siinä tilanteessa kun lisättävä on kelpo).
             */
            for (int i = 0; i < alueet.size(); i++) {
                if (alueet.get(i).get_nimi().equalsIgnoreCase(uusiAlue)) { // Huom. Vertailu equalsIGNORECASE !
                    errorLabel.setText("Alue on jo olemassa!");
                    return;
                }
            }
            SQL_yhteys.insertToiminta(uusiAlue);
        }
    }
}