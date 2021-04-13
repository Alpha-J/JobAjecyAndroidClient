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

class HomeRecyclerItemViewModel(context: Context): ViewModel() {
    private var viewModelDataList=MutableLiveData<ArrayList<HomeRecyclerItemViewModelData>>()


    init {
        viewModelScope.launch {
            initializeContent(context)
        }
    }

    private suspend fun initializeContent(context: Context){
            withContext(Dispatchers.Default) {
                val jobFileString =
                    getJsonDataFromAsset(context, "employer_and_employee_mock_data.json")

                val dateFile = getJsonDataFromAsset(context, "MOCK_DATA.json")

                val nameFile =
                    getJsonDataFromAsset(context, "employer_and_employee_name_mock_data.json")

                val gson = Gson()
                val listJobType = object : TypeToken<List<SampleData>>() {}.type

                val jobs: List<SampleData> = gson.fromJson(jobFileString, listJobType)
                //persons.forEachIndexed { idx, person -> Log.i("data", "> Item $idx:\n$person") }

                val datesType = object : TypeToken<List<DateFromJson>>() {}.type
                val dateList: List<DateFromJson> = gson.fromJson(dateFile, datesType)

                val namesType = object : TypeToken<List<NamesData>>() {}.type
                val namesList: List<NamesData> = gson.fromJson(nameFile, namesType)


                val list = ArrayList<HomeRecyclerItemViewModelData>()
                for (i in 0 until 50) {
                    val data = HomeRecyclerItemViewModelData()
                    data.companyName = namesList[i].company_name
                    data.jobDescription = jobs[i].description
                    data.expanded = false
                    data.requested = false
                    data.jobType = jobs[i].skill_level
                    data.neededSkills = jobs[i].description
                    data.skillRate = i.rem(5).toFloat()
                    data.uploadedDate = dateList[i].date
                    data.id = i.toLong()
                    list.add(data)
                }

                viewModelDataList.postValue(list)
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