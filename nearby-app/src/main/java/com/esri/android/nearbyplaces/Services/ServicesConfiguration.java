package com.esri.android.nearbyplaces.Services;

import com.esri.android.nearbyplaces.Common.IEntityMapper;
import com.esri.android.nearbyplaces.Common.IEntitySearcher;
import com.esri.android.nearbyplaces.Mappers.BarMapper;
import com.esri.android.nearbyplaces.Mappers.UserMapper;
import com.esri.android.nearbyplaces.Searchers.UserSearcher;
import com.google.firebase.firestore.FirebaseFirestore;

public class ServicesConfiguration {

    private static ServicesConfiguration _instance;
    private static EntityService _usersService;
    private static EntityService _barsService;

    private ServicesConfiguration() {
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        IEntityMapper usersMapper = new UserMapper();
        IEntityMapper barsMapper = new BarMapper();
        IEntitySearcher usersSearcher = new UserSearcher(database, "Users", "Main activity");

        EntityService usersService = new EntityService(database, "Users", usersMapper, usersSearcher,"Main Activity");
        EntityService barsService = new EntityService(database, "Bars", barsMapper, null, "Main Activity");

        this._usersService = usersService;
        this._barsService = barsService;
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
