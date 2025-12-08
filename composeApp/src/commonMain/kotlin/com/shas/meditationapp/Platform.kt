package com.shas.meditationapp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform