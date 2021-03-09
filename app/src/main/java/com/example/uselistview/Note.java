package com.example.uselistview;

public class Note {
    int id;
    String title;
    long time;
    String value;

    boolean isVisible = false;

    boolean isChoose = false;

    public Note() {
    }

    public Note(int id, String title, long time, String value) {
        this.id = id;
        this.title = title;
        this.time = time;
        this.value = value;
    }

    @Override
    public String toString() {
        return "Note{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", time=" + time +
                ", value='" + value + '\'' +
                ", isVisible=" + isVisible +
                ", isChoose=" + isChoose +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }

    public boolean isChoose() {
        return isChoose;
    }

    public void setChoose(boolean choose) {
        isChoose = choose;
    }
}
