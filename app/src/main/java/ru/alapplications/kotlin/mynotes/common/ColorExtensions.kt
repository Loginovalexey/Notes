package ru.alapplications.kotlin.common

import android.content.Context
import androidx.core.content.ContextCompat
import ru.alapplications.kotlin.mynotes.R
import ru.alapplications.kotlin.mynotes.model.entity.Note


fun Note.Color.getColorInt(context: Context): Int =
    ContextCompat.getColor(
        context, getColorRes()
    )

fun Note.Color.getColorRes(): Int = when (this) {
    Note.Color.WHITE -> R.color.white
    Note.Color.VIOLET -> R.color.violet
    Note.Color.YELLOW -> R.color.yellow
    Note.Color.RED -> R.color.red
    Note.Color.GREEN -> R.color.green
    Note.Color.BLUE -> R.color.blue
}