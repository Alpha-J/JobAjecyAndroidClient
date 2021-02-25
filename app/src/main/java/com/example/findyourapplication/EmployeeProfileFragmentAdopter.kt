package com.example.findyourapplication

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.findyourapplication.databinding.ProfileCardItemBinding
import com.example.findyourapplication.databinding.ProfileResumeCardBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private val ITEM_VIEW_TYPE_Resume = 0
private val ITEM_VIEW_TYPE_ITEM = 1

class EmployeeProfileFragmentAdopter(private val context: Context,onItemClickListener:OnRecyclerViewProfileItemClickListener): ListAdapter<EmployeeProfileFragmentAdopter.DataItem,RecyclerView.ViewHolder>(ProfileDiffCallBack()) {

    private val adapterScope = CoroutineScope(Dispatchers.Default)
    private val mOnItemClickListener=onItemClickListener

    fun addMultipleViewsAndSubmitList(list: List<EmployeeProfileViewModelData>?) {
        adapterScope.launch {
            val items = when (list) {
                null -> listOf(DataItem.Resume)
                else -> list.map { DataItem.ProfileItem(it)}+listOf(DataItem.Resume)
            }
            withContext(Dispatchers.Main) {
                submitList(items)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_VIEW_TYPE_Resume -> ResumeViewHolder.from(parent)
            ITEM_VIEW_TYPE_ITEM -> ProfileItemHolder.from(parent)
            else -> throw ClassCastException("Unknown viewType $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ProfileItemHolder -> {
                val item = getItem(position) as DataItem.ProfileItem
                holder.bind(item.item, mOnItemClickListener,position)
            }
            is ResumeViewHolder->{
                holder.bind(mOnItemClickListener)
            }
        }
    }


    class ResumeViewHolder private constructor(val binding: ProfileResumeCardBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(clickListener: OnRecyclerViewProfileItemClickListener){
            binding.addUpdateResume.setOnClickListener {
                clickListener.onUploadResumeClick()
            }

        }

        companion object {
            fun from(parent: ViewGroup): ResumeViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ProfileResumeCardBinding.inflate(layoutInflater, parent, false)
                return ResumeViewHolder(binding)
            }
        }
    }


    class ProfileItemHolder private constructor(val binding: ProfileCardItemBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(item: EmployeeProfileViewModelData, clickListener: OnRecyclerViewProfileItemClickListener,position:Int) {

            binding.profileItemImage.setImageResource(item.itemIcon)
            binding.profileItemDescription.text=item.itemData
            binding.profileItemHeader.text=item.itemHeaderText


            binding.root.setOnClickListener {
                clickListener.onItemClick(position)
            }

            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ProfileItemHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ProfileCardItemBinding.inflate(layoutInflater, parent, false)
                return ProfileItemHolder(binding)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is DataItem.Resume -> ITEM_VIEW_TYPE_Resume
            is DataItem.ProfileItem -> ITEM_VIEW_TYPE_ITEM
        }
    }



    class ProfileDiffCallBack: DiffUtil.ItemCallback<DataItem>() {
        override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
            return oldItem.id==newItem.id
        }

        override fun areContentsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
            return oldItem==newItem
        }

    }

    interface OnRecyclerViewProfileItemClickListener{
        fun onItemClick(pos: Int)
        fun onApplyClick(pos:Int)
        fun onUploadResumeClick()
    }

    sealed class DataItem {
        data class ProfileItem(val item: EmployeeProfileViewModelData): DataItem() {
            override val id = item.id
        }

        object Resume: DataItem() {
            override val id = Long.MIN_VALUE
        }

        abstract val id: Long
    }

}