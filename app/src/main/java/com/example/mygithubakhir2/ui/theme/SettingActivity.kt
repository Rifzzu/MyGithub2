package com.example.mygithubakhir2.ui.theme

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import com.example.mygithubakhir2.R
import com.example.mygithubakhir2.databinding.ActivityThemeSettingBinding
import com.example.mygithubakhir2.settings.SettingsPreferences
import com.example.mygithubakhir2.viewmodel.SettingsViewModel

class SettingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityThemeSettingBinding
    private lateinit var viewModel: SettingsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityThemeSettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this, SettingsViewModel.Factory(SettingsPreferences(this))).get(SettingsViewModel::class.java)

        viewModel.getTheme().observe(this) {
            if (it) {
                binding.switchTheme.text = getString(R.string.dark_mode)
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                binding.switchTheme.text = getString(R.string.light_mode)
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
            binding.switchTheme.isChecked = it
        }

        binding.switchTheme.setOnCheckedChangeListener { _, isChecked ->
            viewModel.setTheme(isChecked)
        }
    }
}