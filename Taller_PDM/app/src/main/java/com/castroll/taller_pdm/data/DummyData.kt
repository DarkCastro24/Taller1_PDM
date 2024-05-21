package com.castroll.taller_pdm.data

import com.castroll.taller_pdm.R
import com.castroll.taller_pdm.model.Task

object DummyData {
    private val tasks: MutableList<Task> = mutableListOf(
        // Lista inicial para poblar la lista con tareas
        Task("Presentación de Analisis", "Preparar diapositivas y notas para la presentación del proyecto final.", "2024-05-10", true, 0xFF003C71.toInt(), R.drawable.ic_icon1, "Proyecto"),
        Task("Estudiar para examen de cálculo", "Revisar apuntes y resolver problemas de práctica para el próximo examen de cálculo.", "2024-05-02", false, 0xFF800000.toInt(), R.drawable.ic_icon2, "Estudio"),
        Task("Asistir a tutoría de física", "Asistir a la sesión de tutoría de física para revisar dudas sobre los ejercicios del libro de texto.", "2024-05-25", false, 0xFF00FF00.toInt(), R.drawable.ic_icon3, "Estudio")
    )

    fun addItem(item: Task) {
        tasks.add(item)
    }

    fun getItems(): List<Task> {

        return tasks
    }

    fun updateTaskState(name: String, newState: Boolean) {
        val item = tasks.find { it.getName() == name }
        item?.setState(newState)
    }

    fun deleteItem(name: String): Boolean {
        val item = tasks.find { it.getName() == name }
        return if (item != null) {
            tasks.remove(item)
            true
        } else {
            false
        }
    }
}
