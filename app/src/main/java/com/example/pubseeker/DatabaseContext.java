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

    private static final String TAG = "MainActivity";

    public static <T> void dataWriter(String collection,T type, String typeId) {
        FirebaseDatabase database =  FirebaseDatabase.getInstance();
        DatabaseReference mRef = database.getReference().child(collection).child(typeId);
        Object obj = type;
        mRef.setValue(obj);
    }

    /* Este venia funcionando
    private void dataWriter() {
        FirebaseDatabase database =  FirebaseDatabase.getInstance();
        String userId = "16";
        User user = new User(userId, "Another User", "another@userDomain.com");
        DatabaseReference mRef = database.getReference().child("Users").child(userId);
        mRef.setValue(user);
    }
    */

    public static void dataReader() {
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
                    Log.d(TAG, "Value is: " + user.showUserDataAsJSON());
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }
}
