package com.esri.android.nearbyplaces.Common;

import java.util.concurrent.Callable;

public interface IEntityService {
    <T> void save(IEntity entityToSave);
    <T, U> void update(T originalEntity, U updatedEntity);
    void prepareData();
}
