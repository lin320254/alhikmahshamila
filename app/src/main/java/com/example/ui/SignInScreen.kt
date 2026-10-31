package com.example.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.CloudDone
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Security
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.firebase.AuthManager
import com.example.ui.theme.AmiriFontFamily
import com.example.ui.theme.JameelNooriFontFamily
import com.example.ui.theme.LuminousGreenText
import com.example.ui.theme.RadiantGreenPrimary
import com.example.ui.theme.TextMutedGreen
import com.example.ui.theme.TextWhitePrimary
import kotlinx.coroutines.launch

@Composable
fun SignInScreen(
    authManager: AuthManager,
    currentLang: AppLanguage,
    onLanguageChange: (AppLanguage) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFF031008),
                            Color(0xFF06180E),
                            Color(0xFF020704)
                        )
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            // Background ambient glow
            Box(
                modifier = Modifier
                    .size(320.dp)
                    .background(
                        Brush.radialGradient(
                            colors = listOf(
                                Color(0x3022C55E),
                                Color.Transparent
                            )
                        ),
                        shape = CircleShape
                    )
            )

            Column(
                modifier = Modifier
                    .padding(24.dp)
                    .widthIn(max = 440.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                // Top Language Switcher
                Surface(
                    shape = RoundedCornerShape(22.dp),
                    color = Color(0x35142E20),
                    border = BorderStroke(1.dp, Color(0x604ADE80)),
                    modifier = Modifier.padding(bottom = 28.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(3.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            shape = RoundedCornerShape(16.dp),
                            color = if (currentLang == AppLanguage.ARABIC) RadiantGreenPrimary else Color.Transparent,
                            modifier = Modifier
                                .clip(RoundedCornerShape(16.dp))
                                .clickable { onLanguageChange(AppLanguage.ARABIC) }
                        ) {
                            Text(
                                text = "العربية",
                                fontFamily = AmiriFontFamily,
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp,
                                color = if (currentLang == AppLanguage.ARABIC) Color(0xFF03190C) else TextMutedGreen,
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                            )
                        }

                        Surface(
                            shape = RoundedCornerShape(16.dp),
                            color = if (currentLang == AppLanguage.URDU) RadiantGreenPrimary else Color.Transparent,
                            modifier = Modifier
                                .clip(RoundedCornerShape(16.dp))
                                .clickable { onLanguageChange(AppLanguage.URDU) }
                        ) {
                            Text(
                                text = "اردو",
                                fontFamily = JameelNooriFontFamily,
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp,
                                color = if (currentLang == AppLanguage.URDU) Color(0xFF03190C) else TextMutedGreen,
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
                            )
                        }
                    }
                }

                // Islamic Emerald Glass Card
                Surface(
                    shape = RoundedCornerShape(28.dp),
                    color = Color(0xB0081C10),
                    border = BorderStroke(1.2.dp, Color(0x7034D399)),
                    shadowElevation = 16.dp,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(28.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // Emblem Icon
                        Surface(
                            shape = RoundedCornerShape(22.dp),
                            color = Color(0x3522C55E),
                            border = BorderStroke(1.5.dp, RadiantGreenPrimary),
                            modifier = Modifier.size(76.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.MenuBook,
                                    contentDescription = null,
                                    tint = RadiantGreenPrimary,
                                    modifier = Modifier.size(42.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(20.dp))

                        // App Title
                        Text(
                            text = if (currentLang == AppLanguage.URDU) "المكتبة الحكمه" else "المكتبة الحكمه",
                            fontFamily = if (currentLang == AppLanguage.URDU) JameelNooriFontFamily else AmiriFontFamily,
                            fontWeight = FontWeight.Bold,
                            fontSize = 28.sp,
                            color = TextWhitePrimary,
                            textAlign = TextAlign.Center
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = if (currentLang == AppLanguage.URDU)
                                "کتب فقہ حنفی و کتب اسلامیہ کا آن لائن ذخیرہ"
                            else
                                "المكتبة الرقمية للكتب الإسلامية والفقه الحنفي",
                            fontFamily = if (currentLang == AppLanguage.URDU) JameelNooriFontFamily else AmiriFontFamily,
                            fontSize = if (currentLang == AppLanguage.URDU) 15.sp else 13.sp,
                            color = LuminousGreenText,
                            textAlign = TextAlign.Center
                        )

                        Spacer(modifier = Modifier.height(24.dp))

                        // Features Badges
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            FeatureBadge(
                                icon = Icons.Default.CloudDone,
                                label = if (currentLang == AppLanguage.URDU) "کلاؤڈ سنک" else "مزامنة سحابية"
                            )
                            FeatureBadge(
                                icon = Icons.Default.Security,
                                label = if (currentLang == AppLanguage.URDU) "محفوظ اکاؤنٹ" else "حساب آمن"
                            )
                        }

                        Spacer(modifier = Modifier.height(28.dp))

                        // Sign in with Google Button
                        Button(
                            onClick = {
                                if (!isLoading) {
                                    isLoading = true
                                    errorMessage = null
                                    scope.launch {
                                        val result = authManager.signInWithGoogle(context)
                                        isLoading = false
                                        if (result.isFailure) {
                                            val ex = result.exceptionOrNull()
                                            errorMessage = ex?.localizedMessage ?: "Sign-in was cancelled or failed."
                                        }
                                    }
                                }
                            },
                            shape = RoundedCornerShape(16.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = RadiantGreenPrimary,
                                contentColor = Color(0xFF03190C)
                            ),
                            border = BorderStroke(1.dp, Color(0xFF86EFAC)),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(52.dp)
                                .testTag("sign_in_google_button")
                        ) {
                            if (isLoading) {
                                CircularProgressIndicator(
                                    color = Color(0xFF03190C),
                                    strokeWidth = 2.5.dp,
                                    modifier = Modifier.size(24.dp)
                                )
                            } else {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = Icons.Default.AccountCircle,
                                        contentDescription = null,
                                        tint = Color(0xFF03190C),
                                        modifier = Modifier.size(24.dp)
                                    )
                                    Spacer(modifier = Modifier.width(10.dp))
                                    Text(
                                        text = if (currentLang == AppLanguage.URDU)
                                            "گوگل کے ساتھ لاگ ان کریں"
                                        else
                                            "تسجيل الدخول باستخدام Google",
                                        fontFamily = if (currentLang == AppLanguage.URDU) JameelNooriFontFamily else AmiriFontFamily,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = if (currentLang == AppLanguage.URDU) 17.sp else 15.sp,
                                        color = Color(0xFF03190C)
                                    )
                                }
                            }
                        }

                        if (errorMessage != null) {
                            Spacer(modifier = Modifier.height(14.dp))
                            Text(
                                text = errorMessage ?: "",
                                color = Color(0xFFF87171),
                                fontSize = 12.sp,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun FeatureBadge(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String
) {
    Surface(
        shape = RoundedCornerShape(14.dp),
        color = Color(0x2222C55E),
        border = BorderStroke(1.dp, Color(0x354ADE80))
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = RadiantGreenPrimary,
                modifier = Modifier.size(14.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = label,
                color = TextMutedGreen,
                fontSize = 11.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}
