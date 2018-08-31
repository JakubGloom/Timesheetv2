package sample.conectivity;

import java.sql.*;

import com.sun.rowset.CachedRowSetImpl;

public class ConnectionManager{
    private static final String url = "jdbc:mysql://localhost:3306/databasetests?useSSL=false";
    private static final String JDBC_DRIVER  = "com.mysql.jdbc.Driver";
    private static final String username = "root";
    private static final String password = "Susanoo12345@";
    private static Connection con = null;

    public static Connection dbConnect() throws SQLException, ClassNotFoundException {
        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            System.out.println("Where is your Oracle JDBC Driver?");
            e.printStackTrace();
            throw e;
        }
        try {
            con = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            System.out.println("Connection Failed! Check output console" + e);
            e.printStackTrace();
            throw e;
        }
        return con;
    }

    public static Connection dbDisconnect() throws SQLException {
        try {
            if (con != null && !con.isClosed()) {
                con.close();
            }
        } catch (Exception e){
            throw e;
        }
        return con;
    }

    public static ResultSet dbExecuteQuery(String queryStmt) throws SQLException, ClassNotFoundException {

        Statement stmt = null;
        ResultSet resultSet = null;
        CachedRowSetImpl crs;
        try {

            dbConnect();
            System.out.println(queryStmt + "\n");

            stmt = con.createStatement();

            resultSet = stmt.executeQuery(queryStmt);

            crs = new CachedRowSetImpl();
            crs.populate(resultSet);
        } catch (SQLException e) {
            System.out.println("Problem occurred at executeQuery operation : " + e);
            throw e;
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
            if (stmt != null) {
                stmt.close();
            }
            dbDisconnect();
        }
        return crs;
    }

    public static void dbExecuteUpdate(String sqlStmt) throws SQLException, ClassNotFoundException {
        //Declare statement as null
        Statement stmt = null;
        try {
            //Connect to DB (Establish Oracle Connection)
            dbConnect();
            //Create Statement
            stmt = con.createStatement();
            //Run executeUpdate operation with given sql statement
            stmt.executeUpdate(sqlStmt);
        } catch (SQLException e) {
            System.out.println("Problem occurred at executeUpdate operation : " + e);
            throw e;
        } finally {
            if (stmt != null) {
                //Close statement
                stmt.close();
            }
            //Close connection
            dbDisconnect();
        }
    }

    public static int getId(String idEmployee) throws SQLException, ClassNotFoundException {
        PreparedStatement pstmt = null;
        int id = 0;
        try{
            dbConnect();
            pstmt = con.prepareStatement(idEmployee, Statement.RETURN_GENERATED_KEYS);
            pstmt.executeUpdate();
            ResultSet resultSet = pstmt.getGeneratedKeys();

            if (resultSet.next()){
                id = resultSet.getInt(1);
            }
        }
        catch (SQLException e){
            System.out.println("Problem occured at getting id: " + e);
            throw e;
        }
        finally {
            if (pstmt != null) {
                pstmt.close();
            }
            dbDisconnect();
        }
        return id;
    }
}