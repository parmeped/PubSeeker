package com.example.pubseeker.Services;
import com.example.pubseeker.Common.IEntityMapper;
import com.example.pubseeker.Common.IEntityService;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class EntityService implements IEntityService {

    private FirebaseFirestore _reference;
    private String _collection;
    private final String _TAG;
    private IEntityMapper _mapper;


    public EntityService
        (
        FirebaseFirestore repository,
        String collection,
        String tag
        ) {
        this._reference = repository;
        this._collection = collection;
        this._TAG = tag;
    }

    @Override
    public <T> void save(T entityToSave) {
        Map<String, Object> hashMap = new HashMap<>();
        T entity = _mapper.map((HashMap) hashMap, entityToSave);
        _reference.collection(_collection).add(entity);
    }

    @Override
    public <T> T searchById(int entityId) {
        //TODO
        return null;
    }
}
