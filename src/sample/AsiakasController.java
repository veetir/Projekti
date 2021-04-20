package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class AsiakasController {
    @FXML
    // Asiakas
    public TextField etunimiTextField, sukunimiTextField, zipTextField, emailTextField, puhnroTextField, osoiteTextField;

    public void varausButtonOnAction(ActionEvent actionEvent) {
        // https://www.mysqltutorial.org/mysql-jdbc-insert/
        String etunimi = etunimiTextField.getText();
        String sukunimi = sukunimiTextField.getText();
        String osoite = osoiteTextField.getText();
        String zip = zipTextField.getText();
        String email = emailTextField.getText();
        String puhnro = puhnroTextField.getText();
        String kysely = "INSERT INTO asiakas(postinro, etunimi, sukunimi, lahiosoite, email, puhelinnro) "
                + "VALUES(?, ?, ?, ?, ?, ?);";

        try {
            Connection conn = SQL_yhteys.getYhteys();
            PreparedStatement pstmt = conn.prepareStatement(kysely,
                    Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, zip);
            pstmt.setString(2, etunimi);
            pstmt.setString(3, sukunimi);
            pstmt.setString(4, osoite);
            pstmt.setString(5, email);
            pstmt.setString(6, puhnro);

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
    }


    public void takaisinButtonOnAction(ActionEvent actionEvent) throws IOException {
        Parent toiseenNakymaan = FXMLLoader.load(getClass().getResource("varaushallinta.fxml"));
        Scene toinenScene = new Scene(toiseenNakymaan);

        Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        window.setScene(toinenScene);
        window.show();
    }
}
