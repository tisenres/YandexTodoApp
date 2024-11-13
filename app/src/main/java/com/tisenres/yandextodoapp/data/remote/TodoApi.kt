package com.tisenres.yandextodoapp.data.remote

import com.tisenres.yandextodoapp.data.remote.dto.TodoListDto
import com.tisenres.yandextodoapp.data.remote.dto.TodoRequestDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface TodoApi {

    @GET("list")
    suspend fun getTodos(): TodoListDto

    @POST("list")
    suspend fun createTodo(
        @Body request: TodoRequestDto
    ): TodoListDto

//    @PATCH("list")
//    suspend fun updateTodos(@Body request: AvailableTimesRequestDto): AvailableTimesResponseDto
//
//    @GET("list/{id}")
//    suspend fun getTodoById(@Body request: AvailableTimesRequestDto, @Path("id") todoId: String): AvailableTimesResponseDto

//    @PUT("list/{id}")
//    suspend fun updateTodoById(@Body request: AvailableTimesRequestDto, @Path("id") todoId: String): AvailableTimesResponseDto
//
//    @DELETE("list/{id}")
//    suspend fun deleteTodoById(@Body request: AvailableTimesRequestDto, @Path("id") todoId: String): AvailableTimesResponseDto
//
}