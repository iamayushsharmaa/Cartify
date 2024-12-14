package com.example.foodorderapp.view.login

import android.app.Activity
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.foodorderapp.R
import com.example.foodorderapp.data.PhoneAuthState
import com.example.foodorderapp.viewmodel.FirebaseViewModel
import com.google.android.play.integrity.internal.ac

@Composable
fun PhoneSignIn(navController: NavHostController, firebaseViewModel: FirebaseViewModel) {
    val authState by firebaseViewModel.authState.collectAsState()
    var phoneNumber by remember { mutableStateOf("") }

    val context = LocalContext.current
    //val activity = context as Activity
    LaunchedEffect(authState) {
        when (authState) {
            is PhoneAuthState.CodeSent -> {
                val message = (authState as PhoneAuthState.CodeSent).message
                navController.navigate("otpVerificationScreen/$phoneNumber")
            }
            is PhoneAuthState.Verified -> {
                val message = (authState as PhoneAuthState.Verified).message
                Toast.makeText( context, message, Toast.LENGTH_SHORT).show()
                navController.navigate("homeScreen") // Navigate to the home screen
            }
            is PhoneAuthState.Error -> {
                val errorMessage = (authState as PhoneAuthState.Error).error
                Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
            }
            else -> Unit
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            if (authState is PhoneAuthState.Loading) {
                // Disable background interaction
                AuthContent(
                    phoneNumber = phoneNumber,
                    onPhoneNumberChange = { phoneNumber = it },
                    onLoginClick = {}
                )
            } else {
                // Enable AuthContent interaction when not loading
                AuthContent(
                    phoneNumber = phoneNumber,
                    onPhoneNumberChange = { phoneNumber = it },
                    onLoginClick = {
                        if (context is Activity) {
                            firebaseViewModel.sendOtp(phoneNumber, context)
                        } else {
                            Log.e("PhoneSignIn", "Context is not an Activity")
                            Toast.makeText(context, "Unable to send OTP: Invalid context", Toast.LENGTH_SHORT).show()
                        }
                    }
                )
            }
        }
        // Show ProgressDialog if the authState is Loading
        if (authState is PhoneAuthState.Loading) {
            ProgressDialog(
                showDialog = true,
                text = "Sending OTP...",
                onDismiss = {}
            )
        }
    }
}

@Composable
fun AuthContent(
    phoneNumber: String,
    onPhoneNumberChange: (String) -> Unit,
    onLoginClick:() -> Unit
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 25.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.blinkit_app_icon),
            contentDescription = "blinkit icon",
            modifier = Modifier.size(100.dp)
        )
        Text(
            text = "Blinkit",
            fontSize = 40.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(15.dp)
        )
        Spacer(modifier = Modifier.height(20.dp))
        OutlinedTextField(
            value = phoneNumber,
            onValueChange = onPhoneNumberChange,
            label = { Text(text = "Phone number") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.phone_icon),
                    contentDescription = null
                )
            },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(10.dp))
        Button(
            onClick = onLoginClick,
            modifier = Modifier.fillMaxWidth(),
            enabled = phoneNumber.length == 10
        ) {
            Text(text = "Login")
        }
    }
}



@Preview
@Composable
fun SignInPreview(){
    PhoneSignIn(navController = rememberNavController(), firebaseViewModel = viewModel())
}