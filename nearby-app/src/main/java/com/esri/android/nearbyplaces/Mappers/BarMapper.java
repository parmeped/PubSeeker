package com.esri.android.nearbyplaces.Mappers;

import com.esri.android.nearbyplaces.Common.IEntityMapper;
import com.esri.android.nearbyplaces.CustomExceptions.BussinessException;
import com.esri.android.nearbyplaces.Entities.Bar;

import java.util.HashMap;

public class BarMapper implements IEntityMapper {

    public BarMapper() {}

    @Override
    public <T> T map(HashMap hashMap, T entityToMap) {
        Bar bar = (Bar) entityToMap;
        hashMap.put("name", bar.getName());
        hashMap.put("gps", bar.getGps());
        hashMap.put("barsId", bar.getId());
        return (T) bar;
    }

    @Override
    public <T, U> T updateEntity(T oldEntity, U updatedEntity) throws BussinessException {
        throw new BussinessException("Not implemented!");
    }
}

