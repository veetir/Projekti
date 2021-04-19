package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.sql.*;

public class Controller {

    @FXML
    // Mökki
    public TextField moknimiTextField, mokzipTextField, mokhloTextField, mokosoiteTextField, mokalueidTextField, mokvarusteluTextField, mokkuvausTextField;
    // Asiakas
    public TextField etunimiTextField, sukunimiTextField, zipTextField, emailTextField, puhnroTextField, osoiteTextField;

    public void varausButtonOnAction(ActionEvent actionEvent) {
    }

    public void takaisinButtonOnAction(ActionEvent actionEvent) {
    }

    public void mokvButtonOnAction(ActionEvent actionEvent) {
    }

    public void astietButtonOnAction(ActionEvent actionEvent) {
    }

    public void toimalueButtonOnAction(ActionEvent actionEvent) {
    }

    public void palvhalButtonOnAction(ActionEvent actionEvent) {
    }

    public void suodatin3SetOnAction(ActionEvent actionEvent) {
    }

    public void suodatin2SetOnAction(ActionEvent actionEvent) {
    }

    public void suodatin1SetOnAction(ActionEvent actionEvent) {
    }

    public void lisaamokkiButtonOnAction(ActionEvent actionEvent) {
        // https://www.mysqltutorial.org/mysql-jdbc-insert/ <- idea otettu suoraan täältä
        String nimi = moknimiTextField.getText();
        String osoite = mokosoiteTextField.getText();
        String zip = mokzipTextField.getText();
        String kuvaus = mokkuvausTextField.getText();
        String hlolkm = mokhloTextField.getText();
        String varustelu = mokvarusteluTextField.getText();
        String alueid = mokalueidTextField.getText();
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
            if(rowAffected == 1)
            {
                // process further here
            }

            // get candidate id
            int candidateId = 0;
            ResultSet rs = pstmt.getGeneratedKeys();
            if(rs.next())
                candidateId = rs.getInt(1);



        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
        }
    }

    public void moklisaustakaisinButtonOnAction(ActionEvent actionEvent) {
    }
}
