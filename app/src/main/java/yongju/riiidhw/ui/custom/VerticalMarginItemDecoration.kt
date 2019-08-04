package yongju.riiidhw.ui.custom

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class VerticalMarginItemDecoration(private val space: Int): RecyclerView.ItemDecoration()  {

//    private fun isFirstPosition(parent: RecyclerView, view: View) = parent.getChildAdapterPosition(view) == 0

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        with(outRect) {
            top = space
        }
    }
}
