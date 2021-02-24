package com.example.findyourapplication

import android.animation.ObjectAnimator
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.Guideline
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.example.findyourapplication.databinding.FragmentHomeBinding

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class HomeFragment : Fragment(),EmployeeHomeRecyclerAdopter.OnRecyclerViewRequestItemClickListener {

    private lateinit var homeRecyclerAdopter: EmployeeHomeRecyclerAdopter
    private lateinit var viewModel:HomeRecyclerItemViewModel
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding:FragmentHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding=DataBindingUtil.inflate(inflater,R.layout.fragment_home, container, false)
        init()

        viewModelObserver()
        return binding.root
    }


    private fun init(){
        viewModel=HomeRecyclerItemViewModel()
        homeRecyclerAdopter= EmployeeHomeRecyclerAdopter(requireContext(),this)
        binding.employeeHomeRecyclerView.adapter=homeRecyclerAdopter
    }

    private fun viewModelObserver(){
        viewModel.getDataForObservation().observe(viewLifecycleOwner, {
            it?.let {
                homeRecyclerAdopter.submitList(it)
            }
        })
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                HomeFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }

    override fun onItemClick(pos: Int) {
            viewModel.getDataForObservation().observe(viewLifecycleOwner, {
                it?.let {
                    if(!viewModel.getData(pos).expanded){
                        viewModel.changeData(pos,true)
                        homeRecyclerAdopter.submitList(viewModel.getDataForObservation().value)
                        homeRecyclerAdopter.notifyItemChanged(pos)
                        homeRecyclerAdopter.notifyDataSetChanged()
                    }
                    else{
                        viewModel.changeData(pos,false)
                        homeRecyclerAdopter.submitList(viewModel.getDataForObservation().value)
                        homeRecyclerAdopter.notifyItemChanged(pos)
                        homeRecyclerAdopter.notifyDataSetChanged()
                    }
                }
            })
    }

    override fun onApplyClick(pos: Int) {
        viewModel.getDataForObservation().observe(viewLifecycleOwner, {
            it?.let {
                if(!viewModel.getData(pos).requested){
                    viewModel.requestForJob(pos,true)
                    homeRecyclerAdopter.submitList(viewModel.getDataForObservation().value)
                    homeRecyclerAdopter.notifyItemChanged(pos)
                    homeRecyclerAdopter.notifyDataSetChanged()
                }
                else{
                    viewModel.requestForJob(pos,false)
                    homeRecyclerAdopter.submitList(viewModel.getDataForObservation().value)
                    homeRecyclerAdopter.notifyItemChanged(pos)
                    homeRecyclerAdopter.notifyDataSetChanged()
                }
            }
        })
    }


}