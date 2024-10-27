package com.phuongtai.myconverter.pages.ui.home

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.phuongtai.myconverter.R
import com.phuongtai.myconverter.databinding.ActivityHomeBinding
import com.phuongtai.myconverter.pages.base.BaseActivity

class HomeActivity : BaseActivity() {
    private lateinit var binding: ActivityHomeBinding
    override fun observeViewModel() {

    }

    override fun initViewBinding() {
        binding = ActivityHomeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }

}