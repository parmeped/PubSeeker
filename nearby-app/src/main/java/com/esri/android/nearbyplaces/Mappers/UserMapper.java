package com.esri.android.nearbyplaces.Mappers;



import com.esri.android.nearbyplaces.Common.IEntityMapper;
import com.esri.android.nearbyplaces.CustomExceptions.BussinessException;
import com.esri.android.nearbyplaces.Entities.User;

import java.util.HashMap;

public class UserMapper implements IEntityMapper {

    public UserMapper() {}

    public <T> T map(HashMap hashMap, T entityToMap) {
        User user = (User) entityToMap;
        hashMap.put("name", user.getName());
        hashMap.put("email", user.getEmail());
        hashMap.put("barsId", user.getBars());
        return (T) user;
    }

    public <T, U> T updateEntity(T oldEntity, U updatedEntity) throws BussinessException {
        User oldUser = (User) oldEntity;
        User updatedUser = (User) updatedEntity;

        if (!oldUser.getId().equals(updatedUser.getId())) {
            throw new BussinessException("Error, the id's are different.");
        }

        oldUser.setName(updatedUser.getName());
        oldUser.setBars(updatedUser.getBars());
        oldUser.setEmail(updatedUser.getEmail());
        return (T) oldUser;
    }
}


