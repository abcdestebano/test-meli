package com.meli.app.utils

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.meli.app.databinding.CustomViewEmptyStateBinding
import com.meli.app.utils.extensions.toggleVisibility

class CustomEmptyState @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val binding =
        CustomViewEmptyStateBinding.inflate(LayoutInflater.from(context), this, true)

    fun setContentView(
        image: Int,
        title: String,
        description: String? = null,
        onButtonClick: (() -> Unit)? = null
    ) {
        binding.apply {
            imgEmptyState.setBackgroundResource(image)
            txvEmptyState.text = title
            setDescription(description)
            setButtonClick(onButtonClick)
        }
    }

    private fun setDescription(description: String?) {
        description?.let {
            binding.apply {
                txvDescriptionEmptyState.toggleVisibility(true)
                txvDescriptionEmptyState.text = it
            }
        }
    }

    private fun setButtonClick(onButtonClick: (() -> Unit)?) {
        onButtonClick?.let { onClick ->
            binding.apply {
                btnEmptyState.toggleVisibility(true)
                btnEmptyState.setOnClickListener { onClick.invoke() }
            }
        }
    }

}