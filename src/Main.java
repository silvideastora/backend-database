import org.apache.log4j.Logger;

import java.sql.*;

public class Main {
    public static Logger LOGGER = Logger.getLogger(Main.class);
    public static String SQL_CREATE = "DROP TABLE IF EXISTS FIGURAS;" +
            "CREATE TABLE FIGURAS (ID INT AUTO_INCREMENT PRIMARY KEY," +
            "FIGURA VARCHAR(50) NOT NULL, COLOR VARCHAR(50) NOT NULL)";

    public static String SQL_INSERT = "INSERT INTO FIGURAS VALUES (DEFAULT, 'CIRCULO', 'ROJO')," +
            "(DEFAULT, 'CUADRADO', 'VERDE'), (DEFAULT, 'CUADRADO', 'VERDE'), (DEFAULT, 'CUADRADO', 'VERDE')," +
            "(DEFAULT, 'CIRCULO', 'ROJO')";
    public static String SQL_QUERY = "SELECT  * FROM FIGURAS WHERE COLOR = 'ROJO'";
    public static String SQL_SELECT = "SELECT *FROM FIGURAS";

    public static void main(String[] args) {
        Connection connection = null;
        try{
            connection = getConnection();
            Statement statement = connection.createStatement();
            // creamos la tabla FIGURAS
            statement.execute( SQL_CREATE);
            // insertar datos en la tabla
            statement.execute(SQL_INSERT);
            // consultar todos los datos
            ResultSet resultSet = statement.executeQuery(SQL_QUERY);
            while (resultSet.next()){
                LOGGER.info("Figura: "+ resultSet.getInt(1)+ " - " + resultSet.getString(2)
                        +" - " + resultSet.getString(3));
            }
            // eliminar una figura
            statement.execute(SQL_SELECT);

            // consultar nuevamente todos las figuras
            LOGGER.info("------------------------------------------------------------");
            resultSet = statement.executeQuery(SQL_SELECT);
            while (resultSet.next()){
                LOGGER.info("Figura: "+ resultSet.getInt(1)+ " - " + resultSet.getString(2)
                        +" - " + resultSet.getString(3));
            }


        }catch (Exception e){
            e.printStackTrace();
            LOGGER.error(e.getMessage());
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
                LOGGER.error(e.getMessage());
            }
        }
    }

    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("org.h2.Driver");
        return DriverManager.getConnection("jdbc:h2:~/clase8","","");
    }
}
