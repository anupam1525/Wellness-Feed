package `in`.wellness.wellnessfeed.model

class FeedListModel(var type: Int, var text: String, var data: Int) {
    companion object {
        const val IMAGE_TYPE = 0
        const val AUDIO_TYPE = 1
        const val VIDEO_TYPE = 2
    }
}