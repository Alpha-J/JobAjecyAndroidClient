package com.example.findyourapplication

import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Typeface
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.*
import android.view.animation.*
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.animation.doOnEnd
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.findyourapplication.databinding.EmployeeHomeRecyclerItemBinding
import java.util.*

class EmployeeHomeRecyclerAdopter(val context:Context,onItemClickListener:OnRecyclerViewRequestItemClickListener): ListAdapter<HomeRecyclerItemViewModelData,EmployeeHomeRecyclerAdopter.HomeItemViewHolder>(HomeDataDiffCallBack()){
    private val mContext=context
    private var mHomeViewModel=HomeRecyclerItemViewModel()
    private var mOnRecyclerViewItemClickListener=onItemClickListener


    init {
        setHasStableIds(true);
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeItemViewHolder {
        return HomeItemViewHolder.from(parent,mHomeViewModel,mContext)
    }

    override fun onBindViewHolder(holder: HomeItemViewHolder, position: Int) {
        holder.bind(getItem(position),position,mOnRecyclerViewItemClickListener)
    }

    override fun getItemCount()=mHomeViewModel.getDataForObservation().value!!.size

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    class HomeItemViewHolder(val binding: EmployeeHomeRecyclerItemBinding,val viewModel: HomeRecyclerItemViewModel,val context:Context)
        :RecyclerView.ViewHolder(binding.root) {

        fun bind(item:HomeRecyclerItemViewModelData,position: Int,mOnRecyclerViewItemClickListener: OnRecyclerViewRequestItemClickListener){
            binding.jobHeader.text=item.header
            binding.jobDescription.text=item.description

            binding.showMoreJobDetails.setOnClickListener {
                if (!item.expanded){

                    expand(binding,context)
                }
                else{

                    collapse(binding,context)
                }
                mOnRecyclerViewItemClickListener.onItemClick(position)
            }
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup,viewModel:HomeRecyclerItemViewModel,context:Context): HomeItemViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = EmployeeHomeRecyclerItemBinding.inflate(layoutInflater, parent, false)
                return HomeItemViewHolder(binding,viewModel,context)
            }
        }
    }

    interface OnRecyclerViewRequestItemClickListener{
        fun onItemClick(pos: Int)

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
            binding.showMoreJobDetailsTextView.text=null
            valueAnimator.start()
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

        fun collapse(binding: EmployeeHomeRecyclerItemBinding,context: Context) {
            val slideUp = AnimationUtils.loadAnimation(context, R.anim.anim_slide_up)
            val gp=(binding.employeeHomeItemV4.layoutParams as ConstraintLayout.LayoutParams).guidePercent
            val valueAnimator:ValueAnimator=ValueAnimator.ofFloat(gp,0.65f)
            valueAnimator.interpolator=LinearInterpolator()
            valueAnimator.duration=300
            valueAnimator.addUpdateListener {
                val tmp=binding.employeeHomeItemV4.layoutParams as ConstraintLayout.LayoutParams
                tmp.guidePercent=valueAnimator.animatedValue as Float
                binding.employeeHomeItemV4.layoutParams=tmp
            }
            binding.showMoreJobDetails.visibility=View.VISIBLE
            valueAnimator.start()

            Handler(Looper.getMainLooper()).postDelayed({
                binding.expandablePart.startAnimation(slideUp)
                binding.expandablePart.visibility = View.GONE
            }, 500)



        }

        private fun changeViewSizeWithAnimation(view: View, viewSize: Int, duration: Long) {
            val startViewSize = view.measuredHeight
            val endViewSize: Int =
                    if (viewSize < startViewSize) (viewSize) else (view.measuredHeight + viewSize)
            val valueAnimator =
                    ValueAnimator.ofInt(startViewSize, endViewSize)
            valueAnimator.duration = duration
            valueAnimator.addUpdateListener {
                val animatedValue = valueAnimator.animatedValue as Int
                val layoutParams = view.layoutParams
                layoutParams.height = animatedValue
                view.layoutParams = layoutParams
            }
            valueAnimator.start()

        }

        private fun height(textView:TextView,context: Context, text: String, typeface: Typeface?, textSize: Int, padding: Int): Int {
            //val textView = TextView(context)
            //textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize.toFloat())
            //textView.setPadding(padding, padding, padding, padding)
            textView.typeface = typeface
            textView.text = text
            val mMeasureSpecWidth =
                    View.MeasureSpec.makeMeasureSpec(getDeviceWidth(context), View.MeasureSpec.AT_MOST)
            val mMeasureSpecHeight = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
            textView.measure(mMeasureSpecWidth, mMeasureSpecHeight)
            return textView.measuredHeight
        }

        private fun dp2px(dpValue: Float, context: Context): Int {
            val scale = context.resources.displayMetrics.density
            return (dpValue * scale + 0.5f).toInt()
        }

        private fun getDeviceWidth(context: Context): Int {
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                val displayMetrics = DisplayMetrics()
                val display: Display? = context.display
                display?.getRealMetrics(displayMetrics)
                displayMetrics.widthPixels
            } else {
                val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
                val displayMetrics = DisplayMetrics()
                wm.defaultDisplay.getMetrics(displayMetrics)
                context.display!!.getRealMetrics(displayMetrics)
                displayMetrics.widthPixels
            }
        }
    }

    class HomeDataDiffCallBack : DiffUtil.ItemCallback<HomeRecyclerItemViewModelData>() {

        override fun areItemsTheSame(oldItem: HomeRecyclerItemViewModelData, newItem: HomeRecyclerItemViewModelData): Boolean {
            return oldItem.expanded==newItem.expanded
        }

        override fun areContentsTheSame(oldItem: HomeRecyclerItemViewModelData, newItem: HomeRecyclerItemViewModelData): Boolean {
            return oldItem.equals(newItem)
        }
    }



}