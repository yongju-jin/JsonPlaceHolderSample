package yongju.riiidhw.ui.viewholder

import android.view.ViewGroup
import yongju.riiidhw.R
import yongju.riiidhw.databinding.ItemPostsBinding
import yongju.riiidhw.model.TypiCodeModel
import yongju.riiidhw.ui.usecase.PostsItemUseCase

class PostsViewHolder(private val binding: ItemPostsBinding,
                      private val postsItemUseCase: PostsItemUseCase): BaseViewHolder<TypiCodeModel>(binding.root) {
    override fun bind(item: TypiCodeModel) {
        binding.usecase = postsItemUseCase
        binding.model = item
        binding.executePendingBindings()
    }

    companion object {
        fun createMainViewHolder(vg: ViewGroup, postsItemUseCase: PostsItemUseCase): PostsViewHolder {
            return PostsViewHolder(dataBinding(vg, R.layout.item_posts), postsItemUseCase)
        }
    }
}