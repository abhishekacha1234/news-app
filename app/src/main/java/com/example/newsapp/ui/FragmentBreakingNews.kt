package com.example.newsapp.ui

import android.content.Context
import android.icu.util.ULocale.Category
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.R
import com.example.newsapp.adapters.ArticleAdapter
import com.example.newsapp.adapters.ItemClicklistner
import com.example.newsapp.db.Article
import com.example.newsapp.mvvm.NewsDatabase
import com.example.newsapp.mvvm.NewsRepo
import com.example.newsapp.mvvm.NewsViewModel
import com.example.newsapp.mvvm.NewsViewModelFac
import com.example.newsapp.wrapper.Resource
import de.hdodenhof.circleimageview.CircleImageView

abstract class FragmentBreakingNews : Fragment(), ItemClicklistner {

    private lateinit var viewModel: NewsViewModel
    private lateinit var newsAdapter: ArticleAdapter
    private lateinit var rv: RecyclerView
    private lateinit var pb: ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_breaking_news, container, false)




        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)
                }

            // Initialize RecyclerView and ProgressBar here
            rv = view.findViewById(R.id.rvBreakingNews)
            pb = view.findViewById(R.id.paginationProgressBar)

            setUpViewModel()
            setUpRecyclerView()

            val cm =
                requireContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val nInfo = cm.activeNetworkInfo
            if (nInfo != null && nInfo.isConnected) {
                loadCategortNews()
            }

            return view
        }

    abstract fun loadCategortNews()

    fun setUpViewModel() {







            val dao = NewsDatabase.getInstance(requireActivity()).newsDao
            val repository = NewsRepo(dao)
            val factory = NewsViewModelFac(repository, requireActivity().application)
            viewModel = ViewModelProvider(this, factory)[NewsViewModel::class.java]
        }


    private fun CategoryNews() {
        viewModel.breakingNews.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is Resource.Success -> {
                    hideLoadingProgressBar()
                    response.data?.let { newsResponse ->
                        newsAdapter.setlist(newsResponse.articles)
                    }
                }
                is Resource.Error -> {
                    hideLoadingProgressBar()
                    response.message?.let { message ->
                        Log.i("BREAKING FRAG", message.toString())
                    }
                }
                is Resource.Loading -> displayProgressBar()
            }
        })
    }

    private fun setUpRecyclerView() {
        newsAdapter = ArticleAdapter()
        newsAdapter.setItemClickListener(this)
        rv.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        }
    }

    override fun onItemClicked(position: Int, article: Article) {
        // Implement navigation to another activity or fragment here
    }

    private fun displayProgressBar() {
        pb.visibility = View.VISIBLE
    }

    private fun hideLoadingProgressBar() {
        pb.visibility = View.INVISIBLE
    }
}
