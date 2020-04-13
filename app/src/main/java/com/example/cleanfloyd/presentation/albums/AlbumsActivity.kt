package com.example.cleanfloyd.presentation.albums

import android.os.Bundle
import com.example.cleanfloyd.R
import dagger.android.support.DaggerAppCompatActivity
import timber.log.Timber

class AlbumsActivity : DaggerAppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_albums)
    if (savedInstanceState == null) {
      supportFragmentManager.beginTransaction()
          .replace(R.id.container, AlbumsFragment.newInstance()).commitNow()
    }
  }
}
