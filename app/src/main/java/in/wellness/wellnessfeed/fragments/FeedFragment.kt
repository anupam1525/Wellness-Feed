package `in`.wellness.wellnessfeed.fragments

import `in`.wellness.wellnessfeed.R
import `in`.wellness.wellnessfeed.adapter.MultiViewTypeAdapter
import `in`.wellness.wellnessfeed.model.FeedListModel
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.*

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class FeedFragment : Fragment() {

    private val feedList: ArrayList<FeedListModel> = ArrayList<FeedListModel>()
    var adapter: MultiViewTypeAdapter? = null

    var audioHandler: Handler? = null
    var videoHandler: Handler? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_feed_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mRecycleview: RecyclerView = view.findViewById(R.id.feed_recycler_view)

        audioHandler = Handler()
        videoHandler = Handler()

        feedList.add(
            FeedListModel(
                FeedListModel.IMAGE_TYPE,
                "Image Type",
                R.drawable.first_feed_image
            )
        )

        adapter = MultiViewTypeAdapter(feedList, requireContext())
        val linearLayoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)

        mRecycleview.layoutManager = linearLayoutManager
        mRecycleview.itemAnimator = DefaultItemAnimator()
        mRecycleview.adapter = adapter

        audioHandler?.postDelayed({ insertAudio() }, 10000)

        videoHandler?.postDelayed({ insertVideo() }, 20000)

    }

    private fun insertAudio() {
        feedList.add(FeedListModel(FeedListModel.AUDIO_TYPE, "Audio Type", R.raw.meditation_music))
        adapter?.notifyDataSetChanged()
    }

    private fun insertVideo() {
        feedList.add(FeedListModel(FeedListModel.VIDEO_TYPE, "Video Type", 0))
        adapter?.notifyDataSetChanged()
    }
}