package com.finals.agrifund

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class UserViewModel : ViewModel() {
    private val _userName = MutableLiveData<String?>()
    val userName: MutableLiveData<String?> get() = _userName

    private val _userEmail = MutableLiveData<String?>()
    val userEmail: MutableLiveData<String?> get() = _userEmail

    private val _userPhoto = MutableLiveData<String?>()
    val userPhoto: MutableLiveData<String?> get() = _userPhoto

    fun setUserDetails(name: String?, email: String?, photoUrl: String?) {
        _userName.value = name
        _userEmail.value = email
        _userPhoto.value = photoUrl
    }
}

