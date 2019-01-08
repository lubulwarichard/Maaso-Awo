package net.unique.awo.db.user

import io.reactivex.Observable
import net.unique.awo.model.LoginCreds
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserLocalRepo @Inject constructor(var userDao: UserDao) {

    fun getUserCreds(): Observable<LoginCreds> {
        return Observable.fromCallable { userDao.getUserCreds() }
    }

    fun addUserCreds(userCreds: LoginCreds) {
        userDao.insertUserCreds(userCreds)
    }

}