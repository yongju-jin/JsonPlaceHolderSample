package yongju.riiidhw.ui.viewholder

import android.view.ViewGroup
import yongju.riiidhw.R
import yongju.riiidhw.databinding.ItemMainBinding
import yongju.riiidhw.model.TypiCodeModel

class MainViewHolder(private val binding: ItemMainBinding): BaseViewHolder<TypiCodeModel>(binding.root) {
    override fun bind(item: TypiCodeModel) {
        binding.model = item
        binding.executePendingBindings()
    }

    companion object {
        fun createMainViewHolder(vg: ViewGroup): MainViewHolder {
            return MainViewHolder(dataBinding(vg, R.layout.item_main))
        }
    }
}