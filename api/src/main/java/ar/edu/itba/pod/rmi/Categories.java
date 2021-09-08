package ar.edu.itba.pod.rmi;

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
}
