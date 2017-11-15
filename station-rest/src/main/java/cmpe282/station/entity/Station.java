package cmpe282.station.entity;

import java.io.Serializable;

public class Station implements Serializable{
    private static final long serialVersionUID = -5510042904214265933L;

    private int station_id;
    
    private String name;
    
    private float latitude;
    
    private float longitude;
    
    private int total_docks;
    
    private int avail_bikes;

    public int getStation_id() {
        return station_id;
    }

    public void setStation_id(int station_id) {
        this.station_id = station_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float logitude) {
        this.longitude = logitude;
    }

    public int getTotal_docks() {
        return total_docks;
    }

    public void setTotal_docks(int total_docks) {
        this.total_docks = total_docks;
    }

    public int getAvail_bikes() {
        return avail_bikes;
    }

    public void setAvail_bikes(int avail_bikes) {
        this.avail_bikes = avail_bikes;
    }
    
}
