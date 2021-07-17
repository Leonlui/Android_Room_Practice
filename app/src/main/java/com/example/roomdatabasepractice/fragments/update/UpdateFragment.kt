package com.example.roomdatabasepractice.fragments.update

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.roomdatabasepractice.R
import com.example.roomdatabasepractice.databinding.FragmentUpdateBinding
import com.example.roomdatabasepractice.model.User
import com.example.roomdatabasepractice.util.StringUtil
import com.example.roomdatabasepractice.viewmodel.UserViewModel

class UpdateFragment : Fragment() {

    private val args by navArgs<UpdateFragmentArgs>()
    private lateinit var userViewModel: UserViewModel
    private var _binding: FragmentUpdateBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentUpdateBinding.inflate(inflater, container, false)

        //viewModel
        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        //binding
        binding.updateFirstName.setText(args.currentUser.firstName)
        binding.updateLasttName.setText(args.currentUser.lastName)
        binding.updateAge.setText(args.currentUser.age.toString())

        binding.updateButton.setOnClickListener {
            updateItem()
        }

        return binding.root
    }

    private fun updateItem() {
        val firstName = binding.updateFirstName.text.toString()
        val lastName = binding.updateLasttName.text.toString()
        val age = binding.updateAge.text
        val id = args.currentUser.id
        if (StringUtil.inputCheck(firstName, lastName, age)) {
            //Create User Oject
            val updateUser = User(id, firstName, lastName, Integer.parseInt(age.toString()))
            //Update Current User
            userViewModel.updateUser(updateUser)
            Toast.makeText(requireContext(), "Successfully Updated User: ${id}", Toast.LENGTH_SHORT)
                .show()

            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        } else {
            Toast.makeText(requireContext(), "Please fill out the inputs", Toast.LENGTH_SHORT)
                .show()
        }
    }
}