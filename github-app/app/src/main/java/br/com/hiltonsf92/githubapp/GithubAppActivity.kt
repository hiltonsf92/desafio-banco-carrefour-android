package br.com.hiltonsf92.githubapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupWithNavController
import br.com.hiltonsf92.githubapp.databinding.ActivityGithubAppBinding

class GithubAppActivity : AppCompatActivity() {
    private var _binding: ActivityGithubAppBinding? = null
    private val binding get() = _binding!!

    private lateinit var navController: NavController
    private lateinit var appConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityGithubAppBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupAppBar()
    }

    private fun setupAppBar() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        navController = navHostFragment.navController
        appConfiguration = AppBarConfiguration(navController.graph)
        binding.appBar.setupWithNavController(navController, appConfiguration)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appConfiguration) || super.onSupportNavigateUp()
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}