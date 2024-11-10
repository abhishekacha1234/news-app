package com.example.newsapp.mvvm

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.newsapp.db.SavedArticle


@Dao
interface NewsDao {

    @Insert
    suspend fun inserrtNews(savedArticle: SavedArticle)



    @Query("SELECT * FROM NEWSARTICLE")
    fun getAllNews() : LiveData<List<SavedArticle>>

    @Query("SELECT * FROM NEWSARTICLE")
    fun getNewsById(): LiveData<SavedArticle>

    @Query("DELETE FROM NEWSARTICLE")
    fun deleteAll()
}