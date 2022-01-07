package com.example.myphase2.ui.bookmark

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.browser.customtabs.CustomTabsIntent
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.example.myphase2.databinding.FragmentBookmarkBinding
import com.example.myphase2.databinding.FragmentNewsBinding
import com.example.myphase2.db.DBHelper
import com.example.myphase2.ui.news.MySingleton
import com.example.myphase2.ui.news.News
import com.example.myphase2.ui.news.NewsListAdapter
import java.util.ArrayList

class BookmarkFragment : Fragment(), BookmarkItemClicked {

    private lateinit var bookmarkViewModel: BookmarkViewModel
    private var _binding: FragmentBookmarkBinding? = null
    private lateinit var mAdapter: BookmarkListAdapter

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBookmarkBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.layoutManager = LinearLayoutManager(this.requireContext())
        mAdapter = BookmarkListAdapter(this)
        fetchData()
        binding.recyclerView.adapter = mAdapter

//        var checkboxBookmark = CheckBox(context)
//        checkboxBookmark.setOnCheckedChangeListener { buttonView, isChecked ->
//            Toast.makeText(this.requireContext(),isChecked.toString(), Toast.LENGTH_SHORT).show()
//
//        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun fetchData() {
        val newsArray = ArrayList<News>()
        val db = DBHelper(this.requireContext(), null)

        // below is the variable for cursor
        // we have called method to get
        // all names from our database
        // and add to name text view
        val cursor = db.getNews()

        // moving the cursor to first position and
        // appending value in the text view
        cursor!!.moveToFirst()
        if (!cursor.isLast) {
            val title = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.TITLE_COL))
            val author = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.AUTHOR_COL))
            val url = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.URL_COL))
            val image_url = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.IMAGE_URL_COL))
            newsArray.add(News(title, author, url, image_url))
        }

        // moving our cursor to next
        // position and appending values
        while(cursor.moveToNext()){
            val title = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.TITLE_COL))
            val author = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.AUTHOR_COL))
            val url = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.URL_COL))
            val image_url = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.IMAGE_URL_COL))
            newsArray.add(News(title,author,url,image_url))
        }

        // at last we close our cursor
        cursor.close()
        mAdapter.updateNews(newsArray)
    }
    override fun onItemClicked(item: News) {
        val builder = CustomTabsIntent.Builder()
        val customTabsIntent = builder.build()
        customTabsIntent.launchUrl(this.requireContext(), Uri.parse(item.url))
    }
}