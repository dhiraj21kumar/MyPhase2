package com.example.myphase2.ui.news

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myphase2.R
import java.util.ArrayList

class NewsListAdapter( private val listner: NewsItemClicked): RecyclerView.Adapter<NewsViewHolder>(){

    private val items: ArrayList<News> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_news, parent, false)
        //        view.setOnClickListener{
//            listner.onItemClicked(items[viewHolder.adapterPosition])
//        }
        return NewsViewHolder(view)
//        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_news, parent, false)
//        val viewHolder = NewsViewHolder(view)
//        view.setOnClickListener{
//            listner.onItemClicked(items[viewHolder.adapterPosition])
//        }
//        return viewHolder
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val currentItem = items[position]
//        holder.cardView.setOnClickListener{
//            listner.onItemClicked(items[holder.adapterPosition])
//        }
        holder.title.text = currentItem.title
        holder.author.text = currentItem.author
        Glide.with(holder.itemView.context).load(currentItem.imageUrl).into(holder.image)
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

class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val title: TextView = itemView.findViewById(R.id.NewsTitle)
    val image: ImageView = itemView.findViewById(R.id.NewsImage)
    val author: TextView = itemView.findViewById(R.id.NewsAuthor)
}

interface NewsItemClicked {
    fun onItemClicked(item: News)
}