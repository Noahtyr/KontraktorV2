package awesomegroupkolond.kontraktor;

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

public class LoginScreen extends AppCompatActivity
        implements View.OnClickListener{
    private final String TAG = "FB_SIGNIN";

    //Get
    private final FirebaseAuth dbAuth = FirebaseAuth.getInstance();
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

        // Set up click handlers and view item references
        findViewById(R.id.cmdCreateUser).setOnClickListener(this);
        findViewById(R.id.cmdLogIn).setOnClickListener(this);

        //Assign txtemail and set OnClickListener
        txtEmail = (EditText)findViewById(R.id.txtEmail);
        txtEmail.setOnClickListener(txtEmailListener);
        //Assign txtPassword and assign OnClickListener
        txtPassword = (EditText)findViewById(R.id.txtPassword);
        txtPassword.setOnClickListener(txtPasswordListener);


        // TODO: Attach a new AuthListener to detect sign in and out
        dbAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "Signed in: " + user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "Currently signed out");
                }
            }
        };

        updateStatus();
    }

    private final View.OnClickListener txtEmailListener = new View.OnClickListener(){

        @Override
        public void onClick(View view) {
            txtEmail.setText("");
        }
    };

    private final View.OnClickListener txtPasswordListener = new View.OnClickListener(){

        @Override
        public void onClick(View view) {
            txtPassword.setText("");
        }
    };


    public void onStart() {
        super.onStart();
        // TODO: add the AuthListener
        dbAuth.addAuthStateListener(dbAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        // TODO: Remove the AuthListener
        if (dbAuthListener != null) {
            dbAuth.removeAuthStateListener(dbAuthListener);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cmdLogIn:
                signUserIn();
                break;

            case R.id.cmdCreateUser:
                createUserAccount();
                break;
        }
    }

    private boolean checkFormFields() {
        String email, password;

        email = txtEmail.getText().toString();
        password = txtPassword.getText().toString();

        if (email.isEmpty()) {
            txtEmail.setError("Email Required");
            return false;
        }
        if (password.isEmpty()){
            txtPassword.setError("Password Required");
            return false;
        }

        return true;
    }

    private void updateStatus() {
        TextView tvStat = (TextView)findViewById(R.id.lblStatus);
        FirebaseUser user = dbAuth.getCurrentUser();
        if (user != null) {
            tvStat.setText("Signed in: " + user.getEmail());
        }
        else {
            tvStat.setText("Signed Out");
        }
    }

    private void updateStatus(String stat) {
        TextView tvStat = (TextView)findViewById(R.id.lblStatus);
        tvStat.setText(stat);
    }

    private void signUserIn() {
        if (!checkFormFields())
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
                                    Toast.makeText(LoginScreen.this, "Signed in", Toast.LENGTH_SHORT)
                                            .show();
                                }
                                else {
                                    Toast.makeText(LoginScreen.this, "Sign in failed", Toast.LENGTH_SHORT)
                                            .show();
                                }

                                updateStatus();
                            }
                        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        if (e instanceof FirebaseAuthInvalidCredentialsException) {
                            updateStatus("Invalid password.");
                        }
                        else if (e instanceof FirebaseAuthInvalidUserException) {
                            updateStatus("No account with this email.");
                        }
                        else {
                            updateStatus(e.getLocalizedMessage());
                        }
                    }
                });
    }


    private void createUserAccount() {
        if (!checkFormFields())
            return;

        String email = txtEmail.getText().toString();
        String password = txtPassword.getText().toString();

        // creating the user account:
        dbAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this,
                        new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(LoginScreen.this, "User created: " + txtEmail.getText(), Toast.LENGTH_SHORT)
                                            .show();
                                } else {
                                    Toast.makeText(LoginScreen.this, "Account creation failed", Toast.LENGTH_SHORT)
                                            .show();
                                }
                            }
                        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, e.toString());
                        if (e instanceof FirebaseAuthUserCollisionException) {
                            updateStatus("This email address is already in use.");
                        }
                        else {
                            updateStatus(e.getLocalizedMessage());
                        }
                    }
                });
    }




}
