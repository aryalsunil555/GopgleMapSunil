package com.gopglemapsunil.model;

public class LattitudeLongitude {
    private double lat;
    private double lon;
    private String marker;

    public LattitudeLongitude(double lat, double lon, String marker) {
        this.lat = lat;
        this.lon = lon;
        this.marker = marker;
    }
    public String getMarker() {
        return marker;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public void setMarker(String marker) {
        this.marker = marker;
    }
}
