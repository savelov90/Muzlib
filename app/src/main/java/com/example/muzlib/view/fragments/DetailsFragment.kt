package com.example.muzlib.view.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.muzlib.R
import com.example.muzlib.data.db.entity_search.ResultAlbums
import com.example.muzlib.data.db.entity_track.ResultTracks
import com.example.muzlib.databinding.FragmentDetailsBinding
import com.example.muzlib.disposable.AutoDisposable
import com.example.muzlib.disposable.addTo
import com.example.muzlib.viewmodel.DetailsFragmentViewModel
import com.squareup.picasso.Picasso
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers


class DetailsFragment : Fragment() {

    private lateinit var binding: FragmentDetailsBinding
    private lateinit var album: ResultAlbums
    private lateinit var allTracks: Observable<List<String>>
    private val autoDisposable = AutoDisposable()
    private val viewModel by lazy {
        ViewModelProvider.NewInstanceFactory().create(DetailsFragmentViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        autoDisposable.bindTo(lifecycle)
        //настраиваем views экрана с деталями альбома
        setAlbumsDetails()
    }

    @SuppressLint("SetTextI18n")
    private fun setAlbumsDetails() {
        album = arguments?.getParcelable<ResultAlbums>("album") as ResultAlbums

        allTracks = viewModel.getTracks(album.collectionId.toString())
        allTracks.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onError = {
                    Toast.makeText(requireContext(), getString(R.string.toast_details), Toast.LENGTH_SHORT)
                        .show()
                    println("")
                },
                onNext = {
                    setTrackNames(it)
                }
            )
            .addTo(autoDisposable)

        Picasso.get()
            .load(album.artworkUrl100)
            .into(binding.detailsPicture)

        binding.detailsAlbumName.text = album.collectionName
        binding.detailsArtistName.text = album.artistName
        binding.detailsGenre.text = album.primaryGenreName
        binding.detailsYear.text = album.releaseDate.dropLast(16)
    }

    private fun setTrackNames(list: List<String>) {
        val tracksNameList = mutableListOf<String>()
        for (i in list.indices) {
            if (list[i] != null) {
                tracksNameList.add(i.toString() + "   " + list[i])
            }
        }
        binding.detailsTracks.text = tracksNameList.joinToString("\n")
    }
}