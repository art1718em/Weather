package com.example.weather.ui.design

import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import com.example.weather.R
import com.example.weather.ui.design.theme.buttonBackground

@Composable
fun RegularButton(
    text: String,
    onClick: () -> Unit,
){
    Button(
        colors = ButtonDefaults.filledTonalButtonColors(
            containerColor = buttonBackground,
            contentColor = Color.White
        ),
        onClick = onClick,
    ) {
        Text(
            text = text,
            fontFamily = FontFamily(Font(R.font.roboto_medium)),
            fontSize = 14.sp,
            lineHeight = 20.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }
}
