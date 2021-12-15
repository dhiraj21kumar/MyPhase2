package com.example.myphase2.ui.news

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RemoteViews
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBindings
import com.bumptech.glide.Glide
import com.example.myphase2.R
import com.example.myphase2.databinding.ItemNewsBinding
import java.util.ArrayList

class NewsListAdapter( private val listner: NewsItemClicked): RecyclerView.Adapter<NewsListAdapter.NewsViewHolder>() {

    private val items: ArrayList<News> = ArrayList()

    inner class NewsViewHolder(val binding: ItemNewsBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {

        val binding = ItemNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsViewHolder(binding)
//        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_news, parent, false)
//        val viewHolder = NewsViewHolder(view)
//        view.setOnClickListener{
//            listner.onItemClicked(items[viewHolder.adapterPosition])
//        }
//        return viewHolder
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        with(holder){
            with(items[position]){
                binding.NewsTitle.text = this.title
                binding.NewsAuthor.text = this.author
                Glide.with(holder.itemView.context).load(items[position].imageUrl).into(binding.NewsImage)
            }
        }
    }

    override fun getItemCount(): Int {

        return items.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateNews(updatedNews: ArrayList<News>) {

        items.clear()
        items.addAll(updatedNews)

        notifyDataSetChanged()
    }
}

interface NewsItemClicked {
    fun onItemClicked(item: News)
}