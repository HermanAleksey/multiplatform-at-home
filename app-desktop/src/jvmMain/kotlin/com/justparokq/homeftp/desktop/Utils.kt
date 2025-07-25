package com.justparokq.homeftp.desktop

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import java.io.InputStream
import javax.imageio.ImageIO
import javax.swing.SwingUtilities
import androidx.compose.ui.graphics.toComposeImageBitmap

internal fun <T> runOnUiThread(block: () -> T): T {
    if (SwingUtilities.isEventDispatchThread()) {
        return block()
    }

    var error: Throwable? = null
    var result: T? = null

    SwingUtilities.invokeAndWait {
        try {
            result = block()
        } catch (e: Throwable) {
            error = e
        }
    }

    error?.also { throw it }

    @Suppress("UNCHECKED_CAST")
    return result as T
}

internal fun getIconBitmapPainter(iconName: String): BitmapPainter {
    val iconStream: InputStream = object {}.javaClass.getResourceAsStream("/$iconName")
    val bufferedImage = ImageIO.read(iconStream)
    val iconBitmap = bufferedImage.toComposeImageBitmap()
    val iconPainter = BitmapPainter(iconBitmap)

    return iconPainter
}