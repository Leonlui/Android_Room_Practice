package com.example.roomdatabasepractice.fragments.list

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.recyclerview.widget.AdapterListUpdateCallback
import androidx.recyclerview.widget.RecyclerView
import com.example.roomdatabasepractice.model.User
import com.example.roomdatabasepractice.databinding.CustomRowBinding

class ListAdapter : RecyclerView.Adapter<ListAdapter.MyViewHolder>() {

    private var userList = emptyList<User>()
    private var onDeleteItem: ((User) -> Unit)? = null

    class MyViewHolder(val binding: CustomRowBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            CustomRowBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = userList[position]
        holder.binding.idTxt.text = currentItem.id.toString()
        holder.binding.firstNameTxt.text = currentItem.firstName
        holder.binding.lastNameTxt.text = currentItem.lastName
        holder.binding.ageTxt.text = "(${currentItem.age.toString()})"

        holder.itemView.setOnClickListener {
            val action = ListFragmentDirections.actionListFragmentToUpdateFragment(currentItem)
            holder.itemView.findNavController().navigate(action)
        }

        holder.binding.deleteButton.setOnClickListener {
            onDeleteItem?.invoke(currentItem)
            userList.drop(position)
            notifyItemRemoved(position)
        }
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    fun setData(users: List<User>) {
        this.userList = users
        notifyDataSetChanged()
    }

    fun setOnDeleteItem(callback: (User) -> Unit) {
        this.onDeleteItem = callback
    }
}