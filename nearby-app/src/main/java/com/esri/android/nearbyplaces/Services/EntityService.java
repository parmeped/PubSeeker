package com.esri.android.nearbyplaces.Services;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.esri.android.nearbyplaces.Common.IEntity;
import com.esri.android.nearbyplaces.Common.IEntityMapper;
import com.esri.android.nearbyplaces.Common.IEntitySearcher;
import com.esri.android.nearbyplaces.Common.IEntityService;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;


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
        IEntityMapper mapper,
        IEntitySearcher searcher,
        String tag

        ) {
        this._reference = repository;
        this._collection = collection;
        this._mapper = mapper;
        this._searcher = searcher;
        this._TAG = tag;
    }

    @Override
    public <T> void save(IEntity entityToSave) {
        if(_mapper == null) {
            throw new UnsupportedOperationException("mapper is null!");
        }
        Map<String, Object> hashMap = new HashMap<>();
        T entity = _mapper.map((HashMap) hashMap, (T) entityToSave);

        try {
            _reference.collection(_collection)
            .document(entityToSave.getId())
            .set(entity) //acá se guarda la entidad.
            .addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.d(_TAG, "DocumentSnapshot successfully written!");
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
            Log.e("Error saving entity", e.toString());
        }
    }

    public void prepareData() { this._searcher.prepareData();
    }

    public <T> T searchById(String entityId) {
        return this._searcher.searchById(entityId);
    }

    public String getNextId() {
        return String.valueOf(this._searcher.getLastId() + 1);
    }
}
