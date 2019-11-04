package com.example.pubseeker;

import android.provider.ContactsContract;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;


public class DatabaseContext {

    private String _tag;
    private static FirebaseDatabase db = FirebaseDatabase.getInstance();

    public DatabaseContext(String tag) {
        this._tag = tag;
    }

    public <T> void dataWriter(String collection,T type, String typeId) {
        DatabaseReference mRef = db.getReference().child(collection).child(typeId);
        mRef.setValue(type);
    }


    /* Este venia funcionando
    public static void dataWriter(String collection, User user, String userId) {
        FirebaseDatabase database =  FirebaseDatabase.getInstance();
        DatabaseReference mRef = database.getReference().child(collection).child(userId);
        mRef.setValue(user);
    }
    */

    public void dataReader() {
        // Read from the database
        FirebaseDatabase database =  FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();
        DatabaseReference userValues = myRef.child("Users");
        userValues.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot singleSnapshot : dataSnapshot.getChildren()){
                    User user = singleSnapshot.getValue(User.class);
                    user.setKey(singleSnapshot.getKey().toString());
                    Log.d(_tag, "Value is: " + user.showUserDataAsJSON());
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(_tag, "Failed to read value.", error.toException());
            }
        });
    }
}
