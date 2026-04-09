package com.example.smartpot.ui.screens

import android.content.Context
import android.graphics.Paint
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.smartpot.ui.Screen
import com.example.smartpot.ui.components.H2
import com.example.smartpot.ui.components.control.DropdownControl
import com.example.smartpot.ui.kit.BackButton
import com.example.smartpot.ui.models.DeviceViewModel
import com.example.smartpot.util.Plant
import com.example.smartpot.util.plants
import kotlin.math.sin
import kotlin.math.cos

@Composable
fun SensorScreen(navController: NavController, vm: DeviceViewModel = hiltViewModel()) {
    val scrollState = rememberScrollState()

    var plant by remember<MutableState<Plant?>> { mutableStateOf(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {
        Spacer(Modifier.height(16.dp))

        BackButton(
            buttonText = "К списку устройств",
            route = Screen.DeviceList.route,
            popUpTo = Screen.Sensor.route,
            navController = navController
        )

        H2("Сенсоры")

        DropdownControl(
            "Вид растения",
            "Выберите вид растения",
            plants.associateWith { it.title }
        ) {
            plant = it
        }

        Spacer(Modifier.height(32.dp))

        CompassDisplay(plant?.preferredAngle)

        Spacer(Modifier.height(16.dp))
    }
}

@Composable
fun CompassDisplay(preferredAngle: Int?) {
    val context = LocalContext.current
    val sensorManager = remember { context.getSystemService(Context.SENSOR_SERVICE) as SensorManager }
    var angle by remember { mutableFloatStateOf(0f) }

    DisposableEffect(sensorManager) {
        val listener = object : SensorEventListener {
            override fun onSensorChanged(event: SensorEvent) {
                val r = FloatArray(9)
                val o = FloatArray(3)
                SensorManager.getRotationMatrixFromVector(r, event.values)
                SensorManager.getOrientation(r, o)
                angle = (Math.toDegrees(o[0].toDouble()).toFloat() + 360) % 360
            }

            override fun onAccuracyChanged(s: Sensor?, a: Int) {}
        }

        sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR)?.let {
            sensorManager.registerListener(listener, it, SensorManager.SENSOR_DELAY_UI)
        }

        onDispose {
            sensorManager.unregisterListener(listener)
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "%.0f°".format(angle),
            fontSize = 48.sp,
            fontWeight = FontWeight.Bold
        )

//        Text(
//            text = getDirectionLabel(angle),
//            fontSize = 24.sp,
//            modifier = Modifier.padding(top = 8.dp)
//        )

        Spacer(modifier = Modifier.height(48.dp))

        Box(
            modifier = Modifier
                .size(250.dp)
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                val centerX = size.width / 2
                val centerY = size.height / 2
                val radius = size.minDimension / 2 - 20.dp.toPx()

                drawCircle(
                    color = Color.Gray,
                    radius = radius,
                    style = Stroke(width = 4.dp.toPx())
                )

                if (preferredAngle != null) {
                    val sweep1 = 90f
                    val sweep2 = 180f

                    drawArc(
                        color = Color.Yellow,
                        startAngle = - preferredAngle - angle - sweep2 / 2,
                        sweepAngle = sweep2,
                        useCenter = false,
                        style = Stroke(width = 6.dp.toPx())
                    )

                    drawArc(
                        color = Color.Green,
                        startAngle = - preferredAngle - angle - sweep1 / 2,
                        sweepAngle = sweep1,
                        useCenter = false,
                        style = Stroke(width = 6.dp.toPx())
                    )
                }

                val directions = listOf("N", "E", "S", "W")
                directions.forEachIndexed { index, direction ->
                    val angle = Math.toRadians((index * 90 - 90 - angle).toDouble())
                    val markerRadius = radius - 28.dp.toPx()
                    val x = centerX + (markerRadius * cos(angle)).toFloat()
                    val y = centerY + (markerRadius * sin(angle)).toFloat()

                    drawContext.canvas.nativeCanvas.apply {
                        val paint = Paint().apply {
                            color = if (direction == "N") android.graphics.Color.RED
                            else android.graphics.Color.WHITE
                            textSize = 24.dp.toPx()
                            textAlign = Paint.Align.CENTER
                        }
                        drawText(direction, x, y + 10.dp.toPx(), paint)
                    }
                }

                for (i in 0 until 360 step 5) {
                    val angle = Math.toRadians(i.toDouble() - angle)
                    var innerRadius = radius - 3.dp.toPx()
                    var outerRadius = radius + 3.dp.toPx()

                    if (i % 45 == 0) {
                        innerRadius -= 4.dp.toPx()
                        outerRadius += 4.dp.toPx()
                    }

                    if (i % 90 == 0) {
                        innerRadius -= 5.dp.toPx()
                        outerRadius += 5.dp.toPx()
                    }

                    val startX = centerX + (innerRadius * cos(angle)).toFloat()
                    val startY = centerY + (innerRadius * sin(angle)).toFloat()
                    val endX = centerX + (outerRadius * cos(angle)).toFloat()
                    val endY = centerY + (outerRadius * sin(angle)).toFloat()

                    drawLine(
                        color = Color.Gray,
                        start = Offset(startX, startY),
                        end = Offset(endX, endY),
                        strokeWidth = 2.dp.toPx()
                    )
                }
            }

            Box(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .offset(y = (-36).dp)
            ) {
                TriangleIndicator(color = Color.White)
            }
        }
    }
}


@Composable
fun TriangleIndicator(color: Color) {
    Canvas(modifier = Modifier.size(36.dp, 24.dp)) {
        val path = Path().apply {
            moveTo(size.width / 2, 0f)
            lineTo(size.width, size.height)
            lineTo(0f, size.height)
            close()
        }
        drawPath(path, color)
    }
}

fun getDirectionLabel(azimuth: Float): String {
    return when (azimuth) {
        !in 22.5..<337.5 -> "N"
        in 22.5..<67.5 -> "NE"
        in 67.5..<112.5 -> "E"
        in 112.5..<157.5 -> "SE"
        in 157.5..<202.5 -> "S"
        in 202.5..<247.5 -> "SW"
        in 247.5..<292.5 -> "W"
        in 292.5..<337.5 -> "NW"
        else -> "?"
    }
}
