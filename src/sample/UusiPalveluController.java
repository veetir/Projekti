package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;

public class UusiPalveluController implements Initializable {
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
    double palvelunHinta = -1.0;
    Alert alert = new Alert(Alert.AlertType.INFORMATION);

    public void initData(Palvelu palvelu) {
        palveluNimiTextField.setText(palvelu.getNimi());
        // laitetaan prompt text, sillä tämä kenttä ei ota vastaan muuta kuin numeroita
        palveluHintaTextField.setPromptText(String.valueOf(palvelu.getHinta()));
        palvelunHinta = palvelu.getHinta();
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
        String uusiPalveluNimi = palveluNimiTextField.getText();
        Double uusiPalveluHinta = 0.0;
        if (palveluHintaTextField.getText() != "") {
            uusiPalveluHinta = Double.valueOf(palveluHintaTextField.getText());
        } else if (palvelunHinta != -1.0) {
            uusiPalveluHinta = palvelunHinta;
        } else {
            errorLabel.setText("Syötä hinta!");
        }

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
                alert.setTitle("Palvelun lisäys");
                alert.setHeaderText(null);
                alert.setContentText("Palvelu lisättiin tietokantaan.");
                alert.showAndWait();
            } else {
                SQL_yhteys.setPalvelu(uusiPalvelu, true, false, null);
                errorLabel.setText("Palvelu päivitetty. Päivitä.");
                errorLabel.setText("Palvelu lisätty. Päivitä.");
                alert.setTitle("Palvelun tietojen päivitys");
                alert.setHeaderText(null);
                alert.setContentText("Palvelun tiedot päivitettiin tietokannassa.");
                alert.showAndWait();
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
        alert.setTitle("Palvelun poisto");
        alert.setHeaderText(null);
        alert.setContentText("Palvelu poistettiin tietokannasta.");
        alert.showAndWait();
    }

    public void sendAlue(ToimintaAlue alue) {
        tAlueId = Long.valueOf(alue.get_toimintaalue_id());
    }

    /**
     * https://stackoverflow.com/a/36436243
     * <p>
     * Hinta-kentän pitäisi ottaa vastaan pelkästään numeroita
     */

    UnaryOperator<TextFormatter.Change> filter = change -> {
        String text = change.getText();
        if (text.matches("[0-9]*")) {
            return change;
        }
        return null;
    };
    TextFormatter<String> textFormatterri = new TextFormatter<>(filter);


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Oikeastaan minkään kentän ei ole mielekästä olla tyhjä, mutta ainakaan nimen ei pitäisi olla tyhjä
        // Alla olevaa metodia ei voida soveltaa jokaiseen tekstikenttään, koska toinen tekstikenttä voi togglettaa
        // napin disablen vaikka sen pitäisi perustua siihen onko *jokaisessa* tekstikentässä ainakin jotain... (siis tämä on kehno ratkaisu)
        ToggleDisableTextField(palveluNimiTextField);
        // Pakottaa kentän numeeriseksi. ks. https://stackoverflow.com/a/36436243
        palveluHintaTextField.setTextFormatter(textFormatterri);
    }

    private void ToggleDisableTextField(TextField palveluNimiTextField) {
        palveluNimiTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.equals("")) {
                lisaaUusiPalveluButton.setDisable(true);
                plisaaUusiPalveluButton.setDisable(true);
                palveluNimiTextField.setStyle("-fx-border-color: #cf1b1b; -fx-background-color: #FFF0F0; -fx-border-width: 2");
                errorLabel.setText("Nimi ei voi olla tyhjä.");
            } else {
                lisaaUusiPalveluButton.setDisable(false);
                plisaaUusiPalveluButton.setDisable(false);
                palveluNimiTextField.setStyle("");
                errorLabel.setText("");
            }
        });
    }

    @FXML
    Button testButton;

    public void testButtonOnAction(ActionEvent actionEvent) {
        System.out.println(testButton.getScene());
    }
}
