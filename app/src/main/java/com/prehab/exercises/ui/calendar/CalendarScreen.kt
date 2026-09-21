package com.prehab.exercises.ui.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.prehab.exercises.progress.CalendarDay
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Locale

private val WEEKDAY_HEADERS = listOf("S", "M", "T", "W", "T", "F", "S")

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarScreen(onBack: () -> Unit) {
    val viewModel: CalendarViewModel = viewModel()
    val monthProgress by viewModel.monthProgress.collectAsStateWithLifecycle()
    val displayedMonth by viewModel.displayedMonth.collectAsStateWithLifecycle()
    val isCurrentOrFutureMonth = !displayedMonth.isBefore(YearMonth.now())

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Calendar", fontWeight = FontWeight.SemiBold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        },
        containerColor = MaterialTheme.colorScheme.surface
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding).padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { viewModel.showPreviousMonth() }) {
                    Icon(Icons.Filled.ChevronLeft, contentDescription = "Previous month")
                }
                Text(
                    text = "${displayedMonth.month.getDisplayName(TextStyle.FULL, Locale.getDefault())} ${displayedMonth.year}",
                    style = MaterialTheme.typography.titleLarge
                )
                IconButton(
                    onClick = { viewModel.showNextMonth() },
                    enabled = !isCurrentOrFutureMonth
                ) {
                    Icon(Icons.Filled.ChevronRight, contentDescription = "Next month")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                WEEKDAY_HEADERS.forEach { label ->
                    Text(
                        text = label,
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.size(40.dp),
                        textAlign = TextAlign.Center
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))
            monthProgress.weeks.forEach { week ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    week.forEach { day ->
                        CalendarDayCell(day)
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "${monthProgress.completedCount} days completed this month",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun CalendarDayCell(day: CalendarDay?) {
    if (day == null) {
        Spacer(modifier = Modifier.size(40.dp))
        return
    }

    val backgroundColor = if (day.isCompleted) {
        MaterialTheme.colorScheme.primary
    } else {
        Color.Transparent
    }
    val textColor = if (day.isCompleted) {
        MaterialTheme.colorScheme.onPrimary
    } else {
        MaterialTheme.colorScheme.onSurface
    }

    Box(
        modifier = Modifier
            .size(40.dp)
            .background(backgroundColor, CircleShape)
            .then(
                if (day.isToday && !day.isCompleted) {
                    Modifier.border(2.dp, MaterialTheme.colorScheme.primary, CircleShape)
                } else {
                    Modifier
                }
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = day.date.dayOfMonth.toString(),
            style = MaterialTheme.typography.bodyMedium,
            color = textColor
        )
    }
}
