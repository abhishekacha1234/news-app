package com.example.newsapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.newsapp.R
import com.example.newsapp.Utils
import com.example.newsapp.db.Source
import com.example.newsapp.mvvm.NewsDatabase
import com.example.newsapp.mvvm.NewsRepo
import com.example.newsapp.mvvm.NewsViewModel
import com.example.newsapp.mvvm.NewsViewModelFac


class FragmentArticle : Fragment() {

    lateinit var viewModel: NewsViewModel


    lateinit var args : FragmentArticleArgs

    var stringCheck = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_article, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        (activity as AppCompatActivity).supportActionBar?.setTitle("Article View")



        val dao = NewsDatabase.getInstance(requireActivity()).newsDao
        val repository = NewsRepo(dao)
        val factory = NewsViewModelFac(repository, requireActivity().application)
        viewModel = ViewModelProvider(this, factory)[NewsViewModel::class.java]

        args = FragmentArticleArgs.fromBundle(requireArguments())

        // initialize the views of Art Frag


        val fab = view.findViewById<FloatingActionButton>(R.id.fab)

        val textTitle: TextView = view.findViewById(R.id.tvTitle)
        val tSource: TextView = view.findViewById(R.id.tvSource)
        val tDescription: TextView = view.findViewById(R.id.tvDescription)
        val tPubslishedAt: TextView = view.findViewById(R.id.tvPublishedAt)
        val imageView: ImageView = view.findViewById(R.id.articleImage)

        val source = Source(args.article.source!!.id, args.article.source!!.name)

        textTitle.setText(args.article.title)
        tSource.setText(source.name)
        tDescription.setText(args.article.description)
        tPubslishedAt.setText(Utils.DateFormat(args.article.publishedAt))

        Glide.with(requireActivity()).load(args.article.urlToImage).into(imageView)

