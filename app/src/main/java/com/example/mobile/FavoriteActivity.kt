package com.example.mobile


import android.os.Bundle
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.example.mobile.data.Post


class FavoriteActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)

        val post = intent.getSerializableExtra("film") as? Post
        // Utilisez l'objet "post" comme requis dans votre activité FavoriteActivity
    }
}

