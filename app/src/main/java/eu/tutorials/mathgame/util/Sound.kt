package eu.tutorials.mathgame.util

import android.content.Context
import android.media.MediaPlayer

object Sound {
    private var mediaPlayer: MediaPlayer? = null

    fun playSound(context: Context, soundResId: Int) {
        mediaPlayer?.release()
        mediaPlayer = MediaPlayer.create(context, soundResId)
        mediaPlayer?.apply {
            start()
            setOnCompletionListener {
                it.release()
                mediaPlayer = null
            }
        }
    }
}
