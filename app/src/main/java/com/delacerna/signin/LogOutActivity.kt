package com.delacerna.signin

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.FragmentTransaction
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.widget.LinearLayout
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_log_out.*
import kotlinx.android.synthetic.main.fragment_fragment_sample.*

class LogOutActivity : AppCompatActivity(), GoogleApiClient.OnConnectionFailedListener {
    override fun onConnectionFailed(p0: ConnectionResult) {
        Log.i("harold", "onConnectionFailed:$p0")
    }
    private var mFragmentTransaction: FragmentTransaction?=null
    private var mGoogleApiClient: GoogleApiClient? = null
    private var mFirebaseAuth: FirebaseAuth? = null
    private val recipe = ArrayList<Campaign>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_out)

        mFirebaseAuth = FirebaseAuth.getInstance()


        val user = mFirebaseAuth?.currentUser
//        textName1.text = user?.displayName
//        textName2.text = user?.email
//        Glide.with(this).load(user?.photoUrl).into(imgaccount)


        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestProfile()
                .build()

        mGoogleApiClient = GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .addApi(Auth.CREDENTIALS_API)
                .build()

        btnLogout?.setOnClickListener {
            Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback {
                Toast.makeText(this@LogOutActivity, "Logout Successfully!", Toast.LENGTH_SHORT).show()
            }
            val i = Intent(this, MainActivity::class.java)
            startActivity(i)
        }

        val fragment = FragmentSample()
        mFragmentTransaction = supportFragmentManager.beginTransaction()
        mFragmentTransaction?.add(R.id.fragContainer, fragment,"Sample")
        mFragmentTransaction?.commit()

    }

    override fun onStart() {
        super.onStart()
        mGoogleApiClient?.connect()
    }
}


