package com.example.findyourapplication

import android.icu.text.DateFormat.getDateInstance
import android.icu.text.SimpleDateFormat
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.text.DateFormat.getDateInstance
import java.util.*
import kotlin.collections.ArrayList

class HomeRecyclerItemViewModel: ViewModel() {
    private var viewModelDataList=MutableLiveData<ArrayList<HomeRecyclerItemViewModelData>>()

    init {
        val list=ArrayList<HomeRecyclerItemViewModelData>()
        for(i in 0 until 50){
            val data=HomeRecyclerItemViewModelData()
            data.companyName="This is company num : "+i.toString()
            data.jobDescription="This is a long description for this part of recycler view to check out the specs.\nThis is" +
                    "the $i'th item to be checked!"
            data.expanded=false
            data.requested=false
            data.jobType="Engineering"
            data.neededSkills="Skill_1,Skill_1,Skill_1Skill_1,Skill_1\nSkill_1,Skill_1,Skill_1,Skill_1\n" +
                    "Skill_1,Skill_1,Skill_1,Skill_1"
            data.skillRate=i.rem(5).toFloat()
            data.uploadedDate=Calendar.getInstance().time
            list.add(data)
        }
        viewModelDataList.value=list

    }

    fun getDataSize():Int{
        return viewModelDataList.value!!.size
    }


    fun getData(i:Int):HomeRecyclerItemViewModelData{
        return viewModelDataList.value!![i]
    }

    fun changeData(i:Int,expand:Boolean){
        viewModelDataList.value!![i].expanded=expand
    }

    fun requestForJob(i:Int,request:Boolean){
        viewModelDataList.value!![i].requested=request
    }

    fun getDataForObservation():MutableLiveData<ArrayList<HomeRecyclerItemViewModelData>>{
        return viewModelDataList
    }
}