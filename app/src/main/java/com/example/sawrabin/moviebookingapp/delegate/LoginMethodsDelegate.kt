package com.example.sawrabin.moviebookingapp.delegate

interface LoginMethodsDelegate {

    fun onClickConfirmSignUp(
        email: String,
        password: String,
        phone: String,
        name: String
    )

    fun onCLickConfirmLoginIn(email: String, password: String)
}
