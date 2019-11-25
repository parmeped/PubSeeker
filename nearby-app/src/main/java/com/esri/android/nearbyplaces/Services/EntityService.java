package com.esri.android.nearbyplaces.Services;

import android.support.annotation.NonNull;
import android.util.Log;

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
    public <T> void save(T entityToSave) {
        if(_mapper == null) {
            throw new UnsupportedOperationException("mapper is null!");
        }
        Map<String, Object> hashMap = new HashMap<>();
        T entity = _mapper.map((HashMap) hashMap, entityToSave);

        try {
            _reference.collection(_collection).add(entity) //acá se guarda la entidad.
            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {
                    Log.d(_TAG, "Document added correctly " + documentReference);
                }
            })
            .addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.w(_TAG, "Error adding document " + e);
                }
            });
        }
        catch(Exception e) {
            Log.e("Error saving entity", e.toString());
        }
    }

    public <T> ArrayList<T> getCollection() {
        return this._searcher.getCollection();
    }

    public <T> T searchById(String entityId) {
        return this._searcher.searchById(entityId);
    }





}
