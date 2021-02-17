package com.example.minirobots.instructions.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.minirobots.R
import com.example.minirobots.databinding.ItemInstructionBinding

class InstructionsAdapter :
    ListAdapter<InstructionItem, InstructionsAdapter.InstructionViewHolder>(InstructionDiffCallback) {

    class InstructionViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemInstructionBinding.bind(view)
        private var currentInstruction: InstructionItem? = null

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

        fun bind(instructionItem: InstructionItem) {
            currentInstruction = instructionItem
            binding.instructionText.text = instructionItem.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InstructionViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_instruction, parent, false)
        return InstructionViewHolder(view)
    }

    override fun onBindViewHolder(holder: InstructionViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

data class InstructionItem(val name: String)

object InstructionDiffCallback : DiffUtil.ItemCallback<InstructionItem>() {
    override fun areItemsTheSame(oldItem: InstructionItem, newItem: InstructionItem): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: InstructionItem, newItem: InstructionItem): Boolean {
        return oldItem.name == newItem.name
    }
}