package com.esri.android.nearbyplaces.Common;

public interface IEntityService {
    <T> void save(IEntity entityToSave);
    void prepareData();
    <T> T searchById(String entityId);
}
