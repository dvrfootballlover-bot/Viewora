package com.example.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.local.entities.*
import com.example.data.repository.VideoTubeRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

enum class MainTab { HOME, SHORTS, SUBSCRIPTIONS, YOU }
enum class ActiveScreen { FEED, VIDEO_DETAIL, CHANNEL_PROFILE, UPLOAD_STUDIO }

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = VideoTubeRepository(application)

    private val _currentTab = MutableStateFlow(MainTab.HOME)
    val currentTab: StateFlow<MainTab> = _currentTab.asStateFlow()

    private val _activeScreen = MutableStateFlow(ActiveScreen.FEED)
    val activeScreen: StateFlow<ActiveScreen> = _activeScreen.asStateFlow()

    private val _selectedCategory = MutableStateFlow("All")
    val selectedCategory: StateFlow<String> = _selectedCategory.asStateFlow()

    private val _isSearching = MutableStateFlow(false)
    val isSearching: StateFlow<Boolean> = _isSearching.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _selectedVideo = MutableStateFlow<VideoEntity?>(null)
    val selectedVideo: StateFlow<VideoEntity?> = _selectedVideo.asStateFlow()

    private val _selectedChannelName = MutableStateFlow<String?>(null)
    val selectedChannelName: StateFlow<String?> = _selectedChannelName.asStateFlow()

    private val _isCommentsSheetOpen = MutableStateFlow(false)
    val isCommentsSheetOpen: StateFlow<Boolean> = _isCommentsSheetOpen.asStateFlow()

    private val _commentsForSelectedVideo = MutableStateFlow<List<CommentEntity>>(emptyList())
    val commentsForSelectedVideo: StateFlow<List<CommentEntity>> = _commentsForSelectedVideo.asStateFlow()

    private val _isPlaying = MutableStateFlow(true)
    val isPlaying: StateFlow<Boolean> = _isPlaying.asStateFlow()

    private val _playbackProgress = MutableStateFlow(0f)
    val playbackProgress: StateFlow<Float> = _playbackProgress.asStateFlow()

    private val _snackbarMessage = MutableStateFlow<String?>(null)
    val snackbarMessage: StateFlow<String?> = _snackbarMessage.asStateFlow()

    val longVideos: StateFlow<List<VideoEntity>> = repository.longVideos
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val shortVideos: StateFlow<List<VideoEntity>> = repository.shortVideos
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val subscribedChannels: StateFlow<List<ChannelEntity>> = repository.subscribedChannels
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val allPosts: StateFlow<List<PostEntity>> = repository.allPosts
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val recentHistory: StateFlow<List<VideoEntity>> = repository.recentHistory
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val likedVideos: StateFlow<List<VideoEntity>> = repository.likedVideos
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val userUploadedVideos: StateFlow<List<VideoEntity>> = repository.userUploadedVideos
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val searchResults: StateFlow<List<VideoEntity>> = _searchQuery
        .debounce(300)
        .flatMapLatest { query ->
            if (query.isBlank()) flowOf(emptyList())
            else repository.searchVideos(query)
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    init {
        viewModelScope.launch {
            repository.seedDatabaseIfEmpty()
        }
    }

    fun selectTab(tab: MainTab) {
        _currentTab.value = tab
        _activeScreen.value = ActiveScreen.FEED
        _isSearching.value = false
    }

    fun selectCategory(category: String) {
        _selectedCategory.value = category
    }

    fun openVideoDetail(video: VideoEntity) {
        _selectedVideo.value = video
        _activeScreen.value = ActiveScreen.VIDEO_DETAIL
        _isPlaying.value = true
        _playbackProgress.value = 0.05f
        viewModelScope.launch {
            repository.recordWatchHistory(video.id)
            repository.getCommentsForTarget(video.id).collect {
                _commentsForSelectedVideo.value = it
            }
        }
    }

    fun closeVideoDetail() {
        _activeScreen.value = ActiveScreen.FEED
        _selectedVideo.value = null
        _isPlaying.value = false
    }

    fun openChannel(channelName: String) {
        _selectedChannelName.value = channelName
        _activeScreen.value = ActiveScreen.CHANNEL_PROFILE
    }

    fun closeChannel() {
        if (_selectedVideo.value != null) {
            _activeScreen.value = ActiveScreen.VIDEO_DETAIL
        } else {
            _activeScreen.value = ActiveScreen.FEED
        }
    }

    fun openUploadStudio() {
        _activeScreen.value = ActiveScreen.UPLOAD_STUDIO
    }

    fun closeUploadStudio() {
        _activeScreen.value = ActiveScreen.FEED
    }

    fun openSearch() {
        _isSearching.value = true
    }

    fun closeSearch() {
        _isSearching.value = false
        _searchQuery.value = ""
    }

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun openComments() {
        _isCommentsSheetOpen.value = true
    }

    fun closeComments() {
        _isCommentsSheetOpen.value = false
    }

    fun togglePlayPause() {
        _isPlaying.value = !_isPlaying.value
    }

    fun seekTo(progress: Float) {
        _playbackProgress.value = progress
    }

    fun toggleLikeVideo(video: VideoEntity) {
        viewModelScope.launch {
            repository.toggleLikeVideo(video)
            if (_selectedVideo.value?.id == video.id) {
                _selectedVideo.value = repository.getVideoById(video.id)
            }
        }
    }

    fun toggleDislikeVideo(video: VideoEntity) {
        viewModelScope.launch {
            repository.toggleDislikeVideo(video)
            if (_selectedVideo.value?.id == video.id) {
                _selectedVideo.value = repository.getVideoById(video.id)
            }
        }
    }

    fun toggleSubscribe(channelName: String) {
        viewModelScope.launch {
            repository.toggleSubscribeChannel(channelName)
            _snackbarMessage.value = "Subscription updated"
        }
    }

    fun addComment(text: String) {
        val video = _selectedVideo.value ?: return
        if (text.isBlank()) return
        viewModelScope.launch {
            repository.addComment(video.id, text, "You")
            _snackbarMessage.value = "Comment added"
        }
    }

    fun clearHistory() {
        viewModelScope.launch {
            repository.clearWatchHistory()
            _snackbarMessage.value = "History cleared"
        }
    }

    fun uploadVideo(
        title: String,
        description: String,
        category: String,
        isShort: Boolean,
        gradientStart: Long,
        gradientEnd: Long
    ) {
        viewModelScope.launch {
            repository.uploadVideo(
                title = title,
                description = description,
                duration = if (isShort) "0:45" else "04:12",
                durationSeconds = if (isShort) 45 else 252,
                category = category,
                videoType = if (isShort) VideoType.SHORT else VideoType.LONG,
                gradientStart = gradientStart,
                gradientEnd = gradientEnd,
                iconName = if (isShort) "shorts" else "video",
                tags = "user, uploaded"
            )
            _snackbarMessage.value = "Video published successfully!"
            closeUploadStudio()
        }
    }

    fun showNotificationToast() {
        _snackbarMessage.value = "No new notifications"
    }

    fun clearSnackbar() {
        _snackbarMessage.value = null
    }
}
