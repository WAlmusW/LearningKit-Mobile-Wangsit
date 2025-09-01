package com.example.lkwangsit.ui.component

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.lkwangsit.theme.LKWangsitTheme

@Composable
fun SegmentedTabMenu(
    modifier: Modifier = Modifier,
    selectedTabIndex: Int,
    onSelectedChange: (Int) -> Unit,
    tabs: List<String>,
    cornerRadius: Dp = 12.dp,
    indicatorPadding: Dp = 6.dp,            // inner margin so the green pill doesn't touch the border
    borderWidth: Dp = 1.dp
) {
    val density = LocalDensity.current
    var containerWidthPx by remember { mutableIntStateOf(0) }

    val segmentWidth = remember(containerWidthPx, tabs.size) {
        if (tabs.isEmpty() || containerWidthPx == 0) 0.dp
        else with(density) { (containerWidthPx / tabs.size).toDp() }
    }

    val indicatorOffset by animateDpAsState(
        targetValue = segmentWidth * selectedTabIndex,
        label = "IndicatorOffset"
    )

    // The white rounded container with a thin border
    Surface(
        shape = RoundedCornerShape(cornerRadius),
        color = MaterialTheme.colorScheme.surface,                 // white
        border = BorderStroke(borderWidth, MaterialTheme.colorScheme.outline),
        modifier = modifier
            .onSizeChanged { containerWidthPx = it.width }
            .height(48.dp)
    ) {
        Box {
            // sliding selected background (green)
            Box(
                Modifier
                    .offset(x = indicatorOffset)
                    .padding(all = indicatorPadding)
                    .width(segmentWidth - indicatorPadding * 2)
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(cornerRadius))
                    .background(MaterialTheme.colorScheme.primary)
            )
            // labels row
            Row(
                Modifier
                    .fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                tabs.forEachIndexed { index, label ->
                    val selected = selectedTabIndex == index
                    Box(
                        Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .clickable { onSelectedChange(index) }
                            .padding(vertical = 12.dp),
                        contentAlignment = Alignment.Center,

                    ) {
                        Text(
                            text = label,
                            style = MaterialTheme.typography.labelLarge,
                            textAlign = TextAlign.Center,
                            color = if (selected)
                                MaterialTheme.colorScheme.onPrimary
                            else
                                MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true, backgroundColor = 0xFF047857)
@Composable
private fun SegmentedTabMenuPreview() {
    LKWangsitTheme(dynamicColor = false) {
        val tabs = listOf("Overview", "Details", "Reviews")
        var selected by remember { mutableIntStateOf(0) }

        Surface(
            color = MaterialTheme.colorScheme.primary
        ) {
            Column(Modifier.fillMaxWidth().padding(16.dp)) {
                SegmentedTabMenu(
                    selectedTabIndex = selected,
                    onSelectedChange = { selected = it },
                    tabs = tabs,
                    modifier = Modifier.fillMaxWidth().height(40.dp)
                )
                Spacer(Modifier.height(16.dp))
                Text("Selected: ${tabs[selected]}")
            }
        }
    }
}