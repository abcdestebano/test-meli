package com.meli.app

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.meli.app.databinding.CustomViewEmptyStateBinding

class CustomEmptyState @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val binding =
        CustomViewEmptyStateBinding.inflate(LayoutInflater.from(context), this, true)

    fun setContentView(
        image: Int,
        text: String,
        showButton: Boolean = false,
        onButtonClick: (() -> Unit)? = null
    ) {
        binding.apply {
            imgEmptyState.setBackgroundResource(image)
            txvEmptyState.text = text
            btnEmptyState.toggleVisibility(show = showButton)
            btnEmptyState.setOnClickListener { onButtonClick?.invoke() }
        }
    }

}