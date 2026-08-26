package com.example.data.repository

import android.content.Context
import com.example.data.local.AppDatabase
import com.example.data.local.InitialData
import com.example.data.local.entities.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class VideoTubeRepository(context: Context) {
    private val database = AppDatabase.getDatabase(context)
    private val videoDao = database.videoDao()
    private val commentDao = database.commentDao()
    private val postDao = database.postDao()
    private val channelDao = database.channelDao()
    private val historyDao = database.historyDao()

    val longVideos: Flow<List<VideoEntity>> = videoDao.getLongVideos()
    val shortVideos: Flow<List<VideoEntity>> = videoDao.getShortVideos()
    val allVideos: Flow<List<VideoEntity>> = videoDao.getAllVideos()
    val likedVideos: Flow<List<VideoEntity>> = videoDao.getLikedVideos()
    val watchLaterVideos: Flow<List<VideoEntity>> = videoDao.getWatchLaterVideos()
    val userUploadedVideos: Flow<List<VideoEntity>> = videoDao.getUserUploadedVideos()
    val subscribedChannels: Flow<List<ChannelEntity>> = channelDao.getSubscribedChannels()
    val allChannels: Flow<List<ChannelEntity>> = channelDao.getAllChannels()
    val allPosts: Flow<List<PostEntity>> = postDao.getAllPosts()
    val recentHistory: Flow<List<VideoEntity>> = historyDao.getRecentHistory()

    suspend fun seedDatabaseIfEmpty() = withContext(Dispatchers.IO) {
        if (videoDao.getVideosCount() == 0) {
            channelDao.insertChannels(InitialData.initialChannels)
            videoDao.insertVideos(InitialData.initialVideos)
            commentDao.insertComments(InitialData.initialComments)
            postDao.insertPosts(InitialData.initialPosts)
        }
    }

    suspend fun getVideoById(id: Long): VideoEntity? = withContext(Dispatchers.IO) {
        videoDao.getVideoById(id)
    }

    fun getVideosByChannel(channelName: String): Flow<List<VideoEntity>> =
        videoDao.getVideosByChannel(channelName)

    fun searchVideos(query: String): Flow<List<VideoEntity>> =
        videoDao.searchVideos(query)

    fun getCommentsForTarget(videoId: Long): Flow<List<CommentEntity>> =
        commentDao.getCommentsForVideo(videoId)

    suspend fun addComment(videoId: Long, text: String, author: String) = withContext(Dispatchers.IO) {
        val comment = CommentEntity(
            videoId = videoId,
            authorName = author,
            authorAvatarColor = 0xFF673AB7,
            authorAvatarInitial = author.take(1).uppercase(),
            text = text,
            timestamp = "Just now"
        )
        commentDao.insertComment(comment)
    }

    suspend fun toggleLikeVideo(video: VideoEntity) = withContext(Dispatchers.IO) {
        val newLiked = !video.isLiked
        val newLikesCount = if (newLiked) video.likesCount + 1 else (video.likesCount - 1).coerceAtLeast(0)
        videoDao.updateVideo(
            video.copy(
                isLiked = newLiked,
                likesCount = newLikesCount,
                isDisliked = if (newLiked) false else video.isDisliked
            )
        )
    }

    suspend fun toggleDislikeVideo(video: VideoEntity) = withContext(Dispatchers.IO) {
        val newDisliked = !video.isDisliked
        videoDao.updateVideo(
            video.copy(
                isDisliked = newDisliked,
                isLiked = if (newDisliked) false else video.isLiked,
                likesCount = if (newDisliked && video.isLiked) (video.likesCount - 1).coerceAtLeast(0) else video.likesCount
            )
        )
    }

    suspend fun toggleSubscribeChannel(channelName: String) = withContext(Dispatchers.IO) {
        val channel = channelDao.getChannelByName(channelName)
        if (channel != null) {
            channelDao.updateChannel(channel.copy(isSubscribed = !channel.isSubscribed))
        }
    }

    suspend fun recordWatchHistory(videoId: Long) = withContext(Dispatchers.IO) {
        historyDao.recordHistory(HistoryEntity(videoId = videoId))
    }

    suspend fun clearWatchHistory() = withContext(Dispatchers.IO) {
        historyDao.clearHistory()
    }

    suspend fun uploadVideo(
        title: String,
        description: String,
        duration: String,
        durationSeconds: Int,
        category: String,
        videoType: VideoType,
        gradientStart: Long,
        gradientEnd: Long,
        iconName: String,
        tags: String
    ): Long = withContext(Dispatchers.IO) {
        val video = VideoEntity(
            title = title,
            description = description,
            duration = duration,
            durationSeconds = durationSeconds,
            viewCount = "1 view",
            viewsRaw = 1,
            uploadTime = "Just now",
            channelName = "You",
            channelSubscribers = "1 Sub",
            channelAvatarColor = 0xFF6200EE,
            channelAvatarInitial = "Y",
            thumbnailGradientStart = gradientStart,
            thumbnailGradientEnd = gradientEnd,
            thumbnailIconName = iconName,
            videoType = videoType,
            category = category,
            isUserUploaded = true,
            tags = tags
        )
        videoDao.insertVideo(video)
    }
}
