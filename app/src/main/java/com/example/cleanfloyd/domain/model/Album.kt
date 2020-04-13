package com.example.cleanfloyd.domain.model

data class Album(val title: String, val image: String = "https://www.sa-cd.net/covers/771rev2.jpg", val format: String? = null, val type: String? = null)