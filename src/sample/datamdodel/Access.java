package sample.datamdodel;

public enum Access {
    Employee(1),
    Admin (2);

    private int value;

    Access(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
