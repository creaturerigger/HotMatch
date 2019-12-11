package com.cop4485_volkan_bakir.hotmatch

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseUser
import java.util.concurrent.Executors

private const val TAG = "LoginFragment"

class LoginFragment: Fragment() {

    interface Callbacks {
        fun onNotRegisteredClicked()
    }

    private var callbacks: Callbacks? = null

    private lateinit var userEmail: EditText
    private lateinit var userPassword: EditText
    private lateinit var loginButton: Button
    private lateinit var notRegisteredText: TextView
    private lateinit var auth: FirebaseAuth

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as Callbacks?
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.hide()
        auth = FirebaseAuth.getInstance()
    }

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        updateUI(currentUser)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_login, container, false)
        userEmail = view.findViewById(R.id.lf_text_edit_email)
        userPassword = view.findViewById(R.id.lf_text_edit_password)
        notRegisteredText = view.findViewById(R.id.lf_not_registered)
        notRegisteredText.setOnClickListener {
            callbacks?.onNotRegisteredClicked()
        }
        loginButton = view.findViewById(R.id.lf_login_button)
        loginButton.setOnClickListener {
            val userEmailText = userEmail.text.toString()
            val userPasswordText = userPassword.text.toString()
            val isInfoValid = validateFields(userEmailText, userPasswordText)
            if (isInfoValid) {
                val isSignedIn = signIn(userEmailText, userPasswordText)
                Log.d(TAG, "isSignedIn = $isSignedIn")
            }
        }
        return view
    }

    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }

    private fun updateUI(currentUser: FirebaseUser?) {
        if (currentUser == null) {
            Log.d(TAG, "Signed in failed")
        } else {
            Log.d(TAG, "${currentUser.email} is signed in")
        }
    }

    private fun validateFields(userEmail: String, password: String): Boolean {
        return if (userEmail.isEmpty() || password.isEmpty()) {
            Toast.makeText(context, R.string.empty_fields, Toast.LENGTH_SHORT).show()
            false
        } else {
            if(!isEmailValid(userEmail)) {
                Toast.makeText(context, R.string.is_not_valid_email, Toast.LENGTH_SHORT).show()
                false
            } else {
                Toast.makeText(context, R.string.logging_in, Toast.LENGTH_SHORT).show()
                true
            }
        }
    }

    private fun isEmailValid(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun signIn(email: String, password: String): Boolean {
        var isSignedIn = false
        val parentActivity = MainActivity.newInstance()
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(parentActivity) { task ->
                isSignedIn = if (task.isSuccessful) {
                    val user = auth.currentUser
                    Log.d(TAG, "${user?.displayName} has signed in")
                    true
                } else {
                    try {
                        throw task.exception!!
                    }
                    catch (invalidMail: FirebaseAuthInvalidUserException) {
                        Toast
                            .makeText(context, "You have entered a wrong e-mail address.", Toast.LENGTH_SHORT)
                            .show()
                    }
                    catch (wrongPass: FirebaseAuthInvalidCredentialsException) {
                        Toast
                            .makeText(context, "You have entered a wrong password", Toast.LENGTH_SHORT)
                            .show()
                    }
                    Toast.makeText(context, "Sign in failed", Toast.LENGTH_SHORT).show()
                    false
                }
            }
        return isSignedIn
    }
}