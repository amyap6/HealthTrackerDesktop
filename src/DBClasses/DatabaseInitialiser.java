package DBClasses;

import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *  --NOT FOR USAGE AFTER INITIAL DB CREATION--
 *  Class to be used at initial implementation of DB to create tables etc.
 */
public class DatabaseInitialiser extends DBAccess{

    public static void main(String[] args) {

        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, UN, PW);
            st = conn.createStatement();

            //st.executeUpdate("");

            st.close();
            conn.close();
            System.out.println("Table created successfully");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

}
