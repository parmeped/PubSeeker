package com.esri.android.nearbyplaces.Mappers;



import com.esri.android.nearbyplaces.Common.IEntityMapper;
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
}


