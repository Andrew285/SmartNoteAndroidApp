package com.example.smartnoteapp.videos.domain.repository

import androidx.paging.Pager
import com.example.smartnoteapp.videos.domain.models.Video

interface IVideoRepository {
    suspend fun getVideosPagingData(): Pager<Int, Video>
}