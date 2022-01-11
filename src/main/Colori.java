package main;

public enum Colori {
    ROSSO("#FF4560"),
    VERDE("#00E396"),
    GIALLO("#FEB019"),
    BLU("#008FFB"),
    VIOLA("#9C27B0"),
    GRIGIO("#C4C4C4");
    
    public final String hex;
    
    private Colori(String hex) {
        this.hex = hex;
    }
}
