package com.example.findyourapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.findyourapplication.databinding.FragmentEmployerHomeBinding

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class EmployerHomeFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding:FragmentEmployerHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding=DataBindingUtil.inflate(layoutInflater,R.layout.fragment_employer_home, container, false)

        return binding.root
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
}