package com.tisenres.yandextodoapp.data.remote

import com.tisenres.yandextodoapp.data.remote.dto.TodoDto
import com.tisenres.yandextodoapp.data.remote.dto.TodoListDto
import com.tisenres.yandextodoapp.data.remote.dto.TodoRequestDto
import com.tisenres.yandextodoapp.data.remote.dto.TodoResponseDto
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import java.util.UUID

interface TodoApiService {

    @GET("list")
    suspend fun getTodos(): TodoListDto

    @POST("list")
    suspend fun createTodo(
        @Body request: TodoRequestDto,
        @Header("X-Last-Known-Revision") revision: Int
    ): TodoListDto

    @PATCH("list")
    suspend fun updateTodos(
        @Body request: List<TodoDto>,
        @Header("X-Last-Known-Revision") revision: Int
    ): TodoListDto

    @GET("list/{id}")
    suspend fun getTodoById(
        @Path("id") todoId: UUID
    ): TodoResponseDto

    @PUT("list/{id}")
    suspend fun updateTodoById(
        @Body request: TodoRequestDto,
        @Path("id") todoId: String,
        @Header("X-Last-Known-Revision") revision: Int
    ): TodoResponseDto

    @DELETE("list/{id}")
    suspend fun deleteTodoById(
        @Path("id") todoId: String,
        @Header("X-Last-Known-Revision") revision: Int
    ): TodoResponseDto
}