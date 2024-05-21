package com.castroll.taller_pdm.model

data class Task(
    private val name: String,
    private val description: String,
    private val date: String,
    private var state: Boolean,
    private val color: Int,
    private val iconId: Int,
    private val category: String
) {
    fun getName(): String = name
    fun getDescription(): String = description
    fun getDate(): String = date
    fun getState(): Boolean = state
    fun setState(value: Boolean) { state = value }
    fun getColor(): Int = color
    fun getIconId(): Int = iconId
    fun getCategory(): String = category
}