package com.example.minirobots.instructions.presentation.editinstructionmenu

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.minirobots.R
import com.example.minirobots.databinding.ItemEditInstructionBinding
import com.example.minirobots.instructions.domain.entities.Modifier

class EditInstructionAdapter(
    private val onItemClickListener: (Modifier) -> Unit
) : ListAdapter<Modifier, EditInstructionAdapter.EditInstructionViewHolder>(EditInstructionDiffCallback) {

    class EditInstructionViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemEditInstructionBinding.bind(view)

        fun bind(modifier: Modifier) {
            binding.instructionName.text = modifier.text
            binding.instructionImage.setImageResource(modifier.imageDrawable)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EditInstructionViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_edit_instruction, parent, false)
        return EditInstructionViewHolder(view)
    }

    override fun onBindViewHolder(holder: EditInstructionViewHolder, position: Int) {
        val modifier = getItem(position)
        //TODO: research what's the best way to tell the viewModel that an instruction has been clicked
        holder.itemView.setOnClickListener { onItemClickListener(modifier) }
        holder.bind(modifier)
    }
}

object EditInstructionDiffCallback : DiffUtil.ItemCallback<Modifier>() {
    override fun areItemsTheSame(oldItem: Modifier, newItem: Modifier): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Modifier, newItem: Modifier): Boolean {
        return oldItem.text == newItem.text
    }
}