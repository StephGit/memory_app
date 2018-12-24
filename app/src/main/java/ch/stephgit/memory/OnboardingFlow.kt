package ch.stephgit.memory

import ch.stephgit.memory.persistence.entity.Player

interface OnboardingFlow {

    fun goToLogin()
    fun goToRegistration()
    fun goToAgb(player: Player)
    fun goToProfile()
    fun finishOnboarding()
}