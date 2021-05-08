package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.sql.SQLException;
import java.util.ArrayList;


public class UusiMokkiController {
    @FXML
    public Label mokkiIdLabel, mokkiTalueIdLabel, mokkiToimAlueNimi, errorLabel;
    public Button lisaaUusiMokkiButton, plisaaUusiMokkiButton, poistaButton, poistaVarmaButton;

    // Muokattavat kentät
    public TextField mokkiNimiTextField;
    public TextField mokkiOsoiteTextField;
    public TextField mokkiZipTextField;
    public TextField mokkiHloMaaraTextField;
    public TextField mokkiHintaTextField;
    public TextArea mokkiVarusteluTextArea;
    public TextArea mokkiKuvausTextArea;


    String paivitys = null, id = null, mokkiToimAlue;
    boolean varma = false;
    int mokkiId, mokkiTalueId;


    public void initData(Mokki mokki) {
        mokkiIdLabel.setText(String.valueOf(mokki.get_mokki_id()));
        mokkiNimiTextField.setText(mokki.get_mokkinimi());
        mokkiOsoiteTextField.setText(mokki.get_katuosoite());
        mokkiZipTextField.setText(String.valueOf(mokki.get_postinro()));
        mokkiHloMaaraTextField.setText(String.valueOf(mokki.get_henkilomaara()));
        mokkiHintaTextField.setText(String.valueOf(mokki.getHinta()));
        mokkiVarusteluTextArea.setText(mokki.get_varustelu());
        mokkiKuvausTextArea.setText(mokki.get_kuvaus());
        mokkiTalueIdLabel.setText(String.valueOf(mokki.get_toimintaalue_id()));
        mokkiToimAlueNimi.setText(mokki.getToimintaalue_nimi());

        mokkiId = mokki.get_mokki_id();
        mokkiTalueId = mokki.get_toimintaalue_id();

        paivitys = mokki.get_mokkinimi();
        System.out.println(paivitys);
        id = String.valueOf(mokki.get_mokki_id());
        plisaaUusiMokkiButton.setDisable(false);
        plisaaUusiMokkiButton.setOpacity(1);
        poistaButton.setDisable(false);
        poistaButton.setOpacity(1);
        lisaaUusiMokkiButton.setDisable(true);
        lisaaUusiMokkiButton.setOpacity(0.1);
    }

    public void lisaaUusiMokkiButtonOnAction(ActionEvent actionEvent) throws SQLException {
        errorLabel.setText("");
        String uusiMokkiNimi = mokkiNimiTextField.getText();
        String uusiMokkiOsoite = mokkiOsoiteTextField.getText();
        String uusiMokkiZip = mokkiZipTextField.getText();
        String uusiMokkiHloMaara = mokkiHloMaaraTextField.getText();
        String uusiMokkiHinta = mokkiHintaTextField.getText();
        String uusiMokkiVarustelu = mokkiVarusteluTextArea.getText();
        String uusiMokkiKuvaus = mokkiKuvausTextArea.getText();

        Mokki uusiMokki = new Mokki(mokkiId, 2, "ruka", uusiMokkiZip, uusiMokkiNimi, uusiMokkiOsoite, uusiMokkiKuvaus, 3, uusiMokkiVarustelu, 3);
        //int mokki_id, int toimintaalue_id,String toimintaalue_nimi, String postinro, String mokkinimi, String katuosoite,String kuvaus, int henkilomaara,String varustelu, long hinta

        // Ei lisätä tyhjää aluetta
        if (uusiMokki == null) {
            errorLabel.setText("Tyhjää mökkiä ei voi lisätä!");
            return;
        } else {
            ArrayList<Mokki> mokit; // Tähän taulukkolistaan ladataan olemassa olevat toim.alueet
            mokit = SQL_yhteys.getMokit();
            // Ei lisätä olemassa olevaa aluetta
            /**
             * Huom. pahimmassa tilanteessa verrataan kaikkia alue-listan alkioita lisättävään
             * (itseasiassa aina siinä tilanteessa kun lisättävä on kelpo).
             */
            for (int i = 0; i < mokit.size(); i++) {
                if (mokit.get(i).get_mokkinimi().equalsIgnoreCase(uusiMokki.get_mokkinimi())) { // Huom. vertailu mökkinimellä
                    errorLabel.setText("Tämän niminen mökki on jo olemassa!");
                    return;
                }
            }
            if (paivitys == null) {
                SQL_yhteys.setMokitX(uusiMokki, false, false);
                errorLabel.setText("Mökki lisätty. Päivitä.");
            }
        }


    }

    public void poistaButtonOnAction(ActionEvent actionEvent) {
    }
}