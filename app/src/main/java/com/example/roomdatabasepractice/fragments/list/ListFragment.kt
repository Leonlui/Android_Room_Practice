package com.example.roomdatabasepractice.fragments.list

import android.app.AlertDialog
import android.os.Bundle
import android.os.ProxyFileDescriptorCallback
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.roomdatabasepractice.R
import com.example.roomdatabasepractice.viewmodel.UserViewModel
import com.example.roomdatabasepractice.databinding.FragmentListBinding
import com.example.roomdatabasepractice.model.User

class ListFragment : Fragment() {

    private lateinit var userViewModel: UserViewModel

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentListBinding.inflate(inflater, container, false)

        //Recyclerview
        val adapter = ListAdapter()
        adapter.setOnDeleteItem { deleteUser(it) }
        val recyclerView = binding.recyclerView
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        //UserViewModel
        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        userViewModel.readAllData.observe(viewLifecycleOwner, Observer { user ->
            adapter.setData(user)
        })

        binding.floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_listFragment_to_addFragment)
        }

        //Add Menu
        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.delete_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_delete) {
            deleteAllUsers()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteAllUsers() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes") { _, _ ->
            userViewModel.deleteAllUser()
            Toast.makeText(
                requireContext(),
                "Successfully removed everything",
                Toast.LENGTH_SHORT
            )
                .show()
        }
        builder.setNegativeButton("No") { _, _ -> }
        builder.setTitle("Delete all users?")
        builder.setMessage("Are you sure you want to delete all users?")
        builder.create().show()
    }

    private fun deleteUser(user: User) {
        userViewModel.deleteUser(user)
    }
}