package com.infinity.interactive.scanqr.generateqr.data.preference;

public class GenerateModel {

    int img_icon;
    String category_name;



    public GenerateModel(int img_icon, String category_name) {
        this.img_icon = img_icon;
        this.category_name = category_name;
    }

    public int getImg_icon() {
        return img_icon;
    }

    public void setImg_icon(int img_icon) {
        this.img_icon = img_icon;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }
}
