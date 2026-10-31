package com.example.ui

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.view.View
import android.view.ViewGroup
import android.webkit.RenderProcessGoneDetail
import android.webkit.WebChromeClient
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.OpenInNew
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Fullscreen
import androidx.compose.material.icons.filled.FullscreenExit
import androidx.compose.material.icons.filled.Laptop
import androidx.compose.material.icons.filled.PhoneAndroid
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.filled.ZoomIn
import androidx.compose.material.icons.filled.ZoomOut
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.data.BookEntity
import com.example.ui.theme.AmiriFontFamily
import com.example.ui.theme.DeepBlackBackground
import com.example.ui.theme.JameelNooriFontFamily
import com.example.ui.theme.LuminousGreenText
import com.example.ui.theme.RadiantGreenPrimary
import com.example.ui.theme.SurfaceBorderGreen
import com.example.ui.theme.TextMutedGreen
import com.example.ui.theme.TextWhitePrimary

private const val DESKTOP_USER_AGENT =
    "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36"

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("SetJavaScriptEnabled")
@Composable
fun WebReaderScreen(
    book: BookEntity,
    lang: AppLanguage = AppLanguage.URDU,
    onClose: () -> Unit,
    onToggleFavorite: (BookEntity) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var webViewInstance by remember { mutableStateOf<WebView?>(null) }
    var canGoBack by remember { mutableStateOf(false) }
    var canGoForward by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(true) }
    var loadProgress by remember { mutableIntStateOf(0) }
    var hasError by remember { mutableStateOf(false) }
    var isFullscreen by remember { mutableStateOf(false) }
    var isDesktopMode by remember { mutableStateOf(false) }
    var textZoomScale by remember { mutableFloatStateOf(100f) }
    var reloadTrigger by remember { mutableIntStateOf(0) }

    val localizedCategory = Strings.localizedCategoryName(book.category, lang)

    // Intercept back button to navigate WebView back stack first
    BackHandler {
        if (isFullscreen) {
            isFullscreen = false
        } else if (webViewInstance?.canGoBack() == true) {
            webViewInstance?.goBack()
        } else {
            onClose()
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            try {
                webViewInstance?.stopLoading()
                webViewInstance?.destroy()
            } catch (e: Exception) {
                // Ignore cleanup error
            }
        }
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            if (!isFullscreen) {
                Column {
                    TopAppBar(
                        title = {
                            Column {
                                Text(
                                    text = book.title,
                                    fontFamily = AmiriFontFamily,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 19.sp,
                                    color = LuminousGreenText,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                                Text(
                                    text = if (book.author.isNotBlank()) "$localizedCategory • ${book.author}" else localizedCategory,
                                    fontFamily = if (lang == AppLanguage.URDU) JameelNooriFontFamily else AmiriFontFamily,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = TextMutedGreen,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                        },
                        navigationIcon = {
                            IconButton(
                                onClick = {
                                    if (webViewInstance?.canGoBack() == true) {
                                        webViewInstance?.goBack()
                                    } else {
                                        onClose()
                                    }
                                },
                                modifier = Modifier.testTag("reader_back_button")
                            ) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                    contentDescription = "Back",
                                    tint = RadiantGreenPrimary
                                )
                            }
                        },
                        actions = {
                            // Zoom Out
                            IconButton(
                                onClick = {
                                    val newZoom = (textZoomScale - 15f).coerceAtLeast(70f)
                                    textZoomScale = newZoom
                                    webViewInstance?.settings?.textZoom = newZoom.toInt()
                                },
                                modifier = Modifier.testTag("reader_zoom_out_button")
                            ) {
                                Icon(Icons.Default.ZoomOut, contentDescription = "Zoom Out", tint = TextWhitePrimary)
                            }

                            // Zoom In
                            IconButton(
                                onClick = {
                                    val newZoom = (textZoomScale + 15f).coerceAtMost(220f)
                                    textZoomScale = newZoom
                                    webViewInstance?.settings?.textZoom = newZoom.toInt()
                                },
                                modifier = Modifier.testTag("reader_zoom_in_button")
                            ) {
                                Icon(Icons.Default.ZoomIn, contentDescription = "Zoom In", tint = TextWhitePrimary)
                            }

                            // Fullscreen Toggle
                            IconButton(
                                onClick = { isFullscreen = true },
                                modifier = Modifier.testTag("reader_fullscreen_button")
                            ) {
                                Icon(Icons.Default.Fullscreen, contentDescription = "Fullscreen", tint = TextWhitePrimary)
                            }

                            // Favorite Toggle
                            IconButton(
                                onClick = { onToggleFavorite(book) },
                                modifier = Modifier.testTag("reader_favorite_button")
                            ) {
                                Icon(
                                    imageVector = if (book.isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                                    contentDescription = "Favorite",
                                    tint = if (book.isFavorite) RadiantGreenPrimary else TextWhitePrimary
                                )
                            }

                            // Close to library
                            IconButton(
                                onClick = onClose,
                                modifier = Modifier.testTag("reader_close_button")
                            ) {
                                Icon(Icons.Default.Close, contentDescription = "Close Reader", tint = TextWhitePrimary)
                            }
                        },
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = DeepBlackBackground
                        )
                    )

                    // Loading Progress Indicator
                    if (isLoading) {
                        LinearProgressIndicator(
                            progress = { loadProgress / 100f },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(3.dp),
                            color = RadiantGreenPrimary,
                            trackColor = Color(0x3514261C)
                        )
                    }
                }
            }
        },
        bottomBar = {
            if (!isFullscreen) {
                Surface(
                    tonalElevation = 3.dp,
                    color = DeepBlackBackground,
                    border = BorderStroke(1.dp, SurfaceBorderGreen)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp, vertical = 4.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            IconButton(
                                onClick = { webViewInstance?.goBack() },
                                enabled = canGoBack,
                                modifier = Modifier.testTag("nav_back_button")
                            ) {
                                Icon(
                                    Icons.AutoMirrored.Filled.ArrowBack,
                                    contentDescription = "Previous Page",
                                    tint = if (canGoBack) RadiantGreenPrimary else TextMutedGreen.copy(alpha = 0.4f)
                                )
                            }

                            IconButton(
                                onClick = { webViewInstance?.goForward() },
                                enabled = canGoForward,
                                modifier = Modifier.testTag("nav_forward_button")
                            ) {
                                Icon(
                                    Icons.AutoMirrored.Filled.ArrowForward,
                                    contentDescription = "Next Page",
                                    tint = if (canGoForward) RadiantGreenPrimary else TextMutedGreen.copy(alpha = 0.4f)
                                )
                            }

                            IconButton(
                                onClick = {
                                    hasError = false
                                    webViewInstance?.reload()
                                },
                                modifier = Modifier.testTag("nav_refresh_button")
                            ) {
                                Icon(Icons.Default.Refresh, contentDescription = "Refresh", tint = RadiantGreenPrimary)
                            }
                        }

                        // Text Zoom Percentage Display
                        Text(
                            text = "${textZoomScale.toInt()}%",
                            style = MaterialTheme.typography.labelSmall,
                            color = TextMutedGreen
                        )

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            // Desktop / Mobile Toggle
                            IconButton(
                                onClick = {
                                    val newDesktop = !isDesktopMode
                                    isDesktopMode = newDesktop
                                    webViewInstance?.let { wv ->
                                        if (newDesktop) {
                                            wv.settings.userAgentString = DESKTOP_USER_AGENT
                                        } else {
                                            wv.settings.userAgentString = null
                                        }
                                        wv.reload()
                                    }
                                },
                                modifier = Modifier.testTag("toggle_desktop_button")
                            ) {
                                Icon(
                                    imageVector = if (isDesktopMode) Icons.Default.Laptop else Icons.Default.PhoneAndroid,
                                    contentDescription = if (isDesktopMode) "Desktop View" else "Mobile View",
                                    tint = if (isDesktopMode) RadiantGreenPrimary else TextMutedGreen
                                )
                            }

                            // Copy Link
                            IconButton(
                                onClick = {
                                    val currentUrl = webViewInstance?.url ?: book.url
                                    val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                                    val clip = ClipData.newPlainText("Book URL", currentUrl)
                                    clipboard.setPrimaryClip(clip)
                                    Toast.makeText(context, Strings.linkCopied(lang), Toast.LENGTH_SHORT).show()
                                },
                                modifier = Modifier.testTag("copy_link_button")
                            ) {
                                Icon(Icons.Default.ContentCopy, contentDescription = "Copy Link", tint = RadiantGreenPrimary)
                            }

                            // Share Link
                            IconButton(
                                onClick = {
                                    val currentUrl = webViewInstance?.url ?: book.url
                                    val sendIntent = Intent().apply {
                                        action = Intent.ACTION_SEND
                                        putExtra(Intent.EXTRA_TEXT, "${book.title}\n$currentUrl")
                                        type = "text/plain"
                                    }
                                    context.startActivity(Intent.createChooser(sendIntent, book.title))
                                },
                                modifier = Modifier.testTag("share_link_button")
                            ) {
                                Icon(Icons.Default.Share, contentDescription = "Share", tint = RadiantGreenPrimary)
                            }

                            // Open in External Browser
                            IconButton(
                                onClick = {
                                    val currentUrl = webViewInstance?.url ?: book.url
                                    try {
                                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(currentUrl))
                                        context.startActivity(intent)
                                    } catch (e: Exception) {
                                        Toast.makeText(context, "Could not open browser", Toast.LENGTH_SHORT).show()
                                    }
                                },
                                modifier = Modifier.testTag("open_external_browser_button")
                            ) {
                                Icon(Icons.AutoMirrored.Filled.OpenInNew, contentDescription = "Open in Browser", tint = RadiantGreenPrimary)
                            }
                        }
                    }
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(DeepBlackBackground)
        ) {
            key(reloadTrigger) {
                AndroidView(
                    modifier = Modifier
                        .fillMaxSize()
                        .testTag("shamila_webview"),
                    factory = { ctx ->
                        WebView(ctx).apply {
                            layoutParams = ViewGroup.LayoutParams(
                                ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.MATCH_PARENT
                            )

                            settings.apply {
                                javaScriptEnabled = true
                                domStorageEnabled = true
                                builtInZoomControls = true
                                displayZoomControls = false
                                useWideViewPort = true
                                loadWithOverviewMode = true
                                databaseEnabled = true
                                cacheMode = WebSettings.LOAD_DEFAULT
                                mixedContentMode = WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE
                                mediaPlaybackRequiresUserGesture = true
                                setGeolocationEnabled(false)
                                textZoom = textZoomScale.toInt()
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                    safeBrowsingEnabled = false
                                }
                            }

                            webViewClient = object : WebViewClient() {
                                override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                                    super.onPageStarted(view, url, favicon)
                                    isLoading = true
                                    hasError = false
                                    canGoBack = view?.canGoBack() == true
                                    canGoForward = view?.canGoForward() == true
                                }

                                override fun onPageFinished(view: WebView?, url: String?) {
                                    super.onPageFinished(view, url)
                                    isLoading = false
                                    canGoBack = view?.canGoBack() == true
                                    canGoForward = view?.canGoForward() == true
                                }

                                override fun onReceivedError(
                                    view: WebView?,
                                    request: WebResourceRequest?,
                                    error: WebResourceError?
                                ) {
                                    super.onReceivedError(view, request, error)
                                    if (request?.isForMainFrame == true) {
                                        hasError = true
                                        isLoading = false
                                    }
                                }

                                // Critical fix: Prevent app termination on Chromium renderer crash
                                override fun onRenderProcessGone(
                                    view: WebView?,
                                    detail: RenderProcessGoneDetail?
                                ): Boolean {
                                    hasError = true
                                    isLoading = false
                                    try {
                                        (view?.parent as? ViewGroup)?.removeView(view)
                                        view?.destroy()
                                    } catch (e: Exception) {
                                        // Ignore
                                    }
                                    webViewInstance = null
                                    return true // Handled: OS will NOT terminate app
                                }
                            }

                            webChromeClient = object : WebChromeClient() {
                                override fun onProgressChanged(view: WebView?, newProgress: Int) {
                                    super.onProgressChanged(view, newProgress)
                                    loadProgress = newProgress
                                    if (newProgress >= 100) {
                                        isLoading = false
                                    }
                                }
                            }

                            loadUrl(book.url)
                            webViewInstance = this
                        }
                    },
                    update = { webView ->
                        webViewInstance = webView
                    }
                )
            }

            // Floating exit button in fullscreen mode
            AnimatedVisibility(
                visible = isFullscreen,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(16.dp)
            ) {
                FilledIconButton(
                    onClick = { isFullscreen = false },
                    colors = IconButtonDefaults.filledIconButtonColors(
                        containerColor = DeepBlackBackground.copy(alpha = 0.85f),
                        contentColor = TextWhitePrimary
                    ),
                    modifier = Modifier.testTag("exit_fullscreen_button")
                ) {
                    Icon(
                        imageVector = Icons.Default.FullscreenExit,
                        contentDescription = "Exit Fullscreen"
                    )
                }
            }

            // Error Overlay if connection fails or renderer crashes
            if (hasError) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(DeepBlackBackground.copy(alpha = 0.95f))
                        .padding(24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = Color(0xF209140F)
                        ),
                        border = BorderStroke(1.dp, Color(0x6034D399)),
                        shape = RoundedCornerShape(24.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(24.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Warning,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.error,
                                modifier = Modifier.size(48.dp)
                            )
                            Spacer(modifier = Modifier.height(14.dp))
                            Text(
                                text = Strings.errorLoading(lang),
                                fontFamily = if (lang == AppLanguage.URDU) JameelNooriFontFamily else AmiriFontFamily,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = TextWhitePrimary
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = book.url,
                                style = MaterialTheme.typography.bodySmall,
                                color = TextMutedGreen,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                            Spacer(modifier = Modifier.height(18.dp))
                            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                                Button(
                                    onClick = {
                                        hasError = false
                                        reloadTrigger++
                                    },
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = RadiantGreenPrimary,
                                        contentColor = Color(0xFF03190C)
                                    ),
                                    shape = RoundedCornerShape(20.dp),
                                    modifier = Modifier.testTag("retry_web_button")
                                ) {
                                    Text(
                                        text = Strings.retryAction(lang),
                                        fontFamily = if (lang == AppLanguage.URDU) JameelNooriFontFamily else AmiriFontFamily,
                                        fontWeight = FontWeight.Bold
                                    )
                                }

                                OutlinedButton(
                                    onClick = {
                                        try {
                                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(book.url))
                                            context.startActivity(intent)
                                        } catch (e: Exception) {
                                            // Handle gracefully
                                        }
                                    },
                                    colors = ButtonDefaults.outlinedButtonColors(
                                        contentColor = TextWhitePrimary
                                    ),
                                    border = BorderStroke(1.dp, Color(0x6034D399)),
                                    shape = RoundedCornerShape(20.dp),
                                    modifier = Modifier.testTag("open_in_browser_error_button")
                                ) {
                                    Text(
                                        text = Strings.openBrowser(lang),
                                        fontFamily = if (lang == AppLanguage.URDU) JameelNooriFontFamily else AmiriFontFamily
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
