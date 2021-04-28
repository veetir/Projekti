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
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
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
        Parent toiseenNakymaan = FXMLLoader.load(getClass().getResource("raportit.fxml"));
        Scene toinenScene = new Scene(toiseenNakymaan);

        Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        window.setScene(toinenScene);
        window.show();
    }


    // Vaihtaa näkymän hallinta näkymään
    public void hallintaButtonOnAction(ActionEvent actionEvent) throws IOException {
        Parent toiseenNakymaan = FXMLLoader.load(getClass().getResource("hallinta.fxml"));
        Scene toinenScene = new Scene(toiseenNakymaan);

        Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        window.setScene(toinenScene);
        window.show();
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
                 Label teksti = new Label(mokki.toString());
                 Pane vali = new Pane();
                 mokkiPaneeli.setHgrow(vali, Priority.ALWAYS);
                 Button varaaBtn = new Button("Varaa");
                 varaaBtn.setAlignment(Pos.BOTTOM_RIGHT);
                 mokkiPaneeli.getChildren().addAll(teksti, vali, varaaBtn);
                 mokkiLista.getChildren().add(mokkiPaneeli);
 
                 // Varaa napin toiminnallsuus, joka lisää mökin viereen asiakastietokentät.
                 varaaBtn.setOnAction(e -> {
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
                         } catch (SQLException ex) {
                             //TODO: handle exception
                             ex.printStackTrace();
                         }
                     });
                 });
 
             }
         } catch (SQLException e) {
             e.getMessage();
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
