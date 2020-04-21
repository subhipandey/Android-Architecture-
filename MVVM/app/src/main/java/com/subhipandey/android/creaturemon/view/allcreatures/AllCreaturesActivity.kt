package com.subhipandey.android.creaturemon.view.allcreatures

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import com.subhipandey.android.creaturemon.R
import com.subhipandey.android.creaturemon.view.creature.CreatureActivity
import com.subhipandey.android.creaturemon.viewmodel.AllCreatureViewModel
import kotlinx.android.synthetic.main.activity_all_creatures.*
import kotlinx.android.synthetic.main.content_all_creatures.*

class AllCreaturesActivity : AppCompatActivity() {

  private lateinit var viewModel: AllCreatureViewModel

  private val adapter = CreatureAdapter(mutableListOf())

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_all_creatures)
    setSupportActionBar(toolbar)

    viewModel = ViewModelProviders.of(this).get(AllCreatureViewModel::class.java)

    creaturesRecyclerView.layoutManager = LinearLayoutManager(this)
    creaturesRecyclerView.adapter = adapter

    viewModel.getAllCreaturesLiveData().observe(this, Observer { creatures ->
      creatures?.let {
        adapter.updateCreatures(creatures)
      }
    })

    fab.setOnClickListener {
      startActivity(Intent(this, CreatureActivity::class.java))
    }
  }

  override fun onCreateOptionsMenu(menu: Menu): Boolean {
    // Inflate the menu; this adds items to the action bar if it is present.
    menuInflater.inflate(R.menu.menu_main, menu)
    return true
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    return when (item.itemId) {
      R.id.action_clear_all -> {
        viewModel.clearAllCreatures()
        true
      }
      else -> super.onOptionsItemSelected(item)
    }
  }
}
