package com.app.calculator_betona.activity

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.app.calculator_betona.R
import com.app.calculator_betona.databinding.ActivityTopicItemBinding
import com.app.calculator_betona.util.getCityName
import com.app.calculator_betona.util.logEvent

class TopicItemActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTopicItemBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_topic_item)

        binding.arrowBack.setOnClickListener {
            finish()
        }

        val index = intent.getIntExtra(EXTRA_TOPIC_ITEM_INDEX, -1)
        require(index != -1) { "Topic index was not sent!" }

        binding.title.text = resources.getStringArray(R.array.titles)[index]
        binding.text.text = resources.getStringArray(R.array.texts)[index]

        val array = resources.obtainTypedArray(R.array.drawables)

        binding.image.setImageDrawable(array.getDrawable(index))

        array.recycle()

        logEvent(
            this,
            "Topic_Screen_Opened",
            getCityName(
                this,
                application.getSharedPreferences(
                    getString(R.string.preferences),
                    Context.MODE_PRIVATE
                )
            )
        )
    }

    companion object {
        const val EXTRA_TOPIC_ITEM_INDEX = "EXTRA_TOPIC_ITEM_INDEX"
    }
}
