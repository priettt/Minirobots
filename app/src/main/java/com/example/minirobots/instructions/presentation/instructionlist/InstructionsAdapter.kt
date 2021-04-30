package com.example.minirobots.instructions.presentation.instructionlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.minirobots.R
import com.example.minirobots.databinding.ItemInstructionBinding
import com.example.minirobots.instructions.domain.entities.Instruction

class InstructionsAdapter(
    private val onItemClickListener: (Int) -> Unit
) : ListAdapter<Instruction, InstructionsAdapter.InstructionViewHolder>(InstructionDiffCallback) {

    class InstructionViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemInstructionBinding.bind(view)

        fun bind(instruction: Instruction, onItemClickListener: (Int) -> Unit) {
            bindModifier(instruction)
            bindInstruction(instruction)
            itemView.setOnClickListener {
                onItemClickListener(adapterPosition)
            }
        }

        private fun bindInstruction(instruction: Instruction) {
            binding.instructionText.text = instruction.name
            binding.instructionImage.setImageResource(instruction.imageDrawable)
        }

        private fun bindModifier(instruction: Instruction) {
            instruction.modifier?.let {
                binding.modifierInfo.visibility = View.VISIBLE
                binding.modifierText.text = it.text
                binding.modifierImage.setImageResource(it.imageDrawable)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InstructionViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_instruction, parent, false)
        return InstructionViewHolder(view)
    }

    override fun onBindViewHolder(holder: InstructionViewHolder, position: Int) {
        holder.bind(getItem(position), onItemClickListener)
    }
}

object InstructionDiffCallback : DiffUtil.ItemCallback<Instruction>() {
    override fun areItemsTheSame(oldItem: Instruction, newItem: Instruction): Boolean {
        return oldItem.name == newItem.name && oldItem.modifier?.text == newItem.modifier?.text
    }

    override fun areContentsTheSame(oldItem: Instruction, newItem: Instruction): Boolean {
        return oldItem.name == newItem.name && oldItem.modifier?.text == newItem.modifier?.text
    }
}