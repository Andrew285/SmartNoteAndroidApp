package com.example.smartnoteapp.videos.domain.usecases

import androidx.paging.Pager
import com.example.smartnoteapp.videos.domain.models.Video
import com.example.smartnoteapp.videos.domain.repository.IVideoRepository
import javax.inject.Inject

class GetVideosPagingUseCase @Inject constructor(
    private val videoRepository: IVideoRepository
) {
    suspend operator fun invoke(): Pager<Int, Video> {
        return videoRepository.getVideosPagingData()
    }
}