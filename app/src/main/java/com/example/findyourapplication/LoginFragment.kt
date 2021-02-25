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
import com.example.findyourapplication.databinding.FragmentLoginBinding

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class Login : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding:FragmentLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding=DataBindingUtil.inflate<FragmentLoginBinding>(inflater,R.layout.fragment_login, container, false)
        btnController()
        return binding.root
    }



    private fun btnController(){
        binding.apply {
            gotoSignupPage.setOnClickListener{
                findNavController().navigate(R.id.action_login_to_signUpFragment)
            }
            //need to be changed after connecting to back end
            loginLoadingBtn.setOnClickListener{
                loginLoadingBtn.startLoading()
                Handler(Looper.getMainLooper()).postDelayed({
                    loginLoadingBtn.loadingSuccessful()
                    loginLoadingBtn.cancelLoading()
                },3000)

            }
            loginLoadingBtn.animationEndAction={
                if(it== AnimationType.SUCCESSFUL){
                    toNextPage()
                }
                if(it== AnimationType.FAILED){
                    loginLoadingBtn.reset()
                }
            }

        }
    }

    private fun toNextPage() {
        val location=IntArray(2)
        binding.loginLoadingBtn.getLocationOnScreen(location)
        val cx = (location[0] + binding.loginLoadingBtn.width/2)
        val cy = (location[1] + binding.loginLoadingBtn.height/2)

        val animator = ViewAnimationUtils.createCircularReveal(binding.animateView, cx, cy, 0f, resources.displayMetrics.heightPixels * 1.2f)
        animator.duration = 350
        animator.interpolator = AccelerateDecelerateInterpolator()
        binding.animateView.visibility = View.VISIBLE
        animator.start()
        animator.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {
                binding.loginLoadingBtn.postDelayed({
                    binding.loginLoadingBtn.reset()
                    binding.animateView.visibility = View.INVISIBLE
                },200)
            }

            override fun onAnimationEnd(animation: Animator) {
                //startActivity(Intent(activity, EmployeeActivity::class.java))
                startActivity(Intent(activity,EmployerActivity::class.java))
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
                Login().apply {
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