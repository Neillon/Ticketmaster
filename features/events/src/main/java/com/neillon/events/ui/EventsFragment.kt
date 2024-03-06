package com.neillon.events.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import com.neillon.common.delegates.viewBinding
import com.neillon.common.exts.hide
import com.neillon.common.exts.launchAndCollectIn
import com.neillon.common.exts.show
import com.neillon.events.databinding.FragmentEventsBinding
import com.neillon.events.ui.model.EventUI
import com.neillon.events.ui.viewmodel.EventsViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class EventsFragment : Fragment() {

    private val binding by viewBinding(FragmentEventsBinding::inflate)
    private val eventsAdapter by lazy { EventsAdapter() }

    private val eventsViewModel by viewModel<EventsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupBindings()

        eventsViewModel.state.launchAndCollectIn(viewLifecycleOwner) { state ->
            when (state) {
                is EventsViewModel.State.Data -> setupForDataState(state.items)
                EventsViewModel.State.Empty -> eventsViewModel.doAction(EventsViewModel.Action.Load)
                EventsViewModel.State.Loading -> setupForLoadingState()
                is EventsViewModel.State.Error -> showErrorMessage(state.message)
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setupBindings(): Any = with(binding) {
        eventsSearchView.setOnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                if (event.rawX >= eventsSearchView.right - eventsSearchView.compoundDrawables[2].bounds.width()) {
                    eventsSearchView.setText("")
                    return@setOnTouchListener true
                }
            }
            return@setOnTouchListener false
        }
        eventsSearchView.addTextChangedListener { eventsAdapter.filter(it.toString()) }
    }

    private fun setupForDataState(items: List<EventUI>) = with(binding) {
        eventsLoadingIndicator.hide()
        eventsSearchView.show()
        eventsRecyclerView.show()
        eventsRecyclerView.adapter = eventsAdapter
        eventsAdapter.setEvents(items)
    }

    private fun setupForLoadingState() = with(binding) {
        eventsSearchView.hide()
        eventsRecyclerView.hide()
        eventsLoadingIndicator.show()
    }

    private fun showErrorMessage(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }

    companion object {
        @JvmStatic
        fun newInstance() = EventsFragment()
    }
}