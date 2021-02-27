package com.example.findyourapplication


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.findyourapplication.databinding.EmployerHomeCardviewItemBinding

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat

private val ITEM_VIEW_TYPE_ITEM = 1

class EmployerHomeRecyclerAdopter(onItemClickListener:OnEmployerHomeRecyclerViewItemClickListener):ListAdapter<EmployerHomeRecyclerAdopter.HomeDataItem,RecyclerView.ViewHolder>(EmployerHomeDataDiffCallBack()) {

    private val adapterScope = CoroutineScope(Dispatchers.Default)
    private val mOnItemClickListener=onItemClickListener


    fun addViewAndSubmitList(list: List<EmployerHomeViewModelData>?) {
        adapterScope.launch {
            val items = when (list) {
                null -> listOf(null)
                else -> list.map { HomeDataItem.EmployerHomeItem(it)}
            }
            withContext(Dispatchers.Main) {
                submitList(items)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_VIEW_TYPE_ITEM -> EmployerHomeItemHolder.from(parent)
            else -> throw ClassCastException("Unknown viewType $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is EmployerHomeItemHolder -> {
                val item = getItem(position) as HomeDataItem.EmployerHomeItem
                holder.bind(item.item, mOnItemClickListener,position)
            }
        }
    }


    class EmployerHomeItemHolder private constructor(val binding:EmployerHomeCardviewItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(item: EmployerHomeViewModelData, clickListener: OnEmployerHomeRecyclerViewItemClickListener, position:Int) {
            val formatter = SimpleDateFormat("yyyy / MM / dd")

            binding.jobDate.text=formatter.format(item.uploadedDate!!)
            binding.jobDescription.text=item.jobDescription
            binding.jobHeader.text=item.companyName
            binding.jobNeededSkillsDesc.text=item.neededSkills
            binding.rating.rating=item.skillRate!!
            binding.jobTypeDesc.text=item.jobType

            binding.deleteJob.setOnClickListener {
                clickListener.onItemClick(adapterPosition)
            }

            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): EmployerHomeItemHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding =EmployerHomeCardviewItemBinding.inflate(layoutInflater, parent, false)
                return EmployerHomeItemHolder(binding)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is HomeDataItem .EmployerHomeItem-> ITEM_VIEW_TYPE_ITEM
        }
    }

    interface OnEmployerHomeRecyclerViewItemClickListener {
        fun onItemClick(pos: Int)
    }

    sealed class HomeDataItem{
        abstract val id: Long
        data class EmployerHomeItem(val item:EmployerHomeViewModelData):HomeDataItem(){
            override val id=item.id
        }
    }

    class EmployerHomeDataDiffCallBack: DiffUtil.ItemCallback<HomeDataItem>() {
        override fun areItemsTheSame(oldItem: HomeDataItem, newItem: HomeDataItem): Boolean {
            return oldItem.id==newItem.id
        }
        override fun areContentsTheSame(oldItem: HomeDataItem, newItem: HomeDataItem): Boolean {
            return oldItem==newItem
        }
    }

}