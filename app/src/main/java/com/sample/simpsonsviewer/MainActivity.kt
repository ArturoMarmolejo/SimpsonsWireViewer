package com.sample.simpsonsviewer

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import com.sample.simpsonsviewer.databinding.ActivityMainBinding
import com.sample.simpsonsviewer.databinding.ActivityMainTabletBinding
import com.sample.simpsonsviewer.views.CharacterList
import com.sample.simpsonsviewer.views.CharacterListTabletFragment
import dagger.hilt.android.AndroidEntryPoint

private const val TAG = "MainActivity"
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val bindingTablet by lazy {
        ActivityMainTabletBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val configuration = resources.configuration
        val screenWidthDp = configuration.screenWidthDp
        Log.d(TAG, "onCreate: Screen Width: $screenWidthDp")
        if(screenWidthDp < 600) {
            setContentView(binding.root)
        } else {
            setContentView(bindingTablet.root)
        }
        var navHostFragment = supportFragmentManager.findFragmentById(R.id.frag_container) as NavHostFragment

    }

    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {

        return super.onCreateView(name, context, attrs)
    }
}