package ar.edu.itba.pod.rmi;

public enum Categories {
    A(1),
    B(2),
    C(3),
    D(4),
    E(5),
    F(6);

    private final Integer authorization;

    Categories(int authorization){
        this.authorization = authorization;
    }
    public boolean isHigherOrEqual(Categories other){
        return this.authorization >= other.authorization;
    }

    public Integer getAuthorization() {
        return authorization;
    }

    public static int maxAuthorization() {
        return F.authorization;
    }

    @Override
    public String toString() {
        String authorizationString;
        switch (authorization) {
            case 1: authorizationString = "A";
                    break;
            case 2: authorizationString = "B";
                break;
            case 3: authorizationString = "C";
                break;
            case 4: authorizationString = "D";
                break;
            case 5: authorizationString = "E";
                break;
            case 6: authorizationString = "F";
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + authorization);
        }
        return authorizationString;
    }

    public static Categories parseString(String s) {
        if( s == null ) {
            return null;
        }
        Categories category;
        switch (s) {
            case "A": category = Categories.A;
                break;
            case "B": category = Categories.B;
                break;
            case "C": category = Categories.C;
                break;
            case "D": category = Categories.D;
                break;
            case "E": category = Categories.E;
                break;
            case "F": category = Categories.F;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + s);
        }
        return category;
    }
}
