package com.ptit.cms.model.other;

import java.io.Serializable;

public class ImageResponse implements Serializable {
    private static final long serialVersionUID = 3L;
    private String image;
    private double similarity;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public double getSimilarity() {
        return similarity;
    }

    public void setSimilarity(double similarity) {
        this.similarity = similarity;
    }
}
