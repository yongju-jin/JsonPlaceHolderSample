package yongju.riiidhw.ui.viewholder

import android.view.ViewGroup
import yongju.riiidhw.R
import yongju.riiidhw.databinding.ItemCommentBinding
import yongju.riiidhw.model.TypiCodeCommentModel

class CommentViewHolder(private val binding: ItemCommentBinding):
    BaseViewHolder<TypiCodeCommentModel>(binding.root) {

    override fun bind(item: TypiCodeCommentModel) {
        binding.model = item
        binding.executePendingBindings()
    }

    companion object {
        fun createCommentViewHolder(vg: ViewGroup): CommentViewHolder {
            return CommentViewHolder(
                dataBinding(vg, R.layout.item_comment)
            )
        }
    }
}