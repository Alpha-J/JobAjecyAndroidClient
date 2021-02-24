package com.example.findyourapplication

import android.animation.Animator
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewAnimationUtils
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.dx.dxloadingbutton.lib.AnimationType
import com.example.findyourapplication.databinding.FragmentSignUpBinding


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class SignUpFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding:FragmentSignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding=DataBindingUtil.inflate<FragmentSignUpBinding>(inflater,R.layout.fragment_sign_up, container, false)
        init()
        btnController()
        return binding.root
    }

    private fun btnController() {
        binding.apply {
            gotoLoginPage.setOnClickListener {
                findNavController().navigate(R.id.action_signUpFragment_to_login)
            }

            dropdownview.setOnSpinnerItemSelectedListener<String>{ oldIndex, oldItem, newIndex, newItem ->

            }

            //needs changes after connecting to backend
            signUpLoadingBtn.setOnClickListener {
                signUpLoadingBtn.startLoading()
                Handler(Looper.getMainLooper()).postDelayed({
                        signUpLoadingBtn.loadingSuccessful()
                        signUpLoadingBtn.cancelLoading()

                },3000)

            }
            signUpLoadingBtn.animationEndAction={
                if(it==AnimationType.SUCCESSFUL){
                    toNextPage()
                }
                if(it==AnimationType.FAILED){
                    signUpLoadingBtn.reset()
                }
            }

        }
    }

    private fun init(){

    }

    private fun toNextPage() {

        val location=IntArray(2)
        binding.signUpLoadingBtn.getLocationOnScreen(location)
        val cx = (location[0] + binding.signUpLoadingBtn.width/2)
        val cy = (location[1] + binding.signUpLoadingBtn.height/2)

        val animator = ViewAnimationUtils.createCircularReveal(binding.animateView, cx ,cy, 100f, resources.displayMetrics.heightPixels * 1.2f)
        animator.duration = 500
        animator.interpolator = AccelerateDecelerateInterpolator()
        binding.animateView.visibility = View.VISIBLE
        animator.start()
        animator.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {
                binding.signUpLoadingBtn.postDelayed({
                    binding.signUpLoadingBtn.reset()
                    binding.animateView.visibility = View.INVISIBLE
                },200)
            }

            override fun onAnimationEnd(animation: Animator) {
                startActivity(Intent(activity, EmployeeActivity::class.java))
            }

            override fun onAnimationCancel(animation: Animator) {

            }

            override fun onAnimationRepeat(animation: Animator) {

            }
        })

    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SignUpFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onDestroy() {
        super.onDestroy()


    }
}