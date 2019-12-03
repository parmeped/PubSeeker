package com.esri.android.nearbyplaces.Common;

import com.esri.android.nearbyplaces.CustomExceptions.BussinessException;

import java.util.HashMap;

public interface IEntityMapper {
    <T> T map(HashMap hashMap, T entityToMap);
    <T, U> T updateEntity(T oldEntity, U updatedEntity) throws BussinessException;
}
