package com.esri.android.nearbyplaces.Services;

import android.app.Application;

import com.esri.android.nearbyplaces.Common.IEntityMapper;
import com.esri.android.nearbyplaces.Common.IEntitySearcher;
import com.esri.android.nearbyplaces.Mappers.BarMapper;
import com.esri.android.nearbyplaces.Mappers.UserMapper;
import com.esri.android.nearbyplaces.Searchers.UserSearcher;
import com.google.firebase.firestore.FirebaseFirestore;

public class ServicesConfiguration extends Application {

    private static ServicesConfiguration _instance;
    private static EntityService _usersService;
    private static EntityService _barsService;

    private ServicesConfiguration() {
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        IEntityMapper usersMapper = new UserMapper();
        IEntityMapper barsMapper = new BarMapper();
        IEntitySearcher usersSearcher = new UserSearcher(database, "Users", "User searcher");
        IEntitySearcher barsSearcher = new UserSearcher(database, "Bars", "Bars searcher");

        EntityService usersService = new EntityService(database, "Users", usersMapper, usersSearcher,"Users Service");
        EntityService barsService = new EntityService(database, "Bars", barsMapper, barsSearcher, "Bars Service");

        this._usersService = usersService;
        this._barsService = barsService;

        _usersService.prepareData();
        _barsService.prepareData();
    }

    private static ServicesConfiguration instance() {
        if (_instance == null) {
            _instance = new ServicesConfiguration();
        }
        return _instance;
    }

    public static EntityService getUsersService() {
        return instance()._usersService;
    }

    public static EntityService getBarsService() {
        return instance()._barsService;
    }


}
