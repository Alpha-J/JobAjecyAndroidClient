package com.example.findyourapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.findyourapplication.databinding.FragmentProfileBinding

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class ProfileFragment : Fragment(),EmployeeProfileFragmentAdopter.OnRecyclerViewProfileItemClickListener {
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding:FragmentProfileBinding
    private lateinit var employeeProfileViewModel: EmployeeProfileViewModel
    private lateinit var employeeProfileFragmentAdopter: EmployeeProfileFragmentAdopter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding=DataBindingUtil.inflate(inflater,R.layout.fragment_profile, container, false)

        init()
        observeViewModel()
        return binding.root
    }

    private fun init(){
        employeeProfileViewModel= EmployeeProfileViewModel(requireContext())
        employeeProfileFragmentAdopter= EmployeeProfileFragmentAdopter(requireContext(),this)
        binding.employeeProfileRecyclerView.adapter=employeeProfileFragmentAdopter
    }

    private fun observeViewModel(){
        employeeProfileViewModel.getDataForObservation().observe(viewLifecycleOwner,{
            employeeProfileFragmentAdopter.addMultipleViewsAndSubmitList(it)
        })
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ProfileFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onItemClick(pos: Int) {

    }

    override fun onApplyClick(pos: Int) {

    }

    override fun onUploadResumeClick() {

    }
}