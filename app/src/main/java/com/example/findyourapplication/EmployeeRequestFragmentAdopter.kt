package com.example.findyourapplication

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.findyourapplication.databinding.EmployeeRequestRecyclerItemBinding


class EmployeeRequestFragmentAdopter(val context:Context,private val viewModel:RequestRecyclerItemViewModel):ListAdapter<RequestRecyclerItemViewModelData,EmployeeRequestFragmentAdopter.RequestViewHolder>(RequestDataDiffCallBack()) {

    init {
        setHasStableIds(true);
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RequestViewHolder {
            return RequestViewHolder.from(parent,viewModel,context)
    }

    override fun onBindViewHolder(holder: RequestViewHolder, position: Int) {
        holder.bind(getItem(position),position)
    }

    override fun getItemCount(): Int {
            return viewModel.getDataSize()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    class RequestViewHolder(val binding:EmployeeRequestRecyclerItemBinding,val viewModel:RequestRecyclerItemViewModel,val context: Context):RecyclerView.ViewHolder(binding.root){
        fun bind(item:RequestRecyclerItemViewModelData,position: Int){
            binding.employeeRequestItemDate.text=item.date
            binding.employeeRequestItemCompanyName.text=item.companyName
            binding.employeeRequestItemJobTypeDesc.text=item.jobType
            binding.employeeRequestTxtView.text=item.requestCondition
            if(item.requestCondition==context.resources.getString(R.string.request_pending)){
                binding.employeeRequestImageView.setImageResource(R.drawable.ic_pending)
            }
            if(item.requestCondition==context.resources.getString(R.string.request_accepted)){
                binding.employeeRequestImageView.setImageResource(R.drawable.ic_check_mark_button)
            }
            if(item.requestCondition==context.resources.getString(R.string.request_rejected)){
                binding.employeeRequestImageView.setImageResource(R.drawable.ic_cancel)
            }

        }
        companion object{
            fun from(parent: ViewGroup,viewModel:RequestRecyclerItemViewModel,context:Context):RequestViewHolder{
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = EmployeeRequestRecyclerItemBinding.inflate(layoutInflater, parent, false)
                return RequestViewHolder(binding, viewModel,context)
            }

        }
    }



    class RequestViewHolder2(val binding:EmployeeRequestRecyclerItemBinding,val viewModel:RequestRecyclerItemViewModel,val context: Context):RecyclerView.ViewHolder(binding.root){
        fun bind(item:RequestRecyclerItemViewModelData,position: Int){
            binding.employeeRequestItemDate.text=item.date
            binding.employeeRequestItemCompanyName.text=item.companyName
            binding.employeeRequestItemJobTypeDesc.text=item.jobType
            binding.employeeRequestTxtView.text=item.requestCondition
            if(item.requestCondition==context.resources.getString(R.string.request_pending)){
                binding.employeeRequestImageView.setImageResource(R.drawable.ic_pending)
            }
            if(item.requestCondition==context.resources.getString(R.string.request_accepted)){
                binding.employeeRequestImageView.setImageResource(R.drawable.ic_check_mark_button)
            }
            if(item.requestCondition==context.resources.getString(R.string.request_rejected)){
                binding.employeeRequestImageView.setImageResource(R.drawable.ic_cancel)
            }

        }
        companion object{
            fun from(parent: ViewGroup,viewModel:RequestRecyclerItemViewModel,context:Context):RequestViewHolder{
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = EmployeeRequestRecyclerItemBinding.inflate(layoutInflater, parent, false)
                return RequestViewHolder(binding, viewModel,context)
            }

        }
    }

    class RequestDataDiffCallBack: DiffUtil.ItemCallback<RequestRecyclerItemViewModelData>(){
        override fun areItemsTheSame(oldItem: RequestRecyclerItemViewModelData, newItem: RequestRecyclerItemViewModelData): Boolean {
            return oldItem.requestCondition==newItem.requestCondition
        }

        override fun areContentsTheSame(oldItem: RequestRecyclerItemViewModelData, newItem: RequestRecyclerItemViewModelData): Boolean {
            return oldItem.equals(newItem)
        }

    }
}