package eu.soilErasmus.soil;

public class MenuModel {
    private String  menuTitle;
    private int arrowImage;

    public MenuModel(String menuTitle, int arrowImage) {
        this.menuTitle = menuTitle;
        this.arrowImage = arrowImage;
    }

    public MenuModel(String menuTitle) {
        this.menuTitle = menuTitle;
        this.arrowImage = -1;
    }

    public String getMenuTitle() {
        return menuTitle;
    }

    public void setMenuTitle(String menuTitle) {
        this.menuTitle = menuTitle;
    }

    public int getArrowImage() {
        return arrowImage;
    }

    public void setArrowImage(int arrowImage) {
        this.arrowImage = arrowImage;
    }
}