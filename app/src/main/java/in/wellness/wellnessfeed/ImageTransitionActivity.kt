package `in`.wellness.wellnessfeed

import android.os.Bundle
import android.view.Window
import androidx.appcompat.app.AppCompatActivity

class ImageTransitionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_CONTENT_TRANSITIONS)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.image_transition_layout)
    }
}