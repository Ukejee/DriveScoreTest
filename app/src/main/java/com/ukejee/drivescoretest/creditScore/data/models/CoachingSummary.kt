package com.ukejee.drivescoretest.creditScore.data.models

data class CoachingSummary(
    val activeChat: Boolean = false,
    val activeTodo: Boolean = false,
    val numberOfCompletedTodoItems: Int? = null,
    val numberOfTodoItems: Int? = null,
    val selected: Boolean = false
)