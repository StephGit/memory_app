package ch.stephgit.memory

import android.app.Application
import android.content.Context
import ch.stephgit.memory.persistence.repository.GameRepository
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    fun provideContext(application: Application) : Context = application

    @Provides
    @Singleton
    fun provideFirebase(): FirebaseFirestore = FirebaseFirestore.getInstance()

    @Provides
    @Singleton
    fun provideGameRepository(db: FirebaseFirestore): GameRepository = GameRepository(db)

}