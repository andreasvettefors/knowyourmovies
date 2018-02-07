package se.vettefors.knowyourmovies.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.app.Fragment
import android.support.v4.view.ViewCompat
import android.widget.ImageView
import kotlinx.android.synthetic.main.activity_main.*
import se.vettefors.knowyourmovies.R
import se.vettefors.knowyourmovies.api.Model
import se.vettefors.knowyourmovies.fragments.TopRatedMoviesFragment

class MainActivity : AppCompatActivity(), TopRatedMoviesFragment.OnTopRatedMovieSelected {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(main_toolbar)

        if (savedInstanceState == null) {
            loadFragment(TopRatedMoviesFragment.newInstance()
                    , getString(R.string.top_rated_movie_fragment))
        }
    }

    private fun loadFragment(fragment: Fragment, tag: String) {
        supportFragmentManager
                .beginTransaction()
                .add(R.id.frameLayout_root, fragment, tag)
                .commit()
    }

    override fun onTopRatedMovieSelected(sharedImageView: ImageView?, movie: Model.Movie) {

        val transitionName = ViewCompat.getTransitionName(sharedImageView)
        val options = ActivityOptionsCompat
                .makeSceneTransitionAnimation(this,
                        sharedImageView!!,
                        transitionName)

        startActivity(MovieDetailsActivity.newIntent(this, transitionName, movie.id),
                options.toBundle())

    }
}

