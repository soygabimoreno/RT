package com.appacoustic.rt.framework.customview

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.appacoustic.rt.R
import kotlinx.android.synthetic.main.custom_play_test_signal.view.*

class PlayTestSignalView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : ConstraintLayout(
    context,
    attrs
) {

    init {
        LayoutInflater.from(context).inflate(
            R.layout.custom_play_test_signal,
            this,
            true
        )
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
            tv.setText(resId)
        } else {
            throw TextNotDefinedException()
        }
    }
}
