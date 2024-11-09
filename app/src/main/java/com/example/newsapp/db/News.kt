package com.example.newsapp.db

data class News(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)