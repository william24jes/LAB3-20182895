package com.example.lab3_20182895.dto;

import java.util.List;

public class DtoPeli {

    private String Title;
    private String Director;
    private String Actors;
    private String Released;
    private String Genre;
    private String Writer;
    private String Plot;
    private List<Ratings> Ratings;

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDirector() {
        return Director;
    }

    public void setDirector(String director) {
        Director = director;
    }

    public String getActors() {
        return Actors;
    }

    public void setActors(String actors) {
        Actors = actors;
    }

    public String getReleased() {
        return Released;
    }

    public void setReleased(String released) {
        Released = released;
    }

    public String getGenre() {
        return Genre;
    }

    public void setGenre(String genre) {
        Genre = genre;
    }

    public String getWriter() {
        return Writer;
    }

    public void setWriter(String writer) {
        Writer = writer;
    }

    public String getPlot() {
        return Plot;
    }

    public void setPlot(String plot) {
        Plot = plot;
    }

    public List<Ratings> getRatings() {
        return Ratings;
    }

    public void setRatings(List<Ratings> ratings) {
        Ratings = ratings;
    }

    public static class Ratings {
        private String Source;
        private String Value;

        public String getSource() {
            return Source;
        }

        public void setSource(String source) {
            Source = source;
        }

        public String getValue() {
            return Value;
        }

        public void setValue(String value) {
            Value = value;
        }
    }
}
