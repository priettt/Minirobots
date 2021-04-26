package com.example.minirobots.instructions.presentation

import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.*
import androidx.recyclerview.widget.RecyclerView
import com.example.minirobots.R
import com.example.minirobots.databinding.FragmentInstructionListBinding
import com.example.minirobots.utilities.observeIn
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class InstructionListFragment : Fragment(R.layout.fragment_instruction_list), DialogInterface.OnDismissListener {

    // TODO: Try nesting InstructionList and AddInstruction fragments, scoping a ViewModel to that nested graph,
    //  and using hiltNavGraphViewModels to inject the VMs. That avoids having a Singleton VM with the scope of the whole app.
    private val viewModel: InstructionsListViewModel by activityViewModels()
    private val adapter = InstructionsAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupBinding(view)
        observeViewModel()
     }

    private fun setupBinding(view: View) {
        val binding = FragmentInstructionListBinding.bind(view)
        binding.instructionsList.adapter = adapter
        binding.addButton.setOnClickListener {
            viewModel.onAddButtonClicked()
        }
        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(binding.instructionsList)
    }

    private fun observeViewModel() {
        viewModel.instructions.observe(viewLifecycleOwner, { instructions ->
            adapter.submitList(instructions.toMutableList())
        })

        viewModel.eventsFlow
            .onEach {
                when (it) {
                    Event.ShowAddInstructionPopUp -> showAddInstructionPopUp()
                    Event.ShowError -> showError()
                }
            }
            .observeIn(this)
    }

    private fun showError() {
        Toast.makeText(requireContext(), "Couldn't retrieve instructions.", Toast.LENGTH_SHORT).show()
    }

    private fun showAddInstructionPopUp() {
        findNavController().navigate(
            InstructionListFragmentDirections.actionInstructionsScreenFragmentToAddInstructionFragment()
        )
    }

    override fun onDismiss(dialog: DialogInterface?) {
        viewModel.onAddSheetDismissed()
    }

    private val itemTouchHelperCallback = object : ItemTouchHelper.Callback() {
        override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {
            // Specify the directions of movement
            return makeMovementFlags(UP or DOWN, START)
        }

        override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
            // Notify your adapter that an item is moved from x position to y position
            viewModel.onItemMoved(viewHolder.adapterPosition, target.adapterPosition)
            return true
        }

        override fun isLongPressDragEnabled() = true

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            viewModel.onItemDeleted(viewHolder.adapterPosition)
        }
    }

}
