package com.meli.app.utils

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.meli.app.databinding.CustomViewEmptyStateBinding
import com.meli.app.utils.extensions.toggleVisibility

/**
 * This custom view have a function where receive image, title,
 * description or event for button to show an empty state, could be
 * an error state or an information state or empty state to show correct
 * to the user
 *
 * @param context
 * @param attrs
 * @param defStyleAttr
 */
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
        binding.apply {
            if (description != null) {
                txvDescriptionEmptyState.text = description
            }
            txvDescriptionEmptyState.toggleVisibility(show = description != null)
        }
    }

    private fun setButtonClick(onButtonClick: (() -> Unit)?) {
        binding.apply {
            if (onButtonClick != null) {
                btnEmptyState.setOnClickListener { onButtonClick.invoke() }
            }
            btnEmptyState.toggleVisibility(show = onButtonClick != null)
        }

    }

}