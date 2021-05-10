package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class UusiPalveluController {
    @FXML
    private TextField palveluNimiTextField;
    @FXML
    private TextField palveluHintaTextField;
    @FXML
    private Button lisaaUusiPalveluButton;
    @FXML
    private Button plisaaUusiPalveluButton;
    @FXML
    private Button poistaButton;
    @FXML
    private Button poistaVarmaButton;
    @FXML
    private Label palveluIdLabel;
    @FXML
    private Label errorLabel;
    @FXML
    private TextArea palveluKuvausTextArea;

    String paivitys;
    Long id, tAlueId;
    boolean varma = false;

    public void initData(Palvelu palvelu) {
        palveluNimiTextField.setText(palvelu.getNimi());
        palveluHintaTextField.setText(String.valueOf(palvelu.getHinta()));
        palveluIdLabel.setText(String.valueOf(palvelu.getPalveluId()));
        palveluKuvausTextArea.setText(palvelu.getKuvaus());

        paivitys = palvelu.getNimi();
        System.out.println(paivitys);
        id = Long.valueOf(palvelu.getPalveluId());
        tAlueId = Long.valueOf(palvelu.getToimintaalueId());

        plisaaUusiPalveluButton.setDisable(false);
        plisaaUusiPalveluButton.setOpacity(1);
        poistaButton.setDisable(false);
        poistaButton.setOpacity(1);
        lisaaUusiPalveluButton.setDisable(true);
        lisaaUusiPalveluButton.setOpacity(0.1);
    }

    public void lisaaUusiPalveluButtonOnAction(ActionEvent actionEvent) throws SQLException {
        errorLabel.setText("");
        String uusiPalveluNimi = palveluNimiTextField.getText();
        Double uusiPalveluHinta = Double.valueOf(palveluHintaTextField.getText());
        String uusiPalveluKuvaus = palveluKuvausTextArea.getText();

        // Huom. ei tarvitse lähettää oikeaa toim.alueen nimeä (tai nimeä ollenkaan), koska SQL-lause tekee mökin pelkän ID:n perusteella
        Palvelu uusiPalvelu = null;
        try {
            if (id != null) {
                uusiPalvelu = new Palvelu(id, tAlueId, uusiPalveluNimi, uusiPalveluKuvaus, uusiPalveluHinta);
            } else {
                uusiPalvelu = new Palvelu(0, tAlueId, uusiPalveluNimi, uusiPalveluKuvaus, uusiPalveluHinta);
            }

        } catch (NumberFormatException e) {
            e.printStackTrace();
            errorLabel.setText("Kaikkia kenttiä ei täytetty oikein!");
        }

        // Ei lisätä tyhjää aluetta
        if (uusiPalvelu == null) {
            errorLabel.setText("Tyhjää palvelua ei voi lisätä!");
            return;
        } else {
            ArrayList<Palvelu> palvelut; // Tähän taulukkolistaan ladataan olemassa olevat palvelut
            palvelut = SQL_yhteys.getPalvelut();
            // Ei lisätä olemassa olevaa aluetta
            if (paivitys == null) {
                for (int i = 0; i < palvelut.size(); i++) {
                    if (palvelut.get(i).getNimi().equalsIgnoreCase(uusiPalvelu.getNimi()) &
                            palvelut.get(i).getToimintaalueId() == uusiPalvelu.getToimintaalueId()) {
                        errorLabel.setText("Tämän niminen palvelu on jo olemassa tällä alueella!");
                        return;
                    }
                }
            }

            // Lähetetään tiedot. Lisäksi kerrotaan SQL_yhteys-luokan metodille onko kyseessä uusi lisäys vai päivitys
            if (paivitys == null) {
                SQL_yhteys.setPalvelu(uusiPalvelu, false, false, null);
                errorLabel.setText("Palvelu lisätty. Päivitä.");
            } else {
                SQL_yhteys.setPalvelu(uusiPalvelu, true, false, null);
                errorLabel.setText("Palvelu päivitetty. Päivitä.");
            }
        }
    }

    public void poistaButtonOnAction(ActionEvent actionEvent) {
        errorLabel.setText("");
        if (!varma) {
            palveluNimiTextField.setDisable(true);
            palveluHintaTextField.setDisable(true);
            lisaaUusiPalveluButton.setDisable(true);
            plisaaUusiPalveluButton.setDisable(true);
            palveluIdLabel.setDisable(true);
            palveluKuvausTextArea.setDisable(true);

            poistaButton.setText("Peru");
            varma = true;
            errorLabel.setText("Oletko varma?");

            plisaaUusiPalveluButton.setDisable(true);
            plisaaUusiPalveluButton.setOpacity(0.1);
            poistaVarmaButton.setDisable(false);
            poistaVarmaButton.setOpacity(1);
            poistaVarmaButton.setVisible(true);
        } else {
            varma = false;
            palveluNimiTextField.setDisable(false);
            palveluHintaTextField.setDisable(false);
            lisaaUusiPalveluButton.setDisable(false);
            plisaaUusiPalveluButton.setDisable(false);
            palveluIdLabel.setDisable(false);
            palveluKuvausTextArea.setDisable(false);

            poistaVarmaButton.setDisable(true);
            poistaVarmaButton.setOpacity(0.1);
            poistaVarmaButton.setVisible(false);
            poistaButton.setText("Poista");

            plisaaUusiPalveluButton.setDisable(false);
            plisaaUusiPalveluButton.setOpacity(1);
        }
    }

    public void poistaVarmaButtonOnAction(ActionEvent actionEvent) throws SQLException {
        SQL_yhteys.setPalvelu(null, false, true, String.valueOf(palveluIdLabel.getText()));
        errorLabel.setText("Palvelu poistettu. Päivitä.");
        poistaButton.setVisible(false);
        plisaaUusiPalveluButton.setVisible(false);
        poistaVarmaButton.setVisible(false);
    }

    public void sendAlue(ToimintaAlue alue) {
        tAlueId = Long.valueOf(alue.get_toimintaalue_id());
    }
}
