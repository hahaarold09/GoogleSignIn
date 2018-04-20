

package com.delacerna.signin

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.auth.api.signin.GoogleSignInResult
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import kotlinx.android.synthetic.main.activity_main.*



class MainActivity : AppCompatActivity(), GoogleApiClient.OnConnectionFailedListener  {

    private val RC_SIGN_IN = 9001
    private var mGoogleApiClient: GoogleApiClient? = null

    private var btnLogin: Button? = null
    private var btnLogout: Button? = null

    override fun onConnectionFailed(p0: ConnectionResult) {
        Log.d("harold", "onConnectionFailed:$p0")
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnLogin = findViewById(R.id.btnLogin)
        btnLogout = findViewById(R.id.btnLogout)

        updateUI(false)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build()

        mGoogleApiClient = GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build()


        btnLogin?.setOnClickListener( {
            val signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient)
            startActivityForResult(signInIntent, RC_SIGN_IN)

        })

        btnLogout?.setOnClickListener( {
            Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback { updateUI(false) }
        })

    }

    private fun updateUI(isLogin: Boolean) {
        if (isLogin) {
            btnLogin?.visibility = View.GONE
            btnLogout?.visibility = View.VISIBLE
//            textName.text = acct?.givenName
        } else {
            btnLogin?.visibility = View.VISIBLE
            btnLogout?.visibility = View.GONE
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val result = Auth.GoogleSignInApi.getSignInResultFromIntent(data)
            handleSignInResult(result)

           val acct = GoogleSignIn.getLastSignedInAccount(this)



            textName.text = "Email: "+acct?.email
            textfam.text = "Name:"+acct?.displayName.toString()

            textfam1.text = "Photo: "+acct?.photoUrl.toString()


        }
    }


    private fun handleSignInResult(result: GoogleSignInResult) {
        if (result.isSuccess) {
            updateUI(true)
        }
    }
}