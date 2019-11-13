package com.example.pubseeker.Services;

import com.google.firebase.firestore.FirebaseFirestore;

public class ServicesConfiguration {

    private final EntityService _usersService;
    private final EntityService _barsService;

    public ServicesConfiguration() {
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        EntityService usersService = new EntityService(database, "Users", "Main Activity");
        EntityService barsService = new EntityService(database, "Bars", "Main Activity");

        this._usersService = usersService;
        this._barsService = barsService;
    }

    public EntityService getUsersService() {
        return this._usersService;
    }

    public EntityService getbarsService() {
        return this._barsService;
    }


}
