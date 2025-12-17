package com.SuperfumeKotlin.data.model.dto.request

import com.google.gson.annotations.SerializedName

/**
 * DTO para cambiar el rol de un usuario
 */
data class ChangeRoleRequest(
    @SerializedName("rolId")
    val rolId: Int
)
