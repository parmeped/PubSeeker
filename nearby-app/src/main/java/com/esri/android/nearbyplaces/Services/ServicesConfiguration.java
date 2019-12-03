package com.esri.android.nearbyplaces.Services;

import android.app.Application;

import com.esri.android.nearbyplaces.Common.IEntityMapper;
import com.esri.android.nearbyplaces.Common.IEntitySearcher;
import com.esri.android.nearbyplaces.Common.IMapper;
import com.esri.android.nearbyplaces.Entities.User;
import com.esri.android.nearbyplaces.Mappers.BarMapper;
import com.esri.android.nearbyplaces.Mappers.BarsToPlacesMapper;
import com.esri.android.nearbyplaces.Mappers.UserMapper;
import com.esri.android.nearbyplaces.Searchers.BarsSearcher;
import com.esri.android.nearbyplaces.Searchers.UserSearcher;
import com.esri.android.nearbyplaces.data.Place;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class ServicesConfiguration extends Application {

    private static ServicesConfiguration _instance;
    private static EntityService _usersService;
    private static EntityService _barsService;
    private static User _currentUser;
    private static IMapper _barsToPlacesMapper;

    private ServicesConfiguration() {
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        IEntitySearcher usersSearcher = new UserSearcher(database, "Users", "User searcher");
        IEntitySearcher barsSearcher = new BarsSearcher(database, "Bars", "Bars searcher");
        IMapper barsToPlacesMapper = new BarsToPlacesMapper();

        EntityService usersService = new EntityService(database, "Users", usersSearcher,"Users Service");
        EntityService barsService = new EntityService(database, "Bars", barsSearcher, "Bars Service");

        this._usersService = usersService;
        this._barsService = barsService;
        this._barsToPlacesMapper = barsToPlacesMapper;

        _usersService.prepareData();
        _barsService.prepareData();
    }

    private static ServicesConfiguration instance() {
        if (_instance == null) {
            _instance = new ServicesConfiguration();
        }
        return _instance;
    }

    public static void initialize() { instance(); } ;

    public static EntityService getUsersService() {
        return instance()._usersService;
    }

    public static EntityService getBarsService() {
        return instance()._barsService;
    }

    public static void setCurrentUser(User user) { _currentUser = user; }

    public static User getCurrentUser() { return _currentUser; }

    public static ArrayList<Place> getUserBarsAsPlaces(User user) { return _barsToPlacesMapper.getBarsAsPlaces(user); }

}
