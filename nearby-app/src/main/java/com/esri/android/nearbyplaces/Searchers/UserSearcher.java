package com.esri.android.nearbyplaces.Searchers;

import android.support.annotation.NonNull;
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


public class UserSearcher implements IEntitySearcher {

    private FirebaseFirestore _reference;
    private String _collection;
    private String _TAG;

    public UserSearcher(FirebaseFirestore ref, String coll, String tag) {
        this._reference = ref;
        this._collection = coll;
        this._TAG = tag;
    }

    @Override
    public <T> ArrayList<T> getCollection() {
        ArrayList<Integer> list = new ArrayList<>();
        try {
            _reference.collection(_collection)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Log.i(_TAG, "Showing collection!");
                                    Log.d(_TAG, document.getId() + " => " + document.getData());
                                }
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
        return (ArrayList<T>) list;
    }

    @Override
    public <T> T searchById(String entityId) {
        User returnedUser = new User();
        this.get(entityId, returnedUser);
        return (T) returnedUser;
    }

    private void get(String entityId, User returnedUser) {
        DocumentReference docRef = _reference.collection(_collection).document(entityId);
        docRef
            .get()
            .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    User user = documentSnapshot.toObject(User.class);
                    returnedUser.setUserId(user.getId());
                    returnedUser.setEmail(user.getEmail());
                    returnedUser.setBars(user.getBars());
                    returnedUser.setName(user.getName());
                    Log.i("Main Activity", "returned user mail " + returnedUser.getEmail());
                }
            });
    }

}
