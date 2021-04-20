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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class MokkiController implements Initializable {
    ObservableList<String> list = FXCollections.observableArrayList(); // Alueet
    @FXML
    // Mökki
    public TextField moknimiTextField, mokzipTextField, mokhloTextField, mokosoiteTextField;
    public TextArea mokvarusteluTextArea, mokkuvausTextArea;
    public ChoiceBox<String> mokkialueChoiceBox;


    @Override // https://www.youtube.com/watch?v=cQK2Yh_kayY
    public void initialize(URL url, ResourceBundle rb) {
        loadData();
    }

    private void loadData() {
        String a = "1", b = "2", c = "3";
        list.addAll(a, b, c);
        mokkialueChoiceBox.getItems().addAll(list);
    }

    public void lisaamokkiButtonOnAction(ActionEvent actionEvent) throws SQLException {
        // https://www.mysqltutorial.org/mysql-jdbc-insert/ <- idea otettu suoraan täältä
        String nimi = moknimiTextField.getText();
        String osoite = mokosoiteTextField.getText();
        String zip = mokzipTextField.getText();
        String kuvaus = mokkuvausTextArea.getText();
        String hlolkm = mokhloTextField.getText();
        String varustelu = mokvarusteluTextArea.getText();
        String alueid = mokkialueChoiceBox.getValue();
        String kysely = "INSERT INTO mokki(toimintaalue_id, postinro, mokkinimi, katuosoite, kuvaus, henkilomaara, varustelu) "
                + "VALUES(?, ?, ?, ?, ?, ?, ?);";

        try {
            Connection conn = SQL_yhteys.getYhteys();
            PreparedStatement pstmt = conn.prepareStatement(kysely,
                    Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, alueid);
            pstmt.setString(2, zip);
            pstmt.setString(3, nimi);
            pstmt.setString(4, osoite);
            pstmt.setString(5, kuvaus);
            pstmt.setString(6, hlolkm);
            pstmt.setString(7, varustelu);

            int rowAffected = pstmt.executeUpdate();
            if (rowAffected == 1) {
                // process further here
            }

            // get candidate id
            int candidateId = 0;
            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next())
                candidateId = rs.getInt(1);


        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
        }
        SQL_yhteys.getMokit();
    }

    // ks. https://www.youtube.com/watch?v=XCgcQTQCfJQ
    // "Using SceneBuilder and Controller class to change scenes in Javafx"
    public void moklisaustakaisinButtonOnAction(ActionEvent actionEvent) throws IOException {
        Parent toiseenNakymaan = FXMLLoader.load(getClass().getResource("varaushallinta.fxml"));
        Scene toinenScene = new Scene(toiseenNakymaan);

        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow(); // ks. 11:30 videolta
        window.setScene(toinenScene);
        window.show();
    }
}