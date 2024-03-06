package com.neillon.events.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.load
import coil.transform.RoundedCornersTransformation
import com.neillon.events.databinding.ItemTicketBinding
import com.neillon.events.ui.model.EventUI
import java.util.Locale

class EventsAdapter : RecyclerView.Adapter<EventsAdapter.EventsViewHolder>() {

    private var events: List<EventUI> = emptyList()
    private var filteredEvents: List<EventUI> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventsViewHolder {
        val binding = ItemTicketBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EventsViewHolder(binding)
    }

    override fun getItemCount(): Int = filteredEvents.size

    override fun onBindViewHolder(holder: EventsViewHolder, position: Int) {
        holder.bind(filteredEvents[position])
    }

    fun setEvents(items: List<EventUI>) {
        events = items
        filteredEvents = events
        notifyDataSetChanged()
    }

    fun filter(query: String) {
        if (query.isEmpty()) {
            filteredEvents = events
        }
        filteredEvents =
            events.filter { it.name.lowercase().trim().startsWith(query.lowercase().trim()) }
        notifyDataSetChanged()
    }

    inner class EventsViewHolder(private val binding: ItemTicketBinding) :
        ViewHolder(binding.root) {
        fun bind(eventUI: EventUI) {
            with(binding) {
                image.load(eventUI.imageUrl) {
                    crossfade(true)
                    transformations(
                        RoundedCornersTransformation(
                            topLeft = 8f,
                            topRight = 8f,
                            bottomLeft = 8f,
                            bottomRight = 8f
                        )
                    )
                }
                name.text = eventUI.name
                date.text = eventUI.date.toString()
                place.text = eventUI.place
                city.text = "${eventUI.city}, ${eventUI.state.uppercase(Locale.getDefault())}"
            }
        }
    }
}