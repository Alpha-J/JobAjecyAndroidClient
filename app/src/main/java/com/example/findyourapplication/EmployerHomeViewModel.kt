package com.example.findyourapplication

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.*
import kotlin.collections.ArrayList

class EmployerHomeViewModel: ViewModel() {

    private var dataList=MutableLiveData<ArrayList<EmployerHomeViewModelData>>()

    init {
        val list=ArrayList<EmployerHomeViewModelData>()
        for(i in 0 until 50){
            val data=EmployerHomeViewModelData()
            data.companyName="This is company $i "
            data.jobDescription="This is a long description for this part of recycler view to check out the specs.\nThis is" +
                    "the $i'th item to be checked!"
            data.jobType="Engineering"
            data.neededSkills="Skill_1,Skill_1,Skill_1Skill_1,Skill_1\nSkill_1,Skill_1,Skill_1,Skill_1\n" +
                    "Skill_1,Skill_1,Skill_1,Skill_1"
            data.skillRate=i.rem(5).toFloat()
            data.uploadedDate= Calendar.getInstance().time
            data.id=i.toLong()
            list.add(data)
        }
        dataList.value=list

    }

    fun getDataSize():Int{
        return dataList.value!!.size
    }

    fun getDataForObservation():MutableLiveData<ArrayList<EmployerHomeViewModelData>>{
        return dataList
    }

    fun removeItem(i:Int){
        dataList.value!!.remove(dataList.value!![i])
    }

}