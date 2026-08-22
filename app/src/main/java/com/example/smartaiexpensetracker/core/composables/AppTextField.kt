package com.example.smartaiexpensetracker.core.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.material3.LocalTextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import java.text.DecimalFormat
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.smartaiexpensetracker.core.theme.customColors

@Composable
fun AppTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    bottonPadding: Double = 10.0,
    label: String? = null,
    hintText: String? = null,
    leadingIcon: (@Composable () -> Unit)? = null,
    trailingIcon: (@Composable () -> Unit)? = null,
    textFieldType: TextFieldType = TextFieldType.NORMAL,
    textAlign: TextAlign = TextAlign.Start,
    fontWeight: FontWeight? = null,
    errorMessage: String? = null
) {

    var passwordVisible by rememberSaveable {
        mutableStateOf(false)
    }

    val keyboardType = when (textFieldType) {
        TextFieldType.NORMAL -> KeyboardType.Text
        TextFieldType.EMAIL -> KeyboardType.Email
        TextFieldType.PASSWORD -> KeyboardType.Password
        TextFieldType.NUMBER_ONLY -> KeyboardType.Number
    }

    val visualTransformation = when {
        textFieldType == TextFieldType.PASSWORD && !passwordVisible -> PasswordVisualTransformation()
        textFieldType == TextFieldType.NUMBER_ONLY -> NumberCommaTransformation()
        else -> VisualTransformation.None
    }
    Column(
        modifier = modifier.padding(bottom = (bottonPadding).dp)
    ) {
        label?.let {
            Text(
                it,
                style = MaterialTheme.typography.headlineSmall.copy(
                    color = MaterialTheme.customColors.onSurfaceVariant,
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp
                ),
            )
        }
        OutlinedTextField(
            modifier = Modifier
                .padding(top = 2.dp)
                .fillMaxWidth(),
            onValueChange = { newValue ->
                if (textFieldType == TextFieldType.NUMBER_ONLY) {
                    if (newValue.isEmpty() || newValue.matches(Regex("^\\d*\\.?\\d*$"))) {
                        onValueChange(newValue)
                    }
                } else {
                    onValueChange(newValue)
                }
            },
            leadingIcon = leadingIcon,
            trailingIcon = when (textFieldType) {
                TextFieldType.PASSWORD -> {
                    {
                        Icon(
                            if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                            contentDescription = null,
                            modifier = Modifier.clickable(onClick = {
                                passwordVisible = !passwordVisible
                            }),
                        )
                    }
                }
                else -> trailingIcon
            },
            supportingText = errorMessage?.let {
                {
                    Text(errorMessage)
                }
            },
            isError = errorMessage != null,
            value = value,
            placeholder = hintText?.let {
                {
                    Text(
                        it,
                        color = MaterialTheme.customColors.slate.copy(alpha = 0.5f),
                        textAlign = textAlign,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = keyboardType
            ),
            textStyle = LocalTextStyle.current.copy(textAlign = textAlign, fontWeight = fontWeight),
            visualTransformation = visualTransformation,
            shape = RoundedCornerShape(8.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent,
                disabledBorderColor = Color.Transparent,
                errorBorderColor = Color.Transparent,
                focusedContainerColor = MaterialTheme.customColors.surfaceContainer,
                unfocusedContainerColor = MaterialTheme.customColors.surfaceContainer,
                disabledContainerColor = MaterialTheme.customColors.surfaceContainer,
                errorContainerColor = MaterialTheme.customColors.surfaceContainer,
            )
        )
    }
}

enum class TextFieldType {
    NORMAL,
    EMAIL,
    PASSWORD,
    NUMBER_ONLY
}

private class NumberCommaTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val original = text.text
        if (original.isEmpty()) return TransformedText(text, OffsetMapping.Identity)

        val parts = original.split(".")
        val integerPart = parts[0]
        val decimalPart = if (parts.size > 1) ".${parts[1]}" else ""

        val formatted = if (integerPart.isNotEmpty()) {
            val number = integerPart.toLongOrNull()
            if (number != null) {
                DecimalFormat("#,###").format(number) + decimalPart
            } else {
                original
            }
        } else {
            original
        }

        val commaPositions = mutableListOf<Int>()
        for (i in formatted.indices) {
            if (formatted[i] == ',') commaPositions.add(i)
        }

        val offsetMapping = object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int {
                var commasBefore = 0
                for (pos in commaPositions) {
                    if (pos - commasBefore < offset) commasBefore++ else break
                }
                return offset + commasBefore
            }

            override fun transformedToOriginal(offset: Int): Int {
                var commasBefore = commaPositions.count { it < offset }
                return offset - commasBefore
            }
        }

        return TransformedText(AnnotatedString(formatted), offsetMapping)
    }
}