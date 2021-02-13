package com.appacoustic.rt.framework.customview

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.appacoustic.rt.databinding.DialogInfoBinding
import com.appacoustic.rt.framework.extension.gone

class InfoAlertDialog private constructor(
    context: Context,
    @DrawableRes private val drawableResId: Int?,
    @StringRes private val titleResId: Int,
    @StringRes private val descriptionResId: Int,
    @StringRes private val btnTextResId: Int,
    private val onButtonClicked: () -> Unit,
    private val cancelable: Boolean
) : AlertDialog(context) {

    private lateinit var binding: DialogInfoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DialogInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initUI()
    }

    private fun initUI() {
        with(binding) {
            if (drawableResId != null) {
                iv.setImageResource(drawableResId)
            } else {
                iv.gone()
            }

            tvTitle.setText(titleResId)
            tvDescription.setText(descriptionResId)
            btn.setText(btnTextResId)
            btn.setOnClickListener {
                dismiss()
                onButtonClicked()
            }
        }

        setCancelable(cancelable)
    }

    class Builder(private val context: Context) {

        private var drawableResId: Int? = null
        private var titleResId: Int = -1
        private var descriptionResId: Int = -1
        private var btnTextResId: Int = -1
        private var onButtonClicked: () -> Unit = {}
        private var cancelable = true

        fun drawable(@DrawableRes drawableResId: Int) = apply {
            this.drawableResId = drawableResId
        }

        fun title(@StringRes titleResId: Int) = apply {
            this.titleResId = titleResId
        }

        fun description(@StringRes descriptionResId: Int) = apply {
            this.descriptionResId = descriptionResId
        }

        fun btnText(@StringRes btnTextResId: Int) = apply {
            this.btnTextResId = btnTextResId
        }

        fun onButtonClicked(onButtonClicked: () -> Unit) = apply {
            this.onButtonClicked = onButtonClicked
        }

        fun cancelable(cancelable: Boolean) = apply {
            this.cancelable = cancelable
        }

        fun buildAndShow() {
            InfoAlertDialog(
                context = context,
                drawableResId = drawableResId,
                titleResId = titleResId,
                descriptionResId = descriptionResId,
                btnTextResId = btnTextResId,
                onButtonClicked = onButtonClicked,
                cancelable = cancelable
            ).show()
        }
    }
}
