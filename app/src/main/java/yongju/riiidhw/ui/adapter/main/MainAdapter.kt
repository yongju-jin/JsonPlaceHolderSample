package yongju.riiidhw.ui.adapter.main

import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import yongju.riiidhw.model.TypiCodeModel
import yongju.riiidhw.ui.viewholder.MainViewHolder

class MainAdapter: PagedListAdapter<TypiCodeModel, MainViewHolder>(
    object: DiffUtil.ItemCallback<TypiCodeModel>() {
        override fun areContentsTheSame(oldItem: TypiCodeModel, newItem: TypiCodeModel): Boolean {
            return oldItem == newItem
        }

        override fun areItemsTheSame(oldItem: TypiCodeModel, newItem: TypiCodeModel): Boolean {
            return oldItem.userId == newItem.userId && oldItem.id == newItem.id
        }
    }
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        return MainViewHolder.createMainViewHolder(parent)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }
}