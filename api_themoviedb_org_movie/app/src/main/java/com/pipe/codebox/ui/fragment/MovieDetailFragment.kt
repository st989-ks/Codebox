package com.pipe.codebox.ui.fragment

import android.os.Bundle
import android.view.View
import com.pipe.codebox.R
import com.pipe.codebox.domain.entity.InformationAll
import com.pipe.codebox.domain.entity.InformationCast
import com.pipe.codebox.domain.entity.InformationMovie
import com.pipe.codebox.extensions.BUNDLE_ID
import com.pipe.codebox.presenter.view.InformationMovieViewModel
import com.pipe.codebox.ui.adapter.ListActorAdapter
import com.pipe.codebox.ui.fragment.base.BaseFragment
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_detail_movie.*
import kotlinx.android.synthetic.main.fragment_detail_movie.view.*
import kotlinx.android.synthetic.main.fragment_list_movie.*
import javax.inject.Inject

@AndroidEntryPoint
class MovieDetailFragment : BaseFragment() {

    override val layoutId: Int = R.layout.fragment_detail_movie

    private var idMovie = 672

    @Inject
    lateinit var listActorAdapter: ListActorAdapter

    @Inject
    lateinit var informationMovieViewModel: InformationMovieViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        idMovie = requireArguments().getInt(BUNDLE_ID,idMovie)
        observer()
    }

    private fun observer() {
        informationMovieViewModel.state.observe(viewLifecycleOwner) { handleState(it) }
        informationMovieViewModel.requestInformation(idMovie.toString())
    }

    override fun handleSuccess(data: Any) {
        when (data) {
            is InformationAll -> {
                fillView(data.movie)
                recycler(data.cast)
            }
        }
    }

    private fun fillView(movie: InformationMovie) {
        if (movie.posterPath.isNullOrEmpty()) {
            overlay.setImageResource(R.drawable.notimgicon)
            poster_image_view.setImageResource(R.drawable.notimgicon)
        } else {
            Picasso.get().load(movie.posterPath).apply {
                into(overlay)
                into(poster_image_view)
            }
        }
        tv_movie_title.text = movie.originalTitle
        vote_count_rating.rating = movie.voteAverage.toFloat()

        vote_average_label.text =
            getString(R.string.vote_average_label_fragment, movie.voteAverage.toString())
        vote_count_label.text =
            getString(R.string.vote_count_label_fragment, movie.voteCount.toString())
        original_lang_label.text =
            getString(R.string.original_lang_label_fragment, movie.originalLanguage)
        overview_text.text = movie.overview
    }

    private fun recycler(cast: List<InformationCast>) {
        listActorAdapter.actors = cast
        recycler_actor.apply {
            recycledViewPool.setMaxRecycledViews(0, 0)
            adapter = listActorAdapter
        }
    }
}