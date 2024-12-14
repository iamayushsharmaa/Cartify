package com.example.foodorderapp.repository.firebase

import android.app.Activity
import android.util.Log
import android.widget.Toast
import com.example.foodorderapp.data.ResultState
import com.example.foodorderapp.data.User
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthMissingActivityForRecaptchaException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.PhoneAuthProvider.ForceResendingToken
import com.google.firebase.auth.PhoneAuthProvider.OnVerificationStateChangedCallbacks
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class FirebaseRepositoryImpl : FirebaseRepository {
    private var _verificationId: String? = null
    override val verificationId: String?
        get() = _verificationId

    private lateinit var resendToken: ForceResendingToken

    private val auth = FirebaseAuth.getInstance()

    override suspend fun createUserWithPhone(phoneNumber: String, activity: Activity) : Flow<ResultState<String>> = callbackFlow{
        trySend(ResultState.Loading)

        val verificationCallbacks = object : OnVerificationStateChangedCallbacks() {

            override fun onVerificationCompleted(credential: PhoneAuthCredential) {

                Log.d("TAG", "onVerificationCompleted:$credential")
                Toast.makeText(activity, "Verification Successful", Toast.LENGTH_SHORT).show()
            }

            override fun onVerificationFailed(e: FirebaseException) {
                trySend(ResultState.Failure(e))

                Log.w("TAG", "Failed", e)
                when (e) {
                    is FirebaseAuthInvalidCredentialsException -> {
                        Toast.makeText(activity, "Invalid credentials: ${e.message}", Toast.LENGTH_SHORT).show()
                    }
                    is FirebaseTooManyRequestsException -> {
                        Toast.makeText(activity, "SMS quota exceeded.", Toast.LENGTH_SHORT).show()
                    }
                    is FirebaseAuthMissingActivityForRecaptchaException -> {
                        Toast.makeText(activity, "ReCaptcha verification failed.", Toast.LENGTH_SHORT).show()
                    }
                    else -> {
                        Toast.makeText(activity, "Verification failed: ${e.message}", Toast.LENGTH_SHORT).show()
                        Log.d("verify", "failed bro: $phoneNumber")
                    }
                }
            }

            override fun onCodeSent(verificationId: String, token: ForceResendingToken) {
                trySend(ResultState.Success("Otp sent successfully"))
                Log.d("TAG", "onCodeSent:$verificationId")
                _verificationId = verificationId
                resendToken = token
                Log.d("verify", "onCodeSent: $phoneNumber")
                Toast.makeText(activity, "Code sent to $phoneNumber", Toast.LENGTH_SHORT).show()
            }

        }

        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber("+91$phoneNumber")
            .setTimeout(60L, java.util.concurrent.TimeUnit.SECONDS)
            .setActivity(activity)
            .setCallbacks(verificationCallbacks)
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
        awaitClose{
            close()
        }

    }

    override suspend fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential,firestore : FirebaseFirestore,phoneNumber: String) : Flow<ResultState<String>> = callbackFlow{
        trySend(ResultState.Loading)
        auth.signInWithCredential(credential)
            .addOnCompleteListener{ task ->
                if (task.isSuccessful) {
                   // trySend(ResultState.Success("Otp verified"))
                    val userId = auth.currentUser!!.uid
                    val userRef = firestore.collection("userData").document(userId)
                    userRef.set(
                        User(
                            userId = userId,
                            userPhoneNumber = phoneNumber,
                            userAddress = null // Replace with actual address if needed
                        )
                    ).addOnCompleteListener { firestoreTask ->
                        if (firestoreTask.isSuccessful) {
                            trySend(ResultState.Success("Otp verified and user data saved"))
                        } else {
                            trySend(ResultState.Failure(firestoreTask.exception ?: Exception("Failed to save user data")))
                        }
                    }
                    Log.d("User id",userId)
                }
            }.addOnFailureListener {
                trySend(ResultState.Failure(it))
            }
        awaitClose{
            close()
        }
    }
}