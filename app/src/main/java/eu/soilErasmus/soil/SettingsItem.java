package eu.soilErasmus.soil;

public class SettingsItem {
    private int imgResource;
    private String name;

    public SettingsItem(int assetResource, String name) {
        this.imgResource = assetResource;
        this.name = name;
    }

    public int getImgResource() {
        return imgResource;
    }

    public void setImgResource(int imgResource) {
        this.imgResource = imgResource;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}