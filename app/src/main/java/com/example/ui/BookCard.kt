package com.example.ui

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.automirrored.filled.OpenInNew
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Link
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.BookEntity
import com.example.ui.theme.AmiriFontFamily
import com.example.ui.theme.JameelNooriFontFamily
import com.example.ui.theme.LuminousGreenText
import com.example.ui.theme.RadiantGreenPrimary
import com.example.ui.theme.TextMutedGreen
import com.example.ui.theme.TextWhitePrimary

@Composable
fun BookCard(
    book: BookEntity,
    lang: AppLanguage,
    onRead: () -> Unit,
    onToggleFavorite: () -> Unit,
    onEdit: () -> Unit,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var menuExpanded by remember { mutableStateOf(false) }

    val categoryDisplayName = Strings.localizedCategoryName(book.category, lang)

    // iOS Transparent Glass Container
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(22.dp))
            .background(
                Brush.verticalGradient(
                    listOf(
                        Color(0x35173322),
                        Color(0x220D1D14),
                        Color(0x2E10261A)
                    )
                )
            )
            .clickable(onClick = onRead)
            .testTag("book_card_${book.id}")
    ) {
        // Specular glass highlight line along the very top
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(
                    Brush.horizontalGradient(
                        listOf(
                            Color.Transparent,
                            Color(0x604ADE80),
                            Color(0x80FFFFFF),
                            Color(0x604ADE80),
                            Color.Transparent
                        )
                    )
                )
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Header Row: Category Badge Pill + Bookmark + More Menu
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Glass Category Tag Pill
                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = Color(0x3322C55E),
                    border = BorderStroke(1.dp, Color(0x664ADE80)),
                    modifier = Modifier.testTag("book_category_badge_${book.id}")
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 5.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.MenuBook,
                            contentDescription = null,
                            tint = RadiantGreenPrimary,
                            modifier = Modifier.size(13.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = categoryDisplayName,
                            fontFamily = if (lang == AppLanguage.URDU) JameelNooriFontFamily else AmiriFontFamily,
                            fontWeight = FontWeight.Bold,
                            fontSize = if (lang == AppLanguage.URDU) 14.sp else 12.sp,
                            color = LuminousGreenText
                        )
                    }
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    // Bookmark toggle
                    IconButton(
                        onClick = onToggleFavorite,
                        modifier = Modifier
                            .size(36.dp)
                            .testTag("book_favorite_btn_${book.id}")
                    ) {
                        Icon(
                            imageVector = if (book.isFavorite) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                            contentDescription = "Bookmark",
                            tint = if (book.isFavorite) RadiantGreenPrimary else TextMutedGreen.copy(alpha = 0.7f)
                        )
                    }

                    // More Menu
                    Box {
                        IconButton(
                            onClick = { menuExpanded = true },
                            modifier = Modifier
                                .size(36.dp)
                                .testTag("book_menu_btn_${book.id}")
                        ) {
                            Icon(
                                imageVector = Icons.Default.MoreVert,
                                contentDescription = "More Options",
                                tint = TextMutedGreen
                            )
                        }

                        DropdownMenu(
                            expanded = menuExpanded,
                            onDismissRequest = { menuExpanded = false },
                            containerColor = Color(0xF008140E)
                        ) {
                            DropdownMenuItem(
                                text = {
                                    Text(
                                        text = Strings.readNow(lang),
                                        fontFamily = if (lang == AppLanguage.URDU) JameelNooriFontFamily else AmiriFontFamily,
                                        fontSize = 16.sp,
                                        color = TextWhitePrimary
                                    )
                                },
                                onClick = {
                                    menuExpanded = false
                                    onRead()
                                },
                                leadingIcon = {
                                    Icon(Icons.AutoMirrored.Filled.MenuBook, contentDescription = null, tint = RadiantGreenPrimary)
                                }
                            )

                            DropdownMenuItem(
                                text = {
                                    Text(
                                        text = Strings.copyLink(lang),
                                        fontFamily = if (lang == AppLanguage.URDU) JameelNooriFontFamily else AmiriFontFamily,
                                        fontSize = 16.sp,
                                        color = TextWhitePrimary
                                    )
                                },
                                onClick = {
                                    menuExpanded = false
                                    val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                                    clipboard.setPrimaryClip(ClipData.newPlainText("URL", book.url))
                                    Toast.makeText(context, Strings.linkCopied(lang), Toast.LENGTH_SHORT).show()
                                },
                                leadingIcon = {
                                    Icon(Icons.Default.ContentCopy, contentDescription = null, tint = RadiantGreenPrimary)
                                }
                            )

                            DropdownMenuItem(
                                text = {
                                    Text(
                                        text = Strings.openBrowser(lang),
                                        fontFamily = if (lang == AppLanguage.URDU) JameelNooriFontFamily else AmiriFontFamily,
                                        fontSize = 16.sp,
                                        color = TextWhitePrimary
                                    )
                                },
                                onClick = {
                                    menuExpanded = false
                                    try {
                                        context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(book.url)))
                                    } catch (e: Exception) {
                                        // Ignore
                                    }
                                },
                                leadingIcon = {
                                    Icon(Icons.AutoMirrored.Filled.OpenInNew, contentDescription = null, tint = RadiantGreenPrimary)
                                }
                            )

                            DropdownMenuItem(
                                text = {
                                    Text(
                                        text = Strings.shareAction(lang),
                                        fontFamily = if (lang == AppLanguage.URDU) JameelNooriFontFamily else AmiriFontFamily,
                                        fontSize = 16.sp,
                                        color = TextWhitePrimary
                                    )
                                },
                                onClick = {
                                    menuExpanded = false
                                    val intent = Intent().apply {
                                        action = Intent.ACTION_SEND
                                        putExtra(Intent.EXTRA_TEXT, "${book.title}\n${book.url}")
                                        type = "text/plain"
                                    }
                                    context.startActivity(Intent.createChooser(intent, book.title))
                                },
                                leadingIcon = {
                                    Icon(Icons.Default.Share, contentDescription = null, tint = RadiantGreenPrimary)
                                }
                            )

                            DropdownMenuItem(
                                text = {
                                    Text(
                                        text = Strings.editAction(lang),
                                        fontFamily = if (lang == AppLanguage.URDU) JameelNooriFontFamily else AmiriFontFamily,
                                        fontSize = 16.sp,
                                        color = TextWhitePrimary
                                    )
                                },
                                onClick = {
                                    menuExpanded = false
                                    onEdit()
                                },
                                leadingIcon = {
                                    Icon(Icons.Default.Edit, contentDescription = null, tint = TextMutedGreen)
                                }
                            )

                            DropdownMenuItem(
                                text = {
                                    Text(
                                        text = Strings.deleteAction(lang),
                                        fontFamily = if (lang == AppLanguage.URDU) JameelNooriFontFamily else AmiriFontFamily,
                                        fontSize = 16.sp,
                                        color = MaterialTheme.colorScheme.error
                                    )
                                },
                                onClick = {
                                    menuExpanded = false
                                    onDelete()
                                },
                                leadingIcon = {
                                    Icon(
                                        Icons.Default.Delete,
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.error
                                    )
                                }
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Book Title in Classical Amiri Font (Arabic) or Jameel Noori Nastaleeq (Urdu)
            Text(
                text = book.title,
                fontFamily = if (lang == AppLanguage.URDU) JameelNooriFontFamily else AmiriFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = if (lang == AppLanguage.URDU) 20.sp else 21.sp,
                lineHeight = if (lang == AppLanguage.URDU) 34.sp else 32.sp,
                color = LuminousGreenText,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            // Author
            if (book.author.isNotBlank()) {
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = null,
                        modifier = Modifier.size(15.dp),
                        tint = RadiantGreenPrimary
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = book.author,
                        fontFamily = if (lang == AppLanguage.URDU) JameelNooriFontFamily else AmiriFontFamily,
                        fontWeight = FontWeight.Medium,
                        fontSize = if (lang == AppLanguage.URDU) 16.sp else 15.sp,
                        color = TextWhitePrimary.copy(alpha = 0.9f),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }

            // Notes / Explanation
            if (book.notes.isNotBlank()) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = book.notes,
                    fontFamily = if (lang == AppLanguage.URDU) JameelNooriFontFamily else AmiriFontFamily,
                    fontSize = if (lang == AppLanguage.URDU) 16.sp else 13.sp,
                    lineHeight = if (lang == AppLanguage.URDU) 28.sp else 22.sp,
                    color = TextMutedGreen,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Bottom Row: URL snippet & iOS Liquid Glass Read Button
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(1f).padding(end = 8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Link,
                        contentDescription = null,
                        tint = RadiantGreenPrimary,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    val displayUrl = book.url
                        .removePrefix("https://")
                        .removePrefix("http://")
                    Text(
                        text = displayUrl,
                        style = MaterialTheme.typography.labelSmall,
                        color = TextMutedGreen.copy(alpha = 0.8f),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                // iPhone Liquid Glass Button
                Button(
                    onClick = onRead,
                    shape = RoundedCornerShape(24.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent
                    ),
                    border = BorderStroke(1.dp, Color(0x8086EFAC)),
                    modifier = Modifier
                        .background(
                            Brush.horizontalGradient(
                                listOf(
                                    Color(0xEE22C55E),
                                    Color(0xDD16A34A)
                                )
                            ),
                            shape = RoundedCornerShape(24.dp)
                        )
                        .testTag("read_book_btn_${book.id}")
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.MenuBook,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp),
                        tint = Color(0xFF03190C)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = Strings.readNow(lang),
                        fontFamily = if (lang == AppLanguage.URDU) JameelNooriFontFamily else AmiriFontFamily,
                        fontSize = if (lang == AppLanguage.URDU) 16.sp else 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF03190C)
                    )
                }
            }
        }
    }
}
