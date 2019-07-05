package com.bahricorp.stumarkt.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bahricorp.stumarkt.R;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener
{

    private Button buttonSignIn;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private TextView textViewSignUp;

    private FirebaseAuth firebaseAuth;

    private ProgressDialog progressDialog;

    private GoogleApiClient googleApiClient;
    private static final int code=100;

    SignInButton btnGoogleSignIn;

    private GoogleSignInClient googleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() != null)
        {
            finish();
            startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
        }
        else
        {
            Toast.makeText(LoginActivity.this, "Couldn't Login. Pls. Try Again", Toast.LENGTH_SHORT).show();
        }

        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        buttonSignIn = (Button) findViewById(R.id.buttonSignIn);
        btnGoogleSignIn = findViewById(R.id.btnGoogleSignIn);
        textViewSignUp = (TextView) findViewById(R.id.textViewSignup);

        progressDialog = new ProgressDialog(this);

        /* // old
        GoogleSignInOptions signInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        googleApiClient = new GoogleApiClient.Builder(this).enableAutoManage(this, this).addApi(Auth.GOOGLE_SIGN_IN_API, signInOptions).build();
        */

        //GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        //googleSignInClient = GoogleSignIn.getClient(this, gso);

        // new
        buttonSignIn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                userLogin();
            }
        });

        textViewSignUp.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                finish();
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

        btnGoogleSignIn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // googleSocialLogin();
            }
        });

        /*
        btnLogout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                googleLogout();
            }
        });
        */

        // old
        // buttonSignIn.setOnClickListener(this);
        // textViewSignUp.setOnClickListener(this);
    }

    private void userLogin()
    {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if(TextUtils.isEmpty(email))
        {
            Toast.makeText(this, "Please Enter Email", Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(password))
        {
            Toast.makeText(this, "Please Enter Password", Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.setMessage("Logging In...");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>()
                {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                      progressDialog.dismiss();
                        if(task.isSuccessful())
                        {
                            finish();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        }
                    }
                });
    }

    /*
    @Override
    public void onClick(View view)
    {
        if (view == buttonSignIn)
        {
            userLogin();
        }

        if (view == textViewSignUp)
        {
            finish();
            startActivity(new Intent(this, RegisterActivity.class));
        }
    }
    */

    private void googleSocialLogin()
    {
        Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        startActivityForResult(intent, code);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == code)
        {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleResultGoogleSignIn(result);
        }
    }

    private void handleResultGoogleSignIn(GoogleSignInResult result)
    {
        if(result.isSuccess())
        {
            btnGoogleSignIn.setVisibility(View.GONE);

            // button visible
            // lyProfile.setVisibility(View.VISIBLE);

            GoogleSignInAccount account = result.getSignInAccount();
            String socialFirstName = account.getGivenName();
            String socialLastName = account.getFamilyName();
            String socialEmail = account.getEmail();
            String socialUserId = account.getId();
            String socialImageUrl = account.getPhotoUrl().toString();

            /* // set text
            tvName.setText(socialFirstName);
            tvSurname.setText(socialLastName);
            tvEmail.setText(socialEmail);
            */

            // load google image
            // Picasso.with(this).load(socialImageUrl).into(ivUser);
        }
    }

    private void googleLogout()
    {
        Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(new ResultCallback<Status>()
        {
            @Override
            public void onResult(@NonNull Status status)
            {
                btnGoogleSignIn.setVisibility(View.VISIBLE);

                // button visible
                // lyProfile.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult)
    {

    }

}
