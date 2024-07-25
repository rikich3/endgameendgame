package com.unsa.eda.service.Reader;


public class Music {
    // Atributos String
    private String id, year, artistName, trackName, trackId, genre, key;
    private String danceability, energy, loudness, speechiness,
                   acousticness, instrumentalness, liveness, 
                   valence, tempo, popularity,   mode, durationMs, timeSignature;

    // Constructor
    public Music(String id, String artistName, String trackName, String trackId, String popularity, String year, String genre, String danceability, 
                  String energy, String key, String loudness, String mode, 
                  String speechiness, String acousticness, String instrumentalness, 
                  String liveness, String valence, String tempo, String durationMs, 
                  String timeSignature) {
        this.id = id;
        this.artistName = artistName;
        this.trackName = trackName;
        this.trackId = trackId;
        this.popularity = popularity;
        this.year = year;
        this.genre = genre;
        this.danceability = danceability;
        this.energy = energy;
        this.key = key;
        this.loudness = loudness;
        this.mode = mode;
        this.speechiness = speechiness;
        this.acousticness = acousticness;
        this.instrumentalness = instrumentalness;
        this.liveness = liveness;
        this.valence = valence;
        this.tempo = tempo;
        this.durationMs = durationMs;
        this.timeSignature = timeSignature;
    }

    // Métodos get y set para atributos String
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    
    public String getArtistName() {
        return artistName;
    }

    public String getTrackName() {
        return trackName;
    }

    public String getTrackId() {
        return trackId;
    }

    public String getPopularity() {
        return popularity;
    }

    public String getYear() {
        return year;
    }

    public String getGenre() {
        return genre;
    }

    public String getDanceability() {
        return danceability;
    }

    public String getEnergy() {
        return energy;
    }

    public String getKey() {
        return key;
    }

    public String getLoudness() {
        return loudness;
    }

    public String getMode() {
        return mode;
    }

    public String getSpeechiness() {
        return speechiness;
    }

    public String getAcousticness() {
        return acousticness;
    }

    public String getInstrumentalness() {
        return instrumentalness;
    }

    public String getLiveness() {
        return liveness;
    }

    public String getValence() {
        return valence;
    }

    public String getTempo() {
        return tempo;
    }

    public String getDurationMs() {
        return durationMs;
    }

    public String getTimeSignature() {
        return timeSignature;
    }
    public String toString(){
        return this.id;
    }
}