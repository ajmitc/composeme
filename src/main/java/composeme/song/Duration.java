package composeme.song;

public enum Duration {
    WHOLE('w', 4),
    HALF('h', 2),
    QUARTER('q', 1),
    EIGHTH('i', 0.5),
    SIXTEENTH('s', 0.25),
    THIRTY_SECOND('t', 0.125),
    SIXTY_FOURTH('x', 0.0625),
    ONE_TWENTY_EIGHTH('n', 0.03125);

    private char code;
    private double beats;
    Duration(char code, double beats){
        this.code = code;
        this.beats = beats;
    }

    public char getCode() {
        return code;
    }

    public double getBeats() {
        return beats;
    }
}
