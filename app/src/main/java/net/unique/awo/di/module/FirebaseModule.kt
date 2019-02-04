package net.unique.awo.di.module

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class FirebaseModule {

    @Singleton
    @Provides
    internal fun provideFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Singleton
    @Provides
    internal fun provideFirebaseDatabase(): FirebaseDatabase {
        return FirebaseDatabase.getInstance()
    }

}