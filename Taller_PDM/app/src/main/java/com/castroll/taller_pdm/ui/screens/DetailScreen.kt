package com.castroll.taller_pdm.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.castroll.taller_pdm.R
import com.castroll.taller_pdm.data.DummyData
import com.castroll.taller_pdm.ui.navigation.Screen

@Composable
fun DetailScreen(taskTitle: String?, navController: NavController) {
    val task = remember { DummyData.getItems().find { it.getName() == taskTitle } }
    var isCompleted by remember { mutableStateOf(task?.getState() ?: false) }
    var showUpdateConfirmation by remember { mutableStateOf(false) }
    var showDeleteConfirmation by remember { mutableStateOf(false) }

    Surface(color = Color.White) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .padding(top = 80.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = task?.getIconId() ?: R.drawable.ic_launcher_foreground),
                    contentDescription = "Icono de ${task?.getName()}",
                    modifier = Modifier.size(64.dp) // Ajustar el tamaño del icono
                )
                Spacer(modifier = Modifier.width(16.dp)) // Ajustar el espaciado lateral
                Column {
                    Text(
                        text = "Título: ${task?.getName()}",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = "Fecha: ${task?.getDate()}",
                        style = MaterialTheme.typography.bodyMedium
                    )

                    Text(
                        text = "Descripción: ${task?.getDescription()}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        text = "Categoría: ${task?.getCategory()}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Color seleccionado:",
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.width(8.dp))
                Box(
                    modifier = Modifier
                        .size(20.dp)
                        .background(
                            task
                                ?.getColor()
                                ?.let { Color(it) } ?: Color.Gray)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Estado:",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.weight(1f)
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(1f)
                ) {
                    Checkbox(
                        checked = isCompleted,
                        onCheckedChange = { isCompleted = it },
                        modifier = Modifier.padding(end = 4.dp)
                    )
                    Text(
                        text = if (isCompleted) "Completada" else "Pendiente",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    onClick = { showUpdateConfirmation = true },
                    colors = ButtonDefaults.buttonColors(Color.Blue),
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Actualizar estado", color = Color.White)
                }

                Button(
                    onClick = { showDeleteConfirmation = true },
                    colors = ButtonDefaults.buttonColors(Color.Red),
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Eliminar tarea", color = Color.White)
                }
            }

            if (showUpdateConfirmation) {
                AlertDialog(
                    onDismissRequest = { showUpdateConfirmation = false },
                    confirmButton = {
                        Button(
                            onClick = {
                                task?.let {
                                    DummyData.updateTaskState(it.getName(), isCompleted)
                                    navController.popBackStack(Screen.HomeScreen.name, inclusive = false)
                                }
                            }
                        ) {
                            Text("Confirmar")
                        }
                    },
                    dismissButton = {
                        Button(onClick = { showUpdateConfirmation = false }) {
                            Text("Cancelar")
                        }
                    },
                    title = { Text("Actualizar estado") },
                    text = { Text("¿Estás seguro de que deseas actualizar el estado de esta tarea?") }
                )
            }

            if (showDeleteConfirmation) {
                AlertDialog(
                    onDismissRequest = { showDeleteConfirmation = false },
                    confirmButton = {
                        Button(
                            onClick = {
                                task?.let {
                                    if (DummyData.deleteItem(it.getName())) {
                                        navController.popBackStack(Screen.HomeScreen.name, inclusive = false)
                                    }
                                }
                            }
                        ) {
                            Text("Confirmar")
                        }
                    },
                    dismissButton = {
                        Button(onClick = { showDeleteConfirmation = false }) {
                            Text("Cancelar")
                        }
                    },
                    title = { Text("Eliminar tarea") },
                    text = { Text("¿Estás seguro de que deseas eliminar esta tarea?") }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { navController.popBackStack() },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text("Regresar a Home")
            }
        }
    }
}
