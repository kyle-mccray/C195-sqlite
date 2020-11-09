package loginScreen;

import java.sql.Connection;
import java.sql.DriverManager;

public class SqliteHelper {
    private static Connection c = null;
    public static Connection getConn() throws Exception {
        if(c == null){
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\Kyle\\Documents\\Projects\\C195\\src\\my_db");
        }
        return c;
    }
}
