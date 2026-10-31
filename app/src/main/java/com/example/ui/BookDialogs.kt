package com.example.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Link
import androidx.compose.material.icons.filled.Laptop
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Notes
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.BookEntity
import com.example.data.CategoryEntity
import com.example.ui.theme.AmiriFontFamily
import com.example.ui.theme.JameelNooriFontFamily
import com.example.ui.theme.LuminousGreenText
import com.example.ui.theme.RadiantGreenPrimary
import com.example.ui.theme.SurfaceBorderGreen
import com.example.ui.theme.TextMutedGreen
import com.example.ui.theme.TextWhitePrimary

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun AddEditBookDialog(
    bookToEdit: BookEntity?,
    categories: List<CategoryEntity>,
    initialCategory: String?,
    lang: AppLanguage,
    onDismiss: () -> Unit,
    onSave: (title: String, author: String, category: String, url: String, notes: String) -> Unit,
    onAddNewCategoryRequest: () -> Unit
) {
    val defaultCat = initialCategory?.takeIf { it != SPECIAL_CATEGORY_FAVORITES }
        ?: categories.firstOrNull()?.name ?: "فقہ حنفی"

    var title by remember { mutableStateOf(bookToEdit?.title ?: "") }
    var author by remember { mutableStateOf(bookToEdit?.author ?: "") }
    var selectedCategory by remember { mutableStateOf(bookToEdit?.category ?: defaultCat) }
    var url by remember { mutableStateOf(bookToEdit?.url ?: "") }
    var notes by remember { mutableStateOf(bookToEdit?.notes ?: "") }

    var titleError by remember { mutableStateOf(false) }
    var urlError by remember { mutableStateOf(false) }

    val scrollState = rememberScrollState()

    val textFieldColors = OutlinedTextFieldDefaults.colors(
        focusedContainerColor = Color(0x3514261C),
        unfocusedContainerColor = Color(0x280E1B14),
        focusedBorderColor = RadiantGreenPrimary,
        unfocusedBorderColor = Color(0x4034D399),
        focusedTextColor = TextWhitePrimary,
        unfocusedTextColor = TextWhitePrimary,
        cursorColor = RadiantGreenPrimary,
        focusedLabelColor = RadiantGreenPrimary,
        unfocusedLabelColor = TextMutedGreen
    )

    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        AlertDialog(
            onDismissRequest = onDismiss,
            containerColor = Color(0xF209140F),
            shape = RoundedCornerShape(26.dp),
            modifier = Modifier.background(Color.Transparent),
            title = {
                Text(
                    text = if (bookToEdit == null) Strings.addBook(lang) else Strings.editAction(lang),
                    fontFamily = if (lang == AppLanguage.URDU) JameelNooriFontFamily else AmiriFontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = if (lang == AppLanguage.URDU) 22.sp else 19.sp,
                    color = TextWhitePrimary
                )
            },
            text = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .verticalScroll(scrollState)
                ) {
                    // Book Title
                    OutlinedTextField(
                        value = title,
                        onValueChange = {
                            title = it
                            if (it.isNotBlank()) titleError = false
                        },
                        label = {
                            Text(
                                Strings.bookTitleLabel(lang),
                                fontFamily = if (lang == AppLanguage.URDU) JameelNooriFontFamily else AmiriFontFamily
                            )
                        },
                        placeholder = {
                            Text(
                                "مثال: الهداية في شرح بداية المبتدي",
                                fontFamily = AmiriFontFamily,
                                color = TextMutedGreen.copy(alpha = 0.5f)
                            )
                        },
                        leadingIcon = {
                            Icon(Icons.Default.MenuBook, contentDescription = null, tint = RadiantGreenPrimary)
                        },
                        colors = textFieldColors,
                        isError = titleError,
                        supportingText = {
                            if (titleError) {
                                Text(
                                    Strings.enterTitleError(lang),
                                    fontFamily = if (lang == AppLanguage.URDU) JameelNooriFontFamily else AmiriFontFamily,
                                    color = MaterialTheme.colorScheme.error
                                )
                            }
                        },
                        singleLine = false,
                        maxLines = 2,
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("book_title_input")
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    // Author
                    OutlinedTextField(
                        value = author,
                        onValueChange = { author = it },
                        label = {
                            Text(
                                Strings.bookAuthorLabel(lang),
                                fontFamily = if (lang == AppLanguage.URDU) JameelNooriFontFamily else AmiriFontFamily
                            )
                        },
                        placeholder = {
                            Text(
                                "مثال: المرغيناني / ابن عابدين",
                                fontFamily = AmiriFontFamily,
                                color = TextMutedGreen.copy(alpha = 0.5f)
                            )
                        },
                        leadingIcon = {
                            Icon(Icons.Default.Person, contentDescription = null, tint = RadiantGreenPrimary)
                        },
                        colors = textFieldColors,
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("book_author_input")
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // Category Selection Label
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = Strings.categoryLabel(lang),
                            fontFamily = if (lang == AppLanguage.URDU) JameelNooriFontFamily else AmiriFontFamily,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 15.sp,
                            color = LuminousGreenText
                        )

                        TextButton(
                            onClick = onAddNewCategoryRequest,
                            modifier = Modifier.testTag("add_category_shortcut_btn")
                        ) {
                            Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(16.dp), tint = RadiantGreenPrimary)
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = Strings.addCategory(lang),
                                fontFamily = if (lang == AppLanguage.URDU) JameelNooriFontFamily else AmiriFontFamily,
                                fontSize = 14.sp,
                                color = RadiantGreenPrimary
                            )
                        }
                    }

                    // Category Chips
                    FlowRow(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        categories.forEach { cat ->
                            val isSelected = selectedCategory == cat.name
                            val categoryName = Strings.localizedCategoryName(cat.name, lang)
                            FilterChip(
                                selected = isSelected,
                                onClick = { selectedCategory = cat.name },
                                label = {
                                    Text(
                                        text = categoryName,
                                        fontFamily = if (lang == AppLanguage.URDU) JameelNooriFontFamily else AmiriFontFamily,
                                        fontSize = if (lang == AppLanguage.URDU) 14.sp else 12.sp,
                                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                                    )
                                },
                                border = BorderStroke(1.dp, if (isSelected) RadiantGreenPrimary else Color(0x3534D399)),
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = Color(0x4022C55E),
                                    selectedLabelColor = LuminousGreenText,
                                    containerColor = Color(0x221E382A),
                                    labelColor = TextMutedGreen
                                ),
                                shape = RoundedCornerShape(16.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Book URL
                    OutlinedTextField(
                        value = url,
                        onValueChange = {
                            url = it
                            if (it.isNotBlank()) urlError = false
                        },
                        label = {
                            Text(
                                Strings.bookUrlLabel(lang),
                                fontFamily = if (lang == AppLanguage.URDU) JameelNooriFontFamily else AmiriFontFamily
                            )
                        },
                        placeholder = { Text("https://shamela.ws/book/11820", color = TextMutedGreen.copy(alpha = 0.5f)) },
                        leadingIcon = {
                            Icon(Icons.Default.Link, contentDescription = null, tint = RadiantGreenPrimary)
                        },
                        colors = textFieldColors,
                        isError = urlError,
                        supportingText = {
                            if (urlError) {
                                Text(
                                    Strings.enterUrlError(lang),
                                    fontFamily = if (lang == AppLanguage.URDU) JameelNooriFontFamily else AmiriFontFamily,
                                    color = MaterialTheme.colorScheme.error
                                )
                            }
                        },
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("book_url_input")
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    // Notes / Description
                    OutlinedTextField(
                        value = notes,
                        onValueChange = { notes = it },
                        label = {
                            Text(
                                Strings.bookNotesLabel(lang),
                                fontFamily = if (lang == AppLanguage.URDU) JameelNooriFontFamily else AmiriFontFamily
                            )
                        },
                        placeholder = {
                            Text(
                                if (lang == AppLanguage.URDU) "کتاب کے بارے میں نوٹس یا تبصرہ..." else "ملاحظات عن الكتاب...",
                                fontFamily = if (lang == AppLanguage.URDU) JameelNooriFontFamily else AmiriFontFamily,
                                color = TextMutedGreen.copy(alpha = 0.5f)
                            )
                        },
                        leadingIcon = {
                            Icon(Icons.Default.Notes, contentDescription = null, tint = RadiantGreenPrimary)
                        },
                        colors = textFieldColors,
                        singleLine = false,
                        maxLines = 3,
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("book_notes_input")
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        val trimmedTitle = title.trim()
                        val trimmedUrl = url.trim()
                        var valid = true

                        if (trimmedTitle.isEmpty()) {
                            titleError = true
                            valid = false
                        }
                        if (trimmedUrl.isEmpty()) {
                            urlError = true
                            valid = false
                        }

                        if (valid) {
                            onSave(trimmedTitle, author.trim(), selectedCategory, trimmedUrl, notes.trim())
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = RadiantGreenPrimary,
                        contentColor = Color(0xFF03190C)
                    ),
                    shape = RoundedCornerShape(20.dp),
                    border = BorderStroke(1.dp, Color(0x8086EFAC)),
                    modifier = Modifier.testTag("save_book_btn")
                ) {
                    Text(
                        text = Strings.saveAction(lang),
                        fontFamily = if (lang == AppLanguage.URDU) JameelNooriFontFamily else AmiriFontFamily,
                        fontSize = if (lang == AppLanguage.URDU) 16.sp else 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            },
            dismissButton = {
                OutlinedButton(
                    onClick = onDismiss,
                    border = BorderStroke(1.dp, Color(0x4034D399)),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = TextMutedGreen
                    ),
                    shape = RoundedCornerShape(20.dp),
                    modifier = Modifier.testTag("cancel_book_btn")
                ) {
                    Text(
                        text = Strings.cancelAction(lang),
                        fontFamily = if (lang == AppLanguage.URDU) JameelNooriFontFamily else AmiriFontFamily,
                        fontSize = if (lang == AppLanguage.URDU) 15.sp else 13.sp
                    )
                }
            }
        )
    }
}

@Composable
fun AddCategoryDialog(
    lang: AppLanguage,
    onDismiss: () -> Unit,
    onSave: (name: String, nameMm: String) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var nameMm by remember { mutableStateOf("") }
    var nameError by remember { mutableStateOf(false) }

    val textFieldColors = OutlinedTextFieldDefaults.colors(
        focusedContainerColor = Color(0x3514261C),
        unfocusedContainerColor = Color(0x280E1B14),
        focusedBorderColor = RadiantGreenPrimary,
        unfocusedBorderColor = Color(0x4034D399),
        focusedTextColor = TextWhitePrimary,
        unfocusedTextColor = TextWhitePrimary,
        cursorColor = RadiantGreenPrimary,
        focusedLabelColor = RadiantGreenPrimary,
        unfocusedLabelColor = TextMutedGreen
    )

    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        AlertDialog(
            onDismissRequest = onDismiss,
            containerColor = Color(0xF209140F),
            shape = RoundedCornerShape(26.dp),
            title = {
                Text(
                    text = Strings.addCategory(lang),
                    fontFamily = if (lang == AppLanguage.URDU) JameelNooriFontFamily else AmiriFontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = if (lang == AppLanguage.URDU) 22.sp else 19.sp,
                    color = TextWhitePrimary
                )
            },
            text = {
                Column(modifier = Modifier.fillMaxWidth()) {
                    OutlinedTextField(
                        value = name,
                        onValueChange = {
                            name = it
                            if (it.isNotBlank()) nameError = false
                        },
                        label = {
                            Text(
                                Strings.categoryNamePrompt(lang),
                                fontFamily = if (lang == AppLanguage.URDU) JameelNooriFontFamily else AmiriFontFamily
                            )
                        },
                        placeholder = {
                            Text(
                                if (lang == AppLanguage.URDU) "مثال: فقہ حنفی، اصول فقہ" else "مثال: فقه حنفي، أصول الفقه",
                                fontFamily = AmiriFontFamily,
                                color = TextMutedGreen.copy(alpha = 0.5f)
                            )
                        },
                        leadingIcon = {
                            Icon(Icons.Default.Category, contentDescription = null, tint = RadiantGreenPrimary)
                        },
                        colors = textFieldColors,
                        isError = nameError,
                        supportingText = {
                            if (nameError) {
                                Text(
                                    Strings.enterTitleError(lang),
                                    fontFamily = if (lang == AppLanguage.URDU) JameelNooriFontFamily else AmiriFontFamily,
                                    color = MaterialTheme.colorScheme.error
                                )
                            }
                        },
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("category_name_input")
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = nameMm,
                        onValueChange = { nameMm = it },
                        label = {
                            Text(
                                Strings.categoryNameSubtitle(lang),
                                fontFamily = if (lang == AppLanguage.URDU) JameelNooriFontFamily else AmiriFontFamily
                            )
                        },
                        placeholder = {
                            Text(
                                if (lang == AppLanguage.URDU) "وضاحت..." else "الوصف...",
                                fontFamily = if (lang == AppLanguage.URDU) JameelNooriFontFamily else AmiriFontFamily,
                                color = TextMutedGreen.copy(alpha = 0.5f)
                            )
                        },
                        colors = textFieldColors,
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("category_namemm_input")
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (name.trim().isEmpty()) {
                            nameError = true
                        } else {
                            onSave(name.trim(), nameMm.trim())
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = RadiantGreenPrimary,
                        contentColor = Color(0xFF03190C)
                    ),
                    shape = RoundedCornerShape(20.dp),
                    border = BorderStroke(1.dp, Color(0x8086EFAC)),
                    modifier = Modifier.testTag("save_category_btn")
                ) {
                    Text(
                        text = Strings.saveAction(lang),
                        fontFamily = if (lang == AppLanguage.URDU) JameelNooriFontFamily else AmiriFontFamily,
                        fontSize = if (lang == AppLanguage.URDU) 16.sp else 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            },
            dismissButton = {
                OutlinedButton(
                    onClick = onDismiss,
                    border = BorderStroke(1.dp, Color(0x4034D399)),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = TextMutedGreen
                    ),
                    shape = RoundedCornerShape(20.dp),
                    modifier = Modifier.testTag("cancel_category_btn")
                ) {
                    Text(
                        text = Strings.cancelAction(lang),
                        fontFamily = if (lang == AppLanguage.URDU) JameelNooriFontFamily else AmiriFontFamily,
                        fontSize = if (lang == AppLanguage.URDU) 15.sp else 13.sp
                    )
                }
            }
        )
    }
}

@Composable
fun DeleteConfirmDialog(
    book: BookEntity,
    lang: AppLanguage,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        AlertDialog(
            onDismissRequest = onDismiss,
            containerColor = Color(0xF209140F),
            shape = RoundedCornerShape(26.dp),
            icon = {
                Icon(
                    Icons.Default.Warning,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.error
                )
            },
            title = {
                Text(
                    text = Strings.confirmDeleteTitle(lang),
                    fontFamily = if (lang == AppLanguage.URDU) JameelNooriFontFamily else AmiriFontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = if (lang == AppLanguage.URDU) 20.sp else 18.sp,
                    color = TextWhitePrimary
                )
            },
            text = {
                Column {
                    Text(
                        text = Strings.confirmDeleteMsg(lang),
                        fontFamily = if (lang == AppLanguage.URDU) JameelNooriFontFamily else AmiriFontFamily,
                        fontSize = 15.sp,
                        color = TextMutedGreen
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "\"${book.title}\"",
                        fontFamily = AmiriFontFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 17.sp,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = onConfirm,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.error
                    ),
                    shape = RoundedCornerShape(20.dp),
                    modifier = Modifier.testTag("confirm_delete_btn")
                ) {
                    Text(
                        text = Strings.deleteAction(lang),
                        fontFamily = if (lang == AppLanguage.URDU) JameelNooriFontFamily else AmiriFontFamily,
                        fontWeight = FontWeight.Bold
                    )
                }
            },
            dismissButton = {
                OutlinedButton(
                    onClick = onDismiss,
                    border = BorderStroke(1.dp, Color(0x4034D399)),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = TextMutedGreen
                    ),
                    shape = RoundedCornerShape(20.dp),
                    modifier = Modifier.testTag("cancel_delete_btn")
                ) {
                    Text(
                        text = Strings.cancelAction(lang),
                        fontFamily = if (lang == AppLanguage.URDU) JameelNooriFontFamily else AmiriFontFamily
                    )
                }
            }
        )
    }
}

/**
 * Laptop & Web reader guide dialog explaining browser-friendly features,
 * cloud sync, and large display reading experience.
 */
@Composable
fun LaptopWebGuideDialog(
    lang: AppLanguage,
    onDismiss: () -> Unit
) {
    val layoutDirection = if (lang == AppLanguage.URDU || lang == AppLanguage.ARABIC) {
        LayoutDirection.Rtl
    } else {
        LayoutDirection.Ltr
    }

    CompositionLocalProvider(LocalLayoutDirection provides layoutDirection) {
        AlertDialog(
            onDismissRequest = onDismiss,
            modifier = Modifier
                .fillMaxWidth(0.95f)
                .testTag("laptop_guide_dialog"),
            containerColor = Color(0xF209140F),
            titleContentColor = RadiantGreenPrimary,
            shape = RoundedCornerShape(24.dp),
            title = {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Laptop,
                        contentDescription = null,
                        tint = RadiantGreenPrimary,
                        modifier = Modifier.size(24.dp)
                    )
                    Text(
                        text = if (lang == AppLanguage.URDU) "لیپ ٹاپ اور ویب پر استعمال" else "الاستخدام على الحاسوب والويب",
                        fontFamily = if (lang == AppLanguage.URDU) JameelNooriFontFamily else AmiriFontFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = TextWhitePrimary
                    )
                }
            },
            text = {
                Column(
                    modifier = Modifier
                        .verticalScroll(rememberScrollState())
                        .padding(vertical = 4.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    GuideStepItem(
                        number = "١",
                        title = if (lang == AppLanguage.URDU) "کلاؤڈ سنک اور بیک اپ" else "المزامنة السحابية والحفظ",
                        description = if (lang == AppLanguage.URDU)
                            "گوگل اکاؤنٹ سے لاگ ان ہونے کے بعد آپ کی شامل کی گئی تمام کتابیں، پسندیدہ کتب اور نوٹس محفوظ رہتے ہیں اور خودکار طور پر سنک ہوتے ہیں۔"
                        else
                            "بعد تسجيل الدخول بحساب جوجل، يتم حفظ ومزامنة جميع الكتب المفضلة والمضافة والملاحظات سحابياً تلقائياً.",
                        lang = lang
                    )
                    GuideStepItem(
                        number = "٢",
                        title = if (lang == AppLanguage.URDU) "براؤزر میں فل سکرین ریڈنگ" else "القراءة بملء الشاشة في المتصفح",
                        description = if (lang == AppLanguage.URDU)
                            "کتاب پڑھتے وقت اوپر دیے گئے فل سکرین اور زوم بٹنوں کی مدد سے بڑی سکرین پر مطالعہ آسان ہوجاتا ہے۔"
                        else
                            "عند قراءة أي كتاب، يمكنك تكبير الصفحة واستخدام وضع ملء الشاشة لقراءة مريحة على شاشات الحواسيب الكبيرة.",
                        lang = lang
                    )
                    GuideStepItem(
                        number = "٣",
                        title = if (lang == AppLanguage.URDU) "براہ راست ویب لنکس" else "روابط مباشرة للمكتبة الشاملة",
                        description = if (lang == AppLanguage.URDU)
                            "آپ شاملا (shamela.ws) کا کوئی بھی براہ راست صفحہ یا کتاب ایپ میں شامل کر کے پڑھ سکتے ہیں۔"
                        else
                            "يمكنك إضافة وتصفح أي رابط مباشر لكتاب من المكتبة الشاملة الرسمية بكل سهولة.",
                        lang = lang
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = onDismiss,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = RadiantGreenPrimary,
                        contentColor = Color(0xFF03160B)
                    ),
                    shape = RoundedCornerShape(20.dp),
                    modifier = Modifier.testTag("dismiss_laptop_guide_btn")
                ) {
                    Text(
                        text = if (lang == AppLanguage.URDU) "سمجھ آگیا" else "حسناً، فهمت",
                        fontFamily = if (lang == AppLanguage.URDU) JameelNooriFontFamily else AmiriFontFamily,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        )
    }
}

@Composable
private fun GuideStepItem(
    number: String,
    title: String,
    description: String,
    lang: AppLanguage
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0x2034D399), RoundedCornerShape(12.dp))
            .padding(12.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Box(
            modifier = Modifier
                .size(28.dp)
                .background(RadiantGreenPrimary.copy(alpha = 0.25f), RoundedCornerShape(8.dp)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = number,
                color = RadiantGreenPrimary,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp
            )
        }
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                fontFamily = if (lang == AppLanguage.URDU) JameelNooriFontFamily else AmiriFontFamily,
                fontWeight = FontWeight.Bold,
                color = TextWhitePrimary,
                fontSize = 14.sp
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = description,
                fontFamily = if (lang == AppLanguage.URDU) JameelNooriFontFamily else AmiriFontFamily,
                color = TextMutedGreen,
                fontSize = 12.sp,
                lineHeight = 16.sp
            )
        }
    }
}

