package com.goalify.chat.android.util

interface DomainToViewModel<Domain, ViewModel> {
    fun translate(domain: Domain): ViewModel
}
