package com.jaroidx.navigation.ui.account

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.jaroidx.navigation.R

class AccountViewModel(private val application: Application) : AndroidViewModel(application) {
    private var auth = Firebase.auth
    private var currentUser = MutableLiveData<FirebaseUser?>()
    var _currentUser: MutableLiveData<FirebaseUser?> = currentUser
    val signInRequest = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken(application.getString(R.string.web_client_id)).requestEmail().build()
    val signInClient = GoogleSignIn.getClient(application, signInRequest)
    lateinit var callback: OnSignInStartedListener

    init {
        currentUser.value = auth.currentUser
    }

    fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null);
        auth.signInWithCredential(credential).addOnSuccessListener {
            if (it != null) {
                _currentUser.value = auth.currentUser
                Toast.makeText(application, "SignInSuccess", Toast.LENGTH_SHORT).show()
            } else {
                _currentUser.value = null
            }
        }.addOnFailureListener {
            Log.d("TAG", "firebaseAuthWithGoogle: ${it.message}")
        }
    }

    fun login() {
        callback.onSignInStarted(signInClient)
    }

    fun logout() {
        FirebaseAuth.getInstance().signOut()
        currentUser.value = null
    }

    interface OnSignInStartedListener {
        fun onSignInStarted(client: GoogleSignInClient?)
    }
}