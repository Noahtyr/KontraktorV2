package awesomegroupkolond.kontraktor;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity
        implements View.OnClickListener {

    //Get
   private FirebaseAuth dbAuth;
    private FirebaseAuth.AuthStateListener dbAuthListener;

    private EditText txtPassword;
   private EditText txtEmail;
    private final String TAG = "FB_SIGNIN";


    /**
     * Standard Activity lifecycle methods
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        dbAuth = FirebaseAuth.getInstance();

        //If currentUser is not null, it's already logged in
        if (dbAuth.getCurrentUser() != null) {
            //Proceed to Main Menu
            startActivity(new Intent(getApplicationContext(), MainMenu.class));
        }




        // Set up click handlers and view item references
        findViewById(R.id.cmdCreateUser).setOnClickListener(this);
        findViewById(R.id.cmdLogIn).setOnClickListener(this);

        //Assign txtemail and set OnClickListener
        txtEmail = (EditText) findViewById(R.id.txtEmail);
//        txtEmail.setOnClickListener(this);

        //Assign txtPassword and assign OnClickListener
        txtPassword = (EditText) findViewById(R.id.txtPassword);
//        txtPassword.setOnClickListener(this);


//
//        dbAuthListener = new FirebaseAuth.AuthStateListener() {
//            @Override
//            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
//                FirebaseUser user = firebaseAuth.getCurrentUser();
//                if (user != null) {
//                    // User is signed in
//                    Log.d(TAG, "Signed in: " + user.getUid());
//                } else {
//                    // User is signed out
//                    Log.d(TAG, "Currently signed out");
//                }
//            }
//        };



    }


    @Override
    public void onStart() {
        super.onStart();
        //Add db auth state listener on startup
//        dbAuth.addAuthStateListener(dbAuthListener);
    }


    @Override
    public void onStop() {
        super.onStop();
//        //Remove the auth listener on stop
        if (dbAuthListener != null) {
            dbAuth.removeAuthStateListener(dbAuthListener);
        }
    }

    //Switch between buttons
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //cmdLogIn listener
            case R.id.cmdLogIn:
                Toast.makeText(Login.this, "LogIn", Toast.LENGTH_SHORT).show();
                signUserIn();
                break;
            //cmdCreateUser listener
            case R.id.cmdCreateUser:
                Toast.makeText(Login.this, "Creation", Toast.LENGTH_SHORT).show();
                createUserAccount();
                break;

        }
    }

    //Check if txt-fields are populated
    private boolean checkFields() {
        String email, password;

        email = txtEmail.getText().toString();
        password = txtPassword.getText().toString();

        if (email.isEmpty()) {
            txtEmail.setError("Email Required");
            return false;
        }
        if (password.isEmpty()) {
            txtPassword.setError("Password Required");
            return false;
        }

        return true;
    }


    /**
     * Signs user in, if credentials are valid.
     */
    private void signUserIn() {
        if (!checkFields())
            return;

        String email = txtEmail.getText().toString();
        String password = txtPassword.getText().toString();

        // TODO: sign the user in with email and password credentials
        dbAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(this,
                        new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(Login.this, "Signed in", Toast.LENGTH_SHORT)
                                            .show();
                                }
                                else {
                                    Toast.makeText(Login.this, "Sign in failed", Toast.LENGTH_SHORT)
                                            .show();
                                }
                            }
                        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        if (e instanceof FirebaseAuthInvalidCredentialsException) {
                            Toast.makeText(Login.this, "Invalid password", Toast.LENGTH_SHORT)
                                    .show();
                        }
                        else if (e instanceof FirebaseAuthInvalidUserException) {
                            Toast.makeText(Login.this, "No account with this email exists", Toast.LENGTH_SHORT)
                                    .show();
                        }
                        else {
                            Toast.makeText(Login.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT)
                                    .show();
                        }
                    }
                });
    }

        private void createUserAccount() {
        if (!checkFields())
            return;

        String email = txtEmail.getText().toString();
        String password = txtPassword.getText().toString();

        // TODO: Create the user account
        dbAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this,
                        new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(Login.this, "User created", Toast.LENGTH_SHORT)
                                            .show();
                                } else {
                                    Toast.makeText(Login.this, "Account creation failed", Toast.LENGTH_SHORT)
                                            .show();
                                }
                            }
                        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, e.toString());
                        if (e instanceof FirebaseAuthUserCollisionException) {
                            Toast.makeText(Login.this, "This email is already in use", Toast.LENGTH_SHORT)
                                    .show();
                        }
                        else {
                            Toast.makeText(Login.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT)
                                    .show();
                        }
                    }
                });
    }


}