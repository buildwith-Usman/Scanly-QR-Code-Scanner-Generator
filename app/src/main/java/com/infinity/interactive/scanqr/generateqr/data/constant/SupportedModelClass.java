package com.infinity.interactive.scanqr.generateqr.data.constant;

public class SupportedModelClass {

    String supportText;
    int supportImg,qrImg;

    public SupportedModelClass(String supportText, int supportImg, int qrImg) {
        this.supportText = supportText;
        this.supportImg = supportImg;
        this.qrImg = qrImg;
    }

    public String getSupportText() {
        return supportText;
    }

    public void setSupportText(String supportText) {
        this.supportText = supportText;
    }

    public int getSupportImg() {
        return supportImg;
    }

    public void setSupportImg(int supportImg) {
        this.supportImg = supportImg;
    }

    public int getQrImg() {
        return qrImg;
    }

    public void setQrImg(int qrImg) {
        this.qrImg = qrImg;
    }
}
