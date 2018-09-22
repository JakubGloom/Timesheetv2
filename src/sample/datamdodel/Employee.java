package sample.datamdodel;

public class Employee {
    private int idEmployee;
    private String name;
    private String surname;
    private String login;
    private String password;
    private String access;
    private int seed;
    private String fullName = " ";

    public static Employee loggedEmployee;

    public Employee(){}
    public Employee(int idEmployee, String name, String surname, String login, String password) {
        this.idEmployee = idEmployee;
        this.name = name;
        this.surname = surname;
        this.login = login;
        this.password = password;
    }

    public Employee(int idEmployee, String name, String surname, String login, String password, String access) {
        this.idEmployee = idEmployee;
        this.name = name;
        this.surname = surname;
        this.login = login;
        this.password = password;
        this.access = access;
    }

    public Employee(int idEmployee, String name, String surname) {
        this.idEmployee = idEmployee;
        this.name = name;
        this.surname = surname;
        this.fullName = name + " " + surname;
    }

    public int getIdEmployee() {
        return idEmployee;
    }

    public void setIdEmployee(int idEmployee) {
        this.idEmployee = idEmployee;
    }

    public String getName() {
        return name;
    }

    public void setName(String nameame) {
        this.name = nameame;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAccess() {
        return access;
    }

    public void setAccess(String access) {
        this.access = access;
    }

    public int getSeed() {
        return seed;
    }

    public void setSeed(int seed) {
        this.seed = seed;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "idEmployee=" + idEmployee +
                ", nameame='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", access='" + access + '\'' +
                ", seed=" + seed +
                '}';
    }
}
