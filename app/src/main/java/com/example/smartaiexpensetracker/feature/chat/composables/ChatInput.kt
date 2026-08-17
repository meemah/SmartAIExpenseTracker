package com.example.smartaiexpensetracker.feature.chat.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.smartaiexpensetracker.R
import com.example.smartaiexpensetracker.core.modifiers.glassCard
import com.example.smartaiexpensetracker.core.theme.customColors


@Composable
fun ChatInput(onSend: (String) -> Unit) {
    var textInput by remember {
        mutableStateOf(value = "")
    }
    Row(
        modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            value = textInput, onValueChange = {
            textInput = it
        }, placeholder = {
            Text(
                stringResource(R.string.chat_placeholder),
                style = MaterialTheme.typography.bodySmall.copy(
                    color = Color.Gray
                )
            )
        }, maxLines = 4, colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            disabledContainerColor = Color.Transparent,
            focusedBorderColor = Color.Transparent,
            unfocusedBorderColor = Color.Transparent
        ), modifier = Modifier
                .glassCard()
                .weight(1f)
        )
        Spacer(Modifier.width(8.dp))
        IconButton(
            onClick = {
                onSend(textInput)
                textInput = ""
            }, enabled = textInput.isNotBlank(), modifier = Modifier
                .background(
                    color = MaterialTheme.customColors.onPrimaryContainer,
                    shape = RoundedCornerShape(100)
                )
                .padding(2.dp)

        ) {
            Icon(
                Icons.AutoMirrored.Filled.Send,
                contentDescription = stringResource(R.string.send),
                tint = MaterialTheme.customColors.onSurfaceVariant
            )
        }
    }


}

@Preview
@Composable
fun ChatInputPreview() {
    ChatInput(onSend = {
        print(it)
    })
}