package com.example.myphase2.viewmodel

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.myphase2.BottomActivity
import com.example.myphase2.R
import com.example.myphase2.databinding.FragmentLoginBinding
import com.google.firebase.auth.FirebaseAuth
import android.app.ProgressDialog as ProgressDialog

class LoginFragment : Fragment(R.layout.fragment_login) {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private lateinit var progressDialog: ProgressDialog
    private lateinit var firebaseAuth: FirebaseAuth

    private var email = ""
    private var password = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnRegLogin.setOnClickListener{
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToRegisterFragment())
        }

        progressDialog = ProgressDialog(requireContext())
        progressDialog.setTitle("Please Wait")
        progressDialog.setMessage("Logging In...")
        progressDialog.setCanceledOnTouchOutside(false)

        firebaseAuth = FirebaseAuth.getInstance()
        checkUser()

        binding.buttonLogin.setOnClickListener{
            validateData()
        }

    }

    private fun validateData() {
        email = binding.emailEt.text.toString().trim()
        password = binding.passwordEt.text.toString().trim()

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            binding.emailEt.error = "Invalid Email Format"
        }
        else if (TextUtils.isEmpty(password)){
            binding.passwordEt.error = "Please Enter Password"
        }
        else{
            firebaseLogin()
        }
    }

    private fun firebaseLogin() {
        progressDialog.show()
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                progressDialog.dismiss()
                val firebaseUser = firebaseAuth.currentUser
                val email = firebaseUser!!.email
                Toast.makeText(requireContext(),"Logged In as $email", Toast.LENGTH_SHORT).show()
                val intent = Intent (activity, BottomActivity::class.java)
                activity?.startActivity(intent)
//                findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToProfileFragment2())
            }
            .addOnFailureListener{ e->
                progressDialog.dismiss()
                Toast.makeText(requireContext(),"Login Failed due to ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun checkUser() {
        val firebaseUser = firebaseAuth.currentUser
        if (firebaseUser != null){
            val intent = Intent (activity, BottomActivity::class.java)
            activity?.startActivity(intent)
//            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToMobileNavigation())

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}