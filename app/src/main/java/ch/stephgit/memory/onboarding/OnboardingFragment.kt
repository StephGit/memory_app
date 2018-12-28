package ch.stephgit.memory.onboarding

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import ch.stephgit.memory.R

class OnboardingFragment: Fragment() {

    private lateinit var callback: OnboardingFlow

    companion object {
        fun newFragment(): Fragment = OnboardingFragment()
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        callback = context as? OnboardingFlow ?: throw RuntimeException("Missing OnbordingFlow implementation")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_onboarding, container, false)
        setHasOptionsMenu(false)
        view.findViewById<Button>(R.id.btn_goto_login).setOnClickListener { callback.goToLogin() }
        view.findViewById<Button>(R.id.btn_goto_registration).setOnClickListener { callback.goToRegistration() }
        return view
    }
}