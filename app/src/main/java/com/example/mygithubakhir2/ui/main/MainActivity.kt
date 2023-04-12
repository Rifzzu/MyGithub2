package com.example.mygithubakhir2.ui.main

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mygithubakhir2.R
import com.example.mygithubakhir2.User
import com.example.mygithubakhir2.adapter.UserAdapter
import com.example.mygithubakhir2.databinding.ActivityMainBinding
import com.example.mygithubakhir2.settings.SettingsPreferences
import com.example.mygithubakhir2.ui.detail.DetailActivity
import com.example.mygithubakhir2.ui.favorite.FavoriteActivity
import com.example.mygithubakhir2.ui.theme.SettingActivity
import com.example.mygithubakhir2.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: UserAdapter
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = UserAdapter()
        adapter.notifyDataSetChanged()

        adapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: User) {
                val intent = Intent(this@MainActivity, DetailActivity::class.java)
                intent.putExtra(DetailActivity.EXTRA_USERNAME, data.login)
                intent.putExtra(DetailActivity.EXTRA_ID, data.id)
                intent.putExtra(DetailActivity.EXTRA_AVATAR, data.avatar_url)
                startActivity(intent)
            }
        })

        viewModel = ViewModelProvider(this, MainViewModel.Factory(SettingsPreferences(this))).get(MainViewModel::class.java)
        viewModel.getTheme().observe(this) {
            if (it) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        binding.apply {
            rvUser.layoutManager = LinearLayoutManager(this@MainActivity)
            rvUser.setHasFixedSize(true)
            rvUser.adapter = adapter
        }

        viewModel.getUser().observe(this){
            if (it != null) {
                adapter.setData(it)
                showLoading(false)
            }
        }
        binding.etSearch.setOnEditorActionListener(TextView.OnEditorActionListener { view, id, _ ->
            if (id == EditorInfo.IME_ACTION_SEARCH) {
                val textSearch = view.text.toString()
                viewModel.getSearchUser(textSearch)

                return@OnEditorActionListener true
            }
            return@OnEditorActionListener false
        })
    }

    private fun showLoading(state: Boolean) { binding.progressBar.visibility = if (state) View.VISIBLE else View.GONE }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.favorite -> {
                val intent = Intent(this, FavoriteActivity::class.java)
                startActivity(intent)
            }
            R.id.themeSetting -> {
                Intent(this@MainActivity, SettingActivity::class.java).apply {
                    startActivity(this)
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }
}