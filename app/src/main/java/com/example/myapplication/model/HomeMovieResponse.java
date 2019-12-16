package com.example.myapplication.model;


import java.util.List;

public class HomeMovieResponse {

    private int totalPages;
    private Dates dates;
    private int totalResults;
    private int page;
    private List<Results>results;

    public int getTotalPages() {
        return totalPages;
    }

    public Dates getDates() {
        return dates;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public int getPage() {
        return page;
    }

    public List<Results> getResults() {
        return results;
    }

    public static class Dates {
        private String minimum;

        private String maximum;

        public String getMaximum() {
            return maximum;
        }

        public String getMinimum() {
            return minimum;
        }
    }

    public static class Results {
        private String releaseDate;

        private String overview;

        private boolean adult;

        private String backdropPath;

        private List<Integer> genreIds;

        private String originalTitle;

        private String OriginalLanguage;

        private String posterPath;

        private double popularity;

        private String title;

        private double voteAverage;

        private boolean video;

        private int id;

        private int voteCount;

        public Results() {
        }
        //----------SET-----------
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

        public void setOriginalLanguage(String OriginalLanguage) {
            this.OriginalLanguage = OriginalLanguage;
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
        //----------GET--------------


        public String getReleaseDate() {
            return releaseDate;
        }

        public String getOverview() {
            return overview;
        }
        public boolean getAdult(){return adult;}

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
            return OriginalLanguage;
        }

        public String getTitle() {
            return title;
        }

        public double getVoteAverage() {
            return voteAverage;
        }

        public double getPopularity() {
            return popularity;
        }

        public int getVoteCount() {
            return voteCount;
        }

        public int getId() {
            return id;
        }

        public String getPosterPath() {
            return posterPath;
        }
    }

}
