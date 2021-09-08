package com.example.crud_firebase;

public class Artist {
    String artistId;
    String artistName;
    String artistGenre;

    public Artist(){

    }

    //constructors
    public Artist(String artistId, String artistName, String artistGenre) {
        this.artistId = artistId;
        this.artistName = artistName;
        this.artistGenre = artistGenre;
    }

    //getters
    public String getArtistId() {
        return artistId;
    }

    public String getArtistName() {
        return artistName;
    }

    public String getArtistGenre() {
        return artistGenre;
    }


}
