package main;

public enum Colori {
    Rosso("#FF4560"),
    Verde("#00E396"),
    Giallo("#FEB019"),
    Blu("#008FFB"),
    Viola("#9C27B0"),
    Grigio("#C4C4C4");
    
    public final String hex;
    
    private Colori(String hex) {
        this.hex = hex;
    }
}
