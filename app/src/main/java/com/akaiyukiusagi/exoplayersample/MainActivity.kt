package com.akaiyukiusagi.exoplayersample

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var mediaPlayer: SimpleExoPlayer? = null
    private var playWhenReady: Boolean = true
    private var playbackPosition: Long = 1
    private var currentWindow: Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initPlayer()
    }

    override fun onStop() {
        super.onStop()
        releasePlayer()
    }

    private fun initPlayer() {
        val uri = Uri.parse("https://storage.googleapis.com/exoplayer-test-media-0/BigBuckBunny_320x180.mp4")
        val mediaSource = buildMediaSource(uri)

        mediaPlayer = SimpleExoPlayer.Builder(this).build()
                .apply {
            playWhenReady = playWhenReady
            seekTo(currentWindow, playbackPosition)
            prepare(mediaSource, false, false)
        }
        video_view.player = mediaPlayer
    }

    private fun releasePlayer() {
        mediaPlayer?.let {
            playWhenReady = it.playWhenReady
            playbackPosition = it.currentPosition
            currentWindow = it.currentWindowIndex
            it.release()
            mediaPlayer = null
        }
    }

    private fun buildMediaSource(uri: Uri): MediaSource {
        val dataSourceFactory = DefaultDataSourceFactory(this, "exoplayersample")
        return ProgressiveMediaSource.Factory(dataSourceFactory).createMediaSource(uri)
    }

}

// 出典：https://hirauchi-genta.com/kotlin-exoplayer/
// 出典：https://gumiossan.hatenablog.com/entry/2020/03/06/002802