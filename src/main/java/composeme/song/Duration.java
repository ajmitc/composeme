package composeme.song;

public enum Duration {
    WHOLE('w'),
    HALF('h'),
    QUARTER('q'),
    EIGHTH('i'),
    SIXTEENTH('s'),
    THIRTY_SECOND('t'),
    SIXTY_FOURTH('x'),
    ONE_TWENTY_EIGHTH('n');

    private char code;
    Duration(char code){
        this.code = code;
    }

    public char getCode() {
        return code;
    }
}
