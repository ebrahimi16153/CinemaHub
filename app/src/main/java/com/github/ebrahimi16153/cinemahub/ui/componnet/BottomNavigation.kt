package com.github.ebrahimi16153.cinemahub.ui.componnet


import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.github.ebrahimi16153.cinemahub.utils.BOTTOM_ITEMS


@Composable
fun MyNavigationBar(
    navController: NavController
) {
    NavigationBar {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination
        val items = BOTTOM_ITEMS
        items.forEach { item ->
            val isSelected = currentDestination?.hierarchy?.any { it.route == item.name } == true

            NavigationBarItem(
                selected = isSelected,
                onClick = {
                    navController.navigate(item.name) {
                        // Pop up to the start destination of the graph to
                        // avoid building up a large stack of destinations
                        // on the back stack as users select items
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        // Avoid multiple copies of the same destination when
                        // reselecting the same item
                        launchSingleTop = true
                        // Restore state when reselecting a previously selected item
                        restoreState = true
                    }
                },
                icon = {
                    Icon(imageVector = item.icon, contentDescription = "")
                },
                label = {
                    Text(text = item.name)
                })
        }
    }

}