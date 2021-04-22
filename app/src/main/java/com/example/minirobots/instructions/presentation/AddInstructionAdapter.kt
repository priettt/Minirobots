package com.example.minirobots.instructions.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.minirobots.R
import com.example.minirobots.databinding.ItemAddInstructionBinding
import com.example.minirobots.instructions.domain.actions.AddInstructionItem

class AddInstructionAdapter :
    ListAdapter<AddInstructionItem, AddInstructionAdapter.AddInstructionViewHolder>(
        AddInstructionDiffCallback
    ) {

    class AddInstructionViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemAddInstructionBinding.bind(view)
        private var currentInstruction: AddInstructionItem? = null

        init {
            view.setOnClickListener {
                Toast.makeText(
                    view.context,
                    currentInstruction?.name.toString(),
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
        }

        fun bind(addInstructionItem: AddInstructionItem) {
            currentInstruction = addInstructionItem
            binding.instructionName.text = addInstructionItem.name
            binding.instructionImage.setImageResource(R.drawable.common_full_open_on_phone)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddInstructionViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_add_instruction, parent, false)
        return AddInstructionViewHolder(view)
    }

    override fun onBindViewHolder(holder: AddInstructionViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

object AddInstructionDiffCallback : DiffUtil.ItemCallback<AddInstructionItem>() {
    override fun areItemsTheSame(
        oldItem: AddInstructionItem,
        newItem: AddInstructionItem
    ): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(
        oldItem: AddInstructionItem,
        newItem: AddInstructionItem
    ): Boolean {
        return oldItem.name == newItem.name
    }
}