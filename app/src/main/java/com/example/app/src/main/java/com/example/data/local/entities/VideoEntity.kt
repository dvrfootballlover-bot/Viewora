package com.example.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

enum class VideoType {
    LONG, SHORT
}

@Entity(tableName = "videos")
data class VideoEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val description: String,
    val duration: String,
    val durationSeconds: Int,
    val viewCount: String,
    val viewsRaw: Long,
    val uploadTime: String,
    val channelName: String,
    val channelSubscribers: String,
    val channelAvatarColor: Long,
    val channelAvatarInitial: String,
    val thumbnailGradientStart: Long,
    val thumbnailGradientEnd: Long,
    val thumbnailIconName: String,
    val videoType: VideoType,
    val soundTitle: String = "",
    val category: String = "All",
    val likesCount: Long = 0,
    val isLiked: Boolean = false,
    val isDisliked: Boolean = false,
    val isSubscribed: Boolean = false,
    val isWatchLater: Boolean = false,
    val isUserUploaded: Boolean = false,
    val createdAtTimestamp: Long = System.currentTimeMillis(),
    val tags: String = ""
)

@Entity(tableName = "comments")
data class CommentEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val videoId: Long,
    val authorName: String,
    val authorAvatarColor: Long,
    val authorAvatarInitial: String,
    val text: String,
    val timestamp: String,
    val likesCount: Int = 0,
    val isLiked: Boolean = false,
    val isHeartedByCreator: Boolean = false,
    val parentCommentId: Long? = null
)

@Entity(tableName = "posts")
data class PostEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val authorName: String,
    val authorAvatarColor: Long,
    val authorAvatarInitial: String,
    val timeAgo: String,
    val contentText: String,
    val likesCount: Int = 0,
    val isLiked: Boolean = false,
    val commentsCount: Int = 0,
    val pollQuestion: String? = null,
    val pollOption1: String? = null,
    val pollOption2: String? = null,
    val pollVotes1: Int = 0,
    val pollVotes2: Int = 0,
    val selectedPollOption: Int? = null,
    val bgGradientStart: Long? = null,
    val bgGradientEnd: Long? = null
)

@Entity(tableName = "channels")
data class ChannelEntity(
    @PrimaryKey
    val name: String,
    val handle: String,
    val subscribersCount: String,
    val avatarColor: Long,
    val avatarInitial: String,
    val bannerGradientStart: Long,
    val bannerGradientEnd: Long,
    val bio: String,
    val isSubscribed: Boolean = false,
    val videoCount: Int = 0
)

@Entity(tableName = "watch_history")
data class HistoryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val videoId: Long,
    val watchedAtTimestamp: Long = System.currentTimeMillis()
)
