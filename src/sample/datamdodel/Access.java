package sample.datamdodel;

public enum Access {
    Employee(1),
    Moderator(2),
    Admin(3);

    private int value;

    Access(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
