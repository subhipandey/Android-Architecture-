package com.subhipandey.android.bookmanstreasure.destinations

import android.os.Bundle
import android.support.v4.app.FragmentManager
import androidx.navigation.NavDestination
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import com.subhipandey.android.bookmanstreasure.ui.authordetails.AuthorDetailsDialog
// 1

@Navigator.Name("author")
class AuthorDetailsNavigator(
        private val manager: FragmentManager
) : Navigator<AuthorDetailsNavigator.Destination>() {

    override fun navigate(destination: Destination, args: Bundle?, navOptions: NavOptions?) {
        val dialog = AuthorDetailsDialog()
        dialog.arguments = args
        dialog.show(manager, AuthorDetailsDialog.TAG)
    }

    override fun createDestination(): Destination {
        return Destination(this)
    }

    override fun popBackStack(): Boolean {
        return false
    }

    class Destination(authorDetailsNavigator: AuthorDetailsNavigator) :
            NavDestination(authorDetailsNavigator)
}