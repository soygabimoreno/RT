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
                initText(this)
            }
            typedArray.recycle()
        }
    }

    private fun initText(typedArray: TypedArray) {
        val resId = typedArray.getResourceId(
            R.styleable.FrequencyTimeView_custom_text,
            0
        )
        if (resId != 0) {
            tvFrequency.setText(resId)
        } else {
            throw TextNotDefinedException()
        }
    }

    fun setTime(text: String) {
        tvTime.text = text
    }

    fun setDefaultColor() {
        tvTime.setTextColor(
            resources.getColor(
                R.color.gray_medium,
                null
            )
        )
    }

    fun setErrorColor() {
        tvTime.setTextColor(
            resources.getColor(
                R.color.sizzlingRed,
                null
            )
        )
    }
}
