package ch.stephgit.memory.di

import android.app.Application
import ch.stephgit.memory.persistence.repository.UserRepository
import ch.stephgit.memory.ui.viewmodel.HistoryViewModel
import ch.stephgit.memory.ui.viewmodel.RankingViewModel
import ch.stephgit.memory.ui.main.GamePlayFragment
import ch.stephgit.memory.ui.main.MainActivity
import ch.stephgit.memory.ui.main.UserFragment
import ch.stephgit.memory.ui.onboarding.LoginFragment
import ch.stephgit.memory.ui.onboarding.OnboardingActivity
import ch.stephgit.memory.ui.onboarding.ProfileFragment
import ch.stephgit.memory.ui.onboarding.RegistrationFragment
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
    fun inject(historyViewModel: HistoryViewModel)
    fun inject(gamePlayFragment: GamePlayFragment)
    fun inject(loginFragment: LoginFragment)
    fun inject(mainActivity: MainActivity)
    fun inject(onboardingActivity: OnboardingActivity)
    fun inject(profileFragment: ProfileFragment)
    fun inject(registrationFragment: RegistrationFragment)
    fun inject(userFragment: UserFragment)
    fun inject(userRepository: UserRepository)
}