package com.esri.android.nearbyplaces.Searchers;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.esri.android.nearbyplaces.Common.IEntitySearcher;
import com.esri.android.nearbyplaces.Entities.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.Callable;


public class UserSearcher implements IEntitySearcher {

    private FirebaseFirestore _reference;
    private String _collection;
    private String _TAG;
    private ArrayList<User> _foundUsers;
    private int _lastId;

    public UserSearcher(FirebaseFirestore ref, String coll, String tag) {
        this._reference = ref;
        this._collection = coll;
        this._TAG = tag;
    }

    @Override
    public void prepareData() {
        try {
            _reference.collection(_collection)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                _foundUsers = new ArrayList<>();
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Log.i(_TAG, "Entity found, adding it to collection!");
                                    User userFound = document.toObject(User.class);
                                    userFound.setId(document.getId());
                                    _foundUsers.add(userFound);
                                }
                                _lastId = setLastId();
                            }
                            else {
                                Log.w(_TAG, "Error getting documents.", task.getException());
                            }
                        }
                    });
        }
        catch (Exception ex) {
            Log.e("Error getting coll.", ex.toString());
        }
    }

    @Override
    public <T> T searchById(String entityId) {
        if (this._foundUsers == null) {
            Log.i(_TAG, "No document present yet, querying db.");
            this.prepareData();
            return null;
        }
        else {
            return (T) this.get(entityId);
        }
    }

    private User get(String entityId) {
        for (User u : _foundUsers) {
            if (u.getId().equals(entityId)) {
                Log.i(_TAG, "User found! id: " + entityId);
                return u;
            }
        }
        Log.i(_TAG, "User was not found! id: " + entityId);
        return null;
    }

    @Override
    public int getLastId() {
        return this._lastId;
    }

    private int setLastId() {
        // hice un custom for loop porq ni idea que puto metodo lo devuelve haciendo un loop con strings.
        int maxId = -1;
        int i = 1; // size, no index.
        maxId = Integer.parseInt(this._foundUsers.get(0).getId()); // arrancar por el primero!
        while (i < this._foundUsers.size()) {
            i++;
            if (maxId > Integer.parseInt(this._foundUsers.get(i).getId())) {
                maxId = Integer.parseInt(this._foundUsers.get(i).getId());
            }
        }
        return maxId;
    }
}
