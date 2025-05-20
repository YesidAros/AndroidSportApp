package com.example.sport_app.Activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.sport_app.MainActivity
import com.example.sport_app.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.android.material.textfield.TextInputEditText

class LoginActivity : AppCompatActivity() {

    private lateinit var btn_google: Button
    private lateinit var btnSignIn: Button
    private lateinit var mGoogleSignInClient: GoogleSignInClient

    private lateinit var etEmail: TextInputEditText
    private lateinit var etPassword: TextInputEditText

    private val RC_SIGN_IN = 123
    private val TAG = "GoogleSignIn"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        etEmail = findViewById(R.id.tie_mail_example)
        etPassword = findViewById(R.id.tie_password)

        //Google Configuration Sign In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .requestProfile()
            .build()

        // //Google Sign In Button
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
        btn_google = findViewById(R.id.bt_google)
        btn_google.setOnClickListener {
            signIn()
            val intent = Intent(this, ResultsActivity::class.java)
            startActivity(intent)
        }

        //Sign In Button
        btnSignIn = findViewById(R.id.bt_sing_in)
        btnSignIn.setOnClickListener {
            val intent = Intent(this, ResultsActivity::class.java)
            startActivity(intent)
        }

        btnSignIn.setOnClickListener {
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Enter valid username and password", Toast.LENGTH_SHORT).show()
            } else {

                Toast.makeText(this, "Trying to log in", Toast.LENGTH_SHORT).show()

                val intent = Intent(this, ResultsActivity::class.java)
                intent.putExtra("email", email)
                intent.putExtra("name", email)
                intent.putExtra("startDestination", "search")
                startActivity(intent)
            }
        }
    }

    private fun signIn(){
        val sigIntent = mGoogleSignInClient.signInIntent
        startActivityForResult(sigIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN){
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }

    }

    private fun handleSignInResult(CompletedTask:Task<GoogleSignInAccount>){
        try {
            val account = CompletedTask.getResult(ApiException::class.java)
            //login Successful with Google Account
            Log.d(TAG, "signInSuccess: ${account.email}")
            Toast.makeText(this, "Login Successful, Welcome ${account.displayName}", Toast.LENGTH_SHORT).show()

            // Go to Main Activity
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("email", account.email)
            intent.putExtra("name", account.displayName)
            intent.putExtra("startDestination", "search")
            startActivity(intent)

        } catch (e: ApiException){
            //Login Failed
            Log.w(TAG, "signInResult:failed code=" + e.statusCode)
            Toast.makeText(this, "Login Failed", Toast.LENGTH_SHORT).show()
        }
    }
}
