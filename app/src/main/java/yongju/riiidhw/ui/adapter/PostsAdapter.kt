package yongju.riiidhw.ui.adapter

import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import yongju.riiidhw.model.TypiCodeModel
import yongju.riiidhw.ui.usecase.PostsItemUseCase
import yongju.riiidhw.ui.viewholder.PostsViewHolder

class PostsAdapter(private val postsItemUseCase: PostsItemUseCase): PagedListAdapter<TypiCodeModel, PostsViewHolder>(
    object: DiffUtil.ItemCallback<TypiCodeModel>() {
        override fun areContentsTheSame(oldItem: TypiCodeModel, newItem: TypiCodeModel): Boolean {
            return oldItem == newItem
        }

        override fun areItemsTheSame(oldItem: TypiCodeModel, newItem: TypiCodeModel): Boolean {
            return oldItem.userId == newItem.userId && oldItem.id == newItem.id
        }
    }
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostsViewHolder {
        return PostsViewHolder.createMainViewHolder(parent, postsItemUseCase)
    }

    override fun onBindViewHolder(holder: PostsViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }
}