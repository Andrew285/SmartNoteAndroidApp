package com.example.smartnoteapp.notes.data.sources.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.smartnoteapp.notes.data.models.converters.Converters.mapToNote
import com.example.smartnoteapp.notes.data.models.remote.NoteRemote
import com.example.smartnoteapp.notes.domain.models.Note
import com.google.firebase.firestore.Query
import kotlinx.coroutines.tasks.await

class NotesRemotePagingSource(
    private val query: Query
): PagingSource<Int, NoteRemote>() {

    override fun getRefreshKey(state: PagingState<Int, NoteRemote>): Int? {
        return state.anchorPosition?.let { anchor ->
            state.closestPageToPosition(anchor)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchor)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, NoteRemote> {
        return try {
            val currentPage = params.key ?: 1
            val limit = params.loadSize

            val snapshot = query
//                .limit(limit.toLong())
//                .startAt((currentPage - 1) * limit.toLong())
                .get()
                .await()

//            val nextPage = if (notes.isNotEmpty()) {
//                query.startAfter(currentPage.documents.last()).get().await()
//            } else null

            val notes = snapshot.documents.mapNotNull {
                it.toObject(NoteRemote::class.java)
//                noteRemote?.mapToNote()
            }

            val nextPage = if (notes.size < limit) null else currentPage + 1
            val prevPage = if (currentPage == 1) null else currentPage - 1

            LoadResult.Page(
                data = notes,
                prevKey = prevPage,
                nextKey = nextPage
            )

        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

}