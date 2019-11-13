package com.example.pubseeker.Common;

import java.util.HashMap;

public interface IEntityMapper {
    <T> T map(HashMap hashMap, T entityToMap);
}
