package com.example.myphase2.ui.bookmark

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myphase2.R
import com.example.myphase2.db.DBHelper
import com.example.myphase2.ui.news.News
import java.util.ArrayList


class BookmarkListAdapter( private val listner: BookmarkItemClicked): RecyclerView.Adapter<BookmarkViewHolder>(){

    private val items: ArrayList<News> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookmarkViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_news, parent, false)
        //        view.setOnClickListener{
//            listner.onItemClicked(items[viewHolder.adapterPosition])
//        }
        return BookmarkViewHolder(view)
//        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_news, parent, false)
//        val viewHolder = NewsViewHolder(view)
//        view.setOnClickListener{
//            listner.onItemClicked(items[viewHolder.adapterPosition])
//        }
//        return viewHolder
    }

    override fun onBindViewHolder(holder: BookmarkViewHolder, position: Int) {
        val currentItem = items[position]
//        holder.cardView.setOnClickListener{
//            listner.onItemClicked(items[holder.adapterPosition])
//        }
        holder.title.text = currentItem.title
        holder.author.text = currentItem.author
        Glide.with(holder.itemView.context).load(currentItem.imageUrl).into(holder.image)
        holder.bookmark.setOnClickListener{
            val db = DBHelper(holder.itemView.context, null)
            if(holder.bookmark.isChecked){
                db.deleteNews(currentItem.url)
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

class BookmarkViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val title: TextView = itemView.findViewById(R.id.NewsTitle)
    val image: ImageView = itemView.findViewById(R.id.NewsImage)
    val author: TextView = itemView.findViewById(R.id.NewsAuthor)
    val bookmark: CheckBox =  itemView.findViewById(R.id.checkboxBookmark)
}

interface BookmarkItemClicked {
    fun onItemClicked(item: News)
}