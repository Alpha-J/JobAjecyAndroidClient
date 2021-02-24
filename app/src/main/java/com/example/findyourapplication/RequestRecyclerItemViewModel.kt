package com.example.findyourapplication

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class RequestRecyclerItemViewModel: ViewModel() {
    private var dataList=MutableLiveData<ArrayList<RequestRecyclerItemViewModelData>>()


    init {
        val list=ArrayList<RequestRecyclerItemViewModelData>()
        for(i in 0 until 10){
            val data=RequestRecyclerItemViewModelData()
            data.companyName="Company num $i"
            val formatter = SimpleDateFormat("yyyy / MM / dd")
            data.date=formatter.format(Calendar.getInstance().time)
            data.jobType="Job type $i"
            data.requestCondition="Pending"
            list.add(data)
        }
        dataList.value=list
    }

    fun getDataForObservation():MutableLiveData<ArrayList<RequestRecyclerItemViewModelData>>{
        return dataList
    }

    fun changeRequestCondition(i:Int,cnd:String){
        dataList.value!![i].requestCondition=cnd
    }

    fun getDataSize():Int{
        return dataList.value!!.size
    }

}