package cat.iticbcn.clientiot;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import com.amazonaws.services.iot.client.AWSIotMessage;

public class AccesMethodsToDB {

    public void selectAlumnes(Connection con) {
        String sql = "SELECT * FROM usuario"; // Consulta SQL

        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int id = rs.getInt("userid");
                String nombre = rs.getString("nombre");
                System.out.println("ID: " + id + ", Nom: " + nombre);
            }

        } catch (SQLException e) {
            System.out.println("Error al ejecutar la consulta: " + e.getMessage());
        }
    }

    public void insertRegistry(Connection con, AWSIotMessage message) {
        // Obtener el payload del mensaje
        String payload = message.getStringPayload();

        // Verificar que no sea nulo
        if (payload == null || payload.isEmpty()) {
            System.out.println("Error: El payload del mensaje está vacío o es nulo.");
            return;
        }

        // Consulta SQL para insertar 
        String sql = "INSERT INTO fichaje (cardid, deviceid) VALUES (?, ?)";

        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            // Comprobar valores es de deviles
            pstmt.setString(1, payload); // cardid
            pstmt.setString(2, payload); // deviceid 

            // Ejecutar insert
            int rowsAffected = pstmt.executeUpdate();
            System.out.println("Registro insertado con éxito. Filas afectadas: " + rowsAffected);

        } catch (SQLException e) {
            System.out.println("Error al realizar el insert: " + e.getMessage());
        }
    }
}

