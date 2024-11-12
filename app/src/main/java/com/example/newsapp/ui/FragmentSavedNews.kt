package com.example.newsapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapiapp.adapters.SavedArticleAdapter
import com.example.newsapp.R
import com.example.newsapp.mvvm.NewsDatabase
import com.example.newsapp.mvvm.NewsRepo
import com.example.newsapp.mvvm.NewsViewModel
import com.example.newsapp.mvvm.NewsViewModelFac


abstract class FragmentSavedNews : Fragment(), MenuProvider {

    lateinit var viewModel: NewsViewModel
    lateinit var newsAdapter: SavedArticleAdapter
    lateinit var rv: RecyclerView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_saved_news, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity).supportActionBar?.setTitle("Saved News")


        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.CREATED)

        setHasOptionsMenu(true)




        rv = view.findViewById(R.id.rvSavedNews)


        val dao = NewsDatabase.getInstance(requireActivity()).newsDao
        val repository = NewsRepo(dao)
        val factory = NewsViewModelFac(repository, requireActivity().application)
        viewModel = ViewModelProvider(this, factory)[NewsViewModel::class.java]
        newsAdapter = SavedArticleAdapter()

        viewModel.getSavedNews.observe(viewLifecycleOwner, Observer {

            newsAdapter.setlist(it)

            setUpRecyclerView()


        })


    }

    private fun setUpRecyclerView() {
        TODO("Not yet implemented")
    }
}

