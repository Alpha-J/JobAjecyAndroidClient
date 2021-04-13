package com.example.findyourapplication

import android.media.Rating
import android.widget.TextView
import androidx.databinding.BindingAdapter


@BindingAdapter("jobDate")
fun TextView.setJobDate(item:HomeRecyclerItemViewModelData?){
    text=item!!.uploadedDate
}

@BindingAdapter("jobDescription")
fun TextView.setJobDescription(item:HomeRecyclerItemViewModelData?){
    text=item!!.jobDescription
}

@BindingAdapter("jobHeader")
fun TextView.setJobHeader(item:HomeRecyclerItemViewModelData?){
    text=item!!.companyName
}

@BindingAdapter("jobNeededSkillsDesc")
fun TextView.setJobNeededSkillsDesc(item:HomeRecyclerItemViewModelData?){
    text=item!!.neededSkills
}

@BindingAdapter("rating")
fun hyogeun.github.com.colorratingbarlib.ColorRatingBar.setRating(item:HomeRecyclerItemViewModelData?){
    rating=item!!.skillRate!!
}

@BindingAdapter("jobTypeDesc")
fun TextView.setJobTypeDesc(item:HomeRecyclerItemViewModelData?){
    text=item!!.jobType
}

