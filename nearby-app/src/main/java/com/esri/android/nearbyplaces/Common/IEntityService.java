package com.esri.android.nearbyplaces.Common;

public interface IEntityService {
    <T> void save(T entity);
    //<T, U> void update(T originalEntity, U updatedEntity);
}
