package com.lucas.petros.ui

import android.animation.LayoutTransition
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.lucas.petros.ui.databinding.UiLoadingBinding

class UILoading @JvmOverloads constructor(
    context: Context,
    private val attrs: AttributeSet? = null,
    private val defStyle: Int = 0,
) : ConstraintLayout(context, attrs, defStyle) {

    private val binding = UiLoadingBinding.inflate(LayoutInflater.from(context), this, true)

    var description: String? = null
        set(value) {
            binding.tvDescription.text = value
            field = value
        }

    init {
        initView()
        configurationAttr()
    }

    private fun initView() {
        layoutTransition = LayoutTransition()
    }

    private fun configurationAttr() {
        val style = context.obtainStyledAttributes(
            attrs,
            R.styleable.UILoading,
            defStyle,
            0
        )
        updateDescription(style.getString(R.styleable.UILoading_description))
        style.recycle()
    }

    private fun updateDescription(value: String?) {
        description = value
    }

}