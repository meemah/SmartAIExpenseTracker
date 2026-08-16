package com.example.smartaiexpensetracker.core.states

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.WarningAmber
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.smartaiexpensetracker.R
import com.example.smartaiexpensetracker.core.theme.customColors


@Composable
fun ErrorState(modifier: Modifier = Modifier, message: String, onRetry: (() -> Unit)? = null) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = Icons.Outlined.WarningAmber,
            contentDescription = null,
            tint = Color.Red,
            modifier = Modifier
                .size(48.dp)
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(14.dp)
                )
                .padding(10.dp)

        )
        Text(
            message,
            color = MaterialTheme.customColors.onSurfaceVariant,
            textAlign = TextAlign.Center,
            modifier = modifier.padding(top = 10.dp)
        )
        if (onRetry != null) {
            Button(
                onClick = onRetry,
                modifier = Modifier.padding(top = 12.dp),
                shape = RoundedCornerShape(20.dp),
                contentPadding = PaddingValues(horizontal = 20.dp, vertical = 2.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.customColors.onSurface,
                    contentColor = MaterialTheme.customColors.onSurface
                )
            ) {

                Text(
                    stringResource(R.string.retry),
                    style = MaterialTheme.typography.bodyLarge.copy(
                        color = MaterialTheme.customColors
                            .surface,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 14.sp
                    )
                )
            }
        }
    }
}