package com.example.smartnoteapp.videos.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSourceFactory
import com.example.smartnoteapp.videos.data.sources.VideoPagingSource
import com.example.smartnoteapp.videos.domain.models.Video
import com.example.smartnoteapp.videos.domain.repository.IVideoRepository
import com.example.smartnoteapp.videos.utils.VideoConstants
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class VideoRepository: IVideoRepository {
    private val firebase = Firebase.firestore
    private val videosCollection = firebase.collection(VideoConstants.FIREBASE_VIDEO_COLLECTION)

    override suspend fun getVideosPagingData(): Pager<Int, Video> {
        val query = videosCollection.orderBy("timestamp", Query.Direction.DESCENDING)
        return Pager(
            config = PagingConfig(10, 10, enablePlaceholders = false),
            pagingSourceFactory = PagingSourceFactory { VideoPagingSource(query) }
        )
    }
}