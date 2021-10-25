package com.example.minirobots.instructionsList.presentation.editinstructionmenu

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.minirobots.R
import com.example.minirobots.databinding.ItemEditInstructionBinding
import com.example.minirobots.instructionsList.domain.entities.UIModifier

class EditInstructionAdapter(
    private val onItemClickListener: (UIModifier) -> Unit
) : ListAdapter<UIModifier, EditInstructionAdapter.EditInstructionViewHolder>(EditInstructionDiffCallback) {

    class EditInstructionViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemEditInstructionBinding.bind(view)

        fun bind(modifier: UIModifier, onItemClickListener: (UIModifier) -> Unit) {
            binding.instructionName.text = modifier.name
            binding.instructionImage.setImageResource(modifier.imageDrawable)
            itemView.setOnClickListener {
                onItemClickListener(modifier)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EditInstructionViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_edit_instruction, parent, false)
        return EditInstructionViewHolder(view)
    }

    override fun onBindViewHolder(holder: EditInstructionViewHolder, position: Int) {
        holder.bind(getItem(position), onItemClickListener)
    }
}

object EditInstructionDiffCallback : DiffUtil.ItemCallback<UIModifier>() {
    override fun areItemsTheSame(oldItem: UIModifier, newItem: UIModifier): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: UIModifier, newItem: UIModifier): Boolean {
        return oldItem.name == newItem.name
    }
}