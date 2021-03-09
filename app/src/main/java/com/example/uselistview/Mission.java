package com.example.uselistview;

public class Mission {
    int id;
    String value;
    boolean choose = false;
    long time;
    boolean isVisible = false;
    boolean isAnim = false;

    public Mission(int id, String value, boolean choose, long time, boolean isAnim) {
        this.id = id;
        this.value = value;
        this.choose = choose;
        this.time = time;
        this.isAnim = isAnim;
    }

    public boolean isAnim() {
        return isAnim;
    }

    public void setAnim(boolean anim) {
        isAnim = anim;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }

    public boolean isChoose() {
        return choose;
    }

    public void setChoose(boolean choose) {
        this.choose = choose;
    }

    @Override
    public String toString() {
        return "Mission{" +
                "id=" + id +
                ", value='" + value + '\'' +
                ", choose=" + choose +
                ", time=" + time +
                '}';
    }

    public Mission(int id, String value, boolean choose, long time) {
        this.id = id;
        this.value = value;
        this.choose = choose;
        this.time = time;
    }

    public Mission() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
