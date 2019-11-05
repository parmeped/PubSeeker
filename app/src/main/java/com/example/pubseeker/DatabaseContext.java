package com.example.pubseeker;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.internal.api.FirebaseNoSignedInUserException;

public class DatabaseContext {

    private FirebaseDatabase _database;
    private String _TAG;

    public DatabaseContext(FirebaseDatabase database, String TAG) {
        this._database = database;
        this._TAG = TAG;
    }

    public <T> void dataWriter(T type, String collection) {
        DatabaseReference mRef = _database.getReference().child(collection);
        String key = mRef.push().getKey();
        mRef.child(key).setValue(type);
    }
}
