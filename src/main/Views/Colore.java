package main.Views;

public enum Colore {
    Rosso("#FF4560"),
    Verde("#00E396"),
    Giallo("#FEB019"),
    Blu("#008FFB"),
    Viola("#9C27B0"),
    Grigio("#C4C4C4");
    
    public final String hex;
    
    private Colore(String hex) {
        this.hex = hex;
    }
}
