package ch.stephgit.memory

import ch.stephgit.memory.persistence.repository.GameRepository
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    fun bindGameRepository(db: FirebaseFirestore): GameRepository = GameRepository(db)

}