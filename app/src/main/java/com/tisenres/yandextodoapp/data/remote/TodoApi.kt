package com.tisenres.yandextodoapp.data.remote

import com.tisenres.yandextodoapp.data.remote.dto.TodoListDto
import retrofit2.http.GET

interface TodoApi {

    @GET("list")
    suspend fun getTodos(): TodoListDto

//    @PATCH("list")
//    suspend fun updateTodos(@Body request: AvailableTimesRequestDto): AvailableTimesResponseDto
//
//    @GET("list/{id}")
//    suspend fun getTodoById(@Body request: AvailableTimesRequestDto, @Path("id") todoId: String): AvailableTimesResponseDto
//
//    @POST("list")
//    suspend fun createTodo(@Body request: AvailableTimesRequestDto, @Path("id") todoId: String): AvailableTimesResponseDto
//
//    @PUT("list/{id}")
//    suspend fun updateTodoById(@Body request: AvailableTimesRequestDto, @Path("id") todoId: String): AvailableTimesResponseDto
//
//    @DELETE("list/{id}")
//    suspend fun deleteTodoById(@Body request: AvailableTimesRequestDto, @Path("id") todoId: String): AvailableTimesResponseDto
//

}