import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.example.mobile.Movie
import com.example.mobile.data.User
import java.io.ByteArrayOutputStream

class MovieDatabase(mContext: Context) : SQLiteOpenHelper(mContext, DB_NAME, null, DB_VERSION) {

    companion object {
        private const val DB_NAME = "MovieDatabase.db"
        private const val DB_VERSION = 1
        private const val MOVIES_TABLE_NAME = "movies"
        private const val USERS_TABLE_NAME = "users"
        private const val MOVIE_ID = "id"
        private const val TITLE = "title"
        private const val DESCRIPTION = "description"
        private const val IMAGE = "image"
        private const val NAME = "name"
        private const val EMAIL = "email"
        private const val PASSWORD = "password"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        // Création des tables
        val createTableMovie = """
            CREATE TABLE $MOVIES_TABLE_NAME(
                $MOVIE_ID INTEGER PRIMARY KEY,
                $TITLE TEXT,
                $DESCRIPTION TEXT,
                $IMAGE BLOB
            )
        """.trimIndent()
        db?.execSQL(createTableMovie)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $MOVIES_TABLE_NAME")
        onCreate(db)
    }

    private fun getBytes(bitmap: Bitmap): ByteArray {
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream)
        return stream.toByteArray()
    }

    private fun getImage(bitmapData: ByteArray): Bitmap {
        return BitmapFactory.decodeByteArray(bitmapData, 0, bitmapData.size)
    }

    fun addUser(user: User): Boolean {
        // Insérer un nouvel utilisateur
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(NAME, user.name)
        values.put(EMAIL, user.email)
        values.put(PASSWORD, user.password)

        val result = db.insert(USERS_TABLE_NAME, null, values)

        db.close()

        return result != -1L
    }

    fun addMovie(movie: Movie): Boolean {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(TITLE, movie.title)
        values.put(DESCRIPTION, movie.description)
        val imageBytes = getBytes(movie.image)
        values.put(IMAGE, imageBytes)

        val result = db.insert(MOVIES_TABLE_NAME, null, values)

        db.close()

        return result != -1L
    }

    fun deleteMovie(movieId: Int): Boolean {
        val db = this.writableDatabase
        val result = db.delete(MOVIES_TABLE_NAME, "$MOVIE_ID = ?", arrayOf(movieId.toString()))
        db.close()

        return result != -1
    }

    fun getMovies(): List<Movie> {
        val data = mutableListOf<Movie>()
        val selectQuery = "SELECT * FROM $MOVIES_TABLE_NAME"
        val db = readableDatabase
        val cursor: Cursor? = db.rawQuery(selectQuery, null)

        cursor?.apply {
            if (moveToFirst()) {
                do {
                    val id = getInt(getColumnIndex(MOVIE_ID))
                    val title = getString(getColumnIndex(TITLE))
                    val description = getString(getColumnIndex(DESCRIPTION))
                    val imageBytes = getBlob(getColumnIndex(IMAGE))
                    val image = getImage(imageBytes)
                    val movie = Movie(id, title, description, image)
                    data.add(movie)
                } while (moveToNext())
            }
            close()
        }

        return data
    }
}
