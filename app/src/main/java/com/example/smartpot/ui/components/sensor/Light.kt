package com.example.smartpot.ui.components.sensor

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun LightDisplay(preferredLight: Int?) {
    val context = LocalContext.current
    var lightValue by remember { mutableFloatStateOf(0f) }

    DisposableEffect(context) {
        val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)

        val listener = object : SensorEventListener {
            override fun onSensorChanged(event: SensorEvent) {
                if (event.sensor.type == Sensor.TYPE_LIGHT) {
                    lightValue = event.values[0]
                }
            }
            override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {}
        }

        lightSensor?.let {
            sensorManager.registerListener(
                listener,
                it,
                SensorManager.SENSOR_DELAY_NORMAL
            )
        }

        onDispose {
            sensorManager.unregisterListener(listener)
        }
    }

    if (preferredLight != null) {
        LightFillBar(lightValue.toInt(), preferredLight * 2, (preferredLight * 0.75).toInt(), (preferredLight * 1.25).toInt())
    } else {
        LightFillBar(lightValue.toInt(), 10000, -1, -1)
    }
}

@Composable
fun LightFillBar(
    lightLux: Int,
    maxLux: Int,
    preferredMin: Int,
    preferredMax: Int,
    modifier: Modifier = Modifier
) {
    val fillFraction = (lightLux.toFloat() / maxLux).coerceIn(0f, 1f)

    val animatedFill by animateFloatAsState(
        targetValue = fillFraction,
        animationSpec = tween(durationMillis = 1000),
        label = "fill"
    )

    val preferredFillFraction = ((preferredMax - preferredMin).toFloat() / maxLux).coerceIn(0f, 1f)
    val preferredOffsetFraction = (preferredMin.toFloat() / maxLux).coerceIn(0f, 1f)

    val animatedColor by animateColorAsState(
        targetValue = when {
            lightLux in preferredMin..preferredMax -> Color(0xDD00FF00)
            else -> Color(0xDDFFFF00)
        },
        animationSpec = tween(durationMillis = 1000)
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxSize()
    ) {
        Text(
            text = "$lightLux lux",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        Box(
            modifier = Modifier
                .width(60.dp)
                .height(300.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .padding(4.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            val height = 300.dp

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color(0xFF2A2A2A))
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(preferredFillFraction)
                    .offset(0.dp, height * -preferredOffsetFraction)
                    .background(Color(0xFF507C45))
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(animatedFill)
                    .clip(RoundedCornerShape(8.dp))
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color(0xBBFFFFFF),
                                animatedColor
                            )
                        )
                    )
            )
        }
    }
}
