package com.esri.android.nearbyplaces.Services;

import android.support.annotation.NonNull;
import android.util.Log;

import com.esri.android.nearbyplaces.Common.IEntity;
import com.esri.android.nearbyplaces.Common.IEntityMapper;
import com.esri.android.nearbyplaces.Common.IEntitySearcher;
import com.esri.android.nearbyplaces.Common.IEntityService;
import com.esri.android.nearbyplaces.CustomExceptions.BussinessException;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class EntityService implements IEntityService {

    private FirebaseFirestore _reference;
    private String _collection;
    private final String _TAG;
    private IEntityMapper _mapper;
    private IEntitySearcher _searcher;

    public EntityService
        (
        FirebaseFirestore repository,
        String collection,
        IEntitySearcher searcher,
        String tag

        ) {
        this._reference = repository;
        this._collection = collection;
        this._searcher = searcher;
        this._TAG = tag;
    }

    @Override
    public <T> void save(IEntity entityToSave) {
        try {

            if (entityToSave.getId() == null || entityToSave.getId().isEmpty()) {
                entityToSave.setId(getNextId());
                this._searcher.setLastId(getNextId());
            }

            _reference.collection(_collection)
            .document(entityToSave.getId())
            .set(entityToSave) //acá se guarda la entidad.
            .addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.d(_TAG, "DocumentSnapshot successfully written!");
                    prepareData();
                }
            })
            .addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.w(_TAG, "Error writing document", e);
                }
            });
        }
        catch(Exception e) {
            Log.e(_TAG,"Error saving entity", e);
        }
    }

    public void prepareData() { this._searcher.prepareData(); }

    public <T> T searchById(String entityId) {
        return this._searcher.searchById(entityId);
    }

    public String getNextId() {
        try {
            return String.valueOf(this._searcher.returnLastId() + 1);
        }
        catch(BussinessException e) {
            Log.e(_TAG,"Error tratando de buscar el siguiente id", e);
            return null;
        }
    }


}
