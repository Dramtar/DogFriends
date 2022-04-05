package com.dramtar.dogfreinds.presenter.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.dramtar.dogfreinds.domain.model.Dog
import com.dramtar.dogfreinds.domain.model.User
import com.dramtar.dogfreinds.domain.usecase.ChangeUserLocalUseCase
import com.dramtar.dogfreinds.domain.usecase.GetLocalDogUseCase
import com.dramtar.dogfreinds.domain.usecase.GetRemoteDogUseCase
import com.dramtar.dogfreinds.domain.usecase.GetUserUseCase
import com.dramtar.dogfreinds.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Dramtar on 14.03.2022
 */

@HiltViewModel
class MainViewModel @Inject constructor(
    getUsersUseCase: GetUserUseCase,
    private val getLocalDogUseCase: GetLocalDogUseCase,
    private val getRemoteDogUseCase: GetRemoteDogUseCase,
    private val changeUserUseCase: ChangeUserLocalUseCase
) : ViewModel() {

    private val _selectedUser = MutableLiveData<User>()
    val selectedUser: LiveData<User> = _selectedUser

    private val _tempDog = MutableLiveData<Dog>()
    val tempDog: LiveData<Dog> = _tempDog

    private val _isDogVisible = MutableLiveData<Boolean>()
    val isDogVisible = _isDogVisible

    @ExperimentalPagingApi
    val usersList: Flow<PagingData<User>> = getUsersUseCase.execute().cachedIn(viewModelScope)

    fun setSelectedUser(user: User) {
        getLocalDog(user)
        _selectedUser.postValue(user)
    }

    fun getLocalDog(user: User) {
        viewModelScope.launch {
            when (val dog = getLocalDogCall(email = user.email)) {
                is Result.Success -> {
                    val data = dog.data
                    if (data != null) {
                        isDogVisible.postValue(true)
                        _tempDog.postValue(data)
                    } else {
                        isDogVisible.postValue(false)
                        getRemoteDog(email = user.email)
                    }
                }
                is Result.Error -> {
                    isDogVisible.postValue(false)
                    Log.i("TEST result error MV", "")
                }
                else -> {}
            }
        }
    }

    fun getRemoteDog(email: String) {
        viewModelScope.launch {
            when (val dog = getRemoteDogCall(email = email)) {
                is Result.Success -> {
                    if (dog.data != null) {
                        isDogVisible.postValue(true)
                        _tempDog.postValue(dog.data)
                    } else {
                        isDogVisible.postValue(false)
                    }
                }
                is Result.Error -> {
                    isDogVisible.postValue(false)
                }
                else -> {}
            }
        }
    }

    fun changeUser() {
        changeUserCall()
    }

    private suspend fun getLocalDogCall(email: String): Result<Dog> {
        return getLocalDogUseCase.execute(email = email)
    }

    private suspend fun getRemoteDogCall(email: String): Result<Dog> {
        return getRemoteDogUseCase.execute(email = email)
    }

    private fun changeUserCall() {
        viewModelScope.launch {
            if (selectedUser.value != null) {
                _selectedUser.postValue(changeUserUseCase.execute(selectedUser.value!!))
            }
        }
    }
}