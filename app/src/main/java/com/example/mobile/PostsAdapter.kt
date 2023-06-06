package com.example.mobile

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import com.example.mobile.data.Post

class PostsAdapter(
    var mContext: Context,
    var resource: Int,
    var values: ArrayList<Post>
) : ArrayAdapter<Post>(mContext, resource, values) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val post = values[position]
        val itemView = LayoutInflater.from(mContext).inflate(resource, parent, false)
        val tvTitre = itemView.findViewById<TextView>(R.id.tvTitre)
        val imageShowPopup = itemView.findViewById<ImageView>(R.id.imageShowPopup)
        val tvDescription = itemView.findViewById<TextView>(R.id.tvDescription1)
        val imagePost = itemView.findViewById<ImageView>(R.id.imagePost)
        val share = itemView.findViewById<ImageView>(R.id.share)
        val likeButton = itemView.findViewById<ImageView>(R.id.likeButton)

        tvTitre.text = post.titre
        tvDescription.text = post.description
        imagePost.setImageResource(post.image)

        imageShowPopup.setOnClickListener {
            val popupMenu = PopupMenu(mContext, it)
            popupMenu.menuInflater.inflate(R.menu.list_popup_menu, popupMenu.menu)

            popupMenu.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.itemShow -> {
                        Intent(mContext, PostDetailActivity::class.java).also {
                            it.putExtra("titre", post.titre)
                            it.putExtra("publication", post.publication)
                            it.putExtra("budget", post.budget)
                            it.putExtra("note", post.note)
                            it.putExtra("description", post.description)
                            mContext.startActivity(it)
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
        likeButton.setOnClickListener {
            post.jaime++
            notifyDataSetChanged()
        }

        return itemView
    }
}
