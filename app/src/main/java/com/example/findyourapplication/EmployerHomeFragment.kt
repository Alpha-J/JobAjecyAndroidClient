package com.example.findyourapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.databinding.DataBindingUtil
import com.example.findyourapplication.databinding.FragmentEmployerHomeBinding

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class EmployerHomeFragment : Fragment(), EmployerHomeRecyclerAdopter.OnEmployerHomeRecyclerViewItemClickListener {
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding:FragmentEmployerHomeBinding
    private lateinit var employerHomeRecyclerAdopter: EmployerHomeRecyclerAdopter
    private lateinit var employerHomeViewModel:EmployerHomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding=DataBindingUtil.inflate(layoutInflater,R.layout.fragment_employer_home, container, false)
        init()
        observeData()
        return binding.root
    }

    private fun init(){
        employerHomeViewModel= EmployerHomeViewModel()
        employerHomeRecyclerAdopter= EmployerHomeRecyclerAdopter(this)
        binding.employerHomeRecyclerView.adapter=employerHomeRecyclerAdopter

        binding.uploadJobBtn.setOnClickListener {
            val fadeIn= AnimationUtils.loadAnimation(context,R.anim.fade_in)
            val fadeOut= AnimationUtils.loadAnimation(context,R.anim.fade_out)
            binding.uploadJobView.root.visibility=View.VISIBLE
            binding.uploadJobView.root.startAnimation(fadeIn)
            binding.uploadJobBtn.startAnimation(fadeOut)
            binding.uploadJobBtn.visibility=View.GONE

        }
        binding.uploadJobView.cancelUploadJobBtn.setOnClickListener {
            val fadeIn= AnimationUtils.loadAnimation(context,R.anim.fade_in)
            val fadeOut= AnimationUtils.loadAnimation(context,R.anim.fade_out)
            binding.uploadJobView.root.startAnimation(fadeOut)
            binding.uploadJobView.root.visibility=View.GONE
            binding.uploadJobBtn.startAnimation(fadeIn)
            binding.uploadJobBtn.visibility=View.VISIBLE
        }
    }

    private fun observeData(){
        employerHomeViewModel.getDataForObservation().observe(viewLifecycleOwner,{
            employerHomeRecyclerAdopter.addViewAndSubmitList(it)
        })
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            EmployerHomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onItemClick(pos: Int) {
        employerHomeViewModel.getDataForObservation().observe(viewLifecycleOwner,{
            employerHomeViewModel.removeItem(pos)
            employerHomeRecyclerAdopter.notifyItemRemoved(pos)
            employerHomeRecyclerAdopter.notifyItemRangeChanged(pos,employerHomeViewModel.getDataSize())
            employerHomeRecyclerAdopter.notifyDataSetChanged()
            employerHomeRecyclerAdopter.addViewAndSubmitList(it)
        })
    }
}