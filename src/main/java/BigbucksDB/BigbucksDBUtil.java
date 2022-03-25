package BigbucksDB;
import yahoofinance.YahooStockAPI;
import yahoofinance.histquotes.HistoricalQuote;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class BigbucksDBUtil {
    private static final String PROTOCOL = "jdbc:derby:";
    private static final String DRIVER = "org.apache.derby.jdbc.EmbeddedDriver";
    private static final String DBNAME = "bigbucks";

    public static BigbucksDBUtil instance = null;
    private Connection connection = null;


    private BigbucksDBUtil() {
        /*
         **
         **			Default location for the database is current directory:
         **			System.out.println(System.getProperty("user.home"));
         **			to change DB location, set derby.system.home property:
         **			System.setProperty("derby.system.home", "[new_DB_location]");
         **
         */

    }

    public static Connection getconnection() throws SQLException {
        if (instance == null) {
            instance = new BigbucksDBUtil();
        }

        try {
            instance.connection = DriverManager.getConnection(PROTOCOL + DBNAME);
        } catch (SQLException e) {
            //if database does not exist, create it and initialize it
            if (e.getErrorCode() == 40000) {
                instance.connection = DriverManager.getConnection(PROTOCOL + DBNAME + ";create=true");
                instance.initDB();
            } else {
                throw e;
            }
        }

        return instance.connection;
    }

    public void initDB() throws SQLException {
        Statement statement = connection.createStatement();
        try {
            statement.execute("DROP TABLE PEOPLE");
            statement.execute("DROP TABLE HISTORICAL");

            //statement.execute("DROP TABLE PORTFOLIO");
        } catch (SQLException e) {
            // fail is not a problem
        }
        statement.execute("CREATE TABLE PEOPLE (USER_ID VARCHAR(50) NOT NULL, PASSWORD VARCHAR(20) NOT NULL, " +
                "FIRST_NAME VARCHAR(100) NOT NULL, LAST_NAME VARCHAR(100) NOT NULL, PRIMARY KEY (USER_ID))");

        statement.execute("Create Table HISTORICAL (id int not null generated always as identity,"
                + "symbol varchar(5), date DATE, adj_price float)");

    }

    private static String converDate(Calendar cal){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String format1 = format.format(cal.getTime());
        return format1;
    }

    public static void DBstoreData() throws SQLException, IOException {
        Connection connection = getconnection();
        Statement statement = connection.createStatement();

        YahooStockAPI yahooStockAPI = new YahooStockAPI();
        List<HistoricalQuote> history = yahooStockAPI.getHistory("GOOG", 1, "daily");

        for(HistoricalQuote quote : history){

            String symbol = quote.getSymbol();
            BigDecimal adjprice = quote.getClose();
            String date = converDate(quote.getDate());

            String sql = "Insert into Historical (symbol, date, adj_price) values ('"+symbol+"','"+date+"',"+ adjprice+")";
            statement.executeUpdate(sql);

        }


    }





    public static void main(String[] args) throws SQLException, IOException {
//        Connection connection = DriverManager.getConnection(PROTOCOL + "bigbucks;create=true");
        BigbucksDBUtil db = new BigbucksDBUtil();
        db.DBstoreData();
//        Connection connection = db.getconnection();
//        instance.initDB();

//        Statement statement = connection.createStatement();
//
//        statement.executeUpdate("Insert into Historical (symbol, date, adj_price) values ('aapl', '2022-03-07', 202.1321)");
//        statement.executeUpdate("Insert into Historical (symbol, date, adj_price) values ('aapl', '2022-03-08',203.1321)");
//        statement.executeUpdate("Insert into Historical (symbol, date, adj_price) values ('aapl', '2022-03-09',204.1321)");
//        statement.executeUpdate("Insert into Historical (symbol, date, adj_price) values ('aapl', '2022-03-10',205.1321)");
//        System.out.println("a row created");

    }
}




//        try{


//
//

//
////            String sql = "Create Table Historical (id int not null generated always as identity,"
////                    + "stock varchar(128), symbol varchar(128), date DATE, openprice float, " +
////                    "closeprice float, highprice float, lowprice float, adjprice float, volume int)";
//
//            String sql = "Insert into Historical (stock, symbol) values ('AAPL',1200)";
//            Statement statement = connection.createStatement();
//            int rows = statement.executeUpdate(sql);
//
//            if (rows>0){
//                System.out.println("A row created");
//            }
//
//
//
//        }
//        catch (SQLException e){
//            e.printStackTrace();
//        }
//    }

