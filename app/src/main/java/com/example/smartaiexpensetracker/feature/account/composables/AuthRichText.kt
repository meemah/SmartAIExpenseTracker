package com.example.smartaiexpensetracker.feature.account.composables

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withLink
import androidx.compose.ui.text.withStyle
import com.example.smartaiexpensetracker.core.theme.customColors


@Composable
fun AuthRichText(primaryText: String, clickableText: String, tag:String, onClick: () -> Unit) {
    Text(
        text = buildAnnotatedString {
            append(primaryText)
            withLink(
                LinkAnnotation.Clickable(
                    tag = tag,
                    linkInteractionListener = { onClick() }
                )
            ) {
                withStyle(
                    style = SpanStyle(
                        color = MaterialTheme.customColors.onSurfaceVariant,
                        fontWeight = FontWeight.Bold
                    )
                ) {
                    append(clickableText)
                }
            }

        }, style = MaterialTheme.typography.bodySmall.copy(
            color = MaterialTheme.customColors.slate
        )
    )
}