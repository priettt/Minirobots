package com.example.minirobots.instructionsList.presentation.instructionlist

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.*
import androidx.recyclerview.widget.RecyclerView
import com.example.minirobots.R
import com.example.minirobots.databinding.FragmentInstructionListBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@AndroidEntryPoint
class InstructionListFragment : Fragment(R.layout.fragment_instruction_list) {

    // TODO: Try using hiltNavGraphViewModels to inject the VMs. That would avoid having a Singleton VM with the scope of the whole app.
    private val viewModel: InstructionsListViewModel by activityViewModels()
    private val adapter = InstructionsAdapter { index ->
        viewModel.onInstructionClicked(index)
    }

    private lateinit var binding: FragmentInstructionListBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupBinding(view)
        observeViewModel()
        viewModel.onViewCreated()
    }

    private fun setupBinding(view: View) {
        binding = FragmentInstructionListBinding.bind(view)
        binding.instructionsList.adapter = adapter
        binding.addInstructionButton.setOnClickListener {
            viewModel.onAddButtonClicked()
        }
        binding.sendInstructionsButton.setOnClickListener {
            viewModel.onSendInstructionsButtonClicked()
        }
        itemTouchHelper.attachToRecyclerView(binding.instructionsList)
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.instructions.collect {
                    adapter.submitList(it)
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.errorState.collect {
                    when (it) {
                        InstructionsListError.FunctionNotStoredError -> showError(getString(R.string.instructions_list_function_not_stored_error))
                        InstructionsListError.InvalidProgramError -> showError(getString(R.string.instructions_list_invalid_program_error))
                        InstructionsListError.EmptyProgramError -> showError(getString(R.string.instructions_list_empty_program_error))
                        InstructionsListError.NoError -> hideError()
                    }
                }
            }
        }

        viewModel.events
            .flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
            .onEach(::handleEvent)
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun showError(message: String) {
        binding.errorText.visibility = View.VISIBLE
        binding.errorText.text = message
        binding.sendInstructionsButton.isEnabled = false
    }

    private fun hideError() {
        binding.errorText.visibility = View.GONE
        binding.sendInstructionsButton.isEnabled = true
    }

    private fun handleEvent(event: Event) {
        when (event) {
            Event.ShowAddInstructionMenu -> showAddInstructionMenu()
            Event.ShowEditInstructionMenu -> showEditInstructionMenu()
            Event.ScrollToBottom -> scrollToBottom()
            Event.ShowFunctionStoredScreen -> showFunctionStoredScreen()
            Event.ShowNetworkFailureScreen -> showNetworkFailureScreen()
            Event.ShowProgramSentScreen -> showProgramSentScreen()
            Event.ShowLoadingScreen -> showLoading()
        }
    }

    private fun showLoading() {
        binding.loadingView.progressOverlay.visibility = View.VISIBLE
        binding.instructionsList.visibility = View.INVISIBLE
    }

    private fun hideLoading() {
        binding.loadingView.progressOverlay.visibility = View.GONE
        binding.instructionsList.visibility = View.VISIBLE
    }

    private fun scrollToBottom() {
        binding.instructionsList.smoothScrollToPosition(adapter.itemCount)
    }

    private fun showAddInstructionMenu() {
        findNavController().navigate(
            InstructionListFragmentDirections.actionInstructionListFragmentToAddInstructionFragment()
        )
    }

    private fun showEditInstructionMenu() {
        findNavController().navigate(
            InstructionListFragmentDirections.actionInstructionListFragmentToEditInstructionFragment()
        )
    }

    private fun showProgramSentScreen() {
        hideLoading()
        findNavController().navigate(
            InstructionListFragmentDirections.actionInstructionListFragmentToInstructionsSentFragment()
        )
    }

    private fun showNetworkFailureScreen() {
        hideLoading()
        findNavController().navigate(
            InstructionListFragmentDirections.actionInstructionListFragmentToNetworkFailureFragment()
        )
    }

    private fun showFunctionStoredScreen() {
        hideLoading()

    }

    private val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.Callback() {
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
    })

}
