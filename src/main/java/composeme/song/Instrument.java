package composeme.song;

public enum Instrument {
    PIANO("Piano"),
    FLUTE("Flute"),
    TUBULAR_BELLS("Tubular_Bells"),
    CHURCH_ORGAN("CHURCH_ORGAN"),
    WARM("Warm"),
    VOICE("Voice");

    private String name;
    Instrument(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
