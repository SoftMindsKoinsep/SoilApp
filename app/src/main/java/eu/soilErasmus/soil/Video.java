package eu.soilErasmus.soil;

public class Video {
    private String name,uri;

    public Video(String name, String uri) {
        this.name = name;
        this.uri = uri;
    }

    public Video(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

}