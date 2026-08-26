package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.components.BottomNavBar
import com.example.ui.components.CommentsBottomSheet
import com.example.ui.components.VieworaTopAppBar
import com.example.ui.screens.ChannelScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.SearchScreen
import com.example.ui.screens.ShortsScreen
import com.example.ui.screens.SubscriptionsScreen
import com.example.ui.screens.UploadStudioScreen
import com.example.ui.screens.VideoDetailScreen
import com.example.ui.screens.YouScreen
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.viewmodel.ActiveScreen
import com.example.ui.viewmodel.MainTab
import com.example.ui.viewmodel.MainViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                VieworaApp()
            }
        }
    }
}

@Composable
fun VieworaApp(
    viewModel: MainViewModel = viewModel()
) {
    val currentTab by viewModel.currentTab.collectAsStateWithLifecycle()
    val activeScreen by viewModel.activeScreen.collectAsStateWithLifecycle()
    val isSearching by viewModel.isSearching.collectAsStateWithLifecycle()
    val isCommentsOpen by viewModel.isCommentsSheetOpen.collectAsStateWithLifecycle()
    val snackbarMessage by viewModel.snackbarMessage.collectAsStateWithLifecycle()
    
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(snackbarMessage) {
        snackbarMessage?.let {
            snackbarHostState.showSnackbar(it)
            viewModel.clearSnackbar()
        }
    }

    // Hardware back handler
    BackHandler(
        enabled = activeScreen != ActiveScreen.FEED || isSearching || isCommentsOpen
    ) {
        when {
            isCommentsOpen -> viewModel.closeComments()
            isSearching -> viewModel.closeSearch()
            activeScreen == ActiveScreen.VIDEO_DETAIL -> viewModel.closeVideoDetail()
            activeScreen == ActiveScreen.CHANNEL_PROFILE -> viewModel.closeChannel()
            activeScreen == ActiveScreen.UPLOAD_STUDIO -> viewModel.closeUploadStudio()
            currentTab != MainTab.HOME -> viewModel.selectTab(MainTab.HOME)
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            if (activeScreen == ActiveScreen.FEED && currentTab != MainTab.SHORTS && !isSearching) {
                VieworaTopAppBar(
                    onSearchClick = { viewModel.openSearch() },
                    onUploadClick = { viewModel.openUploadStudio() },
                    onNotificationClick = { viewModel.showNotificationToast() }
                )
            }
        },
        bottomBar = {
            if (activeScreen == ActiveScreen.FEED && !isSearching) {
                BottomNavBar(
                    currentTab = currentTab,
                    onTabSelected = { viewModel.selectTab(it) },
                    onAddClick = { viewModel.openUploadStudio() }
                )
            }
        },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(
                    top = if (activeScreen == ActiveScreen.FEED && currentTab != MainTab.SHORTS && !isSearching) innerPadding.calculateTopPadding() else androidx.compose.ui.unit.dp,
                    bottom = if (activeScreen == ActiveScreen.FEED && !isSearching) innerPadding.calculateBottomPadding() else androidx.compose.ui.unit.dp
                )
        ) {
            when (activeScreen) {
                ActiveScreen.FEED -> {
                    when (currentTab) {
                        MainTab.HOME -> HomeScreen(viewModel = viewModel)
                        MainTab.SHORTS -> ShortsScreen(viewModel = viewModel)
                        MainTab.SUBSCRIPTIONS -> SubscriptionsScreen(viewModel = viewModel)
                        MainTab.YOU -> YouScreen(viewModel = viewModel)
                    }
                }
                ActiveScreen.VIDEO_DETAIL -> {
                    VideoDetailScreen(viewModel = viewModel)
                }
                ActiveScreen.CHANNEL_PROFILE -> {
                    ChannelScreen(viewModel = viewModel)
                }
                ActiveScreen.UPLOAD_STUDIO -> {
                    UploadStudioScreen(viewModel = viewModel)
                }
            }

            // Search Overlay
            if (isSearching) {
                SearchScreen(viewModel = viewModel)
            }

            // Comments Bottom Sheet
            if (isCommentsOpen) {
                CommentsBottomSheet(viewModel = viewModel)
            }
        }
    }
}
