package ch.stephgit.memory.di

import android.app.Application
import android.content.Context
import ch.stephgit.memory.ui.viewmodel.RankingViewModel
import ch.stephgit.memory.persistence.repository.GameRepository
import ch.stephgit.memory.persistence.repository.UserRepository
import ch.stephgit.memory.ui.viewmodel.HistoryViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import dagger.BindsOptionalOf
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
    fun provideFirebase() : FirebaseFirestore = FirebaseFirestore.getInstance()

    @Provides
    @Singleton
    fun provideStorage() : FirebaseStorage = FirebaseStorage.getInstance()

    @Provides
    @Singleton
    fun provideFirebaseAuth() : FirebaseAuth = FirebaseAuth.getInstance()

    @BindsOptionalOf
    fun provideCurrentUser(auth: FirebaseAuth) : FirebaseUser? {
        if (auth.currentUser != null) {
            return auth.currentUser!!
        } else {
            return null
        }
    }

    @Provides
    @Singleton
    fun provideGameRepository(db: FirebaseFirestore) : GameRepository = GameRepository(db)

    @Provides
    @Singleton
    fun provideUserRepository(auth: FirebaseAuth) : UserRepository = UserRepository(auth)

    @Provides
    fun provideRankingViewModel(rankingViewModel: RankingViewModel) : RankingViewModel = rankingViewModel

    @Provides
    fun provideHistoryViewModel(historyViewModel: HistoryViewModel) : HistoryViewModel = historyViewModel
}