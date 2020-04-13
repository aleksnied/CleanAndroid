package com.example.cleanfloyd.presentation.albums

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cleanfloyd.R
import com.example.cleanfloyd.utls.PaginationScrollListener
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_albums.*
import timber.log.Timber
import javax.inject.Inject

class AlbumsFragment : DaggerFragment() {

  private var adapter: AlbumsAdapter? = null

  @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
  private val viewModel: AlbumsViewModel by lazy {
    ViewModelProvider(this, viewModelFactory).get(AlbumsViewModel::class.java)
  }

  companion object {
    fun newInstance() = AlbumsFragment()
  }

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
  ): View? {
    return inflater.inflate(R.layout.fragment_albums, container, false)
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    adapter = AlbumsAdapter()
    viewModel.loadAlbums()
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    albums_refresh.setOnRefreshListener {
      adapter?.clear()
      viewModel.loadAlbums()
    }

    viewModel.loading.observe(viewLifecycleOwner, Observer {
      it?.let { loading ->
        albums_refresh.isRefreshing = loading
      }
    })

    viewModel.empty.observe(viewLifecycleOwner, Observer {
      it?.let { empty ->
        albums_none_text.visibility = if (empty) View.VISIBLE else View.GONE
      }
    })

    viewModel.albums.observe(viewLifecycleOwner, Observer {
      it?.let {
        adapter?.addData(it)
      }
    })

    viewModel.error.observe(viewLifecycleOwner, Observer {
      albums_error_text.visibility = if (it != null) View.VISIBLE else View.GONE
      it?.let { error -> Timber.e(error) }
    })

    albums_recycler_view.adapter = adapter
//    albums_recycler_view.addOnScrollListener(object : PaginationScrollListener(
//        albums_recycler_view.layoutManager as LinearLayoutManager) {
//
//      override fun isLoading(): Boolean {
//        return viewModel.loading.value ?: false
//      }
//
//      override fun loadMoreItems() {
//        viewModel.loadAlbums()
//      }
//    })
  }

  override fun onDetach() {
    super.onDetach()
    adapter = null
  }
}
