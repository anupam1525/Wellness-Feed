package `in`.wellness.wellnessfeed.adapter

import `in`.wellness.wellnessfeed.ImageTransitionActivity
import `in`.wellness.wellnessfeed.R
import `in`.wellness.wellnessfeed.constants.AppConstants
import `in`.wellness.wellnessfeed.model.FeedListModel
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.widget.*
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.recyclerview.widget.RecyclerView
import java.util.*
import java.util.concurrent.TimeUnit


class MultiViewTypeAdapter(data: ArrayList<FeedListModel>, context: Context) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val dataSet: ArrayList<FeedListModel> = data
    private var mContext: Context = context
    private var totalTypes: Int

    init {
        totalTypes = dataSet.size
    }

    class ImageTypeViewHolder(itemView: View, context: Context) :
        RecyclerView.ViewHolder(itemView) {

        var image: ImageView = itemView.findViewById(R.id.feedImage)

        init {
            image.setOnClickListener { view: View? ->

                val pairImage: Pair<View, String> =
                    Pair(image, context.getString(R.string.transition_container))
                val intent = Intent(context, ImageTransitionActivity::class.java)
                val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                    (context as Activity),
                    pairImage
                )
                context.startActivity(intent, options.toBundle())
            }
        }
    }

    class AudioTypeViewHolder(itemView: View, context: Context?) :
        RecyclerView.ViewHolder(itemView) {

        val seekBar: SeekBar = itemView.findViewById(R.id.seekbar)
        var playPause: ImageButton = itemView.findViewById(R.id.playPauseButton)
        val mediaPlayer: MediaPlayer = MediaPlayer.create(context, R.raw.meditation_music)
        var elapsedTime: TextView? = null
        var audioHandler = Handler()
        private var clickedPlay = false

        private val updateSongTime: Runnable = object : Runnable {

            override fun run() {
                sTime = mediaPlayer.currentPosition
                elapsedTime?.text = String.format(
                    Locale.US, "%d min, %d sec", TimeUnit.MILLISECONDS.toMinutes(sTime.toLong()),
                    TimeUnit.MILLISECONDS.toSeconds(sTime.toLong()) - TimeUnit.MINUTES.toSeconds(
                        TimeUnit.MILLISECONDS.toMinutes(sTime.toLong())
                    )
                )
                seekBar.progress = sTime
                audioHandler.postDelayed(this, 100)
            }
        }

        companion object {
            private var oTime: Int = 0
            private var sTime: Int = 0
            private var eTime: Int = 0
            var scaleDown: ObjectAnimator? = null
        }

        init {
            elapsedTime = itemView.findViewById(R.id.elapsedTime)

            scaleDown = ObjectAnimator.ofPropertyValuesHolder(
                playPause,
                PropertyValuesHolder.ofFloat("scaleX", 1.1f),
                PropertyValuesHolder.ofFloat("scaleY", 1.1f)
            )
            scaleDown?.duration = 500

            scaleDown?.repeatCount = ObjectAnimator.INFINITE
            scaleDown?.repeatMode = ObjectAnimator.REVERSE

            playPause.setOnClickListener { view: View? ->

                if (mediaPlayer.isPlaying) {
                    Toast.makeText(context, "Audio Paused", Toast.LENGTH_SHORT).show()
                    mediaPlayer.pause()
                    playPause.setImageResource(R.drawable.play)
                    clickedPlay = false
                    scaleDown?.removeAllListeners()
                    scaleDown?.cancel()
                } else {
                    clickedPlay = true
                    Toast.makeText(context, "Playing Audio", Toast.LENGTH_SHORT).show()
                    mediaPlayer.start()
                    if (clickedPlay) {
                        scaleDown?.start()
                        playPause.setImageResource(R.drawable.pause)
                    }
                    eTime = mediaPlayer.duration
                    sTime = mediaPlayer.currentPosition
                    if (oTime == 0) {
                        seekBar.max = eTime
                        oTime = 1
                    }
                    seekBar.progress = sTime
                    audioHandler.postDelayed(updateSongTime, 100)
                    playPause.isEnabled = true
                }
            }
        }
    }

    class VideoTypeViewHolder(itemView: View, context: Context?) :
        RecyclerView.ViewHolder(itemView) {
        var webView: WebView

        init {
            webView = itemView.findViewById(R.id.webView)
            webView.settings.javaScriptEnabled = true
            webView.loadData(AppConstants.youTubeVideoLink, "text/html", "utf-8")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View
        return when (viewType) {
            FeedListModel.IMAGE_TYPE -> {
                view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.image_row_layout, parent, false)
                return ImageTypeViewHolder(view, mContext)
            }
            FeedListModel.AUDIO_TYPE -> {
                view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.audio_row_layout, parent, false)
                return AudioTypeViewHolder(view, mContext)
            }
            FeedListModel.VIDEO_TYPE -> {
                view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.video_row_layout, parent, false)
                return VideoTypeViewHolder(view, mContext)
            }

            else -> throw IllegalArgumentException("Unsupported layout")
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (dataSet[position].type) {
            0 -> FeedListModel.IMAGE_TYPE
            1 -> FeedListModel.AUDIO_TYPE
            2 -> FeedListModel.VIDEO_TYPE
            else -> -1
        }
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val `object`: FeedListModel = dataSet[position]
        when (`object`.type) {
            FeedListModel.IMAGE_TYPE -> {
            }
            FeedListModel.AUDIO_TYPE -> {
            }
            FeedListModel.VIDEO_TYPE -> {
            }
        }
    }
}