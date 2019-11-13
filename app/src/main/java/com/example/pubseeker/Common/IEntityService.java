package com.example.pubseeker.Common;

public interface IEntityService {
    <T> void save(T entity);
    //<T, U> void update(T originalEntity, U updatedEntity);
    <T> T searchById(int entityId);
}
