package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.input.KeyEvent;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;

public class VarausController implements Initializable {

    @FXML
    private BorderPane varausNaytto;
    @FXML
    private DatePicker saapumisPaivaDp;
    @FXML
    private DatePicker lahtopaivaDp;
    @FXML
    private ComboBox<String> toimintaAlueCb;
    @FXML
    private TextField minHintaTf;
    @FXML
    private TextField maxHintaTf;
    @FXML
    private TextField henkiloMaaraTf;



    public void haeMokkejaBtnOnAction(ActionEvent actionEvent) {
        haeMokkeja();
    
    }

    public void nappainOnAction(KeyEvent event) {
        haeMokkeja();
        
    }

    // Vaihtaa näkymän raportit näkymään. 
    public void raportitButtonOnAction(ActionEvent actionEvent) throws IOException {
        Tyokalu.vaihdaIkkuna("raportit.fxml", actionEvent);
    }


    // Vaihtaa näkymän hallinta näkymään
    public void hallintaButtonOnAction(ActionEvent actionEvent) throws IOException {
        Tyokalu.vaihdaIkkuna("hallinta.fxml", actionEvent);
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TODO Auto-generated method stub
        ObservableList<String> toimintaAlueet = FXCollections.observableArrayList();
        try {
            ArrayList<String> alueet = SQL_yhteys.getToimintaAlueet();
            toimintaAlueet.add("-KAIKKI-");
            for(String alue : alueet) {
                toimintaAlueet.add(alue);
            }
            toimintaAlueCb.setItems(toimintaAlueet);
            toimintaAlueCb.getSelectionModel().selectFirst();
        }catch(SQLException e) {
            e.printStackTrace();
        }
        haeMokkeja();
        
    }


    public void haeMokkeja() {
         // Luodaan ScrollPane, johon laitetaan VBox mokkilista. Mokkilistassa näytetään mökit
         ScrollPane sp = new ScrollPane();
         VBox mokkiLista = new VBox(10);
         mokkiLista.setPadding(new Insets(10, 10, 10, 10));
         sp.setContent(mokkiLista);
         varausNaytto.setCenter(sp);
         // Tarkistetaan Suodattimine arvot ja asetetaan muuttujiin
         int minHinta, maxHinta, henkiloMaara;
         String alue = toimintaAlueCb.getSelectionModel().getSelectedItem().toString();
 
         if (minHintaTf.getText() == null || minHintaTf.getText().trim().isEmpty()) {
             minHinta = 0;
         } else if (! onNumero(minHintaTf.getText())) {
             minHinta = 0;
         } else {
             minHinta = Integer.parseInt(minHintaTf.getText());
         }
 
         if (maxHintaTf.getText() == null || maxHintaTf.getText().trim().isEmpty()) {
             maxHinta = 9999999;
         } else if (! onNumero(maxHintaTf.getText())) {
             maxHinta = 9999999;
         } else {
             maxHinta = Integer.parseInt(maxHintaTf.getText());
         }
 
         if (henkiloMaaraTf.getText() == null || henkiloMaaraTf.getText().trim().isEmpty()) {
             henkiloMaara = 0;
         } else if (! onNumero(henkiloMaaraTf.getText())) {
             henkiloMaara = 0;
         } else {
             henkiloMaara = Integer.parseInt(henkiloMaaraTf.getText());
         }
         System.out.println("alue: " + alue + "\nminHinta:  " + minHinta + "\nmaxHinta: " + maxHinta + "\nhenkilomaara: " + henkiloMaara);
         // Luodaan lista mokit, johon haetaan mökkejä tietokannasta. 
         ArrayList<Mokki> mokit;
         try {
             // Haetaan mökit tietokannasta ja suodatetaan suodattimien mukaan mitkä näytetään. 
             if (saapumisPaivaDp.getValue() != null && lahtopaivaDp.getValue() != null) {
                 mokit = SQL_yhteys.getMokit(saapumisPaivaDp.getValue(), lahtopaivaDp.getValue());
             }
             else {
                 mokit = SQL_yhteys.getMokit(); 
             }
             mokit = suodata(mokit, alue, minHinta, maxHinta, henkiloMaara);
             //Luodaan jokaiselle mökille oma HBox paneeli, jossa ne näytetään. 
             for (Mokki mokki : mokit) {
                 
                 HBox mokkiPaneeli = new HBox(10);
                 mokkiPaneeli.setPadding(new Insets(10, 10, 10, 10));
                 mokkiPaneeli.setStyle("-fx-border-color: aquamarine");
                 VBox mokinTiedotVb = new VBox(5);
                 Label mokkiOtsikkoLbl = new Label(mokki.get_mokkinimi()+", "+mokki.get_katuosoite()+", "+mokki.get_postinro()+", "+mokki.getToimintaalue_nimi());
                 Label mokinTiedotLbl = new Label("Henkilömäärä: "+mokki.get_henkilomaara()+
                 "\nVarustelu: "+mokki.get_varustelu()+
                 "\nHinta: "+mokki.getHinta()+"€");
                 mokkiOtsikkoLbl.setFont(Font.font(null, FontWeight.SEMI_BOLD, 16));
                 mokinTiedotLbl.setFont(Font.font(null, FontWeight.SEMI_BOLD, 14));
                 mokinTiedotVb.getChildren().addAll(mokkiOtsikkoLbl, mokinTiedotLbl);
                 Pane vali = new Pane();
                 mokkiPaneeli.setHgrow(vali, Priority.ALWAYS);
                 Button varaaBtn = new Button("Varaa");
                 mokkiPaneeli.getChildren().addAll(mokinTiedotVb, vali, varaaBtn);
                 mokkiLista.getChildren().add(mokkiPaneeli);
 
                 // Varaa napin toiminnallsuus, joka lisää mökin viereen asiakastietokentät.
                 varaaBtn.setOnAction(e -> {
                     Label mokinKuvaus = new Label("Kuvaus: " + mokki.get_kuvaus());
                     mokinKuvaus.setFont(Font.font(null, FontWeight.SEMI_BOLD, 14));
                     mokinTiedotVb.getChildren().add(mokinKuvaus);
                     VBox asiakasTiedot = new VBox(5);
                     TextField etunimiTF = new TextField();
                     TextField sukunimiTF = new TextField();
                     TextField osoiteTF = new TextField();
                     TextField postinroTF = new TextField();
                     TextField puhelinTF = new TextField();
                     TextField sahkopostiTF = new TextField();
                     Label etunimiLBL = new Label("etunimi:       ", etunimiTF);
                     Label sukunimiLBL = new Label("sukunimi:     ", sukunimiTF);
                     Label osoiteLBL = new Label("osoite:          ", osoiteTF);
                     Label postinroLBL = new Label("postinro:      ", postinroTF);
                     Label puhelinLBL = new Label("puhelin nro: ", puhelinTF);
                     Label sahkopostiLBL = new Label("sahkoposti:  ", sahkopostiTF);
 
                     etunimiLBL.setContentDisplay(ContentDisplay.RIGHT);
                     sukunimiLBL.setContentDisplay(ContentDisplay.RIGHT);
                     osoiteLBL.setContentDisplay(ContentDisplay.RIGHT);
                     postinroLBL.setContentDisplay(ContentDisplay.RIGHT);
                     puhelinLBL.setContentDisplay(ContentDisplay.RIGHT);
                     sahkopostiLBL.setContentDisplay(ContentDisplay.RIGHT);
                     HBox napit = new HBox(10);
                     Button teeVarausBtn = new Button("TEE VARAUS");
                     Button peruutaBtn = new Button("PERUUTA");
                     napit.getChildren().addAll(teeVarausBtn, peruutaBtn);
                     asiakasTiedot.getChildren().addAll(etunimiLBL, sukunimiLBL, osoiteLBL, postinroLBL, puhelinLBL, sahkopostiLBL, napit);
                     mokkiPaneeli.getChildren().remove(varaaBtn);
                     mokkiPaneeli.getChildren().add(asiakasTiedot);
 
                     // Peruuta napin toiminnallisuus, joka poistaa asiakastietojen syöttökentät. 
                     peruutaBtn.setOnAction(peruuta -> {
                         mokkiPaneeli.getChildren().remove(asiakasTiedot);
                         mokinTiedotVb.getChildren().remove(mokinKuvaus);
                         mokkiPaneeli.getChildren().add(varaaBtn);
                     });
                     //teeVaraus napin toiminnallisuus, joka tekee asiakastietojen perusteella uuden asiakkaan, jos ei ole jo olemassa.
                     // Sen jälkeen tekee varauksen kyseisestä mökistä datePickereiden osoittamalle ajalle
                     teeVarausBtn.setOnAction(varaa -> {
                        String etunimi = etunimiTF.getText();
                        String sukunimi = sukunimiTF.getText();
                        String lahiosoite = osoiteTF.getText();
                        String email = sahkopostiTF.getText();
                        String puhelinnro = puhelinTF.getText();
                        String postinro = postinroTF.getText();
                         if (saapumisPaivaDp.getValue() == null || lahtopaivaDp.getValue() == null) {
                             Alert pvmAlert = new Alert(AlertType.ERROR);
                             pvmAlert.setHeaderText("Valitse saapumis- ja lähtöpäivä vasemmasta reunasta!");
                             pvmAlert.showAndWait();
                         }else if(etunimi == null || etunimi.trim().isEmpty() || sukunimi == null || sukunimi.trim().isEmpty() || lahiosoite == null || lahiosoite.trim().isEmpty() || 
                        email == null || email.trim().isEmpty() || puhelinnro == null || puhelinnro.trim().isEmpty()) {
                            Alert asiakastietoAlert = new Alert(AlertType.ERROR);
                            asiakastietoAlert.setHeaderText("Tarkista, että olet syöttänyt kaikki asiakastiedot");
                            asiakastietoAlert.showAndWait();
                        } else {
                            Alert tilausVahvistusAlert = new Alert(AlertType.CONFIRMATION);
                            tilausVahvistusAlert.setHeaderText("Tarkista varauksen tiedot");
                            tilausVahvistusAlert.setContentText("ASIAKAS:\n  \netunimi: " + etunimi +
                            "\nsukunimi: " + sukunimi+
                            "\nlähiosoite: "+lahiosoite+
                            "\npostinumero: "+postinro+
                            "\nemail: "+email+
                            "\npuhelin-numero: "+puhelinnro+"\n"+
                            "\nMÖKKI:\n"+
                            "\n"+mokkiOtsikkoLbl.getText()+
                            "\nhinta: "+mokki.getHinta()+
                            "\nsaapuminen: "+saapumisPaivaDp.getValue()+
                            "\nlähtö:      "+lahtopaivaDp.getValue());
                            Optional<ButtonType> result = tilausVahvistusAlert.showAndWait();
                            System.out.println(result.get());
                            if(result.isPresent() && result.get() == ButtonType.OK) {
                                try {
                                    int asiakas_id = SQL_yhteys.getAsiakasId(etunimi, sukunimi, lahiosoite, email, puhelinnro, postinro);
                                    if (asiakas_id == -1) {
                                        asiakas_id = SQL_yhteys.insertAsiakas(postinro, etunimi, sukunimi, lahiosoite, email, puhelinnro);
                                        System.out.println("Luotiin uusi asiakas numerolla: "+asiakas_id);
                                    }
                                    else {
                                        System.out.println("asiakas on jo olemassa numerolla: "+asiakas_id);
                                    }
                                    int varaus_id = SQL_yhteys.insertVaraus(asiakas_id, mokki.get_mokki_id(), saapumisPaivaDp.getValue(), lahtopaivaDp.getValue());
                                    System.out.println("uusi varaus numerolla: "+varaus_id);
                                    Alert onnistuiAlert = new Alert(AlertType.INFORMATION);
                                    onnistuiAlert.setHeaderText("Varaus tehty!\nVaraus ID: "+varaus_id);
                                    onnistuiAlert.showAndWait();
                                    haeMokkeja();
                                } catch (SQLException ex) {
                                    //TODO: handle exception
                                    ex.printStackTrace();
                                }
                            }
                        }
                     });
                 });
 
             }
         } catch (SQLException e) {
             e.getMessage();
             e.getSQLState();
             e.getErrorCode();
         }
    }

        // Suodattaa mökkilistan toiminta-alueen, min- ja max hinnan ja henkilomaaran mukaan. Palauttaa suodatetun listan 
        static ArrayList<Mokki> suodata(ArrayList<Mokki> lista, String alue, int minHinta, int maxHinta, int henkilomaara) {
            ArrayList<Mokki> suodatettuLista = new ArrayList<Mokki>();
    
            for (Mokki m : lista) {
    
                if (! alue.equals("-KAIKKI-")) {
                    if (! m.getToimintaalue_nimi().equals(alue)) {
                        continue;
                    }
                }
                if (m.getHinta() < minHinta || m.getHinta() > maxHinta) {
                    continue;
                }
                if (m.get_henkilomaara() < henkilomaara) {
                    continue;
                }
                suodatettuLista.add(m);
            }
            return suodatettuLista;
        }
    
        // Tehty maxhinta, minhinta ja henkilomaara textfieldien arvon tarkistamiseksi. ottaa argumenttina String olion ja palauttaa true jos pelkkiä numeroita, muuten false. 
        static boolean onNumero(String s) {
            boolean onNumero = true;
            for (char c : s.toCharArray()) {
                if (! Character.isDigit(c)) {
                    onNumero = false;
                }
            }
            return onNumero;
        }
        
}
