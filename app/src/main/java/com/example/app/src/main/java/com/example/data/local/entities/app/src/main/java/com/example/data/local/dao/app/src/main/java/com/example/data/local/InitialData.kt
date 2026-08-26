package com.example.data.local

import com.example.data.local.entities.ChannelEntity
import com.example.data.local.entities.CommentEntity
import com.example.data.local.entities.PostEntity
import com.example.data.local.entities.VideoEntity
import com.example.data.local.entities.VideoType

object InitialData {
    val initialChannels = listOf(
        ChannelEntity(
            name = "Ceylon Beats",
            handle = "@ceylonbeats",
            subscribersCount = "450K",
            avatarColor = 0xFFFF5722,
            avatarInitial = "CB",
            bannerGradientStart = 0xFFFF5722,
            bannerGradientEnd = 0xFFFF9800,
            bio = "Modern Sri Lankan Pop, Baila & Cinematic Beats. Official releases and lyric videos.",
            isSubscribed = true,
            videoCount = 84
        ),
        ChannelEntity(
            name = "Lanka Nomads",
            handle = "@lankanomads",
            subscribersCount = "890K",
            avatarColor = 0xFF4CAF50,
            avatarInitial = "LN",
            bannerGradientStart = 0xFF1B5E20,
            bannerGradientEnd = 0xFF4CAF50,
            bio = "Exploring hidden waterfalls, misty Ella peaks, and southern surf breaks across Sri Lanka.",
            isSubscribed = true,
            videoCount = 142
        ),
        ChannelEntity(
            name = "Spice Island Kitchen",
            handle = "@spiceisland",
            subscribersCount = "620K",
            avatarColor = 0xFFE91E63,
            avatarInitial = "SI",
            bannerGradientStart = 0xFF880E4F,
            bannerGradientEnd = 0xFFE91E63,
            bio = "Authentic Sri Lankan recipes made easy. Black pork curry, Kottu, Hoppers & more.",
            isSubscribed = false,
            videoCount = 95
        ),
        ChannelEntity(
            name = "Tech Sinhala",
            handle = "@techsinhala",
            subscribersCount = "1.2M",
            avatarColor = 0xFF2196F3,
            avatarInitial = "TS",
            bannerGradientStart = 0xFF0D47A1,
            bannerGradientEnd = 0xFF2196F3,
            bio = "Smartphone reviews, AI tech breakdowns & gadget unboxings in Sinhala.",
            isSubscribed = true,
            videoCount = 310
        ),
        ChannelEntity(
            name = "Ella Wanderer",
            handle = "@ellawanderer",
            subscribersCount = "310K",
            avatarColor = 0xFF9C27B0,
            avatarInitial = "EW",
            bannerGradientStart = 0xFF4A148C,
            bannerGradientEnd = 0xFF9C27B0,
            bio = "Solo backpacking and slow living in the central highlands.",
            isSubscribed = false,
            videoCount = 68
        )
    )

    val initialVideos = listOf(
        VideoEntity(
            title = "Ella to Kandy Scenic Blue Train Ride | 4K Ultra HD Full Journey",
            description = "Experience the world-famous blue train journey passing Nine Arches Bridge, tea plantations, and misty tunnels.",
            duration = "18:42",
            durationSeconds = 1122,
            viewCount = "1.8M",
            viewsRaw = 1840000,
            uploadTime = "3 days ago",
            channelName = "Lanka Nomads",
            channelSubscribers = "890K",
            channelAvatarColor = 0xFF4CAF50,
            channelAvatarInitial = "LN",
            thumbnailGradientStart = 0xFF0D47A1,
            thumbnailGradientEnd = 0xFF00E5FF,
            thumbnailIconName = "train",
            videoType = VideoType.LONG,
            category = "Travel",
            likesCount = 94200,
            isLiked = true,
            isSubscribed = true,
            tags = "train, ella, kandy, travel, 4k"
        ),
        VideoEntity(
            title = "Authentic Sri Lankan Chicken Curry with Roast Paan & Pol Sambol Recipe",
            description = "Step-by-step masterclass on brewing the ultimate roasted curry powder and crispy baker's roast bread.",
            duration = "14:15",
            durationSeconds = 855,
            viewCount = "920K",
            viewsRaw = 920000,
            uploadTime = "1 week ago",
            channelName = "Spice Island Kitchen",
            channelSubscribers = "620K",
            channelAvatarColor = 0xFFE91E63,
            channelAvatarInitial = "SI",
            thumbnailGradientStart = 0xFFD50000,
            thumbnailGradientEnd = 0xFFFF6D00,
            thumbnailIconName = "food",
            videoType = VideoType.LONG,
            category = "Food",
            likesCount = 48100,
            isLiked = false,
            isSubscribed = false,
            tags = "curry, food, cooking, recipe, chicken"
        ),
        VideoEntity(
            title = "Midnight Colombo Street Kottu Roti ASMR (Sound of Chopping Blades)",
            description = "Crispy godamba roti chopped to perfection on the hot tawa in Hulftsdorp Colombo.",
            duration = "0:58",
            durationSeconds = 58,
            viewCount = "3.4M",
            viewsRaw = 3400000,
            uploadTime = "5 days ago",
            channelName = "Ceylon Beats",
            channelSubscribers = "450K",
            channelAvatarColor = 0xFFFF5722,
            channelAvatarInitial = "CB",
            thumbnailGradientStart = 0xFFFF3D00,
            thumbnailGradientEnd = 0xFFFFD600,
            thumbnailIconName = "music",
            videoType = VideoType.SHORT,
            soundTitle = "Colombo Night Live Beats - Original Audio",
            category = "Shorts",
            likesCount = 280000,
            isLiked = true,
            tags = "kottu, asmr, food, streetfood"
        ),
        VideoEntity(
            title = "Sunset Drone at Mirissa Coconut Tree Hill 🌴",
            description = "Golden hour vibes over the Indian Ocean waves.",
            duration = "0:34",
            durationSeconds = 34,
            viewCount = "1.2M",
            viewsRaw = 1200000,
            uploadTime = "2 weeks ago",
            channelName = "Ella Wanderer",
            channelSubscribers = "310K",
            channelAvatarColor = 0xFF9C27B0,
            channelAvatarInitial = "EW",
            thumbnailGradientStart = 0xFF6200EA,
            thumbnailGradientEnd = 0xFFFF4081,
            thumbnailIconName = "beach",
            videoType = VideoType.SHORT,
            soundTitle = "Chill Lofi Ceylon Vibes",
            category = "Shorts",
            likesCount = 145000,
            isLiked = false,
            tags = "mirissa, drone, ocean, sunset"
        )
    )

    val initialComments = listOf(
        CommentEntity(
            videoId = 1,
            authorName = "Kasun Perera",
            authorAvatarColor = 0xFF3F51B5,
            authorAvatarInitial = "KP",
            text = "The view at 12:45 passing through the mist was absolutely breathtaking! Pure magic.",
            timestamp = "2 days ago",
            likesCount = 342,
            isLiked = true,
            isHeartedByCreator = true
        ),
        CommentEntity(
            videoId = 1,
            authorName = "Sarah Jenkins",
            authorAvatarColor = 0xFFFF9800,
            authorAvatarInitial = "SJ",
            text = "Visiting Sri Lanka next month and this train journey is #1 on my itinerary!",
            timestamp = "1 day ago",
            likesCount = 89,
            isLiked = false,
            isHeartedByCreator = false
        )
    )

    val initialPosts = listOf(
        PostEntity(
            authorName = "Lanka Nomads",
            authorAvatarColor = 0xFF4CAF50,
            authorAvatarInitial = "LN",
            timeAgo = "1 day ago",
            contentText = "Where should our next 4K travel documentary be filmed? Vote below! 🗺️",
            likesCount = 1240,
            commentsCount = 84,
            pollQuestion = "Next Travel Location?",
            pollOption1 = "Jaffna & Delft Island 🏝️",
            pollOption2 = "Knuckles Mountain Range ⛰️",
            pollVotes1 = 640,
            pollVotes2 = 820,
            selectedPollOption = null
        )
    )
}
