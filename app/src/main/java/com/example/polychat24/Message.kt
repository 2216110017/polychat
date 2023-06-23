package com.example.polychat24

data class Message(
    var message: String?,
    var sendId: String? //접속자 userID
){
    constructor():this("","")
}
