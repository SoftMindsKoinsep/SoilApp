package eu.soilErasmus.soil;

class Plant {
    String name;
    String description;
    int assetResource;
    boolean visibility;


    public Plant(String name, String description,int assetResource) {
        this.name = name;
        this.description = description;
        this.visibility = false;
        this.assetResource = assetResource;
    }

    public boolean isVisible() {
        return visibility;
    }

    public void setVisibility(boolean visibility) {
        this.visibility = visibility;
    }


    public String getDescription() {
        return description;
    }

    public int getAssetResource() {
        return assetResource;
    }

    public String getName() {
        return name;
    }
}
