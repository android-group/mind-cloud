package ru.android_group.dmtou.tagscloud;

/**
 * Created by y.andreev on 19.06.2016.
 */
public class Mind {
    private String tagName;
    private double sizeText;
    private int typeface;
    private int subIndexMind;

    public int getSubIndexMind() {
        return subIndexMind;
    }

    public void setSubIndexMind(int subIndexMind) {
        this.subIndexMind = subIndexMind;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public double getSizeText() {
        return sizeText;
    }

    public void setSizeText(double sizeText) {
        this.sizeText = sizeText;
    }

    public int getTypeface() {
        return typeface;
    }

    public void setTypeface(int typeface) {
        this.typeface = typeface;
    }
}
