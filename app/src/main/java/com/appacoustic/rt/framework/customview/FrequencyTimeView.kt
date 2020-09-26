package com.appacoustic.rt.framework.customview

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.appacoustic.rt.R
import kotlinx.android.synthetic.main.custom_frequency_time.view.*

class FrequencyTimeView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    init {
        LayoutInflater.from(context).inflate(R.layout.custom_frequency_time, this, true)
        attrs?.let {
            val typedArray = context.obtainStyledAttributes(it, R.styleable.FrequencyTimeView)
            typedArray.apply {
                initIcon(this)
            }
            typedArray.recycle()
        }
    }

    private fun initIcon(typedArray: TypedArray) {
        val resId = typedArray.getResourceId(R.styleable.FrequencyTimeView_custom_text, 0)
        if (resId != 0) {
            tvFrequency.setText(resId)
        } else {
            throw TextNotDefinedException()
        }
    }

    fun setTime(text: String) {
        tvTime.text = text
    }
}

class TextNotDefinedException : Exception(
    "The 'custom_text' attribute has not been defined." +
        " Please, do it to ensure a proper UX.\n" +
        "This check is to ensure this attribute is filled as soon as possible and avoid UI errors."
)
