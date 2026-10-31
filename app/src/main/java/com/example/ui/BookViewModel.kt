package com.example.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.R
import com.example.data.AppDatabase
import com.example.data.BookEntity
import com.example.data.BookRepository
import com.example.data.CategoryEntity
import com.example.data.firebase.AuthManager
import com.example.data.firebase.FirestoreRepository
import com.example.data.firebase.UserBookFirestore
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

const val SPECIAL_CATEGORY_FAVORITES = "__FAVORITES__"

enum class ActiveAppScreen {
    HOME,
    MAIN_LIBRARY
}

enum class LibraryViewMode {
    CATEGORIZED_SHELVES,
    EXPANDED_GRID
}


class BookViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: BookRepository
    val authManager: AuthManager = AuthManager()
    private val firestoreRepository: FirestoreRepository = FirestoreRepository(
        FirebaseFirestore.getInstance(
            application.applicationContext.getString(R.string.firestore_database_id)
        )
    )

    val currentUser: StateFlow<FirebaseUser?> = authManager.authStateFlow()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), authManager.currentUser)

    init {
        val database = AppDatabase.getDatabase(application, viewModelScope)
        repository = BookRepository(database.bookDao(), database.categoryDao())
        viewModelScope.launch {
            repository.ensureDefaultDataPopulated()
        }

        // Real-time Cloud Sync with Firestore for authenticated sessions
        viewModelScope.launch {
            authManager.authStateFlow().collect { user ->
                if (user != null) {
                    firestoreRepository.syncUserProfile(user)
                    try {
                        firestoreRepository.observeUserBooks(user.uid).collect { firestoreBooks ->
                            firestoreBooks.forEach { fb ->
                                val existing = repository.getBookById(fb.id.toLongOrNull() ?: 0L)
                                if (existing == null) {
                                    repository.insertBook(
                                        BookEntity(
                                            title = fb.title,
                                            author = fb.author,
                                            category = fb.category,
                                            url = fb.url,
                                            notes = fb.notes,
                                            isFavorite = fb.isFavorite
                                        )
                                    )
                                }
                            }
                        }
                    } catch (ignored: Exception) {
                        // Handled in repository via handleFirestoreError
                    }
                }
            }
        }
    }

    val categories: StateFlow<List<CategoryEntity>> = repository.allCategories
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _selectedCategory = MutableStateFlow<String?>(null)
    val selectedCategory: StateFlow<String?> = _selectedCategory.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _isSearchActive = MutableStateFlow(false)
    val isSearchActive: StateFlow<Boolean> = _isSearchActive.asStateFlow()

    private val _activeReadingBook = MutableStateFlow<BookEntity?>(null)
    val activeReadingBook: StateFlow<BookEntity?> = _activeReadingBook.asStateFlow()

    private val _showAddBookDialog = MutableStateFlow(false)
    val showAddBookDialog: StateFlow<Boolean> = _showAddBookDialog.asStateFlow()

    private val _bookToEdit = MutableStateFlow<BookEntity?>(null)
    val bookToEdit: StateFlow<BookEntity?> = _bookToEdit.asStateFlow()

    private val _showAddCategoryDialog = MutableStateFlow(false)
    val showAddCategoryDialog: StateFlow<Boolean> = _showAddCategoryDialog.asStateFlow()

    private val _bookToDelete = MutableStateFlow<BookEntity?>(null)
    val bookToDelete: StateFlow<BookEntity?> = _bookToDelete.asStateFlow()

    private val _currentLanguage = MutableStateFlow(AppLanguage.URDU)
    val currentLanguage: StateFlow<AppLanguage> = _currentLanguage.asStateFlow()

    fun setLanguage(lang: AppLanguage) {
        _currentLanguage.value = lang
    }

    private val _currentAppScreen = MutableStateFlow(ActiveAppScreen.MAIN_LIBRARY)
    val currentAppScreen: StateFlow<ActiveAppScreen> = _currentAppScreen.asStateFlow()

    private val _libraryViewMode = MutableStateFlow(LibraryViewMode.CATEGORIZED_SHELVES)
    val libraryViewMode: StateFlow<LibraryViewMode> = _libraryViewMode.asStateFlow()

    val allRoomBooks: StateFlow<List<BookEntity>> = repository.allBooks
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun selectAppScreen(screen: ActiveAppScreen) {
        _currentAppScreen.value = screen
    }

    fun setLibraryViewMode(mode: LibraryViewMode) {
        _libraryViewMode.value = mode
    }

    val displayedBooks: StateFlow<List<BookEntity>> = combine(
        repository.allBooks,
        _selectedCategory,
        _searchQuery
    ) { allBooks, category, query ->
        val trimmedQuery = query.trim()
        if (trimmedQuery.isNotEmpty()) {
            allBooks.filter { book ->
                ArabicSearchUtils.matches(
                    trimmedQuery,
                    book.title,
                    book.author,
                    book.category,
                    book.notes
                )
            }
        } else if (category == SPECIAL_CATEGORY_FAVORITES) {
            allBooks.filter { it.isFavorite }
        } else if (category != null) {
            allBooks.filter { it.category == category }
        } else {
            allBooks
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun selectCategory(category: String?) {
        _selectedCategory.value = category
    }

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
        _isSearchActive.value = query.isNotBlank()
    }

    fun clearSearch() {
        _searchQuery.value = ""
        _isSearchActive.value = false
    }

    fun toggleSearch(active: Boolean) {
        _isSearchActive.value = active
        if (!active) {
            _searchQuery.value = ""
        }
    }

    fun openReader(book: BookEntity) {
        _activeReadingBook.value = book
        viewModelScope.launch {
            repository.updateLastOpened(book.id)
        }
    }

    fun closeReader() {
        _activeReadingBook.value = null
    }

    fun toggleFavorite(book: BookEntity) {
        viewModelScope.launch {
            val newFav = !book.isFavorite
            repository.setFavorite(book.id, newFav)
            val user = authManager.currentUser
            if (user != null) {
                firestoreRepository.setFavorite(user.uid, book.id.toString(), newFav)
            }
            if (_activeReadingBook.value?.id == book.id) {
                _activeReadingBook.value = _activeReadingBook.value?.copy(isFavorite = newFav)
            }
        }
    }

    fun showAddBook(preselectedCategory: String? = null) {
        _bookToEdit.value = null
        if (preselectedCategory != null && preselectedCategory != SPECIAL_CATEGORY_FAVORITES) {
            _selectedCategory.value = preselectedCategory
        }
        _showAddBookDialog.value = true
    }

    fun showEditBook(book: BookEntity) {
        _bookToEdit.value = book
        _showAddBookDialog.value = true
    }

    fun dismissBookDialog() {
        _showAddBookDialog.value = false
        _bookToEdit.value = null
    }

    fun saveBook(title: String, author: String, category: String, url: String, notes: String) {
        viewModelScope.launch {
            var formattedUrl = url.trim()
            if (!formattedUrl.startsWith("http://") && !formattedUrl.startsWith("https://")) {
                formattedUrl = "https://$formattedUrl"
            }
            val existing = _bookToEdit.value
            val bookId: Long
            if (existing != null) {
                repository.updateBook(
                    existing.copy(
                        title = title.trim(),
                        author = author.trim(),
                        category = category.trim(),
                        url = formattedUrl,
                        notes = notes.trim()
                    )
                )
                bookId = existing.id
            } else {
                bookId = repository.insertBook(
                    BookEntity(
                        title = title.trim(),
                        author = author.trim(),
                        category = category.trim(),
                        url = formattedUrl,
                        notes = notes.trim()
                    )
                )
            }

            // Sync with Firestore Cloud
            val user = authManager.currentUser
            if (user != null) {
                firestoreRepository.saveBook(
                    userId = user.uid,
                    book = UserBookFirestore(
                        id = bookId.toString(),
                        userId = user.uid,
                        title = title.trim(),
                        author = author.trim(),
                        category = category.trim(),
                        url = formattedUrl,
                        notes = notes.trim(),
                        isFavorite = existing?.isFavorite ?: false
                    )
                )
            }
            dismissBookDialog()
        }
    }

    fun confirmDeleteBook(book: BookEntity) {
        _bookToDelete.value = book
    }

    fun dismissDeleteDialog() {
        _bookToDelete.value = null
    }

    fun deleteConfirmedBook() {
        val book = _bookToDelete.value ?: return
        viewModelScope.launch {
            repository.deleteBook(book)
            val user = authManager.currentUser
            if (user != null) {
                firestoreRepository.deleteBook(user.uid, book.id.toString())
            }
            if (_activeReadingBook.value?.id == book.id) {
                closeReader()
            }
            _bookToDelete.value = null
        }
    }

    fun showAddCategory() {
        _showAddCategoryDialog.value = true
    }

    fun dismissAddCategory() {
        _showAddCategoryDialog.value = false
    }

    fun saveCategory(name: String, nameMm: String) {
        if (name.isBlank()) return
        viewModelScope.launch {
            repository.insertCategory(
                CategoryEntity(
                    name = name.trim(),
                    nameMm = nameMm.trim(),
                    isDefault = false
                )
            )
            _selectedCategory.value = name.trim()
            dismissAddCategory()
        }
    }

    fun deleteCategory(category: CategoryEntity) {
        viewModelScope.launch {
            repository.deleteCategory(category)
            if (_selectedCategory.value == category.name) {
                _selectedCategory.value = null
            }
        }
    }

    fun signOut() {
        authManager.signOut()
    }
}
