package com.example.polychat24

class User(
    val userID: Int,
    val stuNum: String,
    val stuName: String
) {
    constructor() : this(
        0,
        "",
        ""
    )
}

