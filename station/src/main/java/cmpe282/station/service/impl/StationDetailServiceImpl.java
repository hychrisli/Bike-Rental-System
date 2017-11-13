package cmpe282.station.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cmpe282.station.entity.StationDetail;
import cmpe282.station.mapper.MapIdMapper;
import cmpe282.station.respository.StationDetailRepository;
import cmpe282.station.service.StationDetailService;

@Service
public class StationDetailServiceImpl implements StationDetailService {
    
    @Autowired
    private StationDetailRepository repo;
    
    @Override
    public StationDetail findStationDetail(int station_id) {
	return repo.findOne(MapIdMapper.toMapId("station_id", station_id));
    }

}
