package se.vettefors.knowyourmovies.activities

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_movie_details.*
import se.vettefors.knowyourmovies.R
import se.vettefors.knowyourmovies.api.Model
import se.vettefors.knowyourmovies.ext.Constants

class MovieDetailsActivity : AppCompatActivity() {

    companion object {
        val EXTRA_MOVIE_BACKDROP_TRANSITION_NAME = "transition_name"
        val EXTRA_MOVIE = "movie"
        fun newIntent(context: Context, transitionName: String, movie: Model.Movie): Intent {
            val intent = Intent(context, MovieDetailsActivity::class.java)

            intent.putExtra(EXTRA_MOVIE_BACKDROP_TRANSITION_NAME, transitionName)
            intent.putExtra(EXTRA_MOVIE, movie)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)
        supportPostponeEnterTransition()
        setSupportActionBar(toolbar_movie_details)

        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val extras = intent.extras

        val movie: Model.Movie = extras.getSerializable(EXTRA_MOVIE) as Model.Movie

        Picasso.with(this)
                .load(Constants.BACKDROP_BASE_URL + movie.backdrop)
                .fit()
                .into(imageView_movie_details_backdrop, object : com.squareup.picasso.Callback {
                    override fun onSuccess() {
                        imageView_movie_details_backdrop.transitionName =
                                extras.getString(EXTRA_MOVIE_BACKDROP_TRANSITION_NAME)
                        supportStartPostponedEnterTransition()
                    }
                    override fun onError() {
                        supportStartPostponedEnterTransition()
                    }
                })

        collapsingToolbarLayout_movie_details.title = movie.title
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        when(item?.itemId){
            android.R.id.home -> onBackPressed()
        }

        return super.onOptionsItemSelected(item)
    }
}
