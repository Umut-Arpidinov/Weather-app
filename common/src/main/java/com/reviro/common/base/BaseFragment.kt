package com.reviro.common.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.reviro.common.base.network.ErrorConverter
import javax.inject.Inject

abstract class BaseFragment<VM : BaseViewModel, VB : ViewBinding>(
    private val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> VB
) : Fragment() {

    @Inject
    protected lateinit var errorConverter: ErrorConverter

    private var loading = false


    private var _binding: VB? = null
    protected val binding
        get() = _binding!!

    protected abstract val viewModel: VM


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = bindingInflater.invoke(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeLoadingState()
        initUi()
        initListeners()
        observeViewModel()
        observeErrorState()
    }

    protected open fun initUi() {}
    protected open fun initListeners() {}
    protected open fun observeViewModel() {}


    private fun observeErrorState() {
        viewModel.error.observe(viewLifecycleOwner) {
            it?.let {
                if (it.throwable is NoSuchElementException)
                    return@observe
                onError(errorConverter.convert(it.throwable))
                onError(it.throwable)

            }
        }
    }

    private fun observeLoadingState() {
        viewModel.loading.observe(viewLifecycleOwner) {
            val anyLoading = viewModel.loading.value == true
            if (loading != anyLoading) {
                loading = anyLoading
                onLoading(loading)
            }
        }
    }

    protected open fun onError(throwable: Throwable) {}

    protected open fun onError(message: String) {}

    protected open fun onLoading(loading: Boolean) {
        this.loading = loading

    }
}