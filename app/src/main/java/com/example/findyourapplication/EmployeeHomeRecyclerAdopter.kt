package com.example.findyourapplication

import android.animation.ValueAnimator
import android.content.Context
import android.view.*
import android.view.animation.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.animation.doOnEnd
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.findyourapplication.databinding.EmployeeHomeRecyclerItemBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.ClassCastException
import java.text.SimpleDateFormat

private val ITEM_VIEW_TYPE_ITEM = 1

class EmployeeHomeRecyclerAdopter(val context:Context,onItemClickListener:OnRecyclerViewRequestItemClickListener): ListAdapter<EmployeeHomeRecyclerAdopter.DataItem,RecyclerView.ViewHolder>(HomeDataDiffCallBack()){
    private val mContext=context
    private var mOnRecyclerViewItemClickListener=onItemClickListener
    private val adapterScope = CoroutineScope(Dispatchers.Default)


    fun addViewSubmitList(list:List<HomeRecyclerItemViewModelData>?){
        adapterScope.launch {
            val items=when(list){
                null-> listOf(null)
                else->list.map { DataItem.EmployeeHomeItem(it)}
            }
            withContext(Dispatchers.Main) {
                submitList(items)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType){
            ITEM_VIEW_TYPE_ITEM->HomeItemViewHolder.from(parent,mContext)
            else ->throw ClassCastException("UnKnown viewType $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is HomeItemViewHolder->{
                val item = getItem(position) as DataItem.EmployeeHomeItem
                holder.bind(item.item,position,mOnRecyclerViewItemClickListener)
            }
        }

    }

    override fun getItemViewType(position: Int): Int {
        return when(getItem(position)){
            is DataItem.EmployeeHomeItem->ITEM_VIEW_TYPE_ITEM
        }
    }


    class HomeItemViewHolder private constructor(val binding: EmployeeHomeRecyclerItemBinding,val context:Context)
        :RecyclerView.ViewHolder(binding.root) {


        fun bind(item:HomeRecyclerItemViewModelData, position: Int, mOnRecyclerViewItemClickListener: OnRecyclerViewRequestItemClickListener){
            //val formatter = SimpleDateFormat("yyyy / MM / dd") //or use getDateInstance()



            //binding.homeItem=item


            binding.jobDate.text=item.uploadedDate
            binding.jobDescription.text=item.jobDescription
            binding.jobHeader.text=item.companyName
            binding.jobNeededSkillsDesc.text=item.neededSkills
            binding.rating.rating=item.skillRate!!
            binding.jobTypeDesc.text=item.jobType


            binding.showMoreJobDetails.setOnClickListener {
                expand(binding,context)
                mOnRecyclerViewItemClickListener.onItemClick(position)
            }
            binding.collapse.setOnClickListener {
                collapse(binding,context)
                mOnRecyclerViewItemClickListener.onItemClick(position)
            }

            binding.applyForJob.setOnClickListener {
                requestForJob(binding,context)
                mOnRecyclerViewItemClickListener.onApplyClick(position)

            }
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup,context:Context): HomeItemViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = EmployeeHomeRecyclerItemBinding.inflate(layoutInflater, parent, false)
                
                return HomeItemViewHolder(binding,context)
            }
        }
    }

    interface OnRecyclerViewRequestItemClickListener{
        fun onItemClick(pos: Int)
        fun onApplyClick(pos:Int)
    }

    companion object {
        fun expand(binding: EmployeeHomeRecyclerItemBinding,context: Context) {
            val slideDown = AnimationUtils.loadAnimation(context, R.anim.anim_slide_down)
            val fadeOut=AnimationUtils.loadAnimation(context,R.anim.fade_out)
            val fadeIn=AnimationUtils.loadAnimation(context,R.anim.fade_in)

            val gp=(binding.employeeHomeItemV444.layoutParams as ConstraintLayout.LayoutParams).guidePercent
            val valueAnimator:ValueAnimator=ValueAnimator.ofFloat(gp,0.87f).setDuration(300)
            valueAnimator.interpolator=AccelerateDecelerateInterpolator()

            valueAnimator.addUpdateListener {
                val tmp=binding.employeeHomeItemV444.layoutParams as ConstraintLayout.LayoutParams
                tmp.guidePercent=valueAnimator.animatedValue as Float
                binding.employeeHomeItemV444.layoutParams=tmp
            }

            val gp2=(binding.employeeHomeItemV44.layoutParams as ConstraintLayout.LayoutParams).guidePercent
            val valueAnimator2:ValueAnimator=ValueAnimator.ofFloat(gp2,0.65f).setDuration(300)
            valueAnimator2.interpolator=AccelerateDecelerateInterpolator()

            valueAnimator2.addUpdateListener {
                val tmp=binding.employeeHomeItemV44.layoutParams as ConstraintLayout.LayoutParams
                tmp.guidePercent=valueAnimator2.animatedValue as Float
                binding.employeeHomeItemV44.layoutParams=tmp
            }


            binding.showMoreJobDetailsTextView.text=null
            valueAnimator.start()
            valueAnimator2.start()
            binding.collapseTxt.text=context.resources.getString(R.string.collapse)

            valueAnimator.doOnEnd {
                binding.showMoreJobDetails.startAnimation(fadeOut)
                binding.showMoreJobDetails.visibility=View.INVISIBLE

            }
            fadeOut.setAnimationListener(object:Animation.AnimationListener{
                override fun onAnimationStart(animation: Animation?) {

                }

                override fun onAnimationEnd(animation: Animation?) {
                    binding.expandablePart.visibility = View.VISIBLE
                    binding.expandablePart.startAnimation(slideDown)
                    binding.collapse.visibility=View.VISIBLE
                    binding.collapse.startAnimation(fadeIn)
                    binding.applyForJob.visibility=View.VISIBLE
                    binding.applyForJob.startAnimation(fadeIn)
                }

                override fun onAnimationRepeat(animation: Animation?) {

                }
            })


        }

        fun requestForJob(binding: EmployeeHomeRecyclerItemBinding,context: Context){
            val fadeOut=AnimationUtils.loadAnimation(context,R.anim.fade_out)
            val fadeIn=AnimationUtils.loadAnimation(context,R.anim.fade_in)
            val gp=(binding.employeeHomeItemV11.layoutParams as ConstraintLayout.LayoutParams).guidePercent
            val valueAnimator:ValueAnimator=ValueAnimator.ofFloat(gp,0.4f,0.1f)
            valueAnimator.duration=300
            valueAnimator.interpolator=AccelerateDecelerateInterpolator()
            valueAnimator.addUpdateListener {
                val tmp=binding.employeeHomeItemV11.layoutParams as ConstraintLayout.LayoutParams
                tmp.guidePercent=valueAnimator.animatedValue as Float
                binding.employeeHomeItemV11.layoutParams=tmp
            }


            if(binding.applyText.text==context.resources.getText(R.string.apply_job)){
                valueAnimator.start()
                binding.applyIcon.startAnimation(fadeOut)
                binding.applyIcon.setImageResource(R.drawable.ic_cancel)
                binding.applyIcon.startAnimation(fadeIn)
                binding.applyText.startAnimation(fadeOut)
                binding.applyText.text=context.resources.getText(R.string.Requested)
                binding.applyText.startAnimation(fadeIn)
            }
            else{
                valueAnimator.start()
                binding.applyIcon.startAnimation(fadeOut)
                binding.applyIcon.setImageResource(R.drawable.ic_check_mark_button)
                binding.applyIcon.startAnimation(fadeIn)
                binding.applyText.startAnimation(fadeOut)
                binding.applyText.text=context.resources.getText(R.string.apply_job)
                binding.applyText.startAnimation(fadeIn)
            }


        }


        fun collapse(binding: EmployeeHomeRecyclerItemBinding,context: Context) {
            val fadeOut=AnimationUtils.loadAnimation(context,R.anim.fade_out)
            val fadeIn=AnimationUtils.loadAnimation(context,R.anim.fade_in)

            val gp=(binding.employeeHomeItemV44.layoutParams as ConstraintLayout.LayoutParams).guidePercent
            val valueAnimator:ValueAnimator=ValueAnimator.ofFloat(gp,0.87f)
            valueAnimator.interpolator=AccelerateDecelerateInterpolator()
            valueAnimator.duration=300
            valueAnimator.addUpdateListener {
                val tmp=binding.employeeHomeItemV44.layoutParams as ConstraintLayout.LayoutParams
                tmp.guidePercent=valueAnimator.animatedValue as Float
                binding.employeeHomeItemV44.layoutParams=tmp
            }

            val gp2=(binding.employeeHomeItemV444.layoutParams as ConstraintLayout.LayoutParams).guidePercent
            val valueAnimator2:ValueAnimator=ValueAnimator.ofFloat(gp2,0.65f).setDuration(300)
            valueAnimator2.interpolator=AccelerateDecelerateInterpolator()

            valueAnimator2.addUpdateListener {
                val tmp=binding.employeeHomeItemV444.layoutParams as ConstraintLayout.LayoutParams
                tmp.guidePercent=valueAnimator2.animatedValue as Float
                binding.employeeHomeItemV444.layoutParams=tmp
            }
            binding.collapseTxt.text=null
            valueAnimator.start()
            valueAnimator2.start()
            binding.showMoreJobDetailsTextView.text=context.resources.getString(R.string.more_details)
            valueAnimator.doOnEnd {
                binding.collapse.startAnimation(fadeOut)
                binding.collapse.visibility=View.INVISIBLE
                binding.applyForJob.startAnimation(fadeOut)
                binding.applyForJob.visibility=View.INVISIBLE
            }
            fadeOut.setAnimationListener(object:Animation.AnimationListener{
                override fun onAnimationStart(animation: Animation?) {
                }
                override fun onAnimationEnd(animation: Animation?) {
                    binding.expandablePart.visibility = View.GONE
                    binding.showMoreJobDetails.visibility=View.VISIBLE
                    binding.showMoreJobDetails.startAnimation(fadeIn)
                }
                override fun onAnimationRepeat(animation: Animation?) {
                }
            })
        }
    }

    sealed class DataItem{
        data class EmployeeHomeItem(val item:HomeRecyclerItemViewModelData):DataItem(){
            override val id=item.id
        }
        abstract val id:Long
    }

    class HomeDataDiffCallBack : DiffUtil.ItemCallback<DataItem>() {
        override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
            return oldItem.id==newItem.id
        }

        override fun areContentsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
            return oldItem.equals(newItem)
        }
    }


}