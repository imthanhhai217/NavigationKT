package com.jaroidx.navigation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseUser
import com.jaroidx.navigation.R
import com.jaroidx.navigation.databinding.FragmentAccountBinding
import com.jaroidx.navigation.ui.account.AccountViewModel

class AccountFragment : Fragment() {

    private lateinit var binding: FragmentAccountBinding
    private lateinit var accountViewModel: AccountViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_account, container, false)
        binding = FragmentAccountBinding.bind(view)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        accountViewModel = (activity as MainActivity).accountViewModel
        accountViewModel.callback = onSignInStartedListener
        accountViewModel._currentUser.observe(viewLifecycleOwner, Observer {
            updateUI(it)
        })

        updateUI(accountViewModel._currentUser.value)

        binding.btnSignInGoogle.setOnClickListener(View.OnClickListener {
            accountViewModel.login()
        })
        binding.btnSignOut.setOnClickListener(View.OnClickListener {
            accountViewModel.logout()
        })
    }


    private val onSignInStartedListener = object : AccountViewModel.OnSignInStartedListener {
        override fun onSignInStarted(client: GoogleSignInClient?) {
            client?.signInIntent?.let { signInResultLauncher.launch(it) }
        }
    }

    private val signInResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult(),
            ActivityResultCallback {
                if (it.resultCode == AppCompatActivity.RESULT_OK && it.data != null) {
                    try {
                        val task = GoogleSignIn.getSignedInAccountFromIntent(it.data)
                        val account = task.getResult(ApiException::class.java)
                        account.idToken?.let { id -> accountViewModel.firebaseAuthWithGoogle(id) }
                    } catch (e: ApiException) {
                        Toast.makeText(activity, "SignInFailed", Toast.LENGTH_SHORT).show()
                    }
                }
            })

    private fun updateUI(it: FirebaseUser?) {
        binding.user = it
    }
}