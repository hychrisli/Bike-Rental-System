package cmpe282.station.entity;

import org.springframework.cassandra.core.PrimaryKeyType;
import org.springframework.data.cassandra.mapping.Column;
import org.springframework.data.cassandra.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.mapping.Table;

@Table
public class Station {

    @PrimaryKeyColumn(name="station_id", type = PrimaryKeyType.PARTITIONED)
    private String stationId;
    
    @Column
    private String name;
    
    @Column(value="total_docks")
    private int totalDocks;
    
    @Column(value="avail_bikes")
    private int availBikes;

    public String getStationId() {
        return stationId;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTotalDocks() {
        return totalDocks;
    }

    public void setTotalDocks(int totalDocks) {
        this.totalDocks = totalDocks;
    }

    public int getAvailBikes() {
        return availBikes;
    }

    public void setAvailBikes(int availBikes) {
        this.availBikes = availBikes;
    }


    
}
