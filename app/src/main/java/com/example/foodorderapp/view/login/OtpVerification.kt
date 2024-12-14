package com.example.foodorderapp.view.login


import android.app.Activity
import android.widget.Toast
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.foodorderapp.R
import com.example.foodorderapp.data.PhoneAuthState
import com.example.foodorderapp.viewmodel.FirebaseViewModel
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun OtpVerificationScreen(
    navController: NavController,
    phoneNumber: String,
    firebaseViewModel: FirebaseViewModel,
    firestore: FirebaseFirestore
) {

    val context = LocalContext.current
    val activity = context as Activity

    val otpValues = remember { mutableStateListOf("", "", "", "", "", "") }
    var showDialog by remember { mutableStateOf(false) }

    val authState by firebaseViewModel.authState.collectAsState()
    val phoneNumber by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()

    when (authState) {
        is PhoneAuthState.Idle -> {}

        is PhoneAuthState.Loading -> {
            ProgressDialog(
                showDialog = true,
                text = "Loading...",
                onDismiss = {}
            )
        }
        is PhoneAuthState.CodeSent -> {
            val message = (authState as PhoneAuthState.CodeSent).message
            Toast.makeText(activity, "otp sent again", Toast.LENGTH_SHORT).show()
        }
        is PhoneAuthState.Verified -> {
            val message = (authState as PhoneAuthState.Verified).message
            Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
            navController.navigate("homeScreen"){
                popUpTo("otpVerificationScreen") { inclusive = true }
            }
        }
        is PhoneAuthState.Error -> {
            val errorMessage = (authState as PhoneAuthState.Error).error
            Toast.makeText(activity, errorMessage, Toast.LENGTH_SHORT).show()
        }
    }
    Scaffold (
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row (modifier = Modifier.padding(5.dp)){
                IconButton(
                    modifier = Modifier.padding(5.dp),
                    onClick = {
                        navController.popBackStack()
                    },
                    colors = IconButtonColors(
                        contentColor = MaterialTheme.colorScheme.primary,
                        containerColor = MaterialTheme.colorScheme.secondary,
                        disabledContainerColor = MaterialTheme.colorScheme.secondary,
                        disabledContentColor = MaterialTheme.colorScheme.primary
                    )
                ){
                    val iconId = if (isSystemInDarkTheme()) {
                        R.drawable.prev_arrow_white
                    } else {
                        R.drawable.prev_arrow_black
                    }
                    Icon(
                        painter = painterResource(id = iconId),
                        contentDescription = "Back button"
                    )
                }

                Text(
                    text = "OTP Verification",
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Normal,
                    fontSize = 30.sp
                )
            }

            Text(
                text = "We have sent an OTP to you Phone Number +91 ${phoneNumber}",
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
                modifier = Modifier.padding(25.dp)
            )
            Spacer(Modifier.height(12.dp))

            OTPInputTextFields(
                otpValues = otpValues,
                modifier = Modifier,
                otpLength = 6,
                onOtpInputComplete = { },
                onUpdateOtpValuesByIndex = { index, value ->
                    otpValues[index] = value
                }
            )

            Button(
                onClick = {
                    // Show progress dialog
                    val otp = otpValues.joinToString("")
                    if (otp.length < otpValues.size) {
                        Toast.makeText(activity, "Please enter a valid OTP", Toast.LENGTH_SHORT).show()
                    }else{
                        showDialog = true
                        firebaseViewModel.verifyOtp(otp, firestore, phoneNumber)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.background
                )
                ) {

                Text(text = "Verify", textAlign = TextAlign.Center, fontSize = 17.sp)
            }
            if (showDialog) {
                ProgressDialog(
                    showDialog = true,
                    text = "Signing in",
                    onDismiss = {}
                )
            }
        }
    }
}

@Preview
@Composable
private fun Alak() {

    OtpVerificationScreen(
        navController =  rememberNavController(),
        phoneNumber = "918957544",
        firebaseViewModel = viewModel(),
        firestore = FirebaseFirestore.getInstance(),
    )
}