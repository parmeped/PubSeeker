package com.esri.android.nearbyplaces.Entities;

import android.support.annotation.Nullable;

import com.esri.android.nearbyplaces.Common.IEntity;
import com.esri.android.nearbyplaces.data.Place;
import com.esri.arcgisruntime.geometry.SpatialReference;

public class Bar implements IEntity {
    private String _barId;
    private String _name;
    private @Nullable
    String _type;
    private @Nullable
    String _address;
    private @Nullable
    String _Url;
    private @Nullable
    String _phone;
    private @Nullable
    String _bearing;
    private long _distance;
    private String _x;
    private String _y;
    private String _a;

public Bar() {

    }

    public Bar(String name, String type, String address, String url, String phone, String bearing, Long distance, String x, String y, String a) {
        this._name = name;
        this._type = type;
        this._address = address;
        this._Url = url;
        this._phone = phone;
        this._bearing = bearing;
        this._distance = distance;
        this._x = x;
        this._y = y;
        this._a = a;
    }

    public String getId() { return this._barId; }

    public void setId(String id) {
        this._barId = id;
    }

    @Nullable
    public String get_type() {
        return _type;
    }

    public void set_type(@Nullable String _type) {
        this._type = _type;
    }

    @Nullable
    public String get_address() {
        return _address;
    }

    public void set_address(@Nullable String _address) {
        this._address = _address;
    }

    public String get_name() {
        return _name;
    }

    public void set_name(String _name) {
        this._name = _name;
    }

    @Nullable
    public String get_Url() {
        return _Url;
    }

    public void set_Url(@Nullable String _Url) {
        this._Url = _Url;
    }

    @Nullable
    public String get_phone() {
        return _phone;
    }

    public void set_phone(@Nullable String _phone) {
        this._phone = _phone;
    }

    @Nullable
    public String get_bearing() {
        return _bearing;
    }

    public void set_bearing(@Nullable String _bearing) {
        this._bearing = _bearing;
    }

    public long get_distance() {
        return _distance;
    }

    public void set_distance(long _distance) {
        this._distance = _distance;
    }

    public String get_x() {
        return _x;
    }

    public void set_x(String _x) {
        this._x = _x;
    }

    public String get_y() {
        return _y;
    }

    public void set_y(String _y) {
        this._y = _y;
    }

    public String get_a() {
        return _a;
    }

    public void set_a(String _a) {
        this._a = _a;
    }

    public void setData(Place place) {
        this._name = place.getName();
        this._type = place.getType();
        this._address = place.getAddress();
        this._Url = place.getURL();
        this._phone = place.getPhone();
        this._bearing = place.getBearing();
        this._distance = place.getDistance();
        this._x =  String.valueOf(place.getLocation().getX());
        this._y =  String.valueOf(place.getLocation().getY());
        this._a = String.valueOf(place.getLocation().getSpatialReference().getWkid());
    }

}
