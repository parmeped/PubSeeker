package com.esri.android.nearbyplaces.Common;

import com.esri.android.nearbyplaces.CustomExceptions.BussinessException;

public interface IEntitySearcher {
    int returnLastId() throws BussinessException;
    <T> T searchById(String entityId);
    void prepareData();
    void setLastId(String lastId) throws BussinessException;
}

