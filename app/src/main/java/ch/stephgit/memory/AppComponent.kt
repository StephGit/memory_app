package ch.stephgit.memory

import android.app.Application
import ch.stephgit.memory.ui.main.MainActivity
import com.google.firebase.firestore.FirebaseFirestore
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance // Dagger injects in DependencyGraph
        fun application(application: Application): Builder
        fun build(): AppComponent
    }
    fun inject(rankingViewModel: RankingViewModel)
}