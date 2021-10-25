package com.example.minirobots.instructionsList.presentation.instructionlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.minirobots.R
import com.example.minirobots.databinding.ItemInstructionBinding
import com.example.minirobots.instructionsList.domain.entities.UIAction
import com.example.minirobots.instructionsList.domain.entities.UIInstruction
import com.example.minirobots.instructionsList.domain.entities.UIModifier

class InstructionsAdapter(
    private val onItemClickListener: (Int) -> Unit
) : ListAdapter<UIInstruction, InstructionsAdapter.InstructionViewHolder>(InstructionDiffCallback) {

    class InstructionViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemInstructionBinding.bind(view)

        fun bind(instruction: UIInstruction, onItemClickListener: (Int) -> Unit) {
            bindAction(instruction.action)
            bindModifier(instruction.modifier)
            itemView.setOnClickListener {
                onItemClickListener(adapterPosition)
            }
        }

        private fun bindAction(instruction: UIAction) {
            binding.instructionText.text = instruction.name
            binding.instructionImage.setImageResource(instruction.imageDrawable)
        }

        private fun bindModifier(modifier: UIModifier?) {
            if (modifier != null) {
                binding.modifierInfo.visibility = View.VISIBLE
                binding.modifierText.text = modifier.name
                binding.modifierImage.setImageResource(modifier.imageDrawable)
            } else {
                binding.modifierInfo.visibility = View.GONE
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

object InstructionDiffCallback : DiffUtil.ItemCallback<UIInstruction>() {
    override fun areItemsTheSame(oldItem: UIInstruction, newItem: UIInstruction): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: UIInstruction, newItem: UIInstruction): Boolean {
        return oldItem == newItem
    }
}