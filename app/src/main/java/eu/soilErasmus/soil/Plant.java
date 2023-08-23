package eu.soilErasmus.soil;

public class Plant {
    private final String name;
    //private final String description;
    private final int assetResource;
    //private boolean visibility;


    public Plant(String name,int assetResource) {
        this.name = name;
        this.assetResource = assetResource;
    }

    public int getAssetResource() {
        return assetResource;
    }

    public String getName() {
        return name;
    }
}
