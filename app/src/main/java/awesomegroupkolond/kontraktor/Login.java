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
    private final String TAG = "FB_SIGNIN";

    //Get
    private FirebaseAuth dbAuth;
    private FirebaseAuth.AuthStateListener dbAuthListener;

    private EditText txtPassword;
    private EditText txtEmail;


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
            finish();
            startActivity(new Intent(getApplicationContext(), MainMenu.class));
        }

        // Set up click handlers and view item references
        findViewById(R.id.cmdCreateUser).setOnClickListener(this);
        findViewById(R.id.cmdLogIn).setOnClickListener(this);

        //Assign txtemail and set OnClickListener
        txtEmail = (EditText) findViewById(R.id.txtEmail);
        txtEmail.setOnClickListener(this);

        //Assign txtPassword and assign OnClickListener
        txtPassword = (EditText) findViewById(R.id.txtPassword);
        txtPassword.setOnClickListener(this);


    }


    @Override
    public void onStart() {
        super.onStart();
        //Add db auth state listener on startup
        dbAuth.addAuthStateListener(dbAuthListener);
    }


    @Override
    public void onStop() {
        super.onStop();
        //Remove the auth listener on stop
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
                signUserIn();
                break;
            //cmdCreateUser listener
            case R.id.cmdCreateUser:
                createUserAccount();
                break;

            //If txtEmail is clicked for the first time, delete existing text
            case R.id.txtEmail:
                if (txtEmail.getText().equals("Brugernavn"))
                    txtEmail.setText("");
                break;

            //If txtPassword is clicked for the first time, delete existing text
            case R.id.txtPassword:
                if (txtPassword.getText().equals("Brugernavn"))
                    txtPassword.setText("");
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
        dbAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this,
                        new OnCompleteListener<AuthResult>() {
                            //Authentication sign-in listener

                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                //Task: Authenticate email and password online
                                if (task.isSuccessful()) {
                                    //User can sign in
                                    //Close activity and start Main Menu
                                    finish();
                                    startActivity(new Intent(getApplicationContext(), MainMenu.class));

                                } else {

                                    //Inform user that authentication failed
                                    Toast.makeText(Login.this, "Sign in failed. Try again.", Toast.LENGTH_SHORT)
                                            .show();
                                }
                            }
                        })

                .addOnFailureListener(new OnFailureListener() {
                    //Authentication sign-in failure listener
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        //Invalid password
                        if (e instanceof FirebaseAuthInvalidCredentialsException) {
                            Toast.makeText(Login.this, "Invalid password", Toast.LENGTH_SHORT)
                                    .show();

                            //Invalid Email/user
                        } else if (e instanceof FirebaseAuthInvalidUserException) {
                            Toast.makeText(Login.this, "No account with this email", Toast.LENGTH_SHORT)
                                    .show();
                        } else {
                            Toast.makeText(Login.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT)
                                    .show();
                        }
                    }
                });
    }


    /**
     * Creates new user, if the credentials are not already in use.
     */
    private void createUserAccount() {
        //Check if fields have been populated
        if (!checkFields())
            return;

        String email = txtEmail.getText().toString();
        String password = txtPassword.getText().toString();


        //Create new user
        dbAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    //Completion listener
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //Task: verify that user doesn't already exist
                        if (task.isSuccessful()) {

                            //User is created
                            Toast.makeText(Login.this, "Creation successful", Toast.LENGTH_SHORT).show();
                            //Close activity and open Main Menu
                            finish();
                            startActivity(new Intent(getApplicationContext(), MainMenu.class));
                        } else {
                            //Inform user that creation failed
                            Toast.makeText(Login.this, "User account creation failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    //Account creation failure listener
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        //Collision listener - ensures the credentials are not already in use
                        if (e instanceof FirebaseAuthUserCollisionException) {
                            Toast.makeText(Login.this, "This email is already in use", Toast.LENGTH_SHORT)
                                    .show();
                        } else {
                            Toast.makeText(Login.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT)
                                    .show();
                        }
                    }
                });
    }


}