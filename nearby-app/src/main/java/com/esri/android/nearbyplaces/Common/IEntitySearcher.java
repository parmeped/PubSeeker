package com.esri.android.nearbyplaces.Common;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public interface IEntitySearcher {
    <T> T searchById(String entityId);
    <T> ArrayList<T> getCollection();
}
