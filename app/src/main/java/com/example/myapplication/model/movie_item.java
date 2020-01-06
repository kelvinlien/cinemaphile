package com.example.myapplication.model;

public class movie_item {
    private String releaseDate;
    private String title;
    private String poster;
    private String overView;
    private String id;

    public movie_item() {
    }

    ;

    public movie_item(String releaseDate, String title, String poster, String overView, String id) {
        this.releaseDate = releaseDate;
        this.title = title;
        this.poster = poster;
        this.overView = overView;
        this.id = id;
    }

    public void setoverView(String overView) {
        this.overView = overView;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    //----------SET-----------
    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }


    //----------GET--------------

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOverView() {
        return overView;
    }

    public String getPoster() {
        return this.poster;
    }

    public void setPoster(String Poster) {
        this.poster = Poster;
    }
}

