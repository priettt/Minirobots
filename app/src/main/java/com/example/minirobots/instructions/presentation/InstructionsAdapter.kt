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
import com.example.minirobots.instructions.domain.entities.Instruction

class InstructionsAdapter :
    ListAdapter<Instruction, InstructionsAdapter.InstructionViewHolder>(InstructionDiffCallback) {

    class InstructionViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemInstructionBinding.bind(view)
        private var currentInstruction: Instruction? = null

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

        fun bind(instruction: Instruction) {
            currentInstruction = instruction
            binding.instructionText.text = instruction.name
            binding.instructionImage.setImageResource(instruction.imageDrawable)
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

object InstructionDiffCallback : DiffUtil.ItemCallback<Instruction>() {
    override fun areItemsTheSame(oldItem: Instruction, newItem: Instruction): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Instruction, newItem: Instruction): Boolean {
        return oldItem.name == newItem.name
    }
}