package com.esri.android.nearbyplaces.Searchers;

import android.support.annotation.NonNull;
import android.util.Log;

import com.esri.android.nearbyplaces.Common.IEntitySearcher;
import com.esri.android.nearbyplaces.Entities.Bar;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class BarsSearcher implements IEntitySearcher {


    private FirebaseFirestore _reference;
    private String _collection;
    private String _TAG;
    private ArrayList<Bar> _foundBars;
    private int _lastId;

    public BarsSearcher(FirebaseFirestore ref, String coll, String tag) {
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
                                _foundBars = new ArrayList<>();
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Log.i(_TAG, "Entity found, adding it to collection!");
                                    Bar barFound = document.toObject(Bar.class);
                                    barFound.setId(document.getId());
                                    _foundBars.add(barFound);
                                }
                                _lastId = getLastId();
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
        if (this._foundBars == null) {
            Log.i(_TAG, "No document present yet, querying db.");
            this.prepareData();
            return null;
        }
        else {
            return (T) this.get(entityId);
        }
    }

    private Bar get(String entityId) {
        for (Bar u : _foundBars) {
            if (u.getId().equals(entityId)) {
                Log.i(_TAG, "Bar found! id: " + entityId);
                return u;
            }
        }
        Log.i(_TAG, "Bar was not found! id: " + entityId);
        return null;
    }

    @Override
    public int returnLastId() {
        return this._lastId;
    }

    private int getLastId() {
        // hice un custom for loop porq ni idea que puto metodo lo devuelve haciendo un loop con strings.
        int maxId = 0;
        int i = 1; // size, no index.
        if (this._foundBars != null && this._foundBars.size() > 0) {
            maxId = Integer.parseInt(this._foundBars.get(0).getId()); // arrancar por el primero!
            while (i < this._foundBars.size()) {
                if (maxId > Integer.parseInt(this._foundBars.get(i).getId())) {
                    maxId = Integer.parseInt(this._foundBars.get(i).getId());
                }
                i++;
            }
        }
        return maxId;
    }

    public void setLastId(String lastId) {
        this._lastId = Integer.parseInt(lastId);
    }

}

