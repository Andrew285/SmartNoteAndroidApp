package com.example.smartnoteapp.videos.data.sources

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.smartnoteapp.videos.domain.models.Video
import com.google.firebase.firestore.Query
import kotlinx.coroutines.tasks.await

class VideoPagingSource(
    private val query: Query
): PagingSource<Int, Video>() {

    override fun getRefreshKey(state: PagingState<Int, Video>): Int? {
        return state.anchorPosition?.let { anchor ->
            state.closestPageToPosition(anchor)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchor)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Video> {
        return try {
            val currentPage = params.key ?: 1
            val limit = params.loadSize

            val snapshot = query
                .get()
                .await()

            val videos = snapshot.documents.mapNotNull {
                it.toObject(Video::class.java)
            }

            val nextPage = if (videos.size < limit) null else currentPage + 1
            val prevPage = if (currentPage == 1) null else currentPage - 1

            LoadResult.Page(
                data = videos,
                prevKey = prevPage,
                nextKey = nextPage
            )

        } catch (e: Exception) {
            Log.e("FIREBASE_ERROR", e.message.toString())
            LoadResult.Error(e)
        }
    }

}