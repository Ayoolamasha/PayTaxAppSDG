package com.ayoolamasha.paytaxappsdg

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import com.ayoolamasha.paytaxappsdg.Login.LoginFragment
import com.ayoolamasha.paytaxappsdg.Login.LoginFragmentDirections
import com.ayoolamasha.paytaxappsdg.Utils.UserSharedPreference
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject
import kotlin.properties.Delegates

@AndroidEntryPoint
class MainActivity : AppCompatActivity(){
    @Inject lateinit var userSharedPreference: UserSharedPreference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null){
            val isLoggedIn:Boolean = userSharedPreference.isUserLoggedIn()
            Timber.tag("User Is Logged In").d(isLoggedIn.toString())
            if (isLoggedIn){
                Navigation.findNavController(this, R.id.myNavHostFragment)
                    .navigate(LoginFragmentDirections.actionLoginFragmentToHomeFragment())

            }


        }
    }
}