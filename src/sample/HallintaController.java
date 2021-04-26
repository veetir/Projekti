package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class HallintaController implements Initializable {

    /*@FXML
    private BorderPane varausNaytto;

    @FXML
    void hallintaButtonOnAction(ActionEvent event) {
        ScrollPane sp = new ScrollPane();
        VBox mokkiLista = new VBox(10);
        sp.setContent(mokkiLista);

        VBox suodattimetVb = new VBox(25);
        suodattimetVb.setPadding(new Insets(10, 10, 10, 10));
        ToggleGroup suodattimetGroup = new ToggleGroup();
        RadioButton mokkiRb = new RadioButton("mökit");
        RadioButton toimintaAlueRb = new RadioButton("Toiminta-alueet");
        RadioButton palveluRb = new RadioButton("palvelut");
        RadioButton asiakasRb = new RadioButton("asiakkaat");
        RadioButton varausRb = new RadioButton("varaukset");
        RadioButton laskuRb = new RadioButton("mökit");
        mokkiRb.setToggleGroup(suodattimetGroup);
        toimintaAlueRb.setToggleGroup(suodattimetGroup);
        palveluRb.setToggleGroup(suodattimetGroup);
        asiakasRb.setToggleGroup(suodattimetGroup);
        varausRb.setToggleGroup(suodattimetGroup);
        laskuRb.setToggleGroup(suodattimetGroup);
        suodattimetVb.getChildren().addAll(new Label("HALLINTA: "), new Pane(), mokkiRb, toimintaAlueRb, palveluRb, asiakasRb, varausRb, laskuRb);


        varausNaytto.setLeft(suodattimetVb);
        varausNaytto.setCenter(sp);

        //tietoVBox.getChildren().clear();
        try {
            ArrayList<Mokki> mokit = SQL_yhteys.getMokit();

            for (Mokki mokki : mokit) {
                //String tiedot = mokki.toString();
                //Label teksti = new Label(tiedot);
                //tietoVBox.getChildren().add(teksti);
                HBox mokkiPaneeli = new HBox(10);
                Label teksti = new Label(mokki.toString());
                Pane vali = new Pane();
                mokkiPaneeli.setHgrow(vali, Priority.ALWAYS);
                Button varaaBtn = new Button("Varaa");
                varaaBtn.setAlignment(Pos.BOTTOM_RIGHT);
                mokkiPaneeli.getChildren().addAll(teksti, vali, varaaBtn);
                mokkiLista.getChildren().add(mokkiPaneeli);
                //tietoVBox.getChildren().add(mokkiPaneeli);
                //tietoVBox.setPrefHeight(tietoVBox.getHeight()+mokkiPaneeli.getHeight());
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

                    peruutaBtn.setOnAction(peruuta -> {
                        mokkiPaneeli.getChildren().remove(asiakasTiedot);
                        mokkiPaneeli.getChildren().add(varaaBtn);
                    });
                });

            }
        } catch (SQLException e) {
            e.getMessage();
        }
    }*/

    @FXML
    public DatePicker loppuPvmDatePicker, alkuPvmDatePicker;
    public Pane aikaPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
       // alkuPvmDatePicker.set
    }

    public void varausButtonOnAction(ActionEvent actionEvent) throws IOException {
        Tyokalu.vaihdaIkkuna("varaus.fxml", actionEvent);
    }

    public void raportitButtonOnAction(ActionEvent actionEvent) throws IOException {
        Tyokalu.vaihdaIkkuna("raportit.fxml", actionEvent);
    }

    public void hallintaButtonOnAction(ActionEvent actionEvent) {
    }


    public void loppuAikaEnable(MouseEvent mouseEvent) {
    }
}
