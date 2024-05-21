package com.castroll.taller_pdm.ui.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.castroll.taller_pdm.R
import com.castroll.taller_pdm.data.DummyData
import com.castroll.taller_pdm.model.Task
import com.maxkeppeker.sheets.core.models.base.rememberUseCaseState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarConfig
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import com.maxkeppeler.sheets.calendar.models.CalendarStyle
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CreateScreen() {

    // Constantes para gestionar categorias
    val categoryOptions = listOf(
        "Estudio", "Proyecto"
    )
    val (selectedCategory, setSelectedCategory) = remember { mutableStateOf("") }

    // Constantes para gestionar icons
    val iconResourceMap = mapOf(
        "Icono1" to R.drawable.ic_icon1,
        "Icono2" to R.drawable.ic_icon2,
        "Icono3" to R.drawable.ic_icon3,
        "Icono4" to R.drawable.ic_icon4,
        "Icono5" to R.drawable.ic_icon5
    )

    val selectedIcon = remember { mutableStateOf("") }

    // Constantes para gestionar campos de texto
    val title = remember { mutableStateOf("") }
    val description = remember { mutableStateOf("") }
    val date = remember { mutableStateOf("") }
    val showAlert = remember { mutableStateOf(false) }

    // Constantes para gestionar colores
    val color = remember { mutableIntStateOf(0xFF003C71.toInt()) }
    val selectedColor = remember { mutableStateOf("") }
    val colorOptions = listOf(
        "Azul", "Rojo", "Verde"
    )

    val colorResourceMap = mapOf(
        "Azul" to 0xFF003C71.toInt(),
        "Rojo" to 0xFF800000.toInt(),
        "Verde" to 0xFF00FF00.toInt()
    )

    if (showAlert.value) {
        AlertDialog(
            onDismissRequest = { showAlert.value = false },
            title = { Text(text = "Tarea agregada") },
            text = { Text(text = "La tarea fue agregada a la lista.") },
            confirmButton = {
                Button(onClick = { showAlert.value = false }) { Text("OK") }
            }
        )
    }

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp) // Incrementa el margen lateral aquí
            .padding(top = 8.dp, bottom = 8.dp) // Mantiene el padding vertical original
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.Start
    ) {
        TitleText("AGREGAR TAREA")
        InputField("Titulo de la tarea:", title, "Agrega un título a la tarea")
        InputField("Descripcion:", description, "Agrega una descripción")
        DatePicker(date = date)

        Text("Color:")
        Row(verticalAlignment = Alignment.CenterVertically) {
            colorOptions.forEach { opt ->
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(
                        checked = selectedColor.value == opt,
                        onCheckedChange = {
                            if (it) {
                                selectedColor.value = opt
                                color.intValue = colorResourceMap[opt] ?: 0xFF003C71.toInt()
                            }
                        }
                    )
                    Text(opt)
                }
            }
        }

        Text("Selecciona un icono:")
        Column {
            Row(
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                for (iconName in listOf("Icono1", "Icono2")) {
                    IconWithCheckbox(iconName, selectedIcon, iconResourceMap)
                }
            }
            Row(
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                for (iconName in listOf("Icono3", "Icono4")) {
                    IconWithCheckbox(iconName, selectedIcon, iconResourceMap)
                }
            }
            Row(
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                for (iconName in listOf("Icono5")) {
                    IconWithCheckbox(iconName, selectedIcon, iconResourceMap)
                }
            }
        }

        Text("Selecciona una categoría:")
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            categoryOptions.forEach { category ->
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(
                        checked = selectedCategory == category,
                        onCheckedChange = {
                            if (it) {
                                setSelectedCategory(category)
                            } else if (selectedCategory == category) {
                                setSelectedCategory("")
                            }
                        },
                        modifier = Modifier.padding(end = 4.dp)
                    )
                    Text(category)
                }
            }
        }

        Button(
            onClick = {
                if (title.value.isNotEmpty() && description.value.isNotEmpty() && selectedIcon.value.isNotEmpty() && date.value.isNotEmpty()) {
                    saveTask(
                        title.value, description.value, date.value,
                        color.value, iconResourceMap[selectedIcon.value] ?: R.drawable.ic_launcher_foreground,
                        selectedCategory
                    )
                    showAlert.value = true
                    title.value = ""
                    description.value = ""
                    date.value = ""
                    selectedIcon.value = ""
                    setSelectedCategory("")
                    selectedColor.value = ""
                }
            },
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .fillMaxSize()
        ) {
            Text(
                text = "Guardar tarea",
                modifier = Modifier.align(Alignment.CenterVertically)
            )
        }
    }
}

@Composable
fun TitleText(text: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(65.dp))
        Text(
            text = text,
            style = TextStyle(
                color = Color.Black,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        )
    }
}

@Composable
fun IconWithCheckbox(iconName: String, selectedIcon: MutableState<String>, iconResourceMap: Map<String, Int>) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Checkbox(
            checked = selectedIcon.value == iconName,
            onCheckedChange = {
                if (it) {
                    selectedIcon.value = iconName
                } else if (selectedIcon.value == iconName) {
                    selectedIcon.value = ""
                }
            },
            modifier = Modifier.padding(end = 4.dp)
        )
        Icon(
            painter = painterResource(id = iconResourceMap[iconName] ?: R.drawable.ic_launcher_foreground),
            contentDescription = iconName,
            modifier = Modifier.size(24.dp)
        )
        Text(iconName)
    }
}

@Composable
fun InputField(label: String, state: MutableState<String>, placeholder: String) {
    Text(label, style = MaterialTheme.typography.bodyMedium)
    OutlinedTextField(
        value = state.value,
        onValueChange = { state.value = it },
        label = { Text(placeholder) },
        modifier = Modifier.fillMaxWidth()
    )
}

fun saveTask(title: String, description: String, date: String, color: Int, iconId: Int, category: String) {
    val effectiveCategory = category.ifBlank { "Sin categoria" }
    val newItem = Task(name = title, description = description, date = date, state = false, color = color, iconId = iconId, category = effectiveCategory)
    DummyData.addItem(newItem)
}


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePicker(date: MutableState<String>) {
    val selectedDate = remember { mutableStateOf(LocalDate.now()) }
    val open = remember { mutableStateOf(false) }

    val handleClose = { open.value = false }

    if (open.value) {
        CalendarDialog(
            state = rememberUseCaseState(
                visible = open.value,
                onCloseRequest = { handleClose() }
            ),
            config = CalendarConfig(
                yearSelection = true,
                style = CalendarStyle.MONTH
            ),
            selection = CalendarSelection.Date(
                selectedDate = selectedDate.value
            ) { newDate ->
                selectedDate.value = newDate
                date.value = newDate.format(DateTimeFormatter.ISO_DATE)
                handleClose()
            }
        )
    }

    TextField(
        modifier = Modifier.clickable { open.value = true },
        enabled = false,
        value = date.value,
        onValueChange = {},
        label = { Text("Fecha") },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            disabledTextColor = MaterialTheme.colorScheme.onSurface,
            disabledBorderColor = MaterialTheme.colorScheme.outline,
            disabledPlaceholderColor = MaterialTheme.colorScheme.onSurfaceVariant,
            disabledLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
            disabledLeadingIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
            disabledTrailingIconColor = MaterialTheme.colorScheme.onSurfaceVariant
        )
    )

}









