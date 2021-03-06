package ch.stephgit.memory.ui.onboarding

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.*
import android.widget.EditText
import android.widget.ProgressBar
import ch.stephgit.memory.R
import ch.stephgit.memory.di.Injector
import ch.stephgit.memory.persistence.repository.UserRepository
import kotlinx.android.synthetic.main.fragment_registration.*
import javax.inject.Inject


class RegistrationFragment: Fragment() {

    @Inject
    lateinit var userRepository: UserRepository

    private lateinit var callback: OnboardingFlow

    private lateinit var etUsername: EditText
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var progressBar: ProgressBar

    private lateinit var username: String
    private lateinit var password: String
    private lateinit var email: String

    companion object {
        fun newFragment(): Fragment = RegistrationFragment()
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        callback = context as? OnboardingFlow ?: throw RuntimeException("Missing OnbordingFlow implementation")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Injector.appComponent.inject(this)
        setHasOptionsMenu(true)

        val view = inflater.inflate(R.layout.fragment_registration, container, false)

        etUsername = view.findViewById(R.id.et_username)
        etEmail = view.findViewById(R.id.et_email)
        etPassword = view.findViewById(R.id.et_password)
        progressBar = view.findViewById(R.id.progressBar)

        return view
    }


    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.onboarding_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.action_next) {
            if (isValid()) {
                username = etUsername.text.toString().trim()
                email = etEmail.text.toString().trim()
                password = etPassword.text.toString().trim()

                progressBar.visibility = View.VISIBLE
                // TODO use UserViewModel
                userRepository.registerUser(email, password, username)
                callback.goToAgb()
            }
            return true
        }
        return super.onOptionsItemSelected(item)
    }

//    private fun registerUser() {
//        mAuth.createUserWithEmailAndPassword(email, password)
//            .addOnCompleteListener { task ->
//                progressBar.visibility = View.GONE
//                if (task.isSuccessful) {
//                    createPlayer(mAuth.currentUser!!)
//                    callback.goToAgb()
//                } else {
//                    if (task.exception is FirebaseAuthUserCollisionException) {
//                        Toast.makeText(
//                            requireContext(),
//                            "You are already register. Please log in.",
//                            Toast.LENGTH_SHORT
//                        ).show()
//                    } else {
//                        Toast.makeText(requireContext(), task.exception!!.message, Toast.LENGTH_SHORT).show()
//                    }
//                }
//            }
//    }

//    private fun createPlayer(currentUser: FirebaseUser) {
//        val app = (requireActivity().application as MemoryApp)
////        app.setCurrentUser(currentUser)
//        app.saveUserInformations(username, null)
//    }


    private fun isValid(): Boolean {
        return when {
            etUsername.text.trim().isBlank() -> {
                et_username.error = "Username can't be empty!"
                false
            }
            etEmail.text.trim().isBlank() -> {
                et_email.error = "Email can't be empty!"
                false
            }
            etPassword.text.trim().isBlank() -> {
                et_password.error = "Password can't be empty!"
                false
            }
            else -> true
        }
    }

}