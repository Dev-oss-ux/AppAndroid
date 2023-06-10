package com.example.mobile

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import com.example.mobile.data.Post
import java.io.Serializable

class PostsAdapter(
    private val mContext: Context,
    private val resource: Int,
    private val values: ArrayList<Post>
) : ArrayAdapter<Post>(mContext, resource, values) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val post = values[position]
        val itemView = LayoutInflater.from(mContext).inflate(resource, parent, false)
        val tvTitre = itemView.findViewById<TextView>(R.id.tvTitre)
        val imageShowPopup = itemView.findViewById<ImageView>(R.id.imageShowPopup)
        val tvDescription = itemView.findViewById<TextView>(R.id.tvDescription1)
        val imagePost = itemView.findViewById<ImageView>(R.id.imagePost)
        val share = itemView.findViewById<ImageView>(R.id.share)
        val favorite = itemView.findViewById<ImageView>(R.id.favorite)

        tvTitre.text = post.titre
        tvDescription.text = post.description
        imagePost.setImageResource(post.image)

        imageShowPopup.setOnClickListener {
            val popupMenu = PopupMenu(mContext, it)
            popupMenu.menuInflater.inflate(R.menu.list_popup_menu, popupMenu.menu)

            popupMenu.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.itemShow -> {
                        Intent(mContext, PostDetailActivity::class.java).also { intent ->
                            intent.putExtra("titre", post.titre)
                            intent.putExtra("publication", post.publication)
                            intent.putExtra("budget", post.budget)
                            intent.putExtra("note", post.note)
                            intent.putExtra("description", post.description)
                            mContext.startActivity(intent)
                        }
                    }
                    R.id.itemDelete -> {
                        values.removeAt(position)
                        notifyDataSetChanged()
                    }
                }
                true
            }
            popupMenu.show()
        }

        share.setOnClickListener {
            val sendIntent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, "https://www.facebook.com/posts/${post.id}")
                type = "text/plain"
            }
            val shareIntent = Intent.createChooser(sendIntent, post.titre)
            mContext.startActivity(shareIntent)
        }

        favorite.setOnClickListener {
            post.jaime++
            toggleFavoriteColor(favorite, post.jaime)
        }

        // Mise à jour de la couleur du bouton "favorite" en fonction de l'état initial du post
        toggleFavoriteColor(favorite, post.jaime)

        return itemView
    }

    private fun toggleFavoriteColor(imageView: ImageView, likes: Int) {
        val color = if (likes % 2 == 1) Color.RED else Color.WHITE
        val colorFilter = PorterDuffColorFilter(color, PorterDuff.Mode.SRC_IN)
        imageView.colorFilter = colorFilter
    }
}
