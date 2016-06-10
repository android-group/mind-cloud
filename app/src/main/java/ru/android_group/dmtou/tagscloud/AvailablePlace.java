package ru.android_group.dmtou.tagscloud;

/**
 * Created by y.andreev on 04.06.2016.
 */
public class AvailablePlace {
    private int y;
    private int x;
    private String name;

    public AvailablePlace(int x, int y, String name) {
        this.x = x;
        this.y = y;
        this.name = name;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
