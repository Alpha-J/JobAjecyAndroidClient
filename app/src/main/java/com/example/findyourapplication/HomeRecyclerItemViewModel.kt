package com.example.findyourapplication

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeRecyclerItemViewModel: ViewModel() {
    private var viewModelDataList=MutableLiveData<ArrayList<HomeRecyclerItemViewModelData>>()

    init {
        val list=ArrayList<HomeRecyclerItemViewModelData>()
        for(i in 0 until 50){
            val data=HomeRecyclerItemViewModelData()
            data.header="This is header num::"+i.toString()
            data.description="This is a long description for this part of recycler view to check out the specs.\nThis is" +
                    "the $i'th item to be checked!"
            data.expanded=false
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

    fun getDataForObservation():MutableLiveData<ArrayList<HomeRecyclerItemViewModelData>>{
        return viewModelDataList
    }
}