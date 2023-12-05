package com.lucas.petros.commons.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment


abstract class BaseFragment(
    @LayoutRes private val layoutId: Int,
) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = inflater.inflate(layoutId, container, false)

}

abstract class BaseFragmentVDB<VDB : ViewDataBinding>(
    @LayoutRes private val layoutId: Int,
) : BaseFragment(
    layoutId,
) {

    protected lateinit var binding: VDB

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = (DataBindingUtil.inflate(inflater, layoutId, container, false) as? VDB)?.apply {
        lifecycleOwner = viewLifecycleOwner
        binding = this
    }?.root

    abstract fun setupViewModel()

    abstract fun setupObservers()
}