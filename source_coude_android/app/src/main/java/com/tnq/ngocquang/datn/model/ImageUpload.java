package com.tnq.ngocquang.datn.model;

import android.graphics.drawable.Drawable;

import java.io.File;

public class ImageUpload {

    private File file;
    private String description;

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ImageUpload(File file, String description) {
        this.file = file;
        this.description = description;
    }
//        private Drawable drawable;
//    private String nameFile;
//    private String description;
//
//    public Drawable getDrawable() {
//        return drawable;
//    }
//
//    public void setDrawable(Drawable drawable) {
//        this.drawable = drawable;
//    }
//
//    public String getNameFile() {
//        return nameFile;
//    }
//
//    public void setNameFile(String nameFile) {
//        this.nameFile = nameFile;
//    }
//
//    public String getDescription() {
//        return description;
//    }
//
//    public void setDescription(String description) {
//        this.description = description;
//    }
//
//    public ImageUpload(Drawable drawable, String nameFile, String description) {
//        this.drawable = drawable;
//        this.nameFile = nameFile;
//        this.description = description;
//    }
}
