package com.example.findyourapplication

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class EmployerProfileViewModel(val context: Context): ViewModel() {
    private var dataList=MutableLiveData<ArrayList<EmployerProfileViewModelData>>()

    init {

        val itemString=ArrayList<String>()
        val itemIcons=ArrayList<Int>()
        val itemData=ArrayList<String>()
        val list=ArrayList<EmployerProfileViewModelData>()

        itemIcons.add(R.drawable.ic_factory)
        itemIcons.add(R.drawable.ic_username_icon)
        itemIcons.add(R.drawable.ic_password)
        itemIcons.add(R.drawable.ic_name)
        itemIcons.add(R.drawable.ic_name)
        itemIcons.add(R.drawable.ic_envelope)
        itemIcons.add(R.drawable.ic_phone)

        itemString.add(context.resources.getString(R.string.company_name))
        itemString.add(context.resources.getString(R.string.username))
        itemString.add(context.resources.getString(R.string.password))
        itemString.add(context.resources.getString(R.string.first_name))
        itemString.add(context.resources.getString(R.string.last_name))
        itemString.add(context.resources.getString(R.string.email))
        itemString.add(context.resources.getString(R.string.phone_number))

        //needs changes
        itemData.add("asjkdfhka")
        itemData.add("Alpha")
        itemData.add("Arsalan2016")
        itemData.add("Ali")
        itemData.add("Seraji")
        itemData.add("aseraji2012@gmail.com")
        itemData.add("+9370158850")

        for(i in 0 until 7){
            val data=EmployerProfileViewModelData()
            data.itemIcon=itemIcons[i]
            data.itemHeaderText=itemString[i]
            data.itemData=itemData[i]
            data.id=i.toLong()
            list.add(data)
        }

        dataList.value=list
    }

    fun getDataForObservation():MutableLiveData<ArrayList<EmployerProfileViewModelData>>{
        return dataList
    }

    fun changeData(index:Int,str:String){
        dataList.value!![index].itemData=str
    }



}