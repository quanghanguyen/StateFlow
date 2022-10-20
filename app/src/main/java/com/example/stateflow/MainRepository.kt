package com.example.stateflow

class MainRepository {
    fun login(
        username: String,
        password: String,
        onSuccess: (String) -> Unit,
        onFail: (String) -> Unit
    ) {
        if (username == "android" && password == "123456") {
            onSuccess("Success")
        } else {
            onFail("Fail")
        }
    }
}