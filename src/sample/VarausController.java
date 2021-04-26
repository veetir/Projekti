package sample;

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

import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

import java.io.IOException;
import java.sql.SQLException;

public class VarausController {

    @FXML
    private BorderPane varausNaytto;
    @FXML
    private DatePicker saapumisPaivaDp;

    @FXML
    private DatePicker lahtopaivaDp;



    public void varausButtonOnAction(ActionEvent actionEvent) {
        ScrollPane sp = new ScrollPane();
        VBox mokkiLista = new VBox(10);
        sp.setContent(mokkiLista);
        varausNaytto.setCenter(sp);

        try {
            ArrayList<Mokki> mokit = SQL_yhteys.getMokit();

            for (Mokki mokki : mokit) {

                HBox mokkiPaneeli = new HBox(10);
                Label teksti = new Label(mokki.toString());
                Pane vali = new Pane();
                mokkiPaneeli.setHgrow(vali, Priority.ALWAYS);
                Button varaaBtn = new Button("Varaa");
                varaaBtn.setAlignment(Pos.BOTTOM_RIGHT);
                mokkiPaneeli.getChildren().addAll(teksti, vali, varaaBtn);
                mokkiLista.getChildren().add(mokkiPaneeli);
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


    public void raportitButtonOnAction(ActionEvent actionEvent) throws IOException {
        Tyokalu.vaihdaIkkuna("raportit.fxml", actionEvent);
    }



    public void hallintaButtonOnAction(ActionEvent actionEvent) throws IOException {
        Tyokalu.vaihdaIkkuna("hallinta.fxml", actionEvent);
    }
}
