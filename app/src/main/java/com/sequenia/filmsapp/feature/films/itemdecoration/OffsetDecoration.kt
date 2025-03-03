package com.sequenia.filmsapp.feature.films.itemdecoration

import android.graphics.Rect
import android.view.View
import androidx.annotation.Px
import androidx.recyclerview.widget.RecyclerView

/**
 * Класс используется для настройки отступов между карточками фильмов в RecyclerView,
 * обеспечивая правильное выравнивание элементов в зависимости от их позиции.
 * @param spacingSide - расстояние от карточки до края экрана
 * @param spacingBottom - расстояние между рядами карточек
 * @param spacingBetweenCards - расстояние между карточками в каждом ряду
 */
class OffsetDecoration(
    @Px private val spacingSide: Int,
    @Px private val spacingBottom: Int,
    @Px private val spacingBetweenCards: Int,
) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view)

        if (position % 2 == 0) {
            outRect.left = spacingSide
            outRect.right = spacingBetweenCards / 2
        } else {
            outRect.left = spacingBetweenCards / 2
            outRect.right = spacingSide
        }

        outRect.bottom = spacingBottom
    }
}
