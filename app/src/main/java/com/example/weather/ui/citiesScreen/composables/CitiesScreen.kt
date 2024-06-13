package com.example.weather.ui.citiesScreen.composables

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weather.ui.citiesScreen.CitiesPresenter
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow
import com.example.weather.R
import com.example.weather.ui.citiesScreen.state.CitiesScreenUiState
import com.example.weather.ui.citiesScreen.state.CityUiModel
import com.example.weather.ui.design.ErrorScreen
import com.example.weather.ui.design.ProgressBar

@Composable
fun CitiesScreen(
    presenter: CitiesPresenter,
){

    val citiesUiState = presenter.citiesScreenUiState.collectAsState().value

    when(citiesUiState){
        is CitiesScreenUiState.Loading -> ProgressBar()
        is CitiesScreenUiState.Error -> {
            ErrorScreen(presenter::loadCities)
            Log.d("mytag", citiesUiState.message)
        }
        is CitiesScreenUiState.Success -> ListWithCustomStickHeaderScreen(
            groupedCities = citiesUiState.groupedCities,
            cities = citiesUiState.cities,
            onClick = presenter::navigateToWeather,
        )
    }
}

@SuppressLint("UnrememberedMutableState")
@Composable
fun ListWithCustomStickHeaderScreen(
    groupedCities: Map<Char, List<CityUiModel>>,
    cities: List<CityUiModel>,
    onClick: (String) -> Unit,
) {
    Box(Modifier.fillMaxSize()) {

        val startIndexes = remember(cities) {
            getStartIndexes(groupedCities.entries)
        }
        val endIndexes = remember(cities) {
            getEndIndexes(groupedCities.entries)
        }
        val listState = rememberLazyListState()
        val moveStickyHeader by remember {
            derivedStateOf {
                endIndexes.contains(listState.firstVisibleItemIndex + 1)
            }
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            state = listState,
        ) {
            itemsIndexed(cities) { index, city ->

                val firstVisibleItemIndex by remember { derivedStateOf { listState.firstVisibleItemIndex } }
                NameItem(
                    cityName = city.name,
                    showCharHeader = startIndexes.contains(index) && firstVisibleItemIndex != index,
                    onClick = onClick,
                )
            }
        }

        LetterHeader(
            char = cities[remember { derivedStateOf { listState.firstVisibleItemIndex } }.value].name[0].toString(),
            modifier = Modifier
                .padding(start = 16.dp, top = 8.dp, bottom = 8.dp)
                .then(
                    if (moveStickyHeader) {
                        Modifier.offset {
                            IntOffset(0, -listState.firstVisibleItemScrollOffset)
                        }
                    } else {
                        Modifier
                    }
                ),
            showCharHeader = true,
        )
    }
}


@Composable
fun NameItem(
    cityName: String,
    showCharHeader: Boolean,
    onClick: (String) -> Unit,
) {
    Row(
        Modifier
            .fillMaxWidth()
            .height(56.dp)
            .padding(start = 16.dp, top = 8.dp, bottom = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        LetterHeader(
            char = cityName[0].toString(),
            showCharHeader,
        )
        CityName(
            onClick = onClick,
            cityName = cityName,
        )
    }
}

@Composable
fun CityName(
    onClick: (String) -> Unit,
    cityName: String,
){
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(end = 16.dp)
            .clip(
                RoundedCornerShape(
                    topStart = 8.dp,
                    bottomStart = 8.dp,
                )
            )
            .clickable {
                onClick(cityName)
            }
            .padding(start = 16.dp),
        contentAlignment = Alignment.CenterStart,
    ) {
        Text(
            text = cityName,
            fontFamily = FontFamily(Font(R.font.roboto_regular)),
            fontSize = 16.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }
}

@Composable
fun LetterHeader(char: String, showCharHeader: Boolean, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .size(40.dp),
        contentAlignment = Alignment.Center,
    ){
        if (showCharHeader){
            Text(
                text = char,
                fontFamily = FontFamily(Font(R.font.roboto_medium)),
                fontSize = 24.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}

private fun getStartIndexes(entries: Set<Map.Entry<Char, List<CityUiModel>>>): List<Int> {
    var acc = 0
    val list = mutableListOf<Int>()
    entries.forEach { entry ->
        list.add(acc)
        acc += entry.value.size
    }
    return list
}

private fun getEndIndexes(entries: Set<Map.Entry<Char, List<CityUiModel>>>): List<Int> {
    var acc = 0
    val list = mutableListOf<Int>()
    entries.forEach { entry ->
        acc += entry.value.size
        list.add(acc)
    }
    return list
}



