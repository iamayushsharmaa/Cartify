package com.example.foodorderapp.view.login

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.foodorderapp.R
import com.example.foodorderapp.data.model.AuthState
import com.example.foodorderapp.ui.theme.poppinsFontFamily
import com.example.foodorderapp.viewmodel.FirebaseViewModel


@Composable
fun Login(
    navController: NavHostController,
    viewModel: FirebaseViewModel = hiltViewModel()
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    var isLoginScreen by remember { mutableStateOf(true) }

    val authState by viewModel.authState.collectAsState(initial = AuthState.Idle)
    val context = LocalContext.current

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .background(color = Color.White),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            when (authState) {
                is AuthState.Loading -> {
                    ProgressDialog(
                        text = "Loading...",
                        showDialog = true,
                        onDismiss = {}
                    )
                }
                is AuthState.Success -> {
                    navController.navigate("homeScreen") {
                        popUpTo("login") { inclusive = true }
                    }
                }
                is AuthState.Error -> {
                    val errorMessage = (authState as AuthState.Error).message
                    Toast.makeText(context, "Try agian", Toast.LENGTH_SHORT).show()
                }
                else -> {}
            }
            Box(
                modifier = Modifier
                    .size(90.dp)
                    .align(Alignment.CenterHorizontally)
                    .background(color = Color.White),
                contentAlignment = Alignment.Center,
            ){
                Icon(
                    painter = painterResource(id = R.drawable.icon_enter),
                    contentDescription = "icon for signin logo",
                    tint = Color.Black,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(12.dp)
                )
            }
            Spacer(Modifier.height(10.dp))
            Text(
                text = if (isLoginScreen) "Sign in." else "Sign up.",
                fontFamily = poppinsFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 40.sp,
                modifier = Modifier
                    .fillMaxWidth(),
                textAlign = TextAlign.Center,
                color = Color.Black
            )

            Spacer(Modifier.height(16.dp))

            if (!isLoginScreen) {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },

                    leadingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.icon_user),
                            contentDescription = "",
                            tint = Color.Black,
                            modifier = Modifier.size(22.dp)
                        )
                    },
                    label = { Text("Name") },
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color(0xFFD4D4D4),
                        unfocusedContainerColor = Color(0xFFD4D4D4),
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.Black,
                        unfocusedLabelColor = Color.Gray,
                        focusedLabelColor = Color.Gray
                    )
                )
            }

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = {Text("Email")},
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.icon_email),
                        contentDescription = "email icon",
                        tint = Color.Black,
                        modifier = Modifier.size(22.dp)
                    )
                },
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 5.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color(0xFFD4D4D4),
                    unfocusedContainerColor = Color(0xFFD4D4D4),
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                    unfocusedLabelColor = Color.Gray,
                    focusedLabelColor = Color.Gray
                )
            )

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text(text = "Password") },
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.lock),
                        contentDescription = "",
                        tint = Color.Black,
                        modifier = Modifier.size(22.dp)
                    )
                },
                trailingIcon = {
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(
                            painter = painterResource(
                                id = if (passwordVisible) R.drawable.show_password_icon
                                else R.drawable.icon_hidepassword
                            ),
                            modifier = Modifier.size(22.dp),
                            contentDescription = if (passwordVisible) "Hide Password" else "Show Password"
                        )
                    }
                },
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),

                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color(0xFFD4D4D4),
                    unfocusedContainerColor = Color(0xFFD4D4D4),
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                    unfocusedLabelColor = Color.Gray,
                    focusedLabelColor = Color.Gray
                ),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 5.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    if (isLoginScreen) {
                        viewModel.loginUser(email, password)
                    } else {
                        viewModel.registerUser(email, password, name)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(75.dp)
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Black,
                    contentColor = Color.White
                )
            ) {
                Text(
                    text = if (isLoginScreen) "Sign in" else "Sign up",
                    fontSize = 18.sp,
                    fontFamily = poppinsFontFamily,
                    fontWeight = FontWeight.SemiBold
                )
            }
            Text(
                text = if (isLoginScreen) "Don't have an account? Sign up!" else "Already have an account? Sign in!",
                color = Color.Black,
                fontSize = 14.sp,
                fontFamily = poppinsFontFamily,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { isLoginScreen = !isLoginScreen }
                    .padding(5.dp),
                textAlign = TextAlign.Center
            )
        }
    }
}
