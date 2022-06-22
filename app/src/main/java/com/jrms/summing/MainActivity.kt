package com.jrms.summing

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.jrms.summing.models.Spend
import com.jrms.summing.other.ObtainableData
import com.jrms.summing.ui.login.LoginActivity
import com.jrms.summing.ui.spend.detail.SpendDataViewModel
import com.jrms.summing.viewmodel.MainActivityViewModel
import org.koin.android.viewmodel.ext.android.viewModel


class MainActivity : AppCompatActivity(), LocationListener {

    private val mainActivityViewModel: MainActivityViewModel by viewModel()
    private val spendViewModel by viewModel<SpendDataViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(setOf(
            R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications))
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        if (!(ContextCompat.checkSelfPermission(
                baseContext,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
                baseContext,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED)
        ) {

            registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { granted ->
                if (granted.values.all { it == true }) {

                    val locationManager = this.getSystemService(LOCATION_SERVICE)
                            as LocationManager

                    spendViewModel.currentLocation =
                        locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0f,
                        this)

                }
            }.launch(arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION))

        }else{
            val locationManager = this.getSystemService(LOCATION_SERVICE)
                    as LocationManager

            spendViewModel.currentLocation =
                locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0f,
                this)
        }


    }

     override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.logout_option -> {
                mainActivityViewModel.logout()
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }

            else -> {
                onBackPressed()
            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_activity_menu, menu)
        return true
    }

    override fun onLocationChanged(location: Location) {
        spendViewModel.currentLocation = location
    }

    override fun onProviderDisabled(provider: String) {

    }

    override fun onProviderEnabled(provider: String) {

    }
}