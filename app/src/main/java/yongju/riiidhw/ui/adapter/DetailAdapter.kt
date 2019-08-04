package yongju.riiidhw.ui.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import yongju.riiidhw.model.TypiCodeCommentModel
import yongju.riiidhw.ui.viewholder.CommentViewHolder

class CommentsAdapter: ListAdapter<TypiCodeCommentModel, CommentViewHolder>(
    object: DiffUtil.ItemCallback<TypiCodeCommentModel>() {
        override fun areItemsTheSame(
            oldItem: TypiCodeCommentModel,
            newItem: TypiCodeCommentModel
        ) = oldItem.id == newItem.id && oldItem.postId == newItem.postId

        override fun areContentsTheSame(
            oldItem: TypiCodeCommentModel,
            newItem: TypiCodeCommentModel
        ) = oldItem == newItem
    }
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        return CommentViewHolder.createCommentViewHolder(parent)
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        holder.bind(
            getItem(position)
        )
    }
}