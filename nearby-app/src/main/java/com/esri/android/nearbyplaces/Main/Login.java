package com.esri.android.nearbyplaces.Main;

import android.app.Service;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.esri.android.nearbyplaces.Common.IEntityService;
import com.esri.android.nearbyplaces.Entities.User;
import com.esri.android.nearbyplaces.R;
import com.esri.android.nearbyplaces.Services.EntityService;
import com.esri.android.nearbyplaces.Services.ServicesConfiguration;
import com.esri.android.nearbyplaces.map.MapActivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

public class Login extends AppCompatActivity {

    SignInButton signin;
    GoogleSignInClient mGoogleSignInClient;
    int RC_SIGN_IN = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        signin = findViewById(R.id.sign_in_button);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail().build();

        ServicesConfiguration.initialize(); // initialize

        signin = findViewById(R.id.sign_in_button);
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);


        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.sign_in_button:
                        signIn();
                        break;
                    // ...
                }
            }
        });







    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.
            Toast.makeText(getApplicationContext(),"Successfully logged in",Toast.LENGTH_SHORT).show();
            IEntityService userService = ServicesConfiguration.getUsersService();
            User user = userService.searchById(account.getId());

            // si el user ya se había logueado en algun momento, debería existir ya. Sino, generar uno nuevo y guardarlo
            if (user == null) {
                ServicesConfiguration.setCurrentUser(new User(account.getId(), account.getDisplayName(), account.getEmail(), null));
                ServicesConfiguration.getUsersService().save(ServicesConfiguration.getCurrentUser());
            } else {
                ServicesConfiguration.setCurrentUser(user);
            }

            startActivity(new Intent(Login.this,MainActivity.class));
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_SHORT).show();
            Log.e("Login", "Error intentando loguear un usuario!", e);
        }
    }

    @Override
    protected void onStart() {
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount((this));
        if(account != null){
            startActivity(new Intent(Login.this,MainActivity.class));
        }
        super.onStart();
    }
}
