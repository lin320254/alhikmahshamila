package com.example.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.automirrored.filled.OpenInNew
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.CloudDone
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Laptop
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.BookEntity
import com.example.data.CategoryEntity
import com.example.ui.theme.AmiriFontFamily
import com.example.ui.theme.JameelNooriFontFamily
import com.example.ui.theme.LuminousGreenText
import com.example.ui.theme.RadiantGreenPrimary
import com.example.ui.theme.TextMutedGreen
import com.example.ui.theme.TextWhitePrimary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: BookViewModel,
    modifier: Modifier = Modifier
) {
    val categories by viewModel.categories.collectAsState()
    val displayedBooks by viewModel.displayedBooks.collectAsState()
    val selectedCategory by viewModel.selectedCategory.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    val currentLang by viewModel.currentLanguage.collectAsState()

    val showAddBookDialog by viewModel.showAddBookDialog.collectAsState()
    val bookToEdit by viewModel.bookToEdit.collectAsState()
    val showAddCategoryDialog by viewModel.showAddCategoryDialog.collectAsState()
    val bookToDelete by viewModel.bookToDelete.collectAsState()

    val currentUser by viewModel.currentUser.collectAsState()
    var showAiServicesDialog by remember { mutableStateOf(false) }
    var showAccountMenu by remember { mutableStateOf(false) }
    var showLaptopGuideDialog by remember { mutableStateOf(false) }

    // RTL text direction for Urdu and Arabic
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        GlassScaffoldBackground(modifier = modifier) {
            Scaffold(
                modifier = Modifier.fillMaxSize(),
                containerColor = Color.Transparent,
                topBar = {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color(0xD0050A07))
                    ) {
                        TopAppBar(
                            title = {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    // Translucent Glass Emblem Box
                                    Surface(
                                        shape = RoundedCornerShape(14.dp),
                                        color = Color(0x3522C55E),
                                        border = BorderStroke(1.dp, Color(0x804ADE80)),
                                        modifier = Modifier.size(42.dp)
                                    ) {
                                        Box(contentAlignment = Alignment.Center) {
                                            Icon(
                                                imageVector = Icons.AutoMirrored.Filled.MenuBook,
                                                contentDescription = null,
                                                tint = RadiantGreenPrimary,
                                                modifier = Modifier.size(24.dp)
                                            )
                                        }
                                    }

                                    Spacer(modifier = Modifier.width(12.dp))

                                    Column {
                                        Text(
                                            text = Strings.appName(currentLang),
                                            fontFamily = if (currentLang == AppLanguage.URDU) JameelNooriFontFamily else AmiriFontFamily,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = if (currentLang == AppLanguage.URDU) 22.sp else 20.sp,
                                            color = TextWhitePrimary
                                        )
                                        Text(
                                            text = Strings.appSubtitle(currentLang),
                                            fontFamily = if (currentLang == AppLanguage.URDU) JameelNooriFontFamily else AmiriFontFamily,
                                            fontSize = if (currentLang == AppLanguage.URDU) 14.sp else 11.sp,
                                            color = LuminousGreenText
                                        )
                                    }
                                }
                            },
                            actions = {
                                // Corner AI Button [ ✨ AI ]
                                Surface(
                                    shape = RoundedCornerShape(18.dp),
                                    color = Color(0x3522C55E),
                                    border = BorderStroke(1.2.dp, RadiantGreenPrimary),
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(18.dp))
                                        .clickable { showAiServicesDialog = true }
                                        .testTag("ai_corner_btn")
                                ) {
                                    Row(
                                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.AutoAwesome,
                                            contentDescription = "AI",
                                            tint = RadiantGreenPrimary,
                                            modifier = Modifier.size(15.dp)
                                        )
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text(
                                            text = "AI",
                                            fontWeight = FontWeight.Black,
                                            fontSize = 13.sp,
                                            color = RadiantGreenPrimary
                                        )
                                    }
                                }

                                Spacer(modifier = Modifier.width(6.dp))

                                // Segmented iOS Glass Language Switcher [العربية | اردو]
                                Surface(
                                    shape = RoundedCornerShape(22.dp),
                                    color = Color(0x35142E20),
                                    border = BorderStroke(1.dp, Color(0x604ADE80)),
                                    modifier = Modifier.padding(end = 4.dp)
                                ) {
                                    Row(
                                        modifier = Modifier.padding(3.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        // Arabic Button (Amiri Font)
                                        Surface(
                                            shape = RoundedCornerShape(16.dp),
                                            color = if (currentLang == AppLanguage.ARABIC) RadiantGreenPrimary else Color.Transparent,
                                            modifier = Modifier
                                                .clip(RoundedCornerShape(16.dp))
                                                .clickable { viewModel.setLanguage(AppLanguage.ARABIC) }
                                                .testTag("lang_arabic_btn")
                                        ) {
                                            Text(
                                                text = "العربية",
                                                fontFamily = AmiriFontFamily,
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 13.sp,
                                                color = if (currentLang == AppLanguage.ARABIC) Color(0xFF03190C) else TextMutedGreen,
                                                modifier = Modifier.padding(horizontal = 9.dp, vertical = 4.dp)
                                            )
                                        }

                                        // Urdu Button (Jameel Noori Nastaleeq Font)
                                        Surface(
                                            shape = RoundedCornerShape(16.dp),
                                            color = if (currentLang == AppLanguage.URDU) RadiantGreenPrimary else Color.Transparent,
                                            modifier = Modifier
                                                .clip(RoundedCornerShape(16.dp))
                                                .clickable { viewModel.setLanguage(AppLanguage.URDU) }
                                                .testTag("lang_urdu_btn")
                                        ) {
                                            Text(
                                                text = "اردو",
                                                fontFamily = JameelNooriFontFamily,
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 13.sp,
                                                color = if (currentLang == AppLanguage.URDU) Color(0xFF03190C) else TextMutedGreen,
                                                modifier = Modifier.padding(horizontal = 9.dp, vertical = 4.dp)
                                            )
                                        }
                                    }
                                }

                                Spacer(modifier = Modifier.width(4.dp))

                                // Favorites Toggle
                                IconButton(
                                    onClick = {
                                        if (selectedCategory == SPECIAL_CATEGORY_FAVORITES) {
                                            viewModel.selectCategory(null)
                                        } else {
                                            viewModel.selectCategory(SPECIAL_CATEGORY_FAVORITES)
                                        }
                                    },
                                    modifier = Modifier.testTag("toggle_favorites_btn")
                                ) {
                                    Icon(
                                        imageVector = if (selectedCategory == SPECIAL_CATEGORY_FAVORITES) Icons.Default.Bookmark else Icons.Default.Star,
                                        contentDescription = "Favorites",
                                        tint = if (selectedCategory == SPECIAL_CATEGORY_FAVORITES) RadiantGreenPrimary else TextWhitePrimary
                                    )
                                }

                                Spacer(modifier = Modifier.width(2.dp))

                                // User Account & Cloud Sync Menu
                                Box {
                                    IconButton(
                                        onClick = { showAccountMenu = true },
                                        modifier = Modifier.testTag("account_menu_btn")
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.AccountCircle,
                                            contentDescription = "Account",
                                            tint = RadiantGreenPrimary,
                                            modifier = Modifier.size(26.dp)
                                        )
                                    }

                                    DropdownMenu(
                                        expanded = showAccountMenu,
                                        onDismissRequest = { showAccountMenu = false },
                                        modifier = Modifier.background(Color(0xFF07190F))
                                    ) {
                                        DropdownMenuItem(
                                            text = {
                                                Column(modifier = Modifier.padding(vertical = 4.dp)) {
                                                    Text(
                                                        text = currentUser?.displayName ?: "User",
                                                        fontFamily = if (currentLang == AppLanguage.URDU) JameelNooriFontFamily else AmiriFontFamily,
                                                        fontWeight = FontWeight.Bold,
                                                        color = TextWhitePrimary,
                                                        fontSize = 15.sp
                                                    )
                                                    Text(
                                                        text = currentUser?.email ?: "",
                                                        color = TextMutedGreen,
                                                        fontSize = 12.sp
                                                    )
                                                    Spacer(modifier = Modifier.height(4.dp))
                                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                                        Icon(
                                                            imageVector = Icons.Default.CloudDone,
                                                            contentDescription = null,
                                                            tint = RadiantGreenPrimary,
                                                            modifier = Modifier.size(14.dp)
                                                        )
                                                        Spacer(modifier = Modifier.width(6.dp))
                                                        Text(
                                                            text = if (currentLang == AppLanguage.URDU) "کلاؤڈ سنک فعال ہے" else "المزامنة السحابية مفعلة",
                                                            fontFamily = if (currentLang == AppLanguage.URDU) JameelNooriFontFamily else AmiriFontFamily,
                                                            color = LuminousGreenText,
                                                            fontSize = 12.sp
                                                        )
                                                    }
                                                }
                                            },
                                            onClick = {}
                                        )
                                        DropdownMenuItem(
                                            text = {
                                                Row(verticalAlignment = Alignment.CenterVertically) {
                                                    Icon(
                                                        imageVector = Icons.Default.Laptop,
                                                        contentDescription = null,
                                                        tint = RadiantGreenPrimary,
                                                        modifier = Modifier.size(16.dp)
                                                    )
                                                    Spacer(modifier = Modifier.width(8.dp))
                                                    Text(
                                                        text = if (currentLang == AppLanguage.URDU) "لیپ ٹاپ اور ویب گائیڈ" else "استخدام اللابتوب والويب",
                                                        fontFamily = if (currentLang == AppLanguage.URDU) JameelNooriFontFamily else AmiriFontFamily,
                                                        color = TextWhitePrimary,
                                                        fontSize = 13.sp
                                                    )
                                                }
                                            },
                                            onClick = {
                                                showAccountMenu = false
                                                showLaptopGuideDialog = true
                                            }
                                        )
                                        DropdownMenuItem(
                                            text = {
                                                Text(
                                                    text = if (currentLang == AppLanguage.URDU) "لاگ آؤٹ کریں" else "تسجيل الخروج",
                                                    fontFamily = if (currentLang == AppLanguage.URDU) JameelNooriFontFamily else AmiriFontFamily,
                                                    color = Color(0xFFF87171),
                                                    fontWeight = FontWeight.Bold,
                                                    fontSize = 14.sp
                                                )
                                            },
                                            onClick = {
                                                showAccountMenu = false
                                                viewModel.signOut()
                                            }
                                        )
                                    }
                                }
                            },
                            colors = TopAppBarDefaults.topAppBarColors(
                                containerColor = Color.Transparent
                            )
                        )

                        // Pinned Global Search Bar - centered and max width for laptops
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .widthIn(max = 840.dp)
                                .padding(horizontal = 16.dp, vertical = 4.dp)
                        ) {
                            GlobalSearchBar(
                                query = searchQuery,
                                onQueryChange = { viewModel.setSearchQuery(it) },
                                onClear = { viewModel.clearSearch() },
                                lang = currentLang,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }

                        // Real-time Global Search Feedback (Match count & quick clear)
                        if (searchQuery.isNotBlank()) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .widthIn(max = 840.dp)
                            ) {
                                GlobalSearchFeedbackBar(
                                    query = searchQuery,
                                    resultsCount = displayedBooks.size,
                                    lang = currentLang,
                                    onClearSearch = { viewModel.clearSearch() }
                                )
                            }
                        }

                        // Subtle bottom divider
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(1.dp)
                                .background(
                                    Brush.horizontalGradient(
                                        listOf(Color.Transparent, Color(0x3522C55E), Color.Transparent)
                                    )
                                )
                        )
                    }
                },
                bottomBar = {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .widthIn(max = 840.dp)
                        ) {
                            IslamicBottomNavigationBar(
                                selectedCategory = selectedCategory,
                                lang = currentLang,
                                onSelectCategory = { viewModel.selectCategory(it) },
                                onOpenLibrary = {
                                    viewModel.selectAppScreen(ActiveAppScreen.MAIN_LIBRARY)
                                }
                            )
                        }
                    }
                },
                floatingActionButton = {
                    // iPhone Liquid Glass Extended FAB
                    ExtendedFloatingActionButton(
                        onClick = { viewModel.showAddBook(selectedCategory) },
                        icon = {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = null,
                                modifier = Modifier.size(20.dp),
                                tint = Color(0xFF03190C)
                            )
                        },
                        text = {
                            Text(
                                text = Strings.addBook(currentLang),
                                fontFamily = if (currentLang == AppLanguage.URDU) JameelNooriFontFamily else AmiriFontFamily,
                                fontSize = if (currentLang == AppLanguage.URDU) 17.sp else 15.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF03190C)
                            )
                        },
                        containerColor = RadiantGreenPrimary,
                        contentColor = Color(0xFF03190C),
                        shape = RoundedCornerShape(26.dp),
                        modifier = Modifier
                            .border(BorderStroke(1.dp, Color(0x8086EFAC)), RoundedCornerShape(26.dp))
                            .testTag("add_book_fab")
                    )
                }
            ) { innerPadding ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    contentAlignment = Alignment.TopCenter
                ) {
                    LazyVerticalGrid(
                        columns = GridCells.Adaptive(minSize = 350.dp),
                        modifier = Modifier
                            .fillMaxSize()
                            .widthIn(max = 1440.dp)
                            .testTag("books_grid"),
                        contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 10.dp, bottom = 96.dp),
                        horizontalArrangement = Arrangement.spacedBy(14.dp),
                        verticalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
                        // When NOT searching, show the hero Ayah and Quick Access shortcuts
                        if (searchQuery.isBlank()) {
                            // 1. AYAH OF THE MOMENT (Featured hero card from user's screenshot)
                            item(span = { GridItemSpan(maxLineSpan) }) {
                                AyahOfTheMomentCard(
                                    lang = currentLang,
                                    onReadAyahInShamela = { book ->
                                        viewModel.openReader(book)
                                    }
                                )
                            }

                            // 2. QUICK ACCESS SHORTCUTS (From user screenshot)
                            item(span = { GridItemSpan(maxLineSpan) }) {
                                QuickAccessShortcutsRow(
                                    lang = currentLang,
                                    onOpenBook = { book ->
                                        viewModel.openReader(book)
                                    },
                                    onOpenAiServices = {
                                        showAiServicesDialog = true
                                    }
                                )
                            }

                            // 3. CATEGORY SELECTOR BAR (فقہ حنفی, تفسیر, حدیث etc.)
                            item(span = { GridItemSpan(maxLineSpan) }) {
                                GlassCategorySelectorBar(
                                    categories = categories,
                                    selectedCategory = selectedCategory,
                                    lang = currentLang,
                                    onSelectCategory = { viewModel.selectCategory(it) },
                                    onAddNewCategory = { viewModel.showAddCategory() },
                                    onDeleteCategory = { viewModel.deleteCategory(it) }
                                )
                            }

                            // 4. STATUS HEADER (Category Name + Books count badge)
                            item(span = { GridItemSpan(maxLineSpan) }) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 4.dp, vertical = 2.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    val currentCategoryTitle = when (selectedCategory) {
                                        null -> Strings.allCategories(currentLang)
                                        SPECIAL_CATEGORY_FAVORITES -> "⭐ " + Strings.favorites(currentLang)
                                        else -> Strings.localizedCategoryName(selectedCategory!!, currentLang)
                                    }

                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Box(
                                            modifier = Modifier
                                                .size(8.dp)
                                                .background(RadiantGreenPrimary, CircleShape)
                                        )
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text(
                                            text = currentCategoryTitle,
                                            fontFamily = if (currentLang == AppLanguage.URDU) JameelNooriFontFamily else AmiriFontFamily,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = if (currentLang == AppLanguage.URDU) 19.sp else 16.sp,
                                            color = LuminousGreenText
                                        )
                                    }

                                    // Glass Badge with Book Count
                                    Surface(
                                        shape = RoundedCornerShape(12.dp),
                                        color = Color(0x2822C55E),
                                        border = BorderStroke(1.dp, Color(0x4034D399))
                                    ) {
                                        Text(
                                            text = "${displayedBooks.size} ${Strings.booksCountSuffix(currentLang)}",
                                            fontFamily = if (currentLang == AppLanguage.URDU) JameelNooriFontFamily else AmiriFontFamily,
                                            fontSize = if (currentLang == AppLanguage.URDU) 14.sp else 12.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = LuminousGreenText,
                                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 3.dp)
                                        )
                                    }
                                }
                            }
                        } else {
                            // WHEN SEARCHING: Show optional Category Filter bar
                            item(span = { GridItemSpan(maxLineSpan) }) {
                                GlassCategorySelectorBar(
                                    categories = categories,
                                    selectedCategory = selectedCategory,
                                    lang = currentLang,
                                    onSelectCategory = { viewModel.selectCategory(it) },
                                    onAddNewCategory = { viewModel.showAddCategory() },
                                    onDeleteCategory = { viewModel.deleteCategory(it) }
                                )
                            }

                            // Search Results Banner
                            item(span = { GridItemSpan(maxLineSpan) }) {
                                Surface(
                                    shape = RoundedCornerShape(16.dp),
                                    color = Color(0x2822C55E),
                                    border = BorderStroke(1.dp, Color(0x604ADE80)),
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Row(
                                        modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            Icon(
                                                imageVector = Icons.Default.Search,
                                                contentDescription = null,
                                                tint = RadiantGreenPrimary,
                                                modifier = Modifier.size(16.dp)
                                            )
                                            Spacer(modifier = Modifier.width(6.dp))
                                            Text(
                                                text = Strings.globalSearchBadge(currentLang),
                                                fontFamily = if (currentLang == AppLanguage.URDU) JameelNooriFontFamily else AmiriFontFamily,
                                                fontWeight = FontWeight.Bold,
                                                fontSize = if (currentLang == AppLanguage.URDU) 15.sp else 13.sp,
                                                color = LuminousGreenText
                                            )
                                        }

                                        Text(
                                            text = "${displayedBooks.size} ${Strings.booksCountSuffix(currentLang)}",
                                            fontFamily = if (currentLang == AppLanguage.URDU) JameelNooriFontFamily else AmiriFontFamily,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = if (currentLang == AppLanguage.URDU) 14.sp else 12.sp,
                                            color = RadiantGreenPrimary
                                        )
                                    }
                                }
                            }
                        }

                        // 5. BOOKS GRID
                        if (displayedBooks.isEmpty()) {
                            item(span = { GridItemSpan(maxLineSpan) }) {
                                GlassEmptyStateView(
                                    isSearch = searchQuery.isNotBlank(),
                                    lang = currentLang,
                                    onAddBook = { viewModel.showAddBook(selectedCategory) },
                                    onClearSearch = { viewModel.clearSearch() }
                                )
                            }
                        } else {
                            items(displayedBooks, key = { it.id }) { book ->
                                BookCard(
                                    book = book,
                                    lang = currentLang,
                                    onRead = { viewModel.openReader(book) },
                                    onToggleFavorite = { viewModel.toggleFavorite(book) },
                                    onEdit = { viewModel.showEditBook(book) },
                                    onDelete = { viewModel.confirmDeleteBook(book) }
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    // Glass Dialogs
    if (showAddBookDialog) {
        AddEditBookDialog(
            bookToEdit = bookToEdit,
            categories = categories,
            initialCategory = selectedCategory,
            lang = currentLang,
            onDismiss = { viewModel.dismissBookDialog() },
            onSave = { title, author, category, url, notes ->
                viewModel.saveBook(title, author, category, url, notes)
            },
            onAddNewCategoryRequest = {
                viewModel.showAddCategory()
            }
        )
    }

    if (showAddCategoryDialog) {
        AddCategoryDialog(
            lang = currentLang,
            onDismiss = { viewModel.dismissAddCategory() },
            onSave = { name, nameMm ->
                viewModel.saveCategory(name, nameMm)
            }
        )
    }

    bookToDelete?.let { book ->
        DeleteConfirmDialog(
            book = book,
            lang = currentLang,
            onDismiss = { viewModel.dismissDeleteDialog() },
            onConfirm = { viewModel.deleteConfirmedBook() }
        )
    }

    if (showAiServicesDialog) {
        AiServicesDialog(
            lang = currentLang,
            onDismiss = { showAiServicesDialog = false },
            onOpenServiceInReader = { book ->
                viewModel.openReader(book)
            }
        )
    }

    if (showLaptopGuideDialog) {
        LaptopWebGuideDialog(
            lang = currentLang,
            onDismiss = { showLaptopGuideDialog = false }
        )
    }
}

/**
 * Pinned Global Search Bar styled with Islamic Liquid Glass UI.
 * Features instant real-time filtering across all book titles and author names.
 */
@Composable
private fun GlobalSearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    onClear: () -> Unit,
    lang: AppLanguage,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = RoundedCornerShape(32.dp),
        color = Color(0x4010261B),
        border = BorderStroke(
            1.2.dp,
            if (query.isNotBlank()) RadiantGreenPrimary else Color(0x604ADE80)
        ),
        modifier = modifier
            .fillMaxWidth()
            .testTag("global_search_bar")
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
        ) {
            // Circular green button with magnifying glass
            Surface(
                shape = CircleShape,
                color = RadiantGreenPrimary,
                modifier = Modifier.size(42.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search",
                        tint = Color(0xFF03190C),
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.width(8.dp))

            // Search input field
            OutlinedTextField(
                value = query,
                onValueChange = onQueryChange,
                placeholder = {
                    Text(
                        text = Strings.searchHint(lang),
                        fontFamily = if (lang == AppLanguage.URDU) JameelNooriFontFamily else AmiriFontFamily,
                        fontSize = if (lang == AppLanguage.URDU) 15.sp else 13.sp,
                        color = TextMutedGreen.copy(alpha = 0.85f),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                trailingIcon = {
                    if (query.isNotEmpty()) {
                        IconButton(
                            onClick = onClear,
                            modifier = Modifier.testTag("clear_search_btn")
                        ) {
                            Icon(
                                imageVector = Icons.Default.Clear,
                                contentDescription = "Clear",
                                tint = RadiantGreenPrimary
                            )
                        }
                    } else {
                        // Ambient Sparkle Icon
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier.padding(end = 4.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.AutoAwesome,
                                contentDescription = null,
                                tint = TextMutedGreen.copy(alpha = 0.6f),
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }
                },
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent,
                    focusedTextColor = TextWhitePrimary,
                    unfocusedTextColor = TextWhitePrimary,
                    cursorColor = RadiantGreenPrimary
                ),
                modifier = Modifier
                    .weight(1f)
                    .testTag("search_input_field")
            )
        }
    }
}

/**
 * Real-time feedback bar displayed beneath the global search bar while typing
 */
@Composable
private fun GlobalSearchFeedbackBar(
    query: String,
    resultsCount: Int,
    lang: AppLanguage,
    onClearSearch: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(7.dp)
                    .background(RadiantGreenPrimary, CircleShape)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = Strings.searchResultsCount(resultsCount, lang),
                fontFamily = if (lang == AppLanguage.URDU) JameelNooriFontFamily else AmiriFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = if (lang == AppLanguage.URDU) 15.sp else 13.sp,
                color = LuminousGreenText
            )
        }

        Surface(
            shape = RoundedCornerShape(12.dp),
            color = Color(0x3022C55E),
            border = BorderStroke(1.dp, Color(0x604ADE80)),
            modifier = Modifier
                .clip(RoundedCornerShape(12.dp))
                .clickable(onClick = onClearSearch)
                .testTag("feedback_clear_search_btn")
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Clear,
                    contentDescription = null,
                    tint = RadiantGreenPrimary,
                    modifier = Modifier.size(12.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = Strings.clearSearch(lang),
                    fontFamily = if (lang == AppLanguage.URDU) JameelNooriFontFamily else AmiriFontFamily,
                    fontSize = if (lang == AppLanguage.URDU) 13.sp else 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = RadiantGreenPrimary
                )
            }
        }
    }
}

/**
 * Quick Access row to instantly jump into classical Hanafi masterpieces
 */
@Composable
private fun QuickAccessShortcutsRow(
    lang: AppLanguage,
    onOpenBook: (BookEntity) -> Unit,
    onOpenAiServices: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Surface(
                    shape = CircleShape,
                    color = Color(0x3522C55E),
                    border = BorderStroke(1.dp, Color(0x804ADE80)),
                    modifier = Modifier.size(24.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = Icons.Default.Dashboard,
                            contentDescription = null,
                            tint = RadiantGreenPrimary,
                            modifier = Modifier.size(14.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = Strings.quickAccess(lang),
                    fontFamily = if (lang == AppLanguage.URDU) JameelNooriFontFamily else AmiriFontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = if (lang == AppLanguage.URDU) 16.sp else 14.sp,
                    color = LuminousGreenText
                )
            }

            // Quick AI Badge on the side
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = Color(0x3522C55E),
                border = BorderStroke(1.dp, RadiantGreenPrimary),
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .clickable(onClick = onOpenAiServices)
                    .testTag("quick_access_ai_header_btn")
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.AutoAwesome,
                        contentDescription = null,
                        tint = RadiantGreenPrimary,
                        modifier = Modifier.size(12.dp)
                    )
                    Spacer(modifier = Modifier.width(3.dp))
                    Text(
                        text = "AI Services",
                        fontWeight = FontWeight.Bold,
                        fontSize = 11.sp,
                        color = RadiantGreenPrimary
                    )
                }
            }
        }

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(horizontal = 2.dp)
        ) {
            // First item: Glowing AI Services Shortcut
            item {
                Surface(
                    shape = RoundedCornerShape(18.dp),
                    color = Color(0x3522C55E),
                    border = BorderStroke(1.2.dp, RadiantGreenPrimary),
                    modifier = Modifier
                        .clip(RoundedCornerShape(18.dp))
                        .clickable(onClick = onOpenAiServices)
                        .testTag("quick_access_ai_pill")
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 14.dp, vertical = 7.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.AutoAwesome,
                            contentDescription = null,
                            tint = RadiantGreenPrimary,
                            modifier = Modifier.size(15.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = if (lang == AppLanguage.URDU) "✨ AI (شاملہ • دیوبند • الإخلاص)" else "✨ AI (الشاملة • ديوبند • الإخلاص)",
                            fontFamily = if (lang == AppLanguage.URDU) JameelNooriFontFamily else AmiriFontFamily,
                            fontWeight = FontWeight.Bold,
                            fontSize = if (lang == AppLanguage.URDU) 14.sp else 12.sp,
                            color = RadiantGreenPrimary
                        )
                    }
                }
            }

            val quickBooks = listOf(
                Triple("رد المحتار", "https://shamela.ws/book/21613", "فقہ حنفی"),
                Triple("الهداية", "https://shamela.ws/book/11820", "فقہ حنفی"),
                Triple("المبسوط", "https://shamela.ws/book/5423", "فقہ حنفی"),
                Triple("صحيح البخاري", "https://shamela.ws/book/1680", "Hadith"),
                Triple("الفتاوى الهندية", "https://shamela.ws/book/21640", "فقہ حنفی"),
                Triple("بدائع الصنائع", "https://shamela.ws/book/8183", "فقہ حنفی"),
                Triple("مختصر القدوري", "https://shamela.ws/book/124336", "فقہ حنفی")
            )

            items(quickBooks) { (name, url, cat) ->
                Surface(
                    shape = RoundedCornerShape(18.dp),
                    color = Color(0x281B3A28),
                    border = BorderStroke(1.dp, Color(0x604ADE80)),
                    modifier = Modifier
                        .clip(RoundedCornerShape(18.dp))
                        .clickable {
                            onOpenBook(
                                BookEntity(
                                    title = name,
                                    author = "المكتبة الشاملة",
                                    category = cat,
                                    url = url,
                                    notes = "الوصول السريع عبر الشاملة"
                                )
                            )
                        }
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 14.dp, vertical = 7.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = name,
                            fontFamily = if (lang == AppLanguage.URDU) JameelNooriFontFamily else AmiriFontFamily,
                            fontWeight = FontWeight.Bold,
                            fontSize = if (lang == AppLanguage.URDU) 15.sp else 13.sp,
                            color = TextWhitePrimary
                        )
                    }
                }
            }
        }
    }
}

/**
 * Islamic Bottom Navigation Bar matching the user's reference screenshot
 */
@Composable
private fun IslamicBottomNavigationBar(
    selectedCategory: String?,
    lang: AppLanguage,
    onSelectCategory: (String?) -> Unit,
    onOpenLibrary: () -> Unit
) {
    Surface(
        color = Color(0xF2060C08),
        border = BorderStroke(1.dp, Color(0x3534D399)),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Right Tab: Hadith & Fiqh
            val isHadith = selectedCategory == "Hadith" || selectedCategory == "فقہ حنفی"
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .clip(RoundedCornerShape(14.dp))
                    .clickable {
                        onSelectCategory("فقہ حنفی")
                    }
                    .padding(horizontal = 12.dp, vertical = 4.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.MenuBook,
                    contentDescription = null,
                    tint = if (isHadith) RadiantGreenPrimary else TextMutedGreen,
                    modifier = Modifier.size(22.dp)
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = Strings.hadithTab(lang),
                    fontFamily = if (lang == AppLanguage.URDU) JameelNooriFontFamily else AmiriFontFamily,
                    fontSize = if (lang == AppLanguage.URDU) 13.sp else 11.sp,
                    fontWeight = if (isHadith) FontWeight.Bold else FontWeight.Normal,
                    color = if (isHadith) LuminousGreenText else TextMutedGreen
                )
            }

            // Center: Elevated curved pill button "المكتبة"
            Surface(
                shape = RoundedCornerShape(26.dp),
                color = RadiantGreenPrimary,
                border = BorderStroke(1.dp, Color(0x9986EFAC)),
                modifier = Modifier
                    .clip(RoundedCornerShape(26.dp))
                    .clickable(onClick = onOpenLibrary)
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.MenuBook,
                        contentDescription = null,
                        tint = Color(0xFF03190C),
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = Strings.libraryCenter(lang),
                        fontFamily = AmiriFontFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp,
                        color = Color(0xFF03190C)
                    )
                }
            }

            // Left Tab: Quran & Tafsir
            val isTafsir = selectedCategory == "Tafsir"
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .clip(RoundedCornerShape(14.dp))
                    .clickable {
                        onSelectCategory("Tafsir")
                    }
                    .padding(horizontal = 12.dp, vertical = 4.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.AutoAwesome,
                    contentDescription = null,
                    tint = if (isTafsir) RadiantGreenPrimary else TextMutedGreen,
                    modifier = Modifier.size(22.dp)
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = Strings.quranTab(lang),
                    fontFamily = if (lang == AppLanguage.URDU) JameelNooriFontFamily else AmiriFontFamily,
                    fontSize = if (lang == AppLanguage.URDU) 13.sp else 11.sp,
                    fontWeight = if (isTafsir) FontWeight.Bold else FontWeight.Normal,
                    color = if (isTafsir) LuminousGreenText else TextMutedGreen
                )
            }
        }
    }
}

@Composable
private fun GlassCategorySelectorBar(
    categories: List<CategoryEntity>,
    selectedCategory: String?,
    lang: AppLanguage,
    onSelectCategory: (String?) -> Unit,
    onAddNewCategory: () -> Unit,
    onDeleteCategory: (CategoryEntity) -> Unit
) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("categories_row"),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 4.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // "All" chip
        item {
            val isSelected = selectedCategory == null
            Surface(
                shape = RoundedCornerShape(20.dp),
                color = if (isSelected) Color(0x4822C55E) else Color(0x221E382A),
                border = BorderStroke(1.dp, if (isSelected) Color(0x994ADE80) else Color(0x3534D399)),
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
                    .clickable { onSelectCategory(null) }
                    .testTag("category_chip_all")
            ) {
                Text(
                    text = Strings.allCategories(lang),
                    fontFamily = if (lang == AppLanguage.URDU) JameelNooriFontFamily else AmiriFontFamily,
                    fontSize = if (lang == AppLanguage.URDU) 15.sp else 13.sp,
                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                    color = if (isSelected) LuminousGreenText else TextMutedGreen,
                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp)
                )
            }
        }

        // "Favorites" chip
        item {
            val isSelected = selectedCategory == SPECIAL_CATEGORY_FAVORITES
            Surface(
                shape = RoundedCornerShape(20.dp),
                color = if (isSelected) Color(0x4822C55E) else Color(0x221E382A),
                border = BorderStroke(1.dp, if (isSelected) Color(0x994ADE80) else Color(0x3534D399)),
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
                    .clickable {
                        if (selectedCategory == SPECIAL_CATEGORY_FAVORITES) {
                            onSelectCategory(null)
                        } else {
                            onSelectCategory(SPECIAL_CATEGORY_FAVORITES)
                        }
                    }
                    .testTag("category_chip_favorites")
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Bookmark,
                        contentDescription = null,
                        tint = if (isSelected) RadiantGreenPrimary else TextMutedGreen,
                        modifier = Modifier.size(15.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = Strings.favorites(lang),
                        fontFamily = if (lang == AppLanguage.URDU) JameelNooriFontFamily else AmiriFontFamily,
                        fontSize = if (lang == AppLanguage.URDU) 15.sp else 13.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                        color = if (isSelected) LuminousGreenText else TextMutedGreen
                    )
                }
            }
        }

        // Category Chips (فقہ حنفی, تفسیر, حدیث etc.)
        items(categories, key = { it.id }) { cat ->
            var showCategoryMenu by remember { mutableStateOf(false) }
            val isSelected = selectedCategory == cat.name
            val isHanafi = cat.name == "فقہ حنفی"
            val displayName = Strings.localizedCategoryName(cat.name, lang)

            Box {
                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = when {
                        isSelected -> Color(0x5522C55E)
                        isHanafi -> Color(0x301E4D2B)
                        else -> Color(0x221E382A)
                    },
                    border = BorderStroke(
                        1.dp,
                        when {
                            isSelected -> Color(0xBB4ADE80)
                            isHanafi -> Color(0x774ADE80)
                            else -> Color(0x3534D399)
                        }
                    ),
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .clickable { onSelectCategory(cat.name) }
                        .testTag("category_chip_${cat.name}")
                ) {
                    Text(
                        text = if (isHanafi) "⚖️ $displayName" else displayName,
                        fontFamily = if (lang == AppLanguage.URDU) JameelNooriFontFamily else AmiriFontFamily,
                        fontSize = if (lang == AppLanguage.URDU) 15.sp else 13.sp,
                        fontWeight = if (isSelected || isHanafi) FontWeight.Bold else FontWeight.Medium,
                        color = if (isSelected || isHanafi) LuminousGreenText else TextMutedGreen,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp)
                    )
                }

                // Delete custom category option
                if (!cat.isDefault) {
                    DropdownMenu(
                        expanded = showCategoryMenu,
                        onDismissRequest = { showCategoryMenu = false },
                        containerColor = Color(0xF008140E)
                    ) {
                        DropdownMenuItem(
                            text = {
                                Text(
                                    Strings.deleteAction(lang),
                                    fontFamily = if (lang == AppLanguage.URDU) JameelNooriFontFamily else AmiriFontFamily,
                                    color = MaterialTheme.colorScheme.error
                                )
                            },
                            onClick = {
                                showCategoryMenu = false
                                onDeleteCategory(cat)
                            },
                            leadingIcon = {
                                Icon(Icons.Default.Delete, contentDescription = null, tint = MaterialTheme.colorScheme.error)
                            }
                        )
                    }
                }
            }
        }

        // "+ Add Category" Glass Pill
        item {
            Surface(
                shape = RoundedCornerShape(20.dp),
                color = Color(0x1834D399),
                border = BorderStroke(1.dp, Color(0x4034D399)),
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
                    .clickable(onClick = onAddNewCategory)
                    .testTag("add_category_chip")
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = null,
                        tint = RadiantGreenPrimary,
                        modifier = Modifier.size(15.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = Strings.addCategory(lang),
                        fontFamily = if (lang == AppLanguage.URDU) JameelNooriFontFamily else AmiriFontFamily,
                        fontSize = if (lang == AppLanguage.URDU) 15.sp else 13.sp,
                        color = LuminousGreenText,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}

@Composable
private fun GlassEmptyStateView(
    isSearch: Boolean,
    lang: AppLanguage,
    onAddBook: () -> Unit,
    onClearSearch: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(32.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Surface(
                shape = CircleShape,
                color = Color(0x3522C55E),
                border = BorderStroke(1.dp, Color(0x804ADE80)),
                modifier = Modifier.size(76.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = if (isSearch) Icons.Default.Search else Icons.AutoMirrored.Filled.MenuBook,
                        contentDescription = null,
                        tint = RadiantGreenPrimary,
                        modifier = Modifier.size(38.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            Text(
                text = if (isSearch) Strings.emptySearch(lang) else Strings.emptyBooks(lang),
                fontFamily = if (lang == AppLanguage.URDU) JameelNooriFontFamily else AmiriFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = if (lang == AppLanguage.URDU) 20.sp else 18.sp,
                color = TextWhitePrimary
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = if (isSearch) {
                    if (lang == AppLanguage.URDU) "کوئی کتاب یا مصنف تلاش کے مطابق نہیں ملا۔"
                    else "لم يتم العثور على أي كتاب أو مؤلف يطابق البحث."
                } else Strings.emptySubtitle(lang),
                fontFamily = if (lang == AppLanguage.URDU) JameelNooriFontFamily else AmiriFontFamily,
                fontSize = if (lang == AppLanguage.URDU) 16.sp else 14.sp,
                color = TextMutedGreen
            )

            Spacer(modifier = Modifier.height(20.dp))

            if (isSearch) {
                Button(
                    onClick = onClearSearch,
                    shape = RoundedCornerShape(24.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = RadiantGreenPrimary,
                        contentColor = Color(0xFF03190C)
                    ),
                    border = BorderStroke(1.dp, Color(0x8086EFAC)),
                    modifier = Modifier.testTag("empty_clear_search_btn")
                ) {
                    Icon(Icons.Default.Clear, contentDescription = null, tint = Color(0xFF03190C))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = Strings.clearSearch(lang),
                        fontFamily = if (lang == AppLanguage.URDU) JameelNooriFontFamily else AmiriFontFamily,
                        fontSize = if (lang == AppLanguage.URDU) 16.sp else 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF03190C)
                    )
                }
            } else {
                Button(
                    onClick = onAddBook,
                    shape = RoundedCornerShape(24.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = RadiantGreenPrimary,
                        contentColor = Color(0xFF03190C)
                    ),
                    border = BorderStroke(1.dp, Color(0x8086EFAC)),
                    modifier = Modifier.testTag("empty_add_book_btn")
                ) {
                    Icon(Icons.Default.Add, contentDescription = null, tint = Color(0xFF03190C))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = Strings.addBook(lang),
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
