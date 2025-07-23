package com.justparokq.login.data

import com.justparokq.login.model.UserModel

internal class UserRepositoryImpl : UserRepository {

    private val tempStorage = hashMapOf<String, UserModel>(
        "user" to UserModel("user", "pass")
    )

    override fun save(user: UserModel): Boolean {
        tempStorage[user.username] = user
        return true
    }

    override fun findByUsername(username: String): UserModel {
        return tempStorage[username] ?: throw NoSuchElementException("no such a user")
    }

    override fun doesUserExist(user: UserModel): Boolean {
        return tempStorage[user.username] == user
    }
}

// in test env only one user is used
internal class TestUserRepositoryImpl : UserRepository {

    private val user = UserModel("test", "qwerty")

    override fun save(user: UserModel): Boolean {
        return true
    }

    override fun findByUsername(username: String): UserModel {
        return user
    }

    override fun doesUserExist(user: UserModel): Boolean {
        // any credentials are ok
        return true
    }
}