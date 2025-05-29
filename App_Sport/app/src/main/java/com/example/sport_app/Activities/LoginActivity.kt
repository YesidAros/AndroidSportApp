package com.example.sport_app.Activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.sport_app.data.AppDatabase
import com.example.sport_app.data.User
import com.example.sport_app.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    private lateinit var btn_google: Button
    private lateinit var btnSignIn: Button
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private lateinit var tvSingUp: TextView

    private lateinit var etEmail: TextInputEditText
    private lateinit var etPassword: TextInputEditText


    private lateinit var userDao: com.example.sport_app.data.UserDao

    private val RC_SIGN_IN = 123
    private val TAG = "LoginActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        userDao = AppDatabase.getDatabase(applicationContext).userDao()

        etEmail = findViewById(R.id.tie_mail_example)
        etPassword = findViewById(R.id.tie_password)
        tvSingUp = findViewById(R.id.Sign_Up)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .requestProfile()
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
        btn_google = findViewById(R.id.bt_google)
        btn_google.setOnClickListener {
            signInWithGoogle()
        }

        btnSignIn = findViewById(R.id.bt_sing_in)
        btnSignIn.setOnClickListener {
            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString().trim()

            if (email.isEmpty()) {
                etEmail.error = "Email is required"
                etEmail.requestFocus()
                return@setOnClickListener
            }

            if (password.isEmpty()) {
                etPassword.error = "Password is required"
                etPassword.requestFocus()
                return@setOnClickListener
            }


            lifecycleScope.launch {
                val user = userDao.getUser(email, password)

                if (user != null) {

                    Log.d(TAG, "Login successful for: ${user.email}")
                    Toast.makeText(this@LoginActivity, "Login successful. Welcome!", Toast.LENGTH_SHORT).show()
                    navigateToResultsActivity(user.email, user.email)
                } else {
                    Log.w(TAG, "Login failed for: $email")
                    Toast.makeText(this@LoginActivity, "Invalid email or password.", Toast.LENGTH_SHORT).show()
                }
            }
        }

        tvSingUp.setOnClickListener {
            val intent = Intent(this, SingUpActivity::class.java)
            startActivity(intent)
        }
    }

    private fun signInWithGoogle() {
        val signInIntent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleGoogleSignInResult(task)
        }
    }

    private fun handleGoogleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)!!
            Log.d(TAG, "Google signInSuccess: ${account.email}")
            Toast.makeText(this, "Google Login Successful, Welcome ${account.displayName}", Toast.LENGTH_SHORT).show()
            navigateToResultsActivity(account.email, account.displayName)
        } catch (e: ApiException) {
            Log.w(TAG, "Google signInResult:failed code=" + e.statusCode)
            Toast.makeText(this, "Google Login Failed: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun navigateToResultsActivity(email: String?, name: String?) {
        val intent = Intent(this, ResultsActivity::class.java)
        intent.putExtra("email", email)
        intent.putExtra("name", name ?: email)
        intent.putExtra("startDestination", "search")
        startActivity(intent)
        finish()
    }
}