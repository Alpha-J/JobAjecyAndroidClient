package com.example.findyourapplication

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.findyourapplication.databinding.EmployerProfileItemCardBinding
import com.example.findyourapplication.databinding.EmployerRequestHandlerCardItemBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private val ITEM_VIEW_TYPE = 1

class EmployerRequestRecyclerAdopter(onItemClickListener:OnEmployerRequestHandlerItemClickListener):ListAdapter<EmployerRequestRecyclerAdopter.EmployerRequestHandlerDataItem,RecyclerView.ViewHolder>(EmployerRequestRecyclerAdopterDiffCallback()) {

    private val adapterScope = CoroutineScope(Dispatchers.Default)
    private val mOnItemClickListener=onItemClickListener


    fun addViewAndSubmitList(list: List<EmployerRequestHandlerViewModelData>?) {
        adapterScope.launch {
            val items = when (list) {
                null -> listOf(null)
                else -> list.map { EmployerRequestHandlerDataItem.EmployerReqHandlerItem(it)}
            }
            withContext(Dispatchers.Main) {
                submitList(items)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_VIEW_TYPE -> EmployerRequestHandlerItemHolder.from(parent)
            else -> throw ClassCastException("Unknown viewType $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is EmployerRequestHandlerItemHolder-> {
                val item = getItem(position) as EmployerRequestHandlerDataItem.EmployerReqHandlerItem
                holder.bind(item.item, mOnItemClickListener,position)
            }
        }
    }

    class EmployerRequestHandlerItemHolder private constructor(val binding: EmployerRequestHandlerCardItemBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(item: EmployerRequestHandlerViewModelData, clickListener:OnEmployerRequestHandlerItemClickListener , position:Int) {
            binding.employerReqHDateTextview.text=item.reqDate
            binding.employerReqHFirstnameTextview.text=item.candidateFirstName
            binding.employerReqHLastnameTextview.text=item.candidateLastName
            binding.employerReqHPhoneTextview.text=item.candidatePhone

            binding.acceptCandidate.setOnClickListener {
                clickListener.onAcceptClick(position)
            }
            binding.rejectCandidate.setOnClickListener {
                clickListener.onRejectClick(position)
            }
            binding.watchCandidateResume.setOnClickListener {
                clickListener.onSeeResumeClick(position)
            }

            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): EmployerRequestHandlerItemHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = EmployerRequestHandlerCardItemBinding.inflate(layoutInflater, parent, false)
                return EmployerRequestHandlerItemHolder(binding)
            }
        }
    }

    interface OnEmployerRequestHandlerItemClickListener{
        fun onAcceptClick(pos:Int)
        fun onRejectClick(pos:Int)
        fun onSeeResumeClick(pos:Int)
    }


    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is EmployerRequestHandlerDataItem -> ITEM_VIEW_TYPE
        }
    }

    sealed class EmployerRequestHandlerDataItem{
        abstract val id:Long
        data class EmployerReqHandlerItem(val item:EmployerRequestHandlerViewModelData):EmployerRequestHandlerDataItem(){
            override val id=item.id
        }
    }

    class EmployerRequestRecyclerAdopterDiffCallback : DiffUtil.ItemCallback<EmployerRequestHandlerDataItem>(){
        override fun areItemsTheSame(oldItem: EmployerRequestHandlerDataItem, newItem: EmployerRequestHandlerDataItem): Boolean {
            return oldItem.id==newItem.id
        }

        override fun areContentsTheSame(oldItem: EmployerRequestHandlerDataItem, newItem: EmployerRequestHandlerDataItem): Boolean {
            return oldItem==newItem
        }

    }
}