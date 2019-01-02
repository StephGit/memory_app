package ch.stephgit.memory.onboarding

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.*
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import ch.stephgit.memory.MainActivity
import ch.stephgit.memory.MemoryApp
import ch.stephgit.memory.R
import com.google.firebase.auth.FirebaseAuth

class LoginFragment : Fragment() {

    private lateinit var mAuth: FirebaseAuth

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
        setHasOptionsMenu(false)

        val view = inflater.inflate(R.layout.fragment_login, container, false)
        mAuth = FirebaseAuth.getInstance()

        if (mAuth.currentUser != null) {
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

        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                progressBar.visibility = View.GONE
                if (task.isSuccessful) {
                    (requireActivity().application as MemoryApp).setCurrentUser(mAuth.currentUser!!)
                    startActivity(MainActivity.newIntent(requireContext()))
                } else {
                    Toast.makeText(requireContext(), task.exception!!.message, Toast.LENGTH_SHORT).show()
                }
            }
    }

}
