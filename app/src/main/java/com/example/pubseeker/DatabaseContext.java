package com.example.pubseeker;

import com.example.pubseeker.Common.IDatabaseEntity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DatabaseContext {

    private FirebaseDatabase _database;
    private String _TAG;

    public DatabaseContext(FirebaseDatabase database, String TAG) {
        this._database = database;
        this._TAG = TAG;
    }

    public void dataWriter(IDatabaseEntity databaseEntity, String collection) {
        DatabaseReference mRef = _database.getReference().child(collection);
        String key = mRef.push().getKey();
        mRef.child(key).setValue(databaseEntity.getAttributes());
    }
}
