package com.esri.android.nearbyplaces.Common;

import com.esri.android.nearbyplaces.Entities.User;
import com.esri.android.nearbyplaces.data.Place;

import java.util.ArrayList;

public interface IMapper {
    ArrayList<Place> getBarsAsPlaces(User user);
}
