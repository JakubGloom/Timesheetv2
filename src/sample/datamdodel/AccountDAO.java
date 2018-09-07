package sample.datamdodel;

import java.sql.ResultSet;



public class AccountDAO {

    private static ResultSet rsEmp;
    private static StringBuilder stringBuilder;

    /*
    public static ObservableList<Account> searchEmployees () throws SQLException, ClassNotFoundException {

        String selectStmt = "SELECT employee.idEmployee, employee.Name, employee.Surname, " +
                "employee.Login, employee.Password, acess.Type " +
                "FROM account JOIN employee ON account.idEmployee=employee.idEmployee " +
                "JOIN acess ON account.idAcess = acess.idAcess";

        try {
            ResultSet rsAccount = ConnectionManager.dbExecuteQuery(selectStmt);

            ObservableList<Account> accountsList = getAccountList(rsAccount);
            return accountsList;

        } catch (SQLException e) {
            System.out.println("SQL select operation has been failed: " + e);
            throw e;
        }
    }

    private static ObservableList<Account> getAccountList(ResultSet rs) throws SQLException{

        ObservableList<Account> accountsList = FXCollections.observableArrayList();
        while (rs.next()) {
            Account account = new Account(new Employee(rs.getInt("idEmployee"), rs.getString("Name"), rs.getString("Surname"),
                    rs.getString("Login"), rs.getString("Password")),rs.getString("Type"));
            accountsList.add(account);
        }
        return accountsList;
    }
    */
}
