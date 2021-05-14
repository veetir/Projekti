package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLClientInfoException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class HallintaController implements Initializable {

    @FXML
    private ScrollPane varausSp;
    @FXML
    private TextField varausHakuTf;

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

    @FXML
    void varausHakuTfOnAction(KeyEvent event) {

        try {
            naytaVaraukset();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            e.getMessage();
            e.getSQLState();
            e.getErrorCode();
        }
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
        ArrayList<Varaus> varaukset;
        if (varausHakuTf.getText() == null || varausHakuTf.getText().trim().isEmpty()) {
            varaukset = SQL_yhteys.getVaraukset();
        } else {
            varaukset = SQL_yhteys.getVaraukset(varausHakuTf.getText());

        }
        if (varaukset.size() < 1) {
            Label eiVarauksiaLbl = new Label("ei löytynyt varauksia");
            eiVarauksiaLbl.setFont(Font.font(null, FontWeight.BOLD, 20));
            varausVb.getChildren().add(eiVarauksiaLbl);
        } else {
            for (Varaus v : varaukset) {

                HBox varausBox = getVarausBox(v);
                varausVb.getChildren().add(varausBox);
            }
        }
    }

    private HBox getVarausBox(Varaus v) {
        VBox varauksenTiedotVb = new VBox(7);
        HBox varausBox = new HBox(10);
        varausBox.setPadding(new Insets(5, 5, 5, 5));

        Label varausOtsikko = new Label("varausID #" + v.getVarausId());
        Label asiakasOtsikko = new Label("ASIAKAS:");
        varausOtsikko.setFont(Font.font(null, FontWeight.BOLD, 20));
        asiakasOtsikko.setFont(Font.font(null, FontWeight.SEMI_BOLD, 16));

        Label asiakasTiedot = new Label("ASIAKAS:\t        " + "ID #" + v.getAsiakasId() + ", " + v.getEtunimi() + " " + v.getSukunimi());
        Label varauksenTiedot = new Label("VARATTU:\t" + v.getVarattuPvm()/*+"\nsaapumis pvm: "+v.getVarattuAlkupvm()+"\nlahto pvm: "+v.getVarattuLoppupvm()*/);
        varauksenTiedot.setFont(Font.font(null, FontWeight.SEMI_BOLD, 16));
        asiakasTiedot.setFont(Font.font(null, FontWeight.SEMI_BOLD, 16));

        Button avaaButton = new Button("avaa");

        avaaButton.setOnAction(avaaVaraus -> {
            
            boolean peruutettavissa = true, muokattavissa = true;

            if (v.getVarattuAlkupvm().toLocalDate().compareTo(LocalDate.now()) < 6) {
                peruutettavissa = false;
            }
            if(v.getVarattuLoppupvm().toLocalDate().compareTo(LocalDate.now()) <= 0) {
                muokattavissa = false;
            }
            ArrayList<VarauksenPalvelu_Hallinta> varauksenPalvelut = SQL_yhteys.getVarauksenPalvelut((int) v.getVarausId());
            HBox mokkiHBox = new HBox();
            HBox palveluHBox = new HBox();
            varausBox.getChildren().remove(avaaButton);

            Label mokkiOtsikko = new Label("MÖKKI:\t        ");
            Label mokinTiedot = new Label("mökkiID #" + v.getMokkiMokkiId() + "\n" + v.getMokkinimi() + ", " + v.getKatuosoite() + ", " + v.getToimintaalue() +
                    "\nsaapumis pvm:\t  " + v.getVarattuAlkupvm() +
                    "\nlähtö pvm:\t  " + v.getVarattuLoppupvm());
            mokkiOtsikko.setFont(Font.font(null, FontWeight.SEMI_BOLD, 16));
            mokinTiedot.setFont(Font.font(null, FontWeight.SEMI_BOLD, 16));

            Label palveluOtsikko = new Label("PALVELUT:\t ");
            palveluOtsikko.setFont(Font.font(null, FontWeight.SEMI_BOLD, 16));
            VBox palveluVbox = new VBox(2);
            if (muokattavissa) {
                Button lisaaPalvelButton = new Button("+");
                Label lisaaPalveluLbl = new Label("Lisää palvelu: ", lisaaPalvelButton);
                lisaaPalveluLbl.setContentDisplay(ContentDisplay.RIGHT);
                lisaaPalveluLbl.setFont(Font.font(null, FontWeight.SEMI_BOLD, 16));
                palveluVbox.getChildren().add(lisaaPalveluLbl);
                lisaaPalvelButton.setOnAction(lisääPalvelu -> {
                    HBox palveluHb = new HBox(5);
                    ComboBox<String> palveluCb = new ComboBox<>();
                    TextField palveluTf = new TextField();
                    palveluTf.setPromptText("lkm");
                    palveluTf.setPrefColumnCount(3);
                    Button lisaaBtn = new Button("lisää");
                    Button peruBtn = new Button("peruuta");
                    palveluHb.getChildren().addAll(palveluCb,palveluTf, lisaaBtn, peruBtn);
                    palveluVbox.getChildren().addAll(palveluHb);
                    ObservableList<String> palveluLista = FXCollections.observableArrayList();
                    try {
                        ArrayList<Palvelu> palvelut = SQL_yhteys.getAlueenPalvelut(v.getToimintaalueId());
                        for (Palvelu p : palvelut) {
                            palveluLista.add(p.toString());
                        }
                        palveluCb.setItems(palveluLista);
                        peruBtn.setOnAction(peruLisäys -> palveluVbox.getChildren().remove(palveluHb));
                        lisaaBtn.setOnAction(vahvistaLisäys -> {
                            boolean olemassa = false;
                            Palvelu p = palvelut.get(palveluCb.getSelectionModel().getSelectedIndex());
                            ArrayList<VarauksenPalvelu_Hallinta> paivitetytPalvelut = SQL_yhteys.getVarauksenPalvelut((int)v.getVarausId());
                            for (VarauksenPalvelu_Hallinta x : paivitetytPalvelut) {
                                if(x.getPalvelu_id() == p.getPalveluId()) {
                                    olemassa = true;
                                    int lkm = x.getLkm()+Integer.parseInt(palveluTf.getText());
                                    SQL_yhteys.updateVarauksenPalvelu((int)v.getVarausId(), x.getPalvelu_id(), lkm);
                                    paivitetytPalvelut = SQL_yhteys.getVarauksenPalvelut((int)v.getVarausId());
                                    palveluVbox.getChildren().clear();
                                    palveluVbox.getChildren().addAll(lisaaPalveluLbl);
                                    for (VarauksenPalvelu_Hallinta vp : paivitetytPalvelut) {
                                        Label lbl = new Label(vp.toString());
                                        lbl.setFont(Font.font(null, FontWeight.SEMI_BOLD, 16));
                                        palveluVbox.getChildren().add(lbl);
                                        if(v.getVarattuAlkupvm().toLocalDate().compareTo(LocalDate.now()) > 6) {
                                            Button poistaVarauksenPalvelButton = new Button("Poista");
                                            lbl.setGraphic(poistaVarauksenPalvelButton);
                                            poistaVarauksenPalvelButton.setOnAction(poistaPalvelu -> {
                                                SQL_yhteys.poistaVarauksenPalvelu((int) v.getVarausId(), vp.getPalvelu_id());
                                                palveluVbox.getChildren().remove(lbl);
                                            });
                                        }
                                    }


                                    break;
                                }
                            }
                            if(! olemassa) {
                                VarauksenPalvelu uusiPalvelu = new VarauksenPalvelu(p, Integer.parseInt(palveluTf.getText()));
                                ArrayList<VarauksenPalvelu> uusipalveluLista = new ArrayList<>();
                                uusipalveluLista.add(uusiPalvelu);
                                SQL_yhteys.insertVarauksenPalvelut(uusipalveluLista, (int) v.getVarausId());
                                paivitetytPalvelut = SQL_yhteys.getVarauksenPalvelut((int)v.getVarausId());
                                palveluVbox.getChildren().clear();
                                palveluVbox.getChildren().addAll(lisaaPalveluLbl);
                                for (VarauksenPalvelu_Hallinta vp : paivitetytPalvelut) {
                                    Label lbl = new Label(vp.toString());
                                    lbl.setFont(Font.font(null, FontWeight.SEMI_BOLD, 16));
                                    palveluVbox.getChildren().add(lbl);
                                    if(v.getVarattuAlkupvm().toLocalDate().compareTo(LocalDate.now()) > 6) {
                                        Button poistaVarauksenPalvelButton = new Button("Poista");
                                        lbl.setGraphic(poistaVarauksenPalvelButton);
                                        poistaVarauksenPalvelButton.setOnAction(poistaPalvelu -> {
                                            SQL_yhteys.poistaVarauksenPalvelu((int) v.getVarausId(), vp.getPalvelu_id());
                                            palveluVbox.getChildren().remove(lbl);
                                            // = SQL_yhteys.getVarauksenPalvelut((int)v.getVarausId());
                                        });
                                    }
                                }
                            }
                            
                        });
                    } catch (SQLException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                });

            }
            for (VarauksenPalvelu_Hallinta vp : varauksenPalvelut) {
                Label lbl = new Label(vp.toString());
                lbl.setFont(Font.font(null, FontWeight.SEMI_BOLD, 16));
                palveluVbox.getChildren().add(lbl);
                if(peruutettavissa) {
                    Button poistaVarauksenPalvelButton = new Button("Poista");
                    lbl.setGraphic(poistaVarauksenPalvelButton);
                    poistaVarauksenPalvelButton.setOnAction(poistaPalvelu -> {
                        SQL_yhteys.poistaVarauksenPalvelu((int) v.getVarausId(), vp.getPalvelu_id());
                        palveluVbox.getChildren().remove(lbl);
                    });
                }
            }

            palveluHBox.getChildren().addAll(palveluOtsikko, palveluVbox);
            mokkiHBox.getChildren().addAll(mokkiOtsikko, mokinTiedot);
            Button peruutaVarausButton = new Button("peruuta");
            if (! peruutettavissa) {
                peruutaVarausButton.setDisable(true);
            }
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
                    try {
                        naytaVaraukset();
                    } catch (SQLException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
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

            varausBox.getChildren().addAll(peruutaVarausButton, suljeButton);
            varauksenTiedotVb.getChildren().addAll(mokkiHBox, palveluHBox);
        });

        varauksenTiedotVb.getChildren().addAll(varausOtsikko, varauksenTiedot, asiakasTiedot);
        Pane pane = new Pane();
        varausBox.getChildren().addAll(varauksenTiedotVb, pane, avaaButton);
        varausBox.setHgrow(pane, Priority.ALWAYS);
        varausBox.setAlignment(Pos.BOTTOM_LEFT);
        varausBox.setStyle("-fx-border-color: #dbd9ff");
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
                }

                // Tässä käytetään add-metodia, jolla root-node saadaan laitettua tiettyyn indeksiin: tässä 0 eli alkuun
                toimAlueVbox.getChildren().clear();
                toimAlueVbox.getChildren().add(0, root);

            } catch (IOException | SQLException e) {
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

        initMokit(null);
        initSearch();
    }

    void initMokit(String area) throws SQLException {
        mokkiVbox.getChildren().clear();
        ArrayList<Mokki> mokit = null;
        if (area == null) {
            mokit = SQL_yhteys.getMokit();
        } else {
            mokit = SQL_yhteys.getAlueenMokit(area);
        }


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

    public void lisaaMokkiButtonOnAction(ActionEvent actionEvent) throws SQLException {
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

    /**
     * haku mökissä
     */

    public ChoiceBox tAlueMokitBox;

    void initSearch() {
        ObservableList<String> toimintaAlueet = FXCollections.observableArrayList();
        try {
            ArrayList<String> alueet = SQL_yhteys.getToimintaAlueet();
            toimintaAlueet.add("Hae alueelta");
            for (String alue : alueet) {
                toimintaAlueet.add(alue);
            }
            tAlueMokitBox.setItems(toimintaAlueet);
            tAlueMokitBox.getSelectionModel().selectFirst();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        tAlueMokitBox.setOnAction(event -> {
            if (tAlueMokitBox.getValue() == null) {
                return;
            } else {
                if (!tAlueMokitBox.getValue().equals("Hae alueelta")) {
                    System.out.println(tAlueMokitBox.getValue().toString());
                    try {
                        initMokit(tAlueMokitBox.getValue().toString());
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                } else {
                    try {
                        initMokit(null);
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }
            }
        });
    }

    /**
     * LASKUJEN HALLINTA
     */
    @FXML
    public ChoiceBox laskutAlueBox;
    @FXML
    private DatePicker alkuPvmLasku;
    @FXML
    private DatePicker loppuPvmLasku;
    @FXML
    private Button lopetaLaskuButton;
    @FXML
    private Button avaaLaskuButton;
    @FXML
    private VBox laskutVBox;
    @FXML
    private Label laskujenLkmLabel;

    private Lasku muokattavaLasku;
    private int laskujenLkm;

    void initLaskuSearch() {
        ObservableList<String> toimintaAlueet = FXCollections.observableArrayList();
        try {
            ArrayList<String> alueet = SQL_yhteys.getToimintaAlueet();
            toimintaAlueet.add("Hae alueelta");
            for (String alue : alueet) {
                toimintaAlueet.add(alue);
            }
            laskutAlueBox.setItems(toimintaAlueet);
            laskutAlueBox.getSelectionModel().selectFirst();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        laskutAlueBox.setOnAction(event -> {
            if (tAlueMokitBox.getValue() == null) {
                return;
            } else {
                if (laskutAlueBox.getValue() != null){
                    if (!laskutAlueBox.getValue().equals("Hae alueelta")) {
                        System.out.println(laskutAlueBox.getValue().toString());
                        try {
                            initLaskut(laskutAlueBox.getValue().toString());
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        }
                }

                } else {
                    try {
                        initLaskut(null);
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }
            }
        });
    }

    public void laskutTabSelected(Event event) throws SQLException {
        valittu = false;
        lisays = false;
        muokattavaLasku = null;
        initLaskuSearch();
        initLaskut(null);

        // Laskut piti tehdä kaikille varauksille, joiden loppupvm < pvm nyt, LocalDate.now()
        ZoneId defaultZoneId = ZoneId.systemDefault();
        LocalDate dateNow = LocalDate.now();
        Date date = Date.from(dateNow.atStartOfDay(defaultZoneId).toInstant()); // https://beginnersbook.com/2017/10/java-convert-localdate-to-date/
        ArrayList<Varaus> varaukset = SQL_yhteys.getVarauksetNoOrder();
        ArrayList<Lasku> laskut = SQL_yhteys.getLaskut();

        /**
         * Alla oleva koodi varsin tehotonta, jossa paljon turhaa vertailua sun muuta.
         * Voisi mieluummin korvata joillakin SQL_yhteydeyn metodeilla
         * kuten hasPostinro postinumeron tapauksessa
         */

        // Talletetaan seuraavaan taulukkolistaan kaikkien laskujen varausid:t,
        // jotta voidaan vertailla niiden perusteella onko lasku jo kannassa
        ArrayList<Long> laskujenVarausId = new ArrayList<>();
        for (int i = 0; i < laskut.size(); i++) {
            laskujenVarausId.add(laskut.get(i).getVarausId());
        }
        // Tehdään taulukko, jossa on varaus_id:t ja niitä vastaavat summat
        // HashMap sopinee tähän: (avain, arvo) -pari, joita tehokasta lisätä ja hakea

        // Tätä varten tein mökkiin metodin, joka palauttaa mokkiIdtä vastaan mökin hinnan
        HashMap<Integer, Double> varauksenSumma = new HashMap<>();
        for (int i = 0; i < varaukset.size(); i++) {
            Double summa = 0.0;
            // Lisätään summaan ensiksi mökin hinta
            summa += Mokki.getMokinHinta(varaukset.get(i).getMokkiMokkiId());
            // Lopuksi summaan lisätään palveluiden hinta
            ArrayList<VarauksenPalvelu_Hallinta> varauksenpalvelut = SQL_yhteys.getVarauksenPalvelut(i);
            try {
                if (varauksenpalvelut.size() != 0 & varauksenpalvelut != null) {
                    for (int j = 0; j < varauksenpalvelut.size(); j++) {
                        summa += varauksenpalvelut.get(j).getHinta() * varauksenpalvelut.get(j).getLkm();
                    }
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }
            //System.out.println("varauksen " + i + " summa on " + summa);
            varauksenSumma.put(i, summa);
        }

        // jos varauksen loppupvm < pvm nyt ja jos laskuissa ei ole jo olemassa
        // nykyistä varaus:idtä, niin varaus voidaan laittaa tietokantaan
        for (int i = 0; i < varaukset.size(); i++) {
            // varaukset.get(i).getVarattuLoppupvm().before(date)
            // yllä olevaa on vaikea testata, koska ei voida luoda varausta, jonka pvm on ennen tätä pv
            if (!laskujenVarausId.contains(varaukset.get(i).getVarausId())) {
                // Lähetetään varaus ja varausta vastaava summa
                System.out.println(varaukset.get(i).getVarausId() + " id ja summa " + varauksenSumma.get(i));
                SQL_yhteys.insertLasku(varaukset.get(i), varauksenSumma.get(i));
                System.out.println("Lisättiin uusi lasku");
            }
        }
    }

    public void initLaskut(String area) throws SQLException {
        laskutVBox.getChildren().clear();
        ArrayList<Lasku> laskut = SQL_yhteys.getLaskut();
        laskujenLkm = 0;
        laskujenLkmLabel.setText(String.valueOf(laskujenLkm));
        // Kun haetaan laskuja alueella, pitää löytää mitä kaikkia laskuja kullakin alueella on.
        // Taulukkolistassa laskut on nyt kaikki olemassa olevat laskut. Kun tiedämme miltä alueelta
        // haluamme laskuja, voimme käydä kaikki laskut läpi, ja näyttää ne laskut, jotka kuuluvat valittuun alueeseen
        if (area != null) {
            ArrayList<Lasku> alueenLaskut = new ArrayList<>();
            for (int i = 0; i < laskut.size(); i++) {
                int k = (int) laskut.get(i).getVarausId(); // Muutetaan long intiksi
                System.out.println("varauksen toim.alue "
                        + Mokki.getMokinToimintaAlue(Varaus.getVarauksenMokki(k)) + " haun alue " + area);
                // TODO: korjaa tämä! Alueita ei voi suodattaa enää laskuissa
                System.out.println("tämä " + k);
                System.out.println("varauksen mökki " + Varaus.getVarauksenMokki(k));
                System.out.println("haettava " + Mokki.getMokinToimintaAlue(Varaus.getVarauksenMokki(k)).getToimintaalue_nimi());
                if (Mokki.getMokinToimintaAlue(Varaus.getVarauksenMokki(k)).getToimintaalue_nimi().equalsIgnoreCase(area)) {
                    System.out.println("LISÄTTIIN");
                    alueenLaskut.add(laskut.get(i));
                }
            }
            laskut = alueenLaskut;
        }

        if (laskut != null) {
            for (int i = 0; i < laskut.size(); i++) {
                try {
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("lasku.fxml"));
                    Parent root = loader.load();

                    LaskuController controller = loader.getController();
                    controller.initData(laskut.get(i));
                    laskujenLkm++;
                    final Lasku k = laskut.get(i);
                    AtomicInteger z = new AtomicInteger();


                    root.setOnMousePressed(event1 -> {
                        z.getAndIncrement();
                        if (z.get() % 2 == 1 & muokattavaLasku == null) {
                            avaaLaskuButton.setText("Toimenpiteet");
                            root.setStyle("-fx-background-color: #dbd9ff; " +
                                    "-fx-border-color: #40424a; -fx-border-width: 3");
                            valittu = true;
                            muokattavaLasku = k;

                        } else if (valittu == true & k.equals(muokattavaLasku)) {
                            avaaLaskuButton.setText("Valitse lasku");
                            root.setStyle("-fx-background-color: #f4f4f4; " +
                                    "-fx-border-color: #dbd9ff; -fx-border-width: 1");
                            valittu = false;
                            muokattavaLasku = null;
                        } else {
                            return;
                        }
                    });
                    laskutVBox.getChildren().add(root);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            laskujenLkmLabel.setText("0");
        }
        laskujenLkmLabel.setText(String.valueOf(laskujenLkm));
    }

        /*public void avaaLaskuButton(ActionEvent actionEvent) throws SQLException {
            if (!lisays) {
                try {
                    avaaLaskuButton.setDisable(true);
                    lopetaLaskuButton.setDisable(false);
                    lopetaLaskuButton.setOpacity(1);
                    lisays = true;
                    avaaLaskuButton.setText("Lisää mokki");
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("uusimokki.fxml"));
                    Parent root = loader.load();
                    UusiMokkiController controller = loader.getController();

                    if (muokattavaLasku != null) {
                        controller.initData(muokattavaLasku);
                    }

                    // Tässä käytetään add-metodia, jolla root-node saadaan laitettua tiettyyn indeksiin: tässä 0 eli alkuun
                    laskutVBox.getChildren().clear();
                    laskutVBox.getChildren().add(0, root);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }*/

    public void actionPerformedRefreshLaskut(Event e) throws SQLException {
        laskutTabSelected(e);
    }

    public void lopetaLaskuButtonOnAction(ActionEvent actionEvent) {
    }

    public void avaaLaskuButtonOnAction(ActionEvent actionEvent) {
    }

    @FXML
    public Label asiakasIdLabel;

    @FXML
    public Label etunimiLabel;

    @FXML
    public Label osoiteLabel;

    @FXML
    public Label sukunimiLabel;

    @FXML
    public Label postinroLabel;

    @FXML
    public Label puhnroLabel;

    @FXML
    public Label spostiLabel;

    @FXML
    public Button muokkaaAsiakasButton, lopetaAsiakasButton;

    Asiakas muokattavaAsiakas;

    @FXML
    public VBox asiakasVBox;
    @FXML
    private TextField puhnroRajausTextField;

    public void asiakkaatTabSelected(Event event) throws SQLException {
        valittu = false;
        lisays = false;
        muokattavaAsiakas = null;
        muokkaaAsiakasButton.setDisable(false);

        initAsiakkaat(null);
        initSearch();
    }

    void initAsiakkaat(String puhnro) throws SQLException {
        asiakasVBox.getChildren().clear();
        ArrayList<Asiakas> asiakkaat = null;
        if (puhnroRajausTextField.getText() == null || puhnroRajausTextField.getText().trim().isEmpty() || ! VarausController.onNumero(puhnroRajausTextField.getText())) {
            asiakkaat = SQL_yhteys.getAsiakkaat();
        } else {
            asiakkaat = SQL_yhteys.getAsiakkaat(puhnroRajausTextField.getText());
        }


        for (int i = 0; i < asiakkaat.size(); i++) {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("asiakas.fxml"));
                Parent root = loader.load();

                AsiakasController controller = loader.getController();
                controller.initData(asiakkaat.get(i));
                final Asiakas k = asiakkaat.get(i);
                AtomicInteger z = new AtomicInteger();

                root.setOnMousePressed(event1 -> {
                    z.getAndIncrement();
                    if (z.get() % 2 == 1 & muokattavaAsiakas == null) {
                        muokkaaAsiakasButton.setText("Muokkaa");
                        root.setStyle("-fx-background-color: #dbd9ff; " +
                                "-fx-border-color: #40424a; -fx-border-width: 3");
                        valittu = true;
                        muokattavaAsiakas = k;

                    } else if (valittu == true & k.equals(muokattavaAsiakas)) {
                        muokkaaAsiakasButton.setText("Muokkaa asiakasta");
                        root.setStyle("-fx-background-color: #f4f4f4; " +
                                "-fx-border-color: #dbd9ff; -fx-border-width: 1");
                        valittu = false;
                        muokattavaAsiakas = null;
                    } else {
                        return;
                    }
                });
                asiakasVBox.getChildren().add(root);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void muokkaaAsiakasButtonOnAction(ActionEvent actionEvent) throws SQLException {
        if (!lisays) {
            try {
                muokkaaAsiakasButton.setDisable(true);
                lopetaAsiakasButton.setDisable(false);
                lopetaAsiakasButton.setOpacity(1);
                lisays = true;
                muokkaaAsiakasButton.setText("Muokkaa asiakasta");
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("uusiAsiakas.fxml"));
                Parent root = loader.load();
                uusiAsiakasController controller = loader.getController();

                if (muokattavaAsiakas != null) {
                    controller.initData(muokattavaAsiakas);
                }

                // Tässä käytetään add-metodia, jolla root-node saadaan laitettua tiettyyn indeksiin: tässä 0 eli alkuun
                asiakasVBox.getChildren().clear();
                asiakasVBox.getChildren().add(0, root);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void actionPerformedRefreshAsiakas(Event e) throws SQLException {
        asiakkaatTabSelected(e);
    }

    public void lopetaAsiakasButton(ActionEvent actionEvent) throws SQLException {
        asiakasVBox.getChildren().remove(muokattavaAsiakas.getAsiakasId());
        lisays = false;
        lopetaAsiakasButton.setDisable(true);
        lopetaAsiakasButton.setOpacity(0.1);
        lopetaAsiakasButton.setDisable(false);
        actionPerformedRefreshAsiakas(e);
        valittu = false;
        lisays = false;
        muokattavaAsiakas = null;
    }

    public void suodataAsiakas(KeyEvent keyEvent) {
        try {
            initAsiakkaat(null);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}


        /*public void lopetaMokkiButtonOnAction(ActionEvent actionEvent) throws SQLException {
            laskutVBox.getChildren().remove(0);
            lisays = false;
            lopetaLaskuButton.setDisable(true);
            lopetaLaskuButton.setOpacity(0.1);
            avaaLaskuButton.setDisable(false);
            actionPerformedRefreshLaskut(e);
            valittu = false;
            lisays = false;
            muokattavaLasku = null;
            avaaLaskuButton.setText("Lisää mokki");
        }*/

