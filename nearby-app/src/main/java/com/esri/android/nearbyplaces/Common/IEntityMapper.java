package com.esri.android.nearbyplaces.Common;

import java.util.HashMap;

public interface IEntityMapper {
    <T> T map(HashMap hashMap, T entityToMap);
}
