package com.example.muzlib.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.muzlib.R
import com.example.muzlib.data.db.entity_search.ResultAlbums
import com.example.muzlib.databinding.FragmentHomeBinding
import com.example.muzlib.disposable.AutoDisposable
import com.example.muzlib.disposable.addTo
import com.example.muzlib.view.MainActivity
import com.example.muzlib.view.AnimationHelper
import com.example.muzlib.view.rv_adapters.AlbumsListRecyclerAdapter
import com.example.muzlib.view.rv_adapters.TopSpacingItemDecoration
import com.example.muzlib.viewmodel.HomeFragmentViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.ObservableOnSubscribe
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.*
import java.util.concurrent.TimeUnit

private const val PADDING = 10

class HomeFragment : Fragment() {

    private lateinit var albumsAdapter: AlbumsListRecyclerAdapter
    private lateinit var binding: FragmentHomeBinding
    private val autoDisposable = AutoDisposable()
    private lateinit var allAlbums: Observable<List<ResultAlbums>>
    private val viewModel by lazy {
        ViewModelProvider.NewInstanceFactory().create(HomeFragmentViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        AnimationHelper.performFragmentCircularRevealAnimation(binding.root, requireActivity(), 1)
        autoDisposable.bindTo(lifecycle)
        getLastSavedAlbums()
        initSearchView()
        initRecyckler()
    }

    private fun initRecyckler() {
        binding.mainRecycler.apply {
            albumsAdapter =
                AlbumsListRecyclerAdapter(object : AlbumsListRecyclerAdapter.OnItemClickListener {
                    override fun click(resultAlbums: ResultAlbums) {
                        (requireActivity() as MainActivity).launchDetailsFragment(resultAlbums)
                    }
                })
            adapter = albumsAdapter
            layoutManager = LinearLayoutManager(requireContext())
            val decorator = TopSpacingItemDecoration(PADDING)
            addItemDecoration(decorator)
        }
    }

    private fun initSearchView() {
        binding.searchView.setOnClickListener {
            binding.searchView.isIconified = false
        }

        Observable.create(ObservableOnSubscribe<String> { subscriber ->
            binding.searchView.setOnQueryTextListener(object :
                SearchView.OnQueryTextListener {
                override fun onQueryTextChange(newText: String): Boolean {
                    albumsAdapter.items.clear()
                    subscriber.onNext(newText)
                    return true
                }

                override fun onQueryTextSubmit(query: String): Boolean {
                    subscriber.onNext(query)
                    return true
                }
            })
        })
            .subscribeOn(Schedulers.io())
            .map {
                it.toLowerCase(Locale.getDefault()).trim()
            }
            .debounce(1200, TimeUnit.MILLISECONDS)
            .filter {
                getLastSavedAlbums()
                it.isNotBlank()
            }
            .flatMap {
                viewModel.getAlbums(it)
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onError = {
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.toast_home),
                        Toast.LENGTH_SHORT
                    )
                        .show()
                },
                onNext = {
                    albumsAdapter.addItems(it)
                }
            )
            .addTo(autoDisposable)
    }

    private fun getLastSavedAlbums() {
        allAlbums = viewModel.albumsListData
        allAlbums.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { list ->
                albumsAdapter.addItems(list)
            }
            .addTo(autoDisposable)
    }
}