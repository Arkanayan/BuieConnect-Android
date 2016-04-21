package me.arkanayan.buieconnect.models;

/**
 * Created by arka on 4/20/16.
 */
public class NoticeFirebaseModel {

    int id;
    String title;
    String message;
    String url;

    public NoticeFirebaseModel() {
    }

    public NoticeFirebaseModel(int id, String title, String message, String url) {
        this.id = id;
        this.title = title;
        this.message = message;
        this.url = url;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getMessage() {
        return message;
    }

    public String getUrl() {
        return url;
    }
}
