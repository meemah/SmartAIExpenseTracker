package com.example.smartaiexpensetracker.feature

import android.graphics.drawable.Icon
import android.graphics.drawable.shapes.Shape
import androidx.compose.foundation.R
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.SmartToy
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.example.smartaiexpensetracker.core.data.TabMenu
import com.example.smartaiexpensetracker.core.navigation.Routes
import com.example.smartaiexpensetracker.core.theme.customColors
import com.example.smartaiexpensetracker.feature.budget.BudgetTrackerView
import com.example.smartaiexpensetracker.feature.chat.ChatView
import com.example.smartaiexpensetracker.feature.home.HomeView
import com.example.smartaiexpensetracker.feature.transactions.TransactionsScreen

@Composable
fun MainView(modifier: Modifier = Modifier, navController: NavController) {
    val colors = MaterialTheme.customColors
    var selectedTab by remember { mutableStateOf<TabMenu>(TabMenu.Home) }
    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .background(color = colors.background),
        floatingActionButton = {
            IconButton(
                modifier = Modifier
                    .size(40.dp)
                    .background(
                        shape = RoundedCornerShape(100),
                        color = colors.onPrimaryContainer

                    ), onClick = {
                    navController.navigate(Routes.CHAT)
                }) {
                Icon(
                    Icons.Default.SmartToy,
                    contentDescription = null,
                    tint = colors.onSurfaceVariant,
                )
            }
        },
        bottomBar = {
            NavigationBar(
                containerColor = colors.primary.copy(alpha = 0.9f)
            ) {
                TabMenu.entries.forEach {
                    NavigationBarItem(
                        label = {
                            Text(
                                it.label, style = MaterialTheme.typography.bodyMedium.copy(
                                    color = colors.onSurfaceVariant,
                                )
                            )
                        }, selected = it == selectedTab, icon = {
                            Icon(it.icon, contentDescription = it.label)
                        }, onClick = {
                            selectedTab = it
                        }, colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = colors.onSurfaceVariant,
                            unselectedIconColor = colors.onSurfaceVariant,
                            indicatorColor = colors.onPrimaryContainer,
                        )
                    )
                }
            }
        }) { innerPadding ->

        Column(Modifier.padding(innerPadding)) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        "Welcome back",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold,
                            color = colors.onSurface
                        )
                    )
                    Text(
                        "Here's your spending overview",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontSize = 13.sp,
                            color = colors.onSurfaceVariant
                        )
                    )
                }
                AsyncImage(
                    model = "https://api.dicebear.com/10.x/moods/png?seed=8zcvrboa",
                    contentDescription = "Profile",
                    placeholder = rememberVectorPainter(Icons.Default.Person),
                    error = rememberVectorPainter(Icons.Default.Person),
                    modifier = Modifier
                        .size(42.dp)
                        .clip(CircleShape)
                        .background(colors.onPrimaryContainer)
                        .clickable { navController.navigate(Routes.SETTINGS) }
                )
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                when (selectedTab) {
                    TabMenu.Home -> HomeView(isActive = selectedTab == TabMenu.Home)
                    TabMenu.History -> TransactionsScreen(isActive = selectedTab == TabMenu.History)
                    TabMenu.Budget -> BudgetTrackerView(isActive = selectedTab == TabMenu.Budget)
                }

            }

        }
    }
}
