package com.example.pubseeker.mappers;

import com.example.pubseeker.Common.IEntityMapper;
import com.example.pubseeker.User;

import java.util.HashMap;

public class UserMapper implements IEntityMapper {

    private User _user;

    public UserMapper() {
        this._user = new User();
    }

    public <T> T map(HashMap hashMap, T entityToMap) {
        _user = (User) entityToMap;
        hashMap.put("name", _user.getName());
        hashMap.put("email", _user.getEmail());
        hashMap.put("barsId", _user.getBars());
        return (T) _user;
    }
}


