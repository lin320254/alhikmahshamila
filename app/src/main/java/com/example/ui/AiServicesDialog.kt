package com.example.ui

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.automirrored.filled.OpenInNew
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Security
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.data.BookEntity
import com.example.ui.theme.AmiriFontFamily
import com.example.ui.theme.JameelNooriFontFamily
import com.example.ui.theme.LuminousGreenText
import com.example.ui.theme.RadiantGreenPrimary
import com.example.ui.theme.TextMutedGreen
import com.example.ui.theme.TextWhitePrimary

data class AiServiceItem(
    val id: Long,
    val titleAr: String,
    val titleUr: String,
    val descAr: String,
    val descUr: String,
    val url: String,
    val badge: String,
    val domain: String,
    val icon: ImageVector
)

val AI_PORTAL_SERVICES = listOf(
    AiServiceItem(
        id = -101L,
        titleAr = "المكتبة الشاملة - الذكاء الاصطناعي",
        titleUr = "المکتبۃ الشاملہ اے آئی ریسرچ",
        descAr = "البحث الدلالي والذكي في مئات آلاف الكتب والمصادر الإسلامية المعتمدة",
        descUr = "ہزاروں معتمد اسلامی کتب اور مآخذ میں جدید ترین سمارٹ ریسرچ پورٹل",
        url = "https://shamela.ws/ai",
        badge = "Shamela AI",
        domain = "shamela.ws/ai",
        icon = Icons.AutoMirrored.Filled.MenuBook
    ),
    AiServiceItem(
        id = -102L,
        titleAr = "دار الإفتاء ديوبند (الفتاوى أونلاين)",
        titleUr = "دار الافتاء دیوبند (آن لائن فتویٰ)",
        descAr = "البحث في آلاف الفتاوى الحنفية الصادرة عن دار الإفتاء ديوبند وطرح الأسئلة الشرعية",
        descUr = "دار العلوم دیوبند کے ہزاروں مستند فتاویٰ میں تلاش اور آن لائن مسئلہ پوچھنے کی سہولت",
        url = "https://m.darulifta.info/#/",
        badge = "Darul Ifta",
        domain = "m.darulifta.info",
        icon = Icons.Default.Security
    ),
    AiServiceItem(
        id = -103L,
        titleAr = "الإخلاص أونلاين - الذكاء الاصطناعي",
        titleUr = "الاخلاص آن لائن اے آئی پورٹل",
        descAr = "محرك بحث إسلامي ذكي للإجابة عن الأسئلة الشرعية مع توثيق الأدلة والمصادر",
        descUr = "اسلامی سوالات کے مصدقہ حوالہ جاتی جوابات فراہم کرنے والا جدید اے آئی پورٹل",
        url = "https://alikhlasonline.ai/",
        badge = "Al-Ikhlas AI",
        domain = "alikhlasonline.ai",
        icon = Icons.Default.AutoAwesome
    )
)

@Composable
fun AiServicesDialog(
    lang: AppLanguage,
    onDismiss: () -> Unit,
    onOpenServiceInReader: (BookEntity) -> Unit
) {
    val context = LocalContext.current

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            shape = RoundedCornerShape(26.dp),
            color = Color(0xF5040906),
            border = BorderStroke(1.2.dp, Color(0x804ADE80)),
            modifier = Modifier
                .fillMaxWidth(0.94f)
                .padding(vertical = 16.dp)
                .clip(RoundedCornerShape(26.dp))
                .testTag("ai_services_dialog")
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                // Header with Glowing AI Icon and Close Button
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Surface(
                            shape = CircleShape,
                            color = Color(0x3522C55E),
                            border = BorderStroke(1.2.dp, RadiantGreenPrimary),
                            modifier = Modifier.size(42.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = Icons.Default.AutoAwesome,
                                    contentDescription = "AI",
                                    tint = RadiantGreenPrimary,
                                    modifier = Modifier.size(22.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.width(12.dp))

                        Column {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = Strings.aiDialogTitle(lang),
                                    fontFamily = if (lang == AppLanguage.URDU) JameelNooriFontFamily else AmiriFontFamily,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = if (lang == AppLanguage.URDU) 20.sp else 18.sp,
                                    color = TextWhitePrimary
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Surface(
                                    shape = RoundedCornerShape(10.dp),
                                    color = Color(0x3522C55E),
                                    border = BorderStroke(1.dp, RadiantGreenPrimary)
                                ) {
                                    Text(
                                        text = "AI",
                                        fontWeight = FontWeight.ExtraBold,
                                        fontSize = 11.sp,
                                        color = RadiantGreenPrimary,
                                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                    )
                                }
                            }
                            Text(
                                text = Strings.aiDialogSubtitle(lang),
                                fontFamily = if (lang == AppLanguage.URDU) JameelNooriFontFamily else AmiriFontFamily,
                                fontSize = if (lang == AppLanguage.URDU) 13.sp else 11.sp,
                                color = TextMutedGreen
                            )
                        }
                    }

                    IconButton(
                        onClick = onDismiss,
                        modifier = Modifier.testTag("close_ai_dialog_btn")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close",
                            tint = TextMutedGreen
                        )
                    }
                }

                Spacer(modifier = Modifier.height(18.dp))

                // The 3 AI Services Cards
                AI_PORTAL_SERVICES.forEach { service ->
                    val title = if (lang == AppLanguage.URDU) service.titleUr else service.titleAr
                    val desc = if (lang == AppLanguage.URDU) service.descUr else service.descAr

                    Surface(
                        shape = RoundedCornerShape(20.dp),
                        color = Color(0x30173623),
                        border = BorderStroke(1.dp, Color(0x604ADE80)),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        ) {
                            // Top Row: Icon + Badge + Domain
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Surface(
                                        shape = RoundedCornerShape(12.dp),
                                        color = Color(0x3522C55E),
                                        border = BorderStroke(1.dp, Color(0x8086EFAC)),
                                        modifier = Modifier.size(34.dp)
                                    ) {
                                        Box(contentAlignment = Alignment.Center) {
                                            Icon(
                                                imageVector = service.icon,
                                                contentDescription = null,
                                                tint = RadiantGreenPrimary,
                                                modifier = Modifier.size(18.dp)
                                            )
                                        }
                                    }
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = service.badge,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 12.sp,
                                        color = RadiantGreenPrimary
                                    )
                                }

                                Surface(
                                    shape = RoundedCornerShape(12.dp),
                                    color = Color(0x25000000),
                                    border = BorderStroke(1.dp, Color(0x4034D399))
                                ) {
                                    Text(
                                        text = service.domain,
                                        fontSize = 11.sp,
                                        color = TextMutedGreen,
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(10.dp))

                            // Title
                            Text(
                                text = title,
                                fontFamily = if (lang == AppLanguage.URDU) JameelNooriFontFamily else AmiriFontFamily,
                                fontWeight = FontWeight.Bold,
                                fontSize = if (lang == AppLanguage.URDU) 18.sp else 16.sp,
                                color = TextWhitePrimary
                            )

                            Spacer(modifier = Modifier.height(4.dp))

                            // Description
                            Text(
                                text = desc,
                                fontFamily = if (lang == AppLanguage.URDU) JameelNooriFontFamily else AmiriFontFamily,
                                fontSize = if (lang == AppLanguage.URDU) 14.sp else 12.sp,
                                color = TextMutedGreen,
                                lineHeight = if (lang == AppLanguage.URDU) 22.sp else 18.sp
                            )

                            Spacer(modifier = Modifier.height(14.dp))

                            // Action buttons
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                // In-App Reader Button (Primary)
                                Button(
                                    onClick = {
                                        onDismiss()
                                        onOpenServiceInReader(
                                            BookEntity(
                                                id = service.id,
                                                title = title,
                                                author = service.badge,
                                                category = "AI & الفتوى",
                                                url = service.url,
                                                notes = desc,
                                                isFavorite = true
                                            )
                                        )
                                    },
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = RadiantGreenPrimary,
                                        contentColor = Color(0xFF03190C)
                                    ),
                                    shape = RoundedCornerShape(14.dp),
                                    modifier = Modifier
                                        .weight(1f)
                                        .testTag("open_service_${service.badge.lowercase().replace(' ', '_')}")
                                ) {
                                    Icon(
                                        imageVector = Icons.AutoMirrored.Filled.MenuBook,
                                        contentDescription = null,
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = Strings.openInAppReader(lang),
                                        fontFamily = if (lang == AppLanguage.URDU) JameelNooriFontFamily else AmiriFontFamily,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = if (lang == AppLanguage.URDU) 15.sp else 13.sp
                                    )
                                }

                                // External Browser Button
                                Surface(
                                    shape = RoundedCornerShape(14.dp),
                                    color = Color(0x35142E20),
                                    border = BorderStroke(1.dp, Color(0x604ADE80)),
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(14.dp))
                                        .clickable {
                                            try {
                                                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(service.url))
                                                context.startActivity(intent)
                                            } catch (e: Exception) {
                                                Toast.makeText(context, "Cannot open browser", Toast.LENGTH_SHORT).show()
                                            }
                                        }
                                ) {
                                    Box(
                                        modifier = Modifier.padding(10.dp),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Icon(
                                            imageVector = Icons.AutoMirrored.Filled.OpenInNew,
                                            contentDescription = "External Browser",
                                            tint = RadiantGreenPrimary,
                                            modifier = Modifier.size(18.dp)
                                        )
                                    }
                                }

                                // Copy Link Button
                                Surface(
                                    shape = RoundedCornerShape(14.dp),
                                    color = Color(0x35142E20),
                                    border = BorderStroke(1.dp, Color(0x604ADE80)),
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(14.dp))
                                        .clickable {
                                            val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                                            val clip = ClipData.newPlainText("URL", service.url)
                                            clipboard.setPrimaryClip(clip)
                                            Toast.makeText(context, Strings.linkCopied(lang), Toast.LENGTH_SHORT).show()
                                        }
                                ) {
                                    Box(
                                        modifier = Modifier.padding(10.dp),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.ContentCopy,
                                            contentDescription = "Copy Link",
                                            tint = TextWhitePrimary,
                                            modifier = Modifier.size(18.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
