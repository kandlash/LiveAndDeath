package com.example.liveanddeath.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.liveanddeath.R
import com.example.liveanddeath.model.Cell
import kotlin.random.Random

class MainViewModel : ViewModel() {
    private val _cells = MutableLiveData<List<Cell>>()
    val cells: LiveData<List<Cell>> = _cells

    private var consecutiveAliveCount = 0
    private var consecutiveDeadCount = 0
    private var lastLiveCellId = -1
    private var canBeDead = false

    init {
        _cells.value = emptyList()
    }

    fun addCell() {
        val isAlive = Random.nextBoolean()
        val newCell = Cell(
            id = cells.value?.size ?: 0,
            isAlive = isAlive,
            title = if (isAlive) "Живая" else "Мёртвая",
            description = if (isAlive) "и шевелится!" else "или прикидывается",
            imageRes = if (isAlive) R.drawable.live_cell else R.drawable.dead_cells
        )

        _cells.value = _cells.value?.plus(newCell)

        if (isAlive) {
            consecutiveAliveCount++
            if (canBeDead){
                canBeDead = false
            }
            consecutiveDeadCount = 0
            if (consecutiveAliveCount >= 3) {
                val lifeCell = Cell(
                    id = cells.value?.size ?: 0,
                    isAlive = true,
                    title = "Жизнь",
                    description = "Ку-ку",
                    imageRes = R.drawable.live
                )
                _cells.value = _cells.value?.plus(lifeCell)
                lastLiveCellId = lifeCell.id
                consecutiveAliveCount = 0
                canBeDead = true
            }
        } else {
            consecutiveDeadCount++
            consecutiveAliveCount = 0
            if (consecutiveDeadCount >= 3 && lastLiveCellId != -1 && canBeDead) {
                _cells.value = _cells.value?.map { cell ->
                    if (cell.id == lastLiveCellId) {
                        cell.copy(
                            isAlive = false,
                            title = "Жизнь померла :(",
                            description = "Когда-то здесь была жизнь..........",
                            imageRes = R.drawable.dead_cells
                        )
                    } else {
                        cell
                    }
                }
                consecutiveDeadCount = 0
                lastLiveCellId = -1
            }
        }
    }

    class MainViewModelFactory : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return MainViewModel() as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }

}
