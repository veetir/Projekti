package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class VarausController implements Initializable {
    ObservableList<String> list = FXCollections.observableArrayList();
    @FXML
    public ChoiceBox<String> hakuehdotChoiceBox;

    @Override // https://www.youtube.com/watch?v=cQK2Yh_kayY
    public void initialize(URL url, ResourceBundle rb) {
        loadData();
    }

    private void loadData() {
        String a = "-", b = "etunimi", c = "mokkinimi";
        list.addAll(a, b, c);
        hakuehdotChoiceBox.getItems().addAll(list);
    }


    public void suodatin3SetOnAction(ActionEvent actionEvent) {
    }

    public void suodatin2SetOnAction(ActionEvent actionEvent) {
    }

    public void suodatin1SetOnAction(ActionEvent actionEvent) {
    }


    public void mokinlisaykseenButtonOnAction(ActionEvent actionEvent) throws IOException {
        Parent toiseenNakymaan = FXMLLoader.load(getClass().getResource("mokki.fxml"));
        Scene toinenScene = new Scene(toiseenNakymaan);

        Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        window.setScene(toinenScene);
        window.show();
    }


    public void kotinakymaanButtonOnAction(ActionEvent actionEvent) throws IOException {
        Parent toiseenNakymaan = FXMLLoader.load(getClass().getResource("hallinta.fxml"));
        Scene toinenScene = new Scene(toiseenNakymaan);

        Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow(); // ks. 11:30 videolta
        window.setScene(toinenScene);
        window.show();
    }

    public void asiakkaanlisaysButtonOnAction(ActionEvent actionEvent) throws IOException {
        Parent toiseenNakymaan = FXMLLoader.load(getClass().getResource("asiakas.fxml"));
        Scene toinenScene = new Scene(toiseenNakymaan);

        Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        window.setScene(toinenScene);
        window.show();
    }

    public void hakuehdotChoiceBoxOnAction(MouseEvent mouseEvent) {
    }
}
