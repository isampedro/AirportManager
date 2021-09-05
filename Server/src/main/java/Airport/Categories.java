package Airport;

public enum Categories {
    A(1),
    B(2),
    C(3),
    D(4),
    E(5),
    F(6);

    private Integer authorization;

    Categories(int authorization){
        this.authorization = authorization;
    }
    public boolean isHigherOrEqual(Categories other){
        return this.authorization >= other.authorization;
    }
}
