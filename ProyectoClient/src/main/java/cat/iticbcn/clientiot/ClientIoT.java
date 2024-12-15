/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
*/

package cat.iticbcn.clientiot;

import java.sql.Connection;
import java.sql.SQLException;

/**
 *
* @author david
*/
public class ClientIoT {

    static final String url = "jdbc:mysql://192.168.22.1:3306/Proyecto";
    static final String usuario = "proyecto";
    static final String contrasena = "pirineus";

    public static void main(String[] args) {

        DispositiuIot disp = new DispositiuIot();

        try {
            disp.conecta();
            disp.subscriu();

            try (Connection con = ConnectDB.getConnection(url, usuario, contrasena)) {
                if (con != null && !con.isClosed()) {
                    System.out.println("Conexión a la BBDD establecida correctamente.");
                }
                AccesMethodsToDB access = new AccesMethodsToDB();
                access.selectAlumnes(con);
                //access.insertRegistry(con, null);
            } catch (SQLException e) {
                System.out.println("Error de conexión: " + e.getMessage());
            }
        } catch (Exception e) {
            System.err.println("Error IOT: " + e.getLocalizedMessage());
            System.exit(-1);
        }

    }
}
