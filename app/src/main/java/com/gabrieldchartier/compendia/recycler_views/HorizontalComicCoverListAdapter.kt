package com.gabrieldchartier.compendia.recycler_views

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.*
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.gabrieldchartier.compendia.R
import com.gabrieldchartier.compendia.models.Comic
import kotlinx.android.synthetic.main.layout_comic_cover_list_item.view.*

class HorizontalComicCoverListAdapter(
        private val interaction: Interaction? = null,
        private val requestManager: RequestManager
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Comic>() {

        override fun areItemsTheSame(oldItem: Comic, newItem: Comic): Boolean {
            return oldItem.pk == newItem.pk
        }

        override fun areContentsTheSame(oldItem: Comic, newItem: Comic): Boolean {
            return oldItem == newItem
        }

    }

    private val differ = AsyncListDiffer(
            ComicCoverRecyclerChangeCallback(this),
            AsyncDifferConfig.Builder(DIFF_CALLBACK).build()
    )

    internal inner class ComicCoverRecyclerChangeCallback(private val adapter: HorizontalComicCoverListAdapter): ListUpdateCallback {
        override fun onChanged(position: Int, count: Int, payload: Any?) {
            adapter.notifyItemRangeChanged(position, count, payload)
        }

        override fun onMoved(fromPosition: Int, toPosition: Int) {
            adapter.notifyDataSetChanged()
        }

        override fun onInserted(position: Int, count: Int) {
            adapter.notifyItemRangeChanged(position, count)
        }

        override fun onRemoved(position: Int, count: Int) {
            adapter.notifyDataSetChanged()
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return ComicCoverViewHolder(
                LayoutInflater.from(parent.context).inflate(
                        R.layout.layout_comic_cover_list_item,
                        parent,
                        false
                ),
                interaction = interaction,
                requestManager = requestManager
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ComicCoverViewHolder -> {
                holder.bind(differ.currentList.get(position))
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun submitList(list: List<Comic>?) {
        val newList = list?.toMutableList()
        differ.submitList(newList)
    }

    class ComicCoverViewHolder
    constructor(
            itemView: View,
            val requestManager: RequestManager,
            private val interaction: Interaction?
    ) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: Comic) = with(itemView) {
            itemView.setOnClickListener {
                interaction?.onItemSelected(adapterPosition, item)
            }

            requestManager
                    .load(item.cover)
                    .transition(withCrossFade())
                    .into(itemView.comicListItemCover)

        }
    }

    interface Interaction {
        fun onItemSelected(position: Int, item: Comic)
    }
}