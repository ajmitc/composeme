package composeme.song;

public enum Accidental {
    FLAT('b'),
    SHARP('#'),
    NATURAL('n');

    private char code;
    Accidental(char code){
        this.code = code;
    }

    public char getCode() {
        return code;
    }
}
