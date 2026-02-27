package com.infinity.interactive.scanqr.generateqr.data.preference;

public class TemplateModel {

    int tmp_pre;
    int tmpStatus;
    int tmp_img;

    public int getTmp_img() {
        return tmp_img;
    }

    public TemplateModel(int tmp_pre ,int tmp_img, int tmpStatus) {
        this.tmp_pre = tmp_pre;
        this.tmp_img = tmp_img;
        this.tmpStatus = tmpStatus;
    }

    public void setTmp_img(int tmp_img) {
        this.tmp_img = tmp_img;
    }



    public int getTmp_pre() {
        return tmp_pre;
    }

    public void setTmp_pre(int tmp_pre) {
        this.tmp_pre = tmp_pre;
    }

    public int getTmpStatus() {
        return tmpStatus;
    }

    public void setTmpStatus(int tmpStatus) {
        this.tmpStatus = tmpStatus;
    }
}
