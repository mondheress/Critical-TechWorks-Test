package com.criticaltechwork.bbcnews.presentation.main


import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.criticaltechwork.bbcnews.data.model.NewsResponse
import com.criticaltechwork.bbcnews.databinding.ItemMainBinding

class MainAdapter(
    private val onHeadlineClick: (NewsResponse.Article) -> Unit
) : RecyclerView.Adapter<MainAdapter.ViewHolder>() {

    private var items = listOf<NewsResponse.Article>()

    fun submitList(newList: List<NewsResponse.Article>) {
        items = newList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemMainBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val article = items[position]
        holder.binding.itemHeadLineTitle.text = article.title
        holder.binding.itemHeadLineDescription.text = article.description
        val imageUrl = article.urlToImage

        if (imageUrl.isNullOrBlank()) {
            holder.binding.itemHeadLineImage.setImageResource(android.R.drawable.ic_menu_gallery)
            holder.binding.itemHeadLineImage.scaleType = ImageView.ScaleType.FIT_XY
        } else {

            Glide.with(holder.itemView.context)
                .load(imageUrl)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(android.R.drawable.ic_menu_report_image)
                .error(android.R.drawable.ic_menu_gallery)
                .centerCrop()
                .into(holder.binding.itemHeadLineImage)

        }
    holder.itemView.setOnClickListener { onHeadlineClick(article) }
    }

    override fun getItemCount() = items.size

    class ViewHolder(val binding: ItemMainBinding) : RecyclerView.ViewHolder(binding.root)
}