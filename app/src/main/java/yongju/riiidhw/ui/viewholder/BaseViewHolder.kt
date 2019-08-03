package yongju.riiidhw.ui.viewholder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

fun <T: ViewDataBinding> dataBinding(vg: ViewGroup, @LayoutRes layoutId: Int): T {
    return DataBindingUtil.inflate(LayoutInflater.from(vg.context), layoutId, vg, false)
}

abstract class BaseViewHolder<T>(v: View): RecyclerView.ViewHolder(v) {
    abstract fun bind(item: T)
}