package com.example.mygithubakhir2.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.example.mygithubakhir2.databinding.ActivityDetailBinding
import com.example.mygithubakhir2.adapter.SectionsPagerAdapter
import com.example.mygithubakhir2.viewmodel.DetailUserViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_USERNAME = "extra_username"
        const val EXTRA_ID = "extra_id"
        const val EXTRA_AVATAR = "extra_avatar"
    }

    private lateinit var binding: ActivityDetailBinding
    private lateinit var viewModel: DetailUserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent.getStringExtra(EXTRA_USERNAME)
        val id = intent.getIntExtra(EXTRA_ID, 0)
        val avatar = intent.getStringExtra(EXTRA_AVATAR)

        val bundle = Bundle()
        bundle.putString(EXTRA_USERNAME, username)

        viewModel = ViewModelProvider(this).get(DetailUserViewModel::class.java)

        if (username != null) {
            viewModel.setDetailUser(username)
        }

        viewModel.getDetailUser().observe(this) {
            if (it != null) {
                binding.apply {
                    Glide.with(this@DetailActivity)
                        .load(it.avatarUrl)
                        .into(ciUserPhoto)
                    tvName.text = it.name
                    tvUsername.text = it.login
                    tvFollowerNumber.text = "${it.followers} Followers"
                    tvFollowingNumber.text = "${it.following} Following"
                }
                showLoading(false)
            }
        }

        var isCheck = false
        CoroutineScope(Dispatchers.IO).launch {
            val count = viewModel.isFavorite(id)
            withContext(Dispatchers.Main) {
                if (count != null) {
                    if (count > 0) {
                        isCheck = true
                        binding.fabAdd.isChecked = true
                    } else {
                        isCheck = false
                        binding.fabAdd.isChecked = false
                    }
                }
            }
        }

        binding.fabAdd.setOnClickListener{
            isCheck = !isCheck
            if (isCheck) {
                if (username != null) {
                    if (avatar != null) {
                        viewModel.insertFavoriteUser(username, id, avatar)
                    }
                }
            } else {
                viewModel.deleteFavoriteUser(id)
            }
            binding.fabAdd.isChecked = isCheck
        }

        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager, bundle)
        val viewPager: ViewPager = binding.detailViewPager
        binding.apply {
            viewPager.adapter = sectionsPagerAdapter
            detailTabs.setupWithViewPager(viewPager)
        }
    }
    private fun showLoading(state: Boolean) { binding.loadingDetail.visibility = if (state) View.VISIBLE else View.GONE }


}