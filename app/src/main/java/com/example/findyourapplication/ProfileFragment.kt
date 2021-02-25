package com.example.findyourapplication

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
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
    private var selectedItemNum=0
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
        binding.darkBackground.setOnClickListener {
            val fadeOut= AnimationUtils.loadAnimation(context,R.anim.fade_out)
            binding.darkBackground.startAnimation(fadeOut)
            binding.darkBackground.visibility=View.GONE
            binding.changeProfileDataEdittext.text=null
        }
        binding.profileCancelBtn.setOnClickListener {
            val fadeOut= AnimationUtils.loadAnimation(context,R.anim.fade_out)
            binding.darkBackground.startAnimation(fadeOut)
            binding.darkBackground.visibility=View.GONE
            binding.changeProfileDataEdittext.text=null
        }
        onAccept()
    }

    private fun observeViewModel(){
        employeeProfileViewModel.getDataForObservation().observe(viewLifecycleOwner,{
            employeeProfileFragmentAdopter.addMultipleViewsAndSubmitList(it)
        })
    }

    private fun onAccept(){
        binding.profileApplyBtn.setOnClickListener {
            acceptChange(selectedItemNum,binding.changeProfileDataEdittext.text.toString())
            val fadeOut= AnimationUtils.loadAnimation(context,R.anim.fade_out)
            binding.darkBackground.startAnimation(fadeOut)
            binding.darkBackground.visibility=View.GONE
            binding.changeProfileDataEdittext.text=null
        }
    }

    private fun acceptChange(i:Int,str:String){
        employeeProfileViewModel.getDataForObservation().observe(viewLifecycleOwner,{
            employeeProfileViewModel.changeData(i,str)
            employeeProfileFragmentAdopter.addMultipleViewsAndSubmitList(it)
            employeeProfileFragmentAdopter.notifyItemChanged(i)
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

    @SuppressLint("SetTextI18n")
    override fun onItemClick(pos: Int) {
        selectedItemNum=pos
        when(pos){
            0->{
                val fadeIn= AnimationUtils.loadAnimation(context,R.anim.fade_in)
                binding.darkBackground.visibility=View.VISIBLE
                binding.darkBackground.startAnimation(fadeIn)
                binding.changeProfileDataTextview.text="Change Username"
                binding.changeProfileDataEdittext.text=null
            }
            1->{
                val fadeIn= AnimationUtils.loadAnimation(context,R.anim.fade_in)
                binding.darkBackground.visibility=View.VISIBLE
                binding.darkBackground.startAnimation(fadeIn)
                binding.changeProfileDataTextview.text="Change Password"
                binding.changeProfileDataEdittext.text=null
            }
            2->{
                val fadeIn= AnimationUtils.loadAnimation(context,R.anim.fade_in)
                binding.darkBackground.visibility=View.VISIBLE
                binding.darkBackground.startAnimation(fadeIn)
                binding.changeProfileDataTextview.text="Change first name"
                binding.changeProfileDataEdittext.text=null
            }
            3->{
                val fadeIn= AnimationUtils.loadAnimation(context,R.anim.fade_in)
                binding.darkBackground.visibility=View.VISIBLE
                binding.darkBackground.startAnimation(fadeIn)
                binding.changeProfileDataTextview.text="Change last name"
                binding.changeProfileDataEdittext.text=null
            }
            4->{
                val fadeIn= AnimationUtils.loadAnimation(context,R.anim.fade_in)
                binding.darkBackground.visibility=View.VISIBLE
                binding.darkBackground.startAnimation(fadeIn)
                binding.changeProfileDataTextview.text="Change email"
                binding.changeProfileDataEdittext.text=null
            }
            5->{
                val fadeIn= AnimationUtils.loadAnimation(context,R.anim.fade_in)
                binding.darkBackground.visibility=View.VISIBLE
                binding.darkBackground.startAnimation(fadeIn)
                binding.changeProfileDataTextview.text="Change phone number"
                binding.changeProfileDataEdittext.text=null
            }
        }
    }

    override fun onApplyClick(pos: Int) {

    }

    override fun onUploadResumeClick() {

    }
}