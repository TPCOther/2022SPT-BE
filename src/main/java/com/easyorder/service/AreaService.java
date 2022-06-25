package com.easyorder.service;

import java.util.List;

import com.easyorder.entity.Area;

public interface AreaService {
    List<Area> selectAreaList();
    Long insertArea(Area area);
    void updateArea(Area area);
    void deleteArea(Area area);
}
