package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.sql.SQLException;
import java.util.ArrayList;


public class UusiAlueController {

    @FXML
    public Label alueIdLabel, errorLabel;
    public TextField uusiToimAlueTextField;
    public Button lisaaUusiAlueButton, plisaaUusiAlueButton, poistaButton;
    String paivitys = null, id = null;

    // getteri sille, mitä textfieldiin on kirjoitettu
    public TextField getUusiToimAlueTextField() {
        return uusiToimAlueTextField;
    }

    public void initData(ToimintaAlue alue) {
        alueIdLabel.setText(String.valueOf(alue.get_toimintaalue_id()));
        uusiToimAlueTextField.setText(alue.get_nimi());
        paivitys = alue.get_nimi();
        System.out.println(paivitys);
        id = String.valueOf(alue.get_toimintaalue_id());
        plisaaUusiAlueButton.setDisable(false);
        plisaaUusiAlueButton.setOpacity(1);
        poistaButton.setDisable(false);
        poistaButton.setOpacity(1);
        lisaaUusiAlueButton.setDisable(true);
        lisaaUusiAlueButton.setOpacity(0.1);
    }

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
            if (paivitys == null) {
                SQL_yhteys.insertToiminta(uusiAlue, null, false);
            } else
                SQL_yhteys.insertToiminta(uusiAlue, id,true);
        }
    }

    public void poistaButtonOnAction(ActionEvent actionEvent) {
    }
}