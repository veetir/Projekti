package sample;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;


public class HallintaController implements Initializable {

    @FXML
    private BorderPane varausNaytto;
    @FXML
    private ScrollPane varausSp;

    public void hallintaButtonOnAction(ActionEvent actionEvent) throws IOException {
        Tyokalu.vaihdaIkkuna("hallinta.fxml", actionEvent);
    }

    public void varausButtonOnAction(ActionEvent actionEvent) throws IOException {
        Tyokalu.vaihdaIkkuna("varaus.fxml", actionEvent);
    }

    public void raportitButtonOnAction(ActionEvent actionEvent) throws IOException {
        Tyokalu.vaihdaIkkuna("raportit.fxml", actionEvent);
    }

    public void varausAlkuPvmOnAction(ActionEvent actionEvent) {
    }

    public void varausLoppuPvmOnAction(ActionEvent actionEvent) {
    }

    public void lisaaAsiakasButtonOnAction(ActionEvent actionEvent) {
    }

    public void muokkaaAsiakastaButtonOnAction(ActionEvent actionEvent) {
    }

    public void poistaAsiakasButtonOnAction(ActionEvent actionEvent) {
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {

        try {
            naytaVaraukset();
        } catch (SQLException e) {
            e.getSQLState();
            e.getErrorCode();
            e.getMessage();
        }

    }

    void naytaVaraukset() throws SQLException {
        VBox varausVb = new VBox(5);
        varausVb.setPadding(new Insets(15, 15, 15, 15));
        varausSp.setFitToWidth(true);
        varausSp.setContent(varausVb);
        ArrayList<Varaus> varaukset = SQL_yhteys.getVaraukset();
        for (Varaus v : varaukset) {

            VBox varauksenTiedotVb = new VBox(3);
            HBox varausBox = getVarausBox(v);
            varausVb.getChildren().add(varausBox);
        }


    }

    private HBox getVarausBox(Varaus v) {
        VBox varauksenTiedotVb = new VBox(7);
        HBox varausBox = new HBox(10);
        varausBox.setPadding(new Insets(5, 5, 5, 5));

        Label varausOtsikko = new Label("varausID #" + v.getVarausId());
        Label asiakasOtsikko = new Label("ASIAKAS:");
        varausOtsikko.setFont(Font.font(null, FontWeight.BOLD, 16));
        asiakasOtsikko.setFont(Font.font(null, FontWeight.SEMI_BOLD, 14));

        Label asiakasTiedot = new Label("ASIAKAS:\t        " + "ID #" + v.getAsiakasId() + ", " + v.getEtunimi() + " " + v.getSukunimi());
        Label varauksenTiedot = new Label("VARATTU:\t" + v.getVarattuPvm()/*+"\nsaapumis pvm: "+v.getVarattuAlkupvm()+"\nlahto pvm: "+v.getVarattuLoppupvm()*/);
        varauksenTiedot.setFont(Font.font(null, FontWeight.SEMI_BOLD, 13));
        asiakasTiedot.setFont(Font.font(null, FontWeight.SEMI_BOLD, 13));

        Button avaaButton = new Button("avaa");
        avaaButton.setOnAction(avaaVaraus -> {
            ArrayList<VarauksenPalvelu_Hallinta> varauksenPalvelut = SQL_yhteys.getVarauksenPalvelut((int) v.getVarausId());
            HBox mokkiHBox = new HBox();
            HBox palveluHBox = new HBox();
            varausBox.getChildren().remove(avaaButton);

            Label mokkiOtsikko = new Label("MÖKKI:\t          ");
            Label mokinTiedot = new Label("mökkiID #" + v.getMokkiMokkiId() + "\n" + v.getMokkinimi() + ", " + v.getKatuosoite() + ", " + v.getToimintaalue() +
                    "\nsaapumis pvm:\t  " + v.getVarattuAlkupvm() +
                    "\nlähtö pvm:\t  " + v.getVarattuLoppupvm());

            Label palveluOtsikko = new Label("PALVELUT:\t  ");
            VBox palveluVbox = new VBox(2);
            for (VarauksenPalvelu_Hallinta vp : varauksenPalvelut) {
                Label lbl = new Label(vp.toString());
                palveluVbox.getChildren().add(lbl);
            }

            palveluHBox.getChildren().addAll(palveluOtsikko, palveluVbox);
            mokkiHBox.getChildren().addAll(mokkiOtsikko, mokinTiedot);
            Button peruutaVarausButton = new Button("peruuta");
            peruutaVarausButton.setOnAction(peruutaVaraus -> {
                Alert peruutusAlert = new Alert(AlertType.NONE);
                peruutusAlert.setHeaderText("Haluatko varmasti peruuttaa varauksen ?");
                ButtonType kylla = new ButtonType("Kyllä", ButtonData.OK_DONE);
                ButtonType ei = new ButtonType("Ei", ButtonData.CANCEL_CLOSE);
                peruutusAlert.getDialogPane().getButtonTypes().addAll(kylla, ei);
                Optional<ButtonType> result = peruutusAlert.showAndWait();
                if (result.isPresent() && result.get() == kylla) {
                    SQL_yhteys.peruutaVaraus((int) v.getVarausId());
                    Alert peruutusOnnistuiAlert = new Alert(AlertType.INFORMATION);
                    peruutusOnnistuiAlert.setHeaderText("Varaus peruutettu onnistuneesti.");
                    peruutusOnnistuiAlert.showAndWait();
                } else if (result.isPresent() && result.get() == ei) {
                    System.out.println("Ei nappia painettiin");
                }
            });

            Button suljeButton = new Button("sulje");
            suljeButton.setOnAction(suljeVaraus -> {
                varausBox.getChildren().removeAll(suljeButton, peruutaVarausButton);
                varauksenTiedotVb.getChildren().remove(mokkiHBox);
                varauksenTiedotVb.getChildren().remove(palveluHBox);
                varausBox.getChildren().add(avaaButton);
            });
            if (v.getVarattuAlkupvm().toLocalDate().compareTo(LocalDate.now()) < 6) {
                peruutaVarausButton.setDisable(true);
            }

            varausBox.getChildren().addAll(peruutaVarausButton, suljeButton);
            varauksenTiedotVb.getChildren().addAll(mokkiHBox, palveluHBox);
        });

        varauksenTiedotVb.getChildren().addAll(varausOtsikko, varauksenTiedot, asiakasTiedot);
        Pane pane = new Pane();
        varausBox.getChildren().addAll(varauksenTiedotVb, pane, avaaButton);
        varausBox.setHgrow(pane, Priority.ALWAYS);
        varausBox.setAlignment(Pos.BOTTOM_LEFT);
        varausBox.setStyle("-fx-border-color: aquamarine");
        return varausBox;
    }


    // Toiminta-alueet -tabin toiminnallisuus
    // Tutoriaalit:
    // https://www.youtube.com/watch?v=8lR9scOLE7U (n. 10:40)
    // https://www.youtube.com/watch?v=Wc1a2KshJ4w&list=LL&index=1 (n. 11:00 (AlueController) ja 14:30)

    @FXML
    private VBox toimAlueVbox;
    public Button lopetaToimButton, lisaaUusiToimAlueButton;

    Event e;

    public void actionPerformed(Event e) throws SQLException {
        toimAlueTabSelected(e);
    }

    private ToimintaAlue muokattava;
    boolean valittu, lisays;

    public void toimAlueTabSelected(Event event) throws SQLException {
        valittu = false;
        lisays = false;
        muokattava = null;
        toimAlueVbox.getChildren().clear();
        ArrayList<ToimintaAlue> alueet; // Tähän taulukkolistaan ladataan olemassa olevat toim.alueet
        alueet = SQL_yhteys.getToimintaAlueetX(); // Metodi palauttaa taulukkolistan, jonka alkiot ovat ToimintaAlue:ita

        // Seuraavalla silmukalla käydään läpi kaikki toiminta-alueet.
        for (int i = 0; i < alueet.size(); i++) {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("alue.fxml"));
                Parent root = loader.load(); // root on nyt node-tyyppiä, eli se voidaan lisätä VBoxiin

                // Pitää vielä ladata AlueControllerin initData-metodin avulla oikeat tiedot (taulukkolistasta alueet)
                AlueController controller = loader.getController();
                controller.initData(alueet.get(i));
                final ToimintaAlue k = alueet.get(i);
                AtomicInteger z = new AtomicInteger();

                // Värin muutos kun hiiri menee tuloksen päälle

                root.setOnMousePressed(event1 -> {
                    z.getAndIncrement();
                    if (z.get() % 2 == 1 & muokattava == null) {
                        lisaaUusiToimAlueButton.setText("Muokkaa");
                        root.setStyle("-fx-background-color: #dbd9ff; " +
                                "-fx-border-color: #40424a; -fx-border-width: 3");
                        valittu = true;
                        muokattava = k;
                    } else if (valittu == true & k.equals(muokattava)) {
                        lisaaUusiToimAlueButton.setText("Lisää");
                        root.setStyle("-fx-background-color: #f4f4f4; " +
                                "-fx-border-color:  #dbd9ff; -fx-border-width: 1");
                        valittu = false;
                        muokattava = null;
                    } else {
                        return;
                    }
                });
                toimAlueVbox.getChildren().add(root);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void lisaaUusiToimAlueButton(ActionEvent actionEvent) {
        if (!lisays) {
            try {
                lisaaUusiToimAlueButton.setDisable(true);
                lopetaToimButton.setDisable(false);
                lopetaToimButton.setOpacity(1);
                lisays = true;
                lisaaUusiToimAlueButton.setText("Lisää alue");
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("uusiAlue.fxml"));
                Parent root = loader.load();
                UusiAlueController controller = loader.getController();

                if (muokattava != null) {
                    controller.initData(muokattava);
                    toimAlueVbox.getChildren().clear();
                }
                root.setOnMousePressed(event1 -> {
                    root.setStyle("-fx-background-color: #dbd9ff");
                });

                // Tässä käytetään add-metodia, jolla root-node saadaan laitettua tiettyyn indeksiin: tässä 0 eli alkuun
                toimAlueVbox.getChildren().add(0, root);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void lopetaToimButtonOnAction(ActionEvent actionEvent) throws SQLException {
        toimAlueVbox.getChildren().remove(0);
        lisays = false;
        lopetaToimButton.setDisable(true);
        lopetaToimButton.setOpacity(0.1);
        lisaaUusiToimAlueButton.setDisable(false);
        actionPerformed(e);
        valittu = false;
        lisays = false;
        muokattava = null;
    }


    /**
     * MÖKKIEN HALLINTA ALLA
     */


    @FXML
    private VBox mokkiVbox;
    public Button lisaaMokkiButton, lopetaMokkiButton;
    private Mokki muokattavaMokki;

    public void mokitTabSelected(Event event) throws SQLException {
        valittu = false;
        lisays = false;
        muokattavaMokki = null;
        mokkiVbox.getChildren().clear();
        ArrayList<Mokki> mokit;
        mokit = SQL_yhteys.getMokit();

        for (int i = 0; i < mokit.size(); i++) {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("mokki.fxml"));
                Parent root = loader.load();

                MokkiController controller = loader.getController();
                controller.initData(mokit.get(i));
                final Mokki k = mokit.get(i);
                AtomicInteger z = new AtomicInteger();

                root.setOnMousePressed(event1 -> {
                    z.getAndIncrement();
                    //System.out.println(z.get());
                    System.out.println("MÖKKI NYT " + k);
                    if (z.get() % 2 == 1 & muokattavaMokki == null) {
                        lisaaMokkiButton.setText("Muokkaa");
                        root.setStyle("-fx-background-color: #dbd9ff; " +
                                "-fx-border-color: #40424a; -fx-border-width: 3");
                        valittu = true;
                        muokattavaMokki = k;

                    } else if (valittu == true & k.equals(muokattavaMokki)) {
                        lisaaMokkiButton.setText("Lisää");
                        root.setStyle("-fx-background-color: #f4f4f4; " +
                                "-fx-border-color: #dbd9ff; -fx-border-width: 1");
                        valittu = false;
                        muokattavaMokki = null;
                    } else {
                        return;
                    }
                });
                mokkiVbox.getChildren().add(root);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void lisaaMokkiButtonOnAction(ActionEvent actionEvent) {
        if (!lisays) {
            try {
                lisaaMokkiButton.setDisable(true);
                lopetaMokkiButton.setDisable(false);
                lopetaMokkiButton.setOpacity(1);
                lisays = true;
                lisaaMokkiButton.setText("Lisää mokki");
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("uusimokki.fxml"));
                Parent root = loader.load();
                UusiMokkiController controller = loader.getController();

                if (muokattavaMokki != null) {
                    controller.initData(muokattavaMokki);
                }
                root.setOnMousePressed(event1 -> {
                    root.setStyle("-fx-background-color: #dbd9ff");
                });

                // Tässä käytetään add-metodia, jolla root-node saadaan laitettua tiettyyn indeksiin: tässä 0 eli alkuun
                mokkiVbox.getChildren().clear();
                mokkiVbox.getChildren().add(0, root);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void actionPerformedRefreshMokki(Event e) throws SQLException {
        mokitTabSelected(e);
    }

    public void lopetaMokkiButtonOnAction(ActionEvent actionEvent) throws SQLException {
        mokkiVbox.getChildren().remove(0);
        lisays = false;
        lopetaMokkiButton.setDisable(true);
        lopetaMokkiButton.setOpacity(0.1);
        lisaaMokkiButton.setDisable(false);
        actionPerformedRefreshMokki(e);
        valittu = false;
        lisays = false;
        muokattavaMokki = null;
        lisaaMokkiButton.setText("Lisää mokki");
    }
}
