package com.example.findyourapplication

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import kotlin.collections.ArrayList

class EmployerHomeViewModel(context: Context): ViewModel() {

    private var dataList=MutableLiveData<ArrayList<EmployerHomeViewModelData>>()

    init {
        viewModelScope.launch {
            loadData(context)
        }
    }

    private suspend fun loadData(context:Context){
        withContext(Dispatchers.Default){
            val list=ArrayList<EmployerHomeViewModelData>()

            val jobFileString = getJsonDataFromAsset(context, "employer_and_employee_mock_data.json")

            val dateFile=getJsonDataFromAsset(context,"MOCK_DATA.json")

            val nameFile=getJsonDataFromAsset(context,"employer_and_employee_name_mock_data.json")

            val gson = Gson()
            val listJobType = object : TypeToken<List<SampleData>>() {}.type

            val jobs: List<SampleData> = gson.fromJson(jobFileString, listJobType)
            //persons.forEachIndexed { idx, person -> Log.i("data", "> Item $idx:\n$person") }

            val datesType=object : TypeToken<List<DateFromJson>>() {}.type
            val dateList:List<DateFromJson> = gson.fromJson(dateFile,datesType)

            val namesType=object : TypeToken<List<NamesData>>() {}.type
            val namesList:List<NamesData> = gson.fromJson(nameFile,namesType)
            //dateList.forEachIndexed{idx, dateList->Log.}

            for(i in 0 until 50){
                val data=EmployerHomeViewModelData()
                data.companyName=namesList[i].company_name
                data.jobDescription=jobs[i].description
                data.jobType=jobs[i].skill_name
                data.neededSkills=namesList[i].description
                data.skillRate=i.rem(5).toFloat()
                data.uploadedDate= dateList[i].date
                data.id=i.toLong()
                list.add(data)
            }
            dataList.postValue(list)
        }
    }

    private fun getJsonDataFromAsset(context: Context, fileName: String): String? {
        val jsonString: String
        try {
            jsonString = context.assets.open(fileName).bufferedReader().use { it.readText() }
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            return null
        }
        return jsonString
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