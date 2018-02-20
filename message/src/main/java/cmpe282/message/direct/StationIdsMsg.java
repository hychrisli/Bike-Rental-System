package cmpe282.message.direct;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class StationIdsMsg {

    @JsonProperty("station_ids")
    List<String> stationIds;

    public List<String> getStationIds() {
        return stationIds;
    }

    public void setStationIds(List<String> stationIds) {
        this.stationIds = stationIds;
    }
}
