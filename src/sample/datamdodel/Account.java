package sample.datamdodel;

public class Account{
    private Employee employee;
    private String access;

    public Account(Employee employee, String access) {
        this.employee = employee;
        this.access = access;
    }

    public String getNameEmployee(){
        return  employee.getName();
    }

    public String getSurnameEmployee(){
        return  employee.getSurname();
    }

    public String getLoginEmployee(){
        return  employee.getLogin();
    }

    public String getPasswordEmployee(){
        return  employee.getPassword();
    }

    public String getAccess() {
        return access;
    }

    public void setAccess(String access) {
        this.access = access;
    }

    @Override
    public String toString() {
        return "Account{" +
                "employee=" + employee.toString() +
                ", access='" + access + '\'' +
                '}';
    }
}
