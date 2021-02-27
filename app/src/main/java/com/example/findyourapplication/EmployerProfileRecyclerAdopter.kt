package com.example.findyourapplication

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.findyourapplication.databinding.EmployerProfileItemCardBinding

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private val ITEM_VIEW_TYPE_ITEM = 1

class EmployerProfileRecyclerAdopter(private val context: Context, onItemClickListener: OnRecyclerViewProfileItemClickListener):ListAdapter<EmployerProfileRecyclerAdopter.DataItem,RecyclerView.ViewHolder>(EmployerProfileDiffCallBack()) {

    private val adapterScope = CoroutineScope(Dispatchers.Default)
    private val mOnItemClickListener=onItemClickListener


    fun addViewAndSubmitList(list: List<EmployerProfileViewModelData>?) {
        adapterScope.launch {
            val items = when (list) {
                null -> listOf(null)
                else -> list.map {DataItem.EmployerProfileItem(it)}
            }
            withContext(Dispatchers.Main) {
                submitList(items)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_VIEW_TYPE_ITEM -> EmployerProfileItemHolder.from(parent)
            else -> throw ClassCastException("Unknown viewType $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is EmployerProfileItemHolder -> {
                val item = getItem(position) as DataItem.EmployerProfileItem
                holder.bind(item.item, mOnItemClickListener,position)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is DataItem.EmployerProfileItem -> ITEM_VIEW_TYPE_ITEM
        }
    }

    class EmployerProfileItemHolder private constructor(val binding: EmployerProfileItemCardBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(item: EmployerProfileViewModelData, clickListener: OnRecyclerViewProfileItemClickListener, position:Int) {

            binding.employerProfileItemImage.setImageResource(item.itemIcon)
            binding.employerProfileItemDescription.text=item.itemData
            binding.employerProfileItemHeader.text=item.itemHeaderText

            binding.root.setOnClickListener {
                clickListener.onItemClick(position)
            }

            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): EmployerProfileItemHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = EmployerProfileItemCardBinding.inflate(layoutInflater, parent, false)
                return EmployerProfileItemHolder(binding)
            }
        }
    }

    sealed class DataItem {
        data class EmployerProfileItem(val item: EmployerProfileViewModelData): DataItem() {
            override val id = item.id
        }
        abstract val id: Long
    }

    interface OnRecyclerViewProfileItemClickListener {
        fun onItemClick(pos: Int)
    }

    class EmployerProfileDiffCallBack: DiffUtil.ItemCallback<DataItem>() {
        override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
            return oldItem.id==newItem.id
        }

        override fun areContentsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
            return oldItem==newItem
        }
    }
}