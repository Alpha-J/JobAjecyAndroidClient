package com.example.findyourapplication

data class EmployeeHomeData(val date:String,val companyName:String,val jobType:String,val skill_name:String,val description:String){
}
data class EmployeeRequest(val date:String,val companyName:String,val jobType:String,val reqCondition:String){}

data class EmployeeProfileData(val username:String,val password:String,val fName:String,val lName:String,val phoneNumber:String,val email:String){}

data class SampleData(val skill_name:String,val skill_level:String,val description:String){
}
data class NamesData(val first_name:String,val last_name:String,val company_name:String,val description:String){}

data class DateFromJson(val date:String){}