package se.vettefors.knowyourmovies.activities

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import com.squareup.picasso.Picasso
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_movie_details.*
import se.vettefors.knowyourmovies.R
import se.vettefors.knowyourmovies.api.Model
import se.vettefors.knowyourmovies.api.MovieService
import se.vettefors.knowyourmovies.ext.Constants

class MovieDetailsActivity : AppCompatActivity() {

    private val mService by lazy {
        MovieService.create()
    }

    companion object {
        val EXTRA_MOVIE_BACKDROP_TRANSITION_NAME = "transition_name"
        val EXTRA_MOVIE_ID = "movie_id"
        fun newIntent(context: Context, transitionName: String, movieId: Int): Intent {
            val intent = Intent(context, MovieDetailsActivity::class.java)

            intent.putExtra(EXTRA_MOVIE_BACKDROP_TRANSITION_NAME, transitionName)
            intent.putExtra(EXTRA_MOVIE_ID, movieId)
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

        mService.getMovie(movieId = intent.extras.getInt(EXTRA_MOVIE_ID) )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {movie -> initDetails(movie)}
                )
    }

    fun initDetails(movie : Model.SingleMovie){

        println(movie)

        Picasso.with(this)
                .load(Constants.BACKDROP_BASE_URL + movie.backdrop)
                .fit()
                .into(imageView_movie_details_backdrop, object : com.squareup.picasso.Callback {
                    override fun onSuccess() {
                        imageView_movie_details_backdrop.transitionName =
                                intent.extras.getString(EXTRA_MOVIE_BACKDROP_TRANSITION_NAME)
                        supportStartPostponedEnterTransition()
                    }
                    override fun onError() {
                        supportStartPostponedEnterTransition()
                    }
                })

        collapsingToolbarLayout_movie_details.title = movie.title
        textView_movie_overview.text = movie.overview
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        when(item?.itemId){
            android.R.id.home -> onBackPressed()
        }

        return super.onOptionsItemSelected(item)
    }
}
