package se.vettefors.knowyourmovies.fragments

import android.content.Context
import android.os.Bundle
import android.provider.SyncStateContract
import android.support.v4.app.Fragment
import android.support.v4.view.ViewCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.squareup.picasso.Picasso
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.top_rated_movie_list_item.view.*
import se.vettefors.knowyourmovies.R
import se.vettefors.knowyourmovies.api.Model
import se.vettefors.knowyourmovies.api.MovieService
import se.vettefors.knowyourmovies.ext.Constants

class TopRatedMoviesFragment : Fragment() {

    private val mService by lazy {
        MovieService.create()
    }
    private lateinit var mListener: OnTopRatedMovieSelected
    private lateinit var mAdapter: PopularMoviesAdapter

    private var mCurrentPage = 1;

    companion object {
        fun newInstance(): TopRatedMoviesFragment {
            return TopRatedMoviesFragment()
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)

        if (context is OnTopRatedMovieSelected) {
            mListener = context
        } else {
            Toast.makeText(activity, "Must implement mListener", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_top_rated_movies, container, false)
        val topRatedMovieList =
                view?.findViewById<RecyclerView>(R.id.recyclerView_top_rated_movies) as RecyclerView
        mAdapter = PopularMoviesAdapter()
        topRatedMovieList.layoutManager = LinearLayoutManager(activity)
        topRatedMovieList.adapter = mAdapter
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        getMovies()
    }

    private fun getMovies() {

        mService.getMovies(page = mCurrentPage)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { result -> mAdapter.addMovies(result.movies) },
                        { error -> println(error.message) }
                )
    }

    internal inner class PopularMoviesAdapter : RecyclerView.Adapter<PopularMovieHolder>() {

        private val mMovies: MutableList<Model.Movie> = arrayListOf()

        override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): PopularMovieHolder {
            return PopularMovieHolder(LayoutInflater
                    .from(parent?.context)
                    .inflate(R.layout.top_rated_movie_list_item, parent, false))
        }

        override fun getItemCount(): Int = mMovies.size

        override fun onBindViewHolder(holder: PopularMovieHolder?, position: Int) {
            val movie = mMovies[position]

            val imageView =
                    holder?.view?.findViewById<ImageView>(R.id.imageView_popular_movie_backdrop)
            Picasso.with(holder?.view?.context)
                    .load(Constants.BACKDROP_BASE_URL + movie.backdrop)
                    .into(imageView)
            val textView = holder?.view?.findViewById<TextView>(R.id.textView_popular_movie_title)
            textView?.text = movie.title

            ViewCompat.setTransitionName(imageView, movie.title)
            holder?.view?.setOnClickListener { mListener.onTopRatedMovieSelected(imageView, movie) }
        }

        fun addMovies(movies: List<Model.Movie>) {
            movies.forEach { mMovies.add(it) }
            notifyDataSetChanged()
        }
    }


    class PopularMovieHolder(val view: View) : RecyclerView.ViewHolder(view)

    interface OnTopRatedMovieSelected {
        fun onTopRatedMovieSelected(sharedImageView: ImageView?, movie: Model.Movie)
    }
}