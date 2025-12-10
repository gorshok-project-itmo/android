package com.example.smartpot.ui.models

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartpot.data.api.Auth
import com.example.smartpot.data.api.AuthRequest
import com.example.smartpot.data.repository.SmartPotRepository
import com.example.smartpot.data.repository.TokenRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(private val repo: SmartPotRepository, private val tokenRepo: TokenRepository) : ViewModel() {
    private val _email = mutableStateOf("")
    val email = _email

    private val _password = mutableStateOf("")
    val password = _password

    private val _confirmPassword = mutableStateOf("")
    val confirmPassword = _confirmPassword

    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error = _error.asStateFlow()

    private val _signedInEvent = MutableSharedFlow<Unit>(replay = 0)
    val signedInEvent: SharedFlow<Unit> = _signedInEvent

    fun onEmailChange(v: String) { _email.value = v }
    fun onPasswordChange(v: String) { _password.value = v }
    fun onConfirmPasswordChange(v: String) { _confirmPassword.value = v }

    fun signup() {
        val e = _email.value.trim()
        val p = _password.value
        val c = _confirmPassword.value

        if (e.isEmpty() || p.length < 4) {
            _error.value = "Введите корректные email и пароль"
            return
        }

        if (p != c) {
            _error.value = "Пароли не совпадают"
            return
        }

        viewModelScope.launch {
            _loading.value = true

            try {
                val req = AuthRequest(Auth(e, p))
                val resp = repo.postSignup(req)
                val token = resp.status.token
                tokenRepo.saveToken(token)
                _signedInEvent.emit(Unit)
            } catch (e: Throwable) {
                _error.value = e.message ?: "Ошибка сети"
            } finally {
                _loading.value = false
            }
        }
    }
}
