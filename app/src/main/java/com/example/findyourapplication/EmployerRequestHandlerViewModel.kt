package com.example.findyourapplication

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class EmployerRequestHandlerViewModel:ViewModel() {
    private var dataList=MutableLiveData<ArrayList<EmployerRequestHandlerViewModelData>>()

    init {
        val list=ArrayList<EmployerRequestHandlerViewModelData>()
        for(i in 0 until 25){
            val data=EmployerRequestHandlerViewModelData()
            data.candidateFirstName="candidate$i"
            data.candidateLastName="candidateLastname$i"
            data.id=i.toLong()
            data.reqDate="DATE"
            data.requestCondition="Pending"
            list.add(data)
        }
        dataList.value=list
    }

    fun getDataForObservation():MutableLiveData<ArrayList<EmployerRequestHandlerViewModelData>>{
        return dataList
    }

    fun removeItem(i:Int){
        dataList.value!!.remove(dataList.value!![i])
    }

}