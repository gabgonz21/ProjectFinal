package com.marudo.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.marudo.myapplication.databinding.ActivityMainBinding
import org.json.JSONObject

class MainActivity : AppCompatActivity(), SpinnerFragment.DisplayUpdate {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

    override fun updateDisplay(itemObject: JSONObject) {
        val displayFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView2) as DisplayFragment
        displayFragment.showArmorInfo(itemObject)
    }
}