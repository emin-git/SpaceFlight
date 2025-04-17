package com.emin.newsfeature.ui.components

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.text.ClickableText
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.core.content.ContextCompat.startActivity

@Composable
fun ClickablePlatformText(platformName: String, url: String, context: android.content.Context) {
    val annotatedText = buildAnnotatedString {
        append("$platformName: $url")
        addStyle(
            style = SpanStyle(
                color = Color.Blue,
                textDecoration = TextDecoration.Underline
            ),
            start = platformName.length + 2,
            end = this.length
        )
        addStringAnnotation(
            tag = "URL",
            annotation = url,
            start = platformName.length + 2,
            end = this.length
        )
    }

    ClickableText(
        text = annotatedText,
        onClick = { offset ->
            annotatedText.getStringAnnotations(tag = "URL", start = offset, end = offset)
                .firstOrNull()?.let { annotation ->
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(annotation.item))
                    startActivity(context, intent, null)
                }
        }
    )
}