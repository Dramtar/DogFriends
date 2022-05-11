package com.dramtar.dogfreinds.presenter.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.dramtar.dogfreinds.domain.model.Dog
import com.dramtar.dogfreinds.domain.model.User
import com.dramtar.dogfreinds.domain.usecase.*
import com.dramtar.dogfreinds.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
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
    private val getUserByIdUseCase: GetUserByIdUseCase,
    private val saveUserUseCase: SaveUserLocalUseCase,
    private val validations: ValidationUseCases,
) : ViewModel() {

    private val _selectedUser = MutableStateFlow<User?>(null)
    val selectedUser: StateFlow<User?> = _selectedUser

    private val _tempDog = MutableStateFlow<Dog?>(null)
    val tempDog: StateFlow<Dog?> = _tempDog

    private val _state = MutableStateFlow(AddingNewUserFromState())
    val state: StateFlow<AddingNewUserFromState> = _state

    private val validationEventChannel = Channel<ValidationEvent>()
    val validationEvents = validationEventChannel.receiveAsFlow()

    @ExperimentalPagingApi
    val usersList: Flow<PagingData<User>> = getUsersUseCase.execute().cachedIn(viewModelScope)

    fun setSelectedUser(user: User) {
        resetUserState()
        resetDogState()
        getLocalDog(user)
        _selectedUser.value = user
    }

    private fun resetAddingUserState() {
        _state.value = AddingNewUserFromState()
    }

    private fun resetDogState() {
        _tempDog.value = null
    }

    private fun resetUserState() {
        _selectedUser.value = null
    }

    fun onEvent(event: AddingNewUserFromEvent) {
        when (event) {
            is AddingNewUserFromEvent.EmailChanged -> {
                _state.value = state.value.copy(email = event.email)
            }
            is AddingNewUserFromEvent.LastNameChanged -> {
                _state.value = state.value.copy(lastName = event.lastName)
            }
            is AddingNewUserFromEvent.FirstNameChanged -> {
                _state.value = state.value.copy(firstName = event.firstName)
            }
            is AddingNewUserFromEvent.GenderChanged -> {
                _state.value = state.value.copy(gender = event.gender)
            }
            is AddingNewUserFromEvent.AvatarChanged -> {
                _state.value = state.value.copy(avatar = event.avatar)
            }
            is AddingNewUserFromEvent.DateChanged -> {
                _state.value = state.value.copy(date = event.date)
            }
            is AddingNewUserFromEvent.Adding -> {
                addUser()
            }
        }
    }

    fun getLocalDog(user: User) {
        viewModelScope.launch {
            when (val dog = getLocalDogCall(email = user.email)) {
                is Result.Success -> {
                    dog.data?.let {
                        _tempDog.value = it
                    } ?: run {
                        getRemoteDog(email = user.email)
                    }
                }
                is Result.Error -> {
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
                    dog.data?.let {
                        _tempDog.value = it
                    }
                }
                is Result.Error -> {
                }
                else -> {}
            }
        }
    }

    fun editUserById(id: Int) {
        resetUserState()
        resetAddingUserState()
        if (id == -1) return
        viewModelScope.launch(Dispatchers.IO) {
            getUserByIdCall(id = id).collectLatest {
                it?.let { user ->
                    _state.value = state.value.copy(
                        email = user.email,
                        firstName = user.firstName,
                        lastName = user.lastName,
                        gender = user.gender,
                        date = user.date,
                        avatar = user.pictureLarge
                    )
                    _selectedUser.value = user
                }
            }
        }
    }

    private fun addUser() {
        if (!validateAllFields()) return
        val newUser = User(
            id = _selectedUser.value?.id ?: 0,
            gender = state.value.gender,
            firstName = state.value.firstName,
            lastName = state.value.lastName,
            age = 18,          //implement counting age
            date = state.value.date,
            pictureLarge = state.value.avatar,
            pictureMedium = state.value.avatar,
            email = state.value.email,
            lastUpdate = 0,
            isVip = true
        )

        addUserCall(user = newUser)

        viewModelScope.launch {
            validationEventChannel.send(ValidationEvent.Success)
        }
    }

    private fun validateAllFields(): Boolean {
        val emailValidation = validations.emailValidation.execute(state.value.email)
        val firstNameValidation = validations.nameValidation.execute(state.value.firstName)
        val lastNameValidation = validations.nameValidation.execute(state.value.lastName)
        val genderValidation = validations.genderValidation.execute(state.value.gender)

        val hasError = listOf(
            emailValidation,
            firstNameValidation,
            lastNameValidation,
            genderValidation
        ).any { !it.successful }

        if (hasError) {
            _state.value = state.value.copy(
                emailError = emailValidation.errorMessage,
                firstNameError = firstNameValidation.errorMessage,
                lastNameError = lastNameValidation.errorMessage,
                genderError = genderValidation.errorMessage
            )
            return false
        }
        return true
    }

    private fun addUserCall(user: User) {
        viewModelScope.launch(Dispatchers.IO) {
            saveUserUseCase(user = user)
        }
    }

    private suspend fun getLocalDogCall(email: String): Result<Dog> {
        return getLocalDogUseCase.execute(email = email)
    }

    private suspend fun getRemoteDogCall(email: String): Result<Dog> {
        return getRemoteDogUseCase.execute(email = email)
    }

    private suspend fun getUserByIdCall(id: Int): Flow<User?> {
        return getUserByIdUseCase.execute(id = id)
    }

    sealed class ValidationEvent {
        object Success : ValidationEvent()
    }
}