package ch.stephgit.memory.ui.onboarding

import android.arch.lifecycle.Observer
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import ch.stephgit.memory.R
import ch.stephgit.memory.di.Injector
import ch.stephgit.memory.persistence.repository.UserRepository
import ch.stephgit.memory.ui.main.MainActivity
import javax.inject.Inject

class LoginFragment : Fragment() {

    @Inject
    lateinit var userRepository: UserRepository

    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var progressBar: ProgressBar

    private lateinit var callback: OnboardingFlow

    companion object {
        fun newFragment(): Fragment = LoginFragment()
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        callback = context as? OnboardingFlow ?: throw RuntimeException("Missing OnbordingFlow implementation")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Injector.appComponent.inject(this)
        setHasOptionsMenu(false)

        val view = inflater.inflate(R.layout.fragment_login, container, false)

        if (userRepository.getCurrentUser() != null) {
            startActivity(MainActivity.newIntent(requireContext()))
        }

        etEmail = view.findViewById(R.id.et_email)
        etPassword = view.findViewById(R.id.et_password)
        progressBar = view.findViewById(R.id.progressBar)

        view.findViewById<Button>(R.id.btn_goto_login).setOnClickListener { loginUser() }
        view.findViewById<Button>(R.id.btn_goto_registration).setOnClickListener { callback.goToRegistration() }

        return view
    }

    private fun loginUser() {
        val email: String = etEmail.text.toString().trim()
        val password: String = etPassword.text.toString().trim()

        if (email.isBlank()) {
            etEmail.error = "Email is required"
        }

        if (password.isBlank()) {
            etPassword.error = "Password is required"
        }

        progressBar.visibility = View.VISIBLE

        userRepository.login(email, password).observe(this, Observer {
            progressBar.visibility = View.GONE
            if (it!!) {
                startActivity(MainActivity.newIntent(requireContext()))
            } else {
                Toast.makeText(requireContext(), "err", Toast.LENGTH_SHORT).show()
            }
        })

    }

}
