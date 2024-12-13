package mp3player2.business;

import javafx.scene.control.Label;

public class Track {
    private String title;
    private String artist;
    private String filePath;
    private String coverPath;
    private int length;

    public Track(String title, String artist, String filePath, String coverPath, int length) {
        this.title = title;
        this.artist = artist;
        this.filePath = filePath;
        this.coverPath = coverPath;
        this.length = length;
    }

    public int getLength() {
        return length;
    }

    public String getTitle() { 
        return title; 
    }

    public String getArtist() { 
        return artist; 
    }

    public String getFilePath() { 
        return filePath; 
    }

    public String getCoverPath() {
        return coverPath;
    }

    public void setCoverPath(String coverPath) {
        this.coverPath = coverPath;       //Muss von M3U Parser gezogen werden
    }

}

