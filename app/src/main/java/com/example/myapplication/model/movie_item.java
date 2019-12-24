package com.example.myapplication.model;

public class movie_item {
        private String releaseDate;
        private String title;
        private String poster;
        private String overView;


        public movie_item(String releaseDate,String title,String poster,String overView) {
            this.releaseDate=releaseDate;
            this.title=title;
            this.poster=poster;
            this.overView=overView;
        }
        //----------SET-----------
        public void setReleaseDate(String releaseDate) {
            this.releaseDate = releaseDate;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setPoster(String Poster){this.poster=Poster;}

        public  void  setoverView( String overView){this.overView=overView;}

        //----------GET--------------


        public String getReleaseDate() {
            return releaseDate;
        }

        public String getTitle() {
            return title;
        }

        public String getOverView(){return overView;}

        public String getPoster(){return this.poster;}
}

