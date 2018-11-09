package buildings.dwelling.hotel;

public enum Stars {
    ONE_STAR(1, 0.25),
    TWO_STARS(2, 0.5),
    THREE_STARS(3, 1),
    FOUR_STARS(4, 1.25),
    FIVE_STARS(5, 1.5);

    private double coef;
    private int value;

    Stars(int value, double coef) {
        this.coef = coef;
        this.value = value;
    }

    public double coef() {
        return coef;
    }

    public int value() {
        return value;
    }

}
