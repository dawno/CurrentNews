package com.example.dellpc.currentnews;


public class FeedItem {
    private int id;
    private String source, title, image, profilePic, timeStamp, url,description,author;

    public FeedItem() {
    }

    public FeedItem(int id, String source, String image, String title,
                    String profilePic, String timeStamp, String url) {
        super();
        this.id = id;
        this.source = source;
        this.image = image;
        this.title = title;
        this.profilePic = profilePic;
        this.timeStamp = timeStamp;
        this.url = url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getImge() {
        return image;
    }

    public void setImge(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String url) {
        this.description = description;
    }
    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
