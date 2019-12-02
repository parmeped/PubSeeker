package com.esri.android.nearbyplaces.Common;

public interface IEntitySearcher {
    int returnLastId();
    <T> T searchById(String entityId);
    void prepareData();
    void setLastId(String lastId);
}

