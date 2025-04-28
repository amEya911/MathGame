package eu.tutorials.mathgame.util

import android.content.Context
import android.media.MediaPlayer

object Sound {
    private var mediaPlayer: MediaPlayer? = null

    fun playSound(context: Context, soundResId: Int) {
        mediaPlayer?.let {
            try {
                it.stop()
                it.release()
            } catch (_: Exception) { }
            mediaPlayer = null
        }

        try {
            mediaPlayer = MediaPlayer.create(context, soundResId)
            mediaPlayer?.apply {
                setOnCompletionListener {
                    it.release()
                    if (mediaPlayer == it) mediaPlayer = null
                }
                start()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
