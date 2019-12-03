package com.esri.android.nearbyplaces.Mappers;

import android.util.Log;

import com.esri.android.nearbyplaces.Common.IMapper;
import com.esri.android.nearbyplaces.Entities.Bar;
import com.esri.android.nearbyplaces.Entities.User;
import com.esri.android.nearbyplaces.data.Place;
import com.esri.arcgisruntime.geometry.Point;
import com.esri.arcgisruntime.geometry.SpatialReference;

import java.util.ArrayList;

public class BarsToPlacesMapper implements IMapper {
    public ArrayList<Place> getBarsAsPlaces(User user) {
        try {
            ArrayList<Place> places = new ArrayList<>();
            if (user.getBars() != null) {
                for (Bar b: user.getBars()) {
                    SpatialReference spatialReference = SpatialReference.create(Integer.valueOf(b.get_a()));
                    Point location = new Point(Double.parseDouble(b.get_x()), Double.parseDouble(b.get_y()), 0, spatialReference );
                    Place place = new Place(b.get_name(), b.get_type(), location, b.get_address(), b.get_Url(), b.get_phone(), b.get_bearing(), b.get_distance());
                    places.add(place);
                }
                return places;
            }
            return null;
        }
        catch(Exception e) {
            Log.e("BarsToMapPlaces", "There was an error mapping a bar to a place", e);
            return null;
        }
    }

}
