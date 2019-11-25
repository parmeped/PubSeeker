package com.esri.android.nearbyplaces.Mappers;

import com.esri.android.nearbyplaces.Common.IEntityMapper;
import com.esri.android.nearbyplaces.Entities.Bar;

import java.util.HashMap;

public class BarMapper implements IEntityMapper {

    public BarMapper() {}

    public <T> T map(HashMap hashMap, T entityToMap) {
        Bar bar = (Bar) entityToMap;
        hashMap.put("name", bar.getName());
        hashMap.put("gps", bar.getGps());
        hashMap.put("barsId", bar.getId());
        return (T) bar;
    }
}

