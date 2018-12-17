package stephgit.ch.memory

import stephgit.ch.memory.persistence.entity.Player

interface OnboardingFlow {

    fun goToRegistration()
    fun goToAgb(player: Player)
    fun goToProfile()
    fun finishOnboarding()
}