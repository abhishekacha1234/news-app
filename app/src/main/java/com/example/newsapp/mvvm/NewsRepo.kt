package com.example.newsapp.mvvm

import androidx.lifecycle.LiveData
import com.example.newsapp.db.SavedArticle
import com.example.newsapp.service.RetrofitInstance
import kotlinx.coroutines.Dispatchers

class NewsRepo(val newsDao:NewsDao) {


    fun getAllSavedNews() : LiveData<List<SavedArticle>>{
        return newsDao.getAllNews()

    }

    fun getNewsById() : LiveData<SavedArticle> {
        return newsDao.getNewsById()
    }




    suspend fun getBreakingNews(code: String, pageNumber: Int) = RetrofitInstance.api.getBreakingNews(code, pageNumber)



    // getting category news

    suspend fun getCategoryNews(code: String) = RetrofitInstance.api.getByCategory(code)


    // to delete ALl news

    fun deleteAll(){
        newsDao.deleteAll()
    }

    fun insertNews(savedArt: SavedArticle) {

    }


}