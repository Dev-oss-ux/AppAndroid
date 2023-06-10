package com.example.mobile

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.ListView
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.mobile.data.Post
import com.example.mobile.db.MovieDatabase


class HomeActivity : AppCompatActivity() {
    private lateinit var listPosts: ListView
    private var postsArray = ArrayList<Post>()
    private lateinit var adapter: PostsAdapter
    private var filteredPostsArray = ArrayList<Post>()
    private lateinit var movieDatabase: MovieDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)



        listPosts = findViewById(R.id.listPosts)

        movieDatabase = MovieDatabase(this)

        movieDatabase.writableDatabase



        postsArray = arrayListOf(
            Post("Babylon", "Note : 6.5", "Budget : 12.435.532 €", "Date de publication: 12-09-2022", "Inception est un film 1 de science-fiction d'action réalisé par Christopher Nolan en 2010...", R.drawable.image1),
            Post("Tempete", "Note : 7.5", "Budget : 11.435.532 €", "Date de publication: 102-02-2021", "Inception est un film 1 de science-fiction d'action réalisé par Christopher Nolan en 2010 ...", R.drawable.image2),
            Post("Hostalga", "Note : 5.5", "Budget : 2.435.532 €", "Date de publication: 01-09-2023", "Inception est un film 1 de science-fiction d'action réalisé par Christopher Nolan en 2010...", R.drawable.image3),
            Post("16 ans", "Note : 8.3", "Budget : 14.435.532 €", "Date de publication: 23-04-2012", "Inception est un film 1 de science-fiction d'action réalisé par Christopher Nolan en 2010...", R.drawable.image4),
            Post("Rascals", "Note : 7.2", "Budget : 23.435.532 €", "Date de publication: 11-05-2019", "Inception est un film 1 de science-fiction d'action réalisé par Christopher Nolan en 2010...", R.drawable.image5)
        )



        adapter = PostsAdapter(this, R.layout.item_post, postsArray)
        listPosts.adapter = adapter

        listPosts.setOnItemClickListener { _, _, position, _ ->
            val clickedPost = postsArray[position]
            Intent(this, PostDetailActivity::class.java).also {
                it.putExtra("titre", clickedPost.titre)
                it.putExtra("note", clickedPost.note)
                it.putExtra("budget", clickedPost.budget)
                it.putExtra("publication", clickedPost.publication)
                it.putExtra("description", clickedPost.description)
                startActivity(it)
            }
        }

        registerForContextMenu(listPosts)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.home_menu, menu)
        val searchItem = menu?.findItem(R.id.search)
        val searchView = searchItem?.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                if (newText.isEmpty()) {
                    clearSearchFilter()
                } else {
                    filterPostsByTitle(newText)
                }
                return true
            }
        })

        return super.onCreateOptionsMenu(menu)
    }

    private fun clearSearchFilter() {
        filteredPostsArray.clear()
        filteredPostsArray.addAll(postsArray)
        adapter.clear()
        adapter.addAll(filteredPostsArray)
        adapter.notifyDataSetChanged()
    }

    private fun filterPostsByTitle(title: String) {
        filteredPostsArray.clear()

        if (title.isNotEmpty()) {
            Toast.makeText(this, "a titre", Toast.LENGTH_SHORT).show()
            for (post in postsArray) {
                if (post.titre.contains(title, true)) {
                    filteredPostsArray.add(post)
                }
            }
        } else {
            Toast.makeText(this, "pas de titre", Toast.LENGTH_SHORT).show()
            filteredPostsArray.addAll(postsArray)
        }

        adapter.clear()
        // rafraichissement de la liste
        adapter.addAll(filteredPostsArray)
        adapter.notifyDataSetChanged()

    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.itemFavorites -> {
                val intent = Intent(this, FavoriteActivity::class.java)
                startActivity(intent)
            }
            R.id.itemAdd -> {
                Intent(this, AddMovieActivity::class.java).also {
                    startActivity(it)
                }
            }
            R.id.itemConfig -> {
                val intentProfil = Intent(this, ProfilActivity::class.java)
                val txtNom = "Mamadou Barry"
                val txtEmail = "barry@gmail.com"
                val txtPrenom = "Mamadou"
                val txtAge = ""
                val image = ""
                intentProfil.putExtra("email", txtEmail)
                intentProfil.putExtra("image", image)
                intentProfil.putExtra("nom", txtNom)
                intentProfil.putExtra("age", txtAge)
                intentProfil.putExtra("prenom", txtPrenom)
                startActivity(intentProfil)
            }
            R.id.itemLogout -> {
                showLogoutConfirmDialog()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showLogoutConfirmDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Confirmation")
        builder.setMessage("Voulez-vous vraiment quitter l'application ?")
        builder.setPositiveButton("OUI") { _, _ ->
            val editor = this.getSharedPreferences("app_state", Context.MODE_PRIVATE).edit()
            editor.remove("is_authenticated")
            editor.apply()
            finish()
        }
        builder.setNegativeButton("NON") { dialogInterface, _ ->
            dialogInterface.dismiss()
        }
        builder.setNeutralButton("Annuler") { dialogInterface, _ ->
            dialogInterface.dismiss()
        }

        val alertDialog: AlertDialog = builder.create()
        alertDialog.show()
    }
}
