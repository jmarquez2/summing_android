package com.jrms.summing.ui.spend.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.jrms.summing.R
import com.jrms.summing.databinding.FragmentTwoOptionsQuestionBinding

abstract class TwoOptionsQuestionFragment(private val title : Int,
        private val optionOne : Int,
        private val optionTwo : Int) : Fragment(), View.OnClickListener {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentTwoOptionsQuestionBinding.inflate(inflater,container,
            false)
        binding.askTextView.setText(title)
        binding.optionOne.setText(optionOne)
        binding.optionTwo.setText(optionTwo)

        binding.optionOne.setOnClickListener(this)
        binding.optionTwo.setOnClickListener(this)

        return binding.root
    }

    override fun onClick(view: View) {
        if(view.id == R.id.optionOne){
            optionOneClick()
        }else if (view.id == R.id.optionTwo){
            optionTwoClick()
        }
    }


    abstract fun optionOneClick()
    abstract fun optionTwoClick()
}