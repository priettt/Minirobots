package com.example.minirobots.instructionsList.presentation.addinstructionmenu

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.minirobots.R
import com.example.minirobots.databinding.ItemAddInstructionBinding
import com.example.minirobots.instructionsList.domain.entities.UIInstruction

class AddInstructionAdapter(
    private val onItemClickListener: (UIInstruction) -> Unit
) :
    ListAdapter<UIInstruction, AddInstructionAdapter.AddInstructionViewHolder>(
        AddInstructionDiffCallback
    ) {

    class AddInstructionViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemAddInstructionBinding.bind(view)

        fun bind(instruction: UIInstruction) {
            binding.instructionName.text = instruction.name
            binding.instructionImage.setImageResource(instruction.imageDrawable)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddInstructionViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_add_instruction, parent, false)
        return AddInstructionViewHolder(view)
    }

    override fun onBindViewHolder(holder: AddInstructionViewHolder, position: Int) {
        val instruction = getItem(position)
        //TODO: research what's the best way to tell the viewModel that an instruction has been clicked
        holder.itemView.setOnClickListener { onItemClickListener(instruction) }
        holder.bind(instruction)
    }
}

object AddInstructionDiffCallback : DiffUtil.ItemCallback<UIInstruction>() {
    override fun areItemsTheSame(
        oldItem: UIInstruction,
        newItem: UIInstruction
    ): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(
        oldItem: UIInstruction,
        newItem: UIInstruction
    ): Boolean {
        return oldItem.name == newItem.name
    }
}