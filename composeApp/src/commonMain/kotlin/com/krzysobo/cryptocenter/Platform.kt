package com.krzysobo.cryptocenter

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
