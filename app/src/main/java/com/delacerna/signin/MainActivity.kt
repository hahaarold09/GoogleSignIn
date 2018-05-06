package com.delacerna.signin


import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.GoogleApiClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.android.synthetic.main.activity_main.*
import com.google.android.gms.auth.api.signin.GoogleSignInResult


class MainActivity : AppCompatActivity(), GoogleApiClient.OnConnectionFailedListener {

    private var mFirebaseAuth: FirebaseAuth? = null
    private var RC_SIGN_IN = 9001
    private var mGoogleApiClient: GoogleApiClient? = null

    override fun onConnectionFailed(p0: ConnectionResult) {
        Log.i("harold", "onConnectionFailed:$p0")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("531470829820-m1me191pbp54rbmh0hd6gapjdtakt055.apps.googleusercontent.com")
                .requestServerAuthCode("531470829820-m1me191pbp54rbmh0hd6gapjdtakt055.apps.googleusercontent.com")
                .requestProfile()
                .requestEmail()
                .build()

        mGoogleApiClient = GoogleApiClient.Builder(this)
                .enableAutoManage(this) {}
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .addApi(Auth.CREDENTIALS_API)
                .build()

        mFirebaseAuth = FirebaseAuth.getInstance()

        googleLogIn()


    }

    private fun googleLogIn() {
        googlelogin.setSize(SignInButton.SIZE_WIDE)
        googlelogin.setColorScheme(SignInButton.COLOR_AUTO)

        googlelogin?.setOnClickListener({
            val signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient)
            startActivityForResult(signInIntent, RC_SIGN_IN)
        })
    }


    private fun updateUI(isLogin: Boolean) {

        if (isLogin) {
            googlelogin?.visibility = View.GONE

        } else {
            googlelogin?.visibility = View.VISIBLE
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val result = Auth.GoogleSignInApi.getSignInResultFromIntent(data)
            handleSignInResult(result)

        }
    }

    private fun handleSignInResult(result: GoogleSignInResult) {
        if (result.isSuccess) {
            firebaseAuthWithGoogle(result.signInAccount!!)
            updateUI(true)
        }
    }

    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {

        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        mFirebaseAuth!!.signInWithCredential(credential)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        println("harold: "+acct.serverAuthCode)
                        val data = Intent(this@MainActivity, LogOutActivity::class.java)
                        startActivity(data)
                    } else {
                        Toast.makeText(applicationContext, "firebase error.", Toast.LENGTH_SHORT).show()
                    }
                }
    }
//
//    private fun updateToken(invalidateToken: Boolean): String {
//
//        var authToken = "null"
//        try {
//            mAccount = AccountManager.get(applicationContext)
//            val accounts = mAccount!!.getAccountsByType("com.google")
//            val accountManagerFuture: AccountManagerFuture<Bundle>
//            accountManagerFuture = mAccount!!.getAuthToken(accounts[0],
//                    "oauth2:https://www.googleapis.com/auth/userinfo.profile",
//                    null, this, null, null)
//            val authTokenBundle = accountManagerFuture.result
//            authToken = authTokenBundle.getString(AccountManager.KEY_AUTHTOKEN)!!.toString()
//            if (invalidateToken) {
//                mAccount!!.invalidateAuthToken("com.google", authToken)
//                authToken = updateToken(false)
//            }
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
//
//        return authToken
//    }


}






