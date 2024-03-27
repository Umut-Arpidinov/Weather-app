package com.reviro.common.base

import androidx.annotation.StringRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.reviro.common.base.network.ApiResult
import com.reviro.common.base.network.MessageException
import com.reviro.common.utils.SingleLiveEvent
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import kotlin.coroutines.CoroutineContext

abstract class BaseViewModel : ViewModel(), CoroutineScope {

    private val scopeJob: Job = SupervisorJob()

    override val coroutineContext: CoroutineContext = scopeJob + Dispatchers.Main

    private val _error = SingleLiveEvent<ApiResult.Error>()
    val error: LiveData<ApiResult.Error> get() = _error


    private val _loading = MutableLiveData<Boolean>(false)
    val loading: LiveData<Boolean> get() = _loading

    private var loadersCount = 0

    protected fun postError(throwable: Throwable) {
        if (throwable !is CancellationException) {
            _error.postValue(ApiResult.error(throwable))
        }
        if (throwable is HttpException && throwable.code() == 401) {
            viewModelScope.coroutineContext.cancelChildren()
        }
    }

    protected fun postErrorMessage(@StringRes strinResId: Int) {
        _error.postValue(ApiResult.error(MessageException(strinResId)))
    }

    fun setLoading(loading: Boolean) {
        val oldValue = loadersCount

        loadersCount += if (loading) 1 else -1
        if (oldValue == 1 && loadersCount == 0) {
            _loading.postValue(false)
        }
        if (oldValue == 0 && loadersCount == 1) {
            _loading.postValue(true)
        }
    }

    protected inline fun <T> request(
        crossinline source: suspend CoroutineScope.() -> ApiResult<T>,
        crossinline onError: (Throwable) -> Unit = { },
        crossinline onFinally: () -> Unit = {},
        crossinline onSuccess: (data: T) -> Unit,
    ) {
        viewModelScope.launch {
            setLoading(true)
            val result = source()
            setLoading(false)
            processResult(result, onError, onSuccess)
            onFinally()
        }
    }


    fun <T : Any> CoroutineScope.makeDbRequest(
        request: suspend () -> T,
        onSuccess: (T) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        viewModelScope.launch {
            try {
                val result = withContext(Dispatchers.IO) { request() }
                onSuccess(result)
            } catch (e: Throwable) {
                onError(e)
            }
        }
    }

    protected inline fun <T> processResult(
        result: ApiResult<T>,
        crossinline onError: (Throwable) -> Unit = { },
        crossinline onSuccess: (data: T) -> Unit
    ) {
        when (result) {
            is ApiResult.Success -> onSuccess(result.data)
            is ApiResult.Error -> {
                onError(result.throwable)
                if (!result.handled) postError(result.throwable)
            }

        }
    }
}