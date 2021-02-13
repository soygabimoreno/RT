package com.appacoustic.rt.framework.customview

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.appacoustic.rt.R
import com.appacoustic.rt.databinding.CustomPlayTestSignalBinding

class PlayTestSignalView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : ConstraintLayout(
    context,
    attrs
) {

    val binding: CustomPlayTestSignalBinding = CustomPlayTestSignalBinding.inflate(
        LayoutInflater.from(context),
        this
    )

    init {
        setBackgroundResource(R.drawable.bg_btn_ripple)
        attrs?.let {
            val typedArray = context.obtainStyledAttributes(
                it,
                R.styleable.PlayTestSignalView
            )
            typedArray.apply {
                initText(this)
            }
            typedArray.recycle()
        }
    }

    private fun initText(typedArray: TypedArray) {
        val resId = typedArray.getResourceId(
            R.styleable.PlayTestSignalView_custom_text,
            0
        )
        if (resId != 0) {
            binding.tv.setText(resId)
        } else {
            throw TextNotDefinedException()
        }
    }
}
