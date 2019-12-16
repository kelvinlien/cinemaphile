package com.example.myapplication.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class MovieResponse {

    private List<Results> results;

    private int totalPages;

    private int totalResults;

    private int page;

    public List<Results> getResults() {
        return results;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public int getPage() {
        return page;
    }

    public static class Results implements Parcelable {

        private String releaseDate;

        private String overview;

        private boolean adult;

        private String backdropPath;

        private List<Integer> genreIds;

        private String originalTitle;

        private String originalLanguage;


        private String posterPath;

        private double popularity;

        private String title;

        private double voteAverage;

        private boolean video;

        private int id;

        private int voteCount;

        //----------GET-------------

        public String getReleaseDate() {
            return releaseDate;
        }

        public String getOverview() {
            return overview;
        }

        public boolean getAdult() {
            return adult;
        }

        public String getBackdropPath() {
            return backdropPath;
        }

        public List<Integer> getGenreIds() {
            return genreIds;
        }

        public String getOriginalTitle() {
            return originalTitle;
        }

        public String getOriginalLanguage() {
            return originalLanguage;
        }

        public String getPosterPath() {
            return posterPath;
        }

        public double getPopularity() {
            return popularity;
        }

        public String getTitle() {
            return title;
        }

        public double getVoteAverage() {
            return voteAverage;
        }

        public boolean getVideo() {
            return video;
        }

        public int getId() {
            return id;
        }

        public int getVoteCount() {
            return voteCount;
        }


        //----------SET---------

        public void setReleaseDate(String releaseDate) {
            this.releaseDate = releaseDate;
        }

        public void setOverview(String overview) {
            this.overview = overview;
        }

        public void setAdult(boolean adult) {
            this.adult = adult;
        }

        public void setBackdropPath(String backdropPath) {
            this.backdropPath = backdropPath;
        }

        public void setGenreIds(List<Integer> genreIds) {
            this.genreIds = genreIds;
        }

        public void setOriginalTitle(String originalTitle) {
            this.originalTitle = originalTitle;
        }

        public void setOriginalLanguage(String originalLanguage) {
            this.originalLanguage = originalLanguage;
        }

        public void setPosterPath(String posterPath) {
            this.posterPath = posterPath;
        }

        public void setPopularity(double popularity) {
            this.popularity = popularity;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setVoteAverage(double voteAverage) {
            this.voteAverage = voteAverage;
        }

        public void setVideo(boolean video) {
            this.video = video;
        }

        public void setId(int id) {
            this.id = id;
        }

        public void setVoteCount(int voteCount) {
            this.voteCount = voteCount;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.releaseDate);
            dest.writeString(this.overview);
            dest.writeByte(this.adult ? (byte) 1 : (byte) 0);
            dest.writeString(this.backdropPath);
            dest.writeList(this.genreIds);
            dest.writeString(this.originalTitle);
            dest.writeString(this.originalLanguage);
            dest.writeString(this.posterPath);
            dest.writeDouble(this.popularity);
            dest.writeString(this.title);
            dest.writeDouble(this.voteAverage);
            dest.writeByte(this.video ? (byte) 1 : (byte) 0);
            dest.writeInt(this.id);
            dest.writeInt(this.voteCount);
        }

        public Results() {
        }

        protected Results(Parcel in) {
            this.releaseDate = in.readString();
            this.overview = in.readString();
            this.adult = in.readByte() != 0;
            this.backdropPath = in.readString();
            this.genreIds = new ArrayList<Integer>();
            in.readList(this.genreIds, Integer.class.getClassLoader());
            this.originalTitle = in.readString();
            this.originalLanguage = in.readString();
            this.posterPath = in.readString();
            this.popularity = in.readDouble();
            this.title = in.readString();
            this.voteAverage = in.readDouble();
            this.video = in.readByte() != 0;
            this.id = in.readInt();
            this.voteCount = in.readInt();
        }

        public static final Parcelable.Creator<Results> CREATOR = new Parcelable.Creator<Results>() {
            @Override
            public Results createFromParcel(Parcel source) {
                return new Results(source);
            }

            @Override
            public Results[] newArray(int size) {
                return new Results[size];
            }
        };
    }
}

