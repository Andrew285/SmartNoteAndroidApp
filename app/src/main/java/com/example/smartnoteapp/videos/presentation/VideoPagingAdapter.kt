package com.example.smartnoteapp.videos.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.smartnoteapp.R
import com.example.smartnoteapp.videos.domain.models.Video
import com.example.smartnoteapp.notes.presentation.custom_views.NoteActionButton
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.ui.PlayerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class VideoPagingAdapter: PagingDataAdapter<Video, VideoPagingAdapter.VideoViewHolder>(VideoDiffCallback) {

    private var currentExoPlayer: ExoPlayer? = null

    object VideoDiffCallback : DiffUtil.ItemCallback<Video>() {
        override fun areItemsTheSame(oldItem: Video, newItem: Video) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Video, newItem: Video) = oldItem == newItem
    }

    class VideoViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var player: ExoPlayer? = null

        val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        val descriptionTextView: TextView = itemView.findViewById(R.id.descriptionTextView)
        val videoPlayerView: PlayerView = itemView.findViewById(R.id.videoPlayerView)
        val likeActionBtn: NoteActionButton = itemView.findViewById(R.id.likeActionBtn)
        val commentActionBtn: NoteActionButton = itemView.findViewById(R.id.commentActionBtn)

        fun releasePlayer() {
            player?.release()
            player = null
        }
    }

    override fun onViewRecycled(holder: VideoViewHolder) {
        super.onViewRecycled(holder)
        holder.releasePlayer()
    }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        val currentVideo = getItem(position)

        with (holder) {
            currentVideo?.let { video ->
                titleTextView.text = video.title
                descriptionTextView.text = video.description
                likeActionBtn.text = video.likesCount.toString()
                commentActionBtn.text = video.commentsCount.toString()

                // Paste video
                player = ExoPlayer.Builder(holder.itemView.context).build()
                holder.videoPlayerView.player = player

                val mediaItem = MediaItem.fromUri(video.videoUrl)
                player?.setMediaItem(mediaItem)
                player?.prepare()

                if (adapterPosition == 0) {
                    player?.playWhenReady = true
                    currentExoPlayer = player
                }

                likeActionBtn.let { btn ->
                    btn.setOnClickListener {
                        CoroutineScope(Dispatchers.Main).launch {

                            var likesCountInt = btn.text.toInt()
                            likesCountInt += 1
                            btn.text = likesCountInt.toString()

                            TODO("Finish implementing like button listener")
                        }
                    }
                }

                commentActionBtn.setOnClickListener {
                    TODO("Implement comment button listener")
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_video_layout, parent, false)
        return VideoViewHolder(view)
    }
}