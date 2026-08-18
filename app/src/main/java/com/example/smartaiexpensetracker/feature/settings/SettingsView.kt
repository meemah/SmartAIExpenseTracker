package com.example.smartaiexpensetracker.feature.settings


import androidx.compose.foundation.background

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.example.smartaiexpensetracker.core.composables.AppButton
import com.example.smartaiexpensetracker.core.data.ThemeMode
import com.example.smartaiexpensetracker.core.modifiers.glassCard
import com.example.smartaiexpensetracker.core.navigation.Routes
import com.example.smartaiexpensetracker.core.theme.Destructive
import com.example.smartaiexpensetracker.core.theme.customColors
import java.util.Locale


@Composable
fun SettingsView(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val colors = MaterialTheme.customColors
    val themeMode by viewModel.themeDataStore.themeMode.collectAsStateWithLifecycle(ThemeMode.SYSTEM)

    Scaffold(
        containerColor = colors.background
    ) { innerPadding ->
        Column(
            modifier = modifier
                .padding(innerPadding)
                .padding(16.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                model = "https://api.dicebear.com/10.x/moods/png?seed=8zcvrboa",
                contentDescription = "Profile",
                placeholder = rememberVectorPainter(Icons.Default.Person),
                error = rememberVectorPainter(Icons.Default.Person),
                modifier = Modifier
                    .padding(top = 60.dp, bottom = 8.dp)
                    .size(72.dp)
                    .clip(CircleShape)
            )
            Text(viewModel.user?.let { "${it.firstName.capitalize(Locale.getDefault())} ${it.lastName.capitalize()}" } ?: "User",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold, fontSize = 18.sp, color = colors.onSurface
                ))
            Text(
                viewModel.user?.email ?: "user @gmail.com",
                modifier = Modifier.padding(top = 4.dp, bottom = 10.dp),
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = colors.slate
                )
            )

            Row(
                modifier = Modifier
                    .glassCard()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        "Budget Settings", style = MaterialTheme.typography.titleSmall.copy(
                            fontWeight = FontWeight.Bold, color = colors.onSurface
                        )
                    )
                    Text(
                        "Take a look at your budget for categories",
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = colors.slate
                        )
                    )
                }
                Icon(
                    Icons.Default.ChevronRight,
                    contentDescription = "Update budget settings",
                    tint = colors.slate
                )
            }
            Spacer(Modifier.height(20.dp))
            Column(
                modifier = Modifier
                    .glassCard()
                    .padding(16.dp)
            ) {
                Text(
                    "APPEARANCE",
                    modifier = Modifier.padding(bottom = 12.dp),
                    style = MaterialTheme.typography.labelMedium.copy(
                        letterSpacing = 1.sp, fontWeight = FontWeight.Bold, color = colors.slate
                    )
                )
                SingleChoiceSegmentedButtonRow(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    ThemeMode.entries.forEachIndexed { index, theme ->
                        SegmentedButton(
                            selected = themeMode == theme,
                            onClick = { viewModel.updateTheme(theme) },
                            shape = SegmentedButtonDefaults.itemShape(
                                index = index, count = ThemeMode.entries.size
                            )
                        ) {
                            Text(
                                theme.label, style = MaterialTheme.typography.bodySmall.copy(
                                    fontWeight = FontWeight.Bold, color = colors.onSurface
                                )
                            )
                        }
                    }
                }
            }
            Spacer(Modifier.height(20.dp))
            AppButton(
                buttonTitle = "Logout", backgroundColor = Destructive
            ) {
                viewModel.logout()
                navController.navigate(Routes.SIGN_IN) {
                    popUpTo(0) { inclusive = true }
                }
            }
        }
    }
}


