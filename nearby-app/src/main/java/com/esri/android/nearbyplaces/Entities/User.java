package com.esri.android.nearbyplaces.Entities;


import com.esri.android.nearbyplaces.Common.IEntity;
import com.esri.android.nearbyplaces.data.Place;
import com.esri.arcgisruntime.geometry.Point;
import com.esri.arcgisruntime.geometry.SpatialReference;
import java.util.ArrayList;


public class User implements IEntity {
    private String _name;
    private String _email;
    private String _userId;
    private ArrayList<Bar> _bars;

    public User(){}

    public User(String id, String name, String email, ArrayList<Bar> bars) {
        this._userId = id;
        this._name = name;
        this._email = email;
        if (bars == null) {
            this._bars = new ArrayList<>();
        }
        else {
            this._bars = bars;
        }
    }

    public String getName() {
        return this._name;
    }

    public String getEmail() {
        return this._email;
    }

    public String getId() {
        return this._userId;
    }

    public void setName(String name) {
        this._name = name;
    }

    public void setEmail(String email) {
        this._email = email;
    }

    public void setId(String userId) {
        this._userId = userId;
    }

    public void setBars(ArrayList<Bar> bars) {
        this._bars = bars;
    }

    public void addBar(Bar aBar) {
        if (this._bars != null) {
            this._bars.add(aBar);
        }
    }

    public ArrayList<Place> getBarsAsPlaces() {
        ArrayList<Place> places = new ArrayList<>();
        if (this._bars != null) {
            for (Bar b: this._bars) {
                SpatialReference spatialReference = SpatialReference.create(Integer.valueOf(b.get_a()));
                Point location = new Point(Integer.valueOf(b.get_x()), Integer.valueOf(b.get_y()), 0, spatialReference );
                Place place = new Place(b.get_name(), b.get_type(), location, b.get_address(), b.get_Url(), b.get_phone(), b.get_bearing(), b.get_distance());
                places.add(place);
            }
            return places;
        }
        return null;
    }

    public void removeBar(Bar bar) {
        //TODO
    }

}
