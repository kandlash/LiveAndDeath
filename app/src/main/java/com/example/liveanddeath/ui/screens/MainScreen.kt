package com.example.liveanddeath.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.liveanddeath.model.Cell
import com.example.liveanddeath.ui.viewmodel.MainViewModel
import com.example.liveanddeath.R
import androidx.lifecycle.viewmodel.compose.viewModel



@Composable
fun MainScreen(modifier: Modifier = Modifier, padding: PaddingValues) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF310050),
                        Color(0xFF000000)
                    )
                )
            )
            .padding(start = padding.calculateBottomPadding())
    ) {
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(padding)) {

            val viewModel = viewModel<MainViewModel>(factory = MainViewModel.MainViewModelFactory())
            val cells by viewModel.cells.observeAsState(initial = emptyList())

            Row(modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.1f),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ){
                Text(text = stringResource(id = R.string.main_screen_title), color = Color.White, fontSize = 24.sp)
            }

            Spacer(modifier = Modifier.height(20.dp))

            Box(modifier = Modifier
                .fillMaxSize()
                .weight(1f)) {
                CellList(cells = cells)
            }

            Spacer(modifier = Modifier.height(20.dp))

            Row(modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.15f),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
                ){

                Button(
                    onClick = { viewModel.addCell() },
                    modifier = Modifier.padding(16.dp).fillMaxWidth(0.95f),
                    shape = RoundedCornerShape(4.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF5A3472))
                ) {
                    Text(text = "СОТВОРИТЬ", color = Color.White, fontSize = 22.sp)
                }

            }
        }

    }
}


@Composable
fun CellItem(cell: Cell) {
    Box(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth().background(color = Color.White, shape = RoundedCornerShape(5.dp)),
    ) {
        Row(
            modifier = Modifier.padding(6.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = cell.imageRes),
                contentDescription = null,
                modifier = Modifier
                    .padding(top = 8.dp)
                    .size(50.dp)
                    .clip(MaterialTheme.shapes.medium)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Column(verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start) {
                Text(
                    text = cell.title,
                )
                Text(
                    text = cell.description,
                )
            }


        }
    }
}


@Composable
fun CellList(cells: List<Cell>) {
    LazyColumn(modifier = Modifier.fillMaxHeight(0.95f).padding(8.dp)) {
        items(cells) { cell ->
            CellItem(cell = cell)
        }
    }
}




