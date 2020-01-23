package com.gabrieldchartier.compendia.recycler_views

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.*
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.bumptech.glide.request.RequestOptions
import com.gabrieldchartier.compendia.R
import com.gabrieldchartier.compendia.models.Comic
import com.gabrieldchartier.compendia.models.ComicWithData
import kotlinx.android.synthetic.main.layout_comic_cover_list_item.view.*

class HorizontalComicCoverListAdapter(
        private val interaction: Interaction? = null,
        private val requestManager: RequestManager,
        private val requestOptions: RequestOptions
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ComicWithData>() {

        override fun areItemsTheSame(oldItem: ComicWithData, newItem: ComicWithData): Boolean {
            return oldItem.comic.pk == newItem.comic.pk
        }

        override fun areContentsTheSame(oldItem: ComicWithData, newItem: ComicWithData): Boolean {
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
                requestManager = requestManager,
                requestOptions = requestOptions
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ComicCoverViewHolder -> {
                holder.bind(differ.currentList[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun submitList(list: List<ComicWithData>?) {
        val newList = list?.toMutableList()
        differ.submitList(newList)
    }

    class ComicCoverViewHolder
    constructor(
            itemView: View,
            val requestManager: RequestManager,
            val requestOptions: RequestOptions,
            private val interaction: Interaction?
    ) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: ComicWithData) = with(itemView) {
            itemView.setOnClickListener {
                interaction?.onItemSelected(adapterPosition, item)
            }

            //todo fix this later
            requestOptions.override(585, 900)

            requestManager
                    .load(item.comic.cover)
                    .apply(requestOptions)
                    .transition(withCrossFade())
                    .into(itemView.comicListItemCover)

        }
    }

    interface Interaction {
        fun onItemSelected(position: Int, item: ComicWithData)
    }
}