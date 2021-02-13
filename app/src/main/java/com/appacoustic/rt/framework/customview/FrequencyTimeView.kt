package com.appacoustic.rt.framework.customview

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.appacoustic.rt.R
import com.appacoustic.rt.databinding.CustomFrequencyTimeBinding

class FrequencyTimeView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    val binding: CustomFrequencyTimeBinding = CustomFrequencyTimeBinding.inflate(
        LayoutInflater.from(context),
        this
    )

    init {
        attrs?.let {
            val typedArray = context.obtainStyledAttributes(
                it,
                R.styleable.FrequencyTimeView
            )
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
            binding.tvFrequency.setText(resId)
        } else {
            throw TextNotDefinedException()
        }
    }

    fun setTime(text: String) {
        binding.tvTime.text = text
    }

    fun setDefaultColor() {
        binding.tvTime.setTextColor(
            resources.getColor(
                R.color.gray_medium,
                null
            )
        )
    }

    fun setErrorColor() {
        binding.tvTime.setTextColor(
            resources.getColor(
                R.color.sizzlingRed,
                null
            )
        )
    }
}
