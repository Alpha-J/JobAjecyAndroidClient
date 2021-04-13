package com.example.findyourapplication

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EmployerRequestHandlerViewModel:ViewModel() {
    private var dataList=MutableLiveData<ArrayList<EmployerRequestHandlerViewModelData>>()

    init {
        viewModelScope.launch {
            loadData()
        }
    }

    private suspend fun loadData(){
        withContext(Dispatchers.Default){
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
            dataList.postValue(list)
        }

    }

    fun getDataForObservation():MutableLiveData<ArrayList<EmployerRequestHandlerViewModelData>>{
        return dataList
    }

    fun removeItem(i:Int){
        dataList.value!!.remove(dataList.value!![i])
    }

}