package com.esri.android.nearbyplaces.Common;

public interface IEntitySearcher {
    int getLastId();
    <T> T searchById(String entityId);
    void prepareData();
}

