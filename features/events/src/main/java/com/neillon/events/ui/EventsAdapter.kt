package com.neillon.events.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.load
import coil.transform.RoundedCornersTransformation
import com.neillon.events.databinding.ItemTicketBinding
import com.neillon.events.ui.model.EventUI
import java.util.Locale

class EventsAdapter : RecyclerView.Adapter<EventsAdapter.EventsViewHolder>() {
    private val diffCallback = object: DiffUtil.ItemCallback<EventUI>() {
            override fun areItemsTheSame(oldItem: EventUI, newItem: EventUI): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: EventUI, newItem: EventUI): Boolean =
            oldItem == newItem
    }
    private var differ: AsyncListDiffer<EventUI> = AsyncListDiffer(this, diffCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventsViewHolder {
        val binding = ItemTicketBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EventsViewHolder(binding)
    }

    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: EventsViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    fun setEvents(items: List<EventUI>) {
        differ.submitList(items)
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