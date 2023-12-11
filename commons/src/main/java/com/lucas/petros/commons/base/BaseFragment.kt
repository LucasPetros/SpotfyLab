package com.lucas.petros.commons.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.lucas.petros.analytics.CommonEvents
import com.lucas.petros.analytics.CommonParams
import com.lucas.petros.analytics.IAnalyticsLog
import dagger.hilt.android.migration.CustomInjection.inject
import javax.inject.Inject


abstract class BaseFragment(
    @LayoutRes private val layoutId: Int,
    private val screenLogName: String
) : Fragment() {

    @Inject
    lateinit var  analyticsLog: IAnalyticsLog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = inflater.inflate(layoutId, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        analyticsLog.logEvent(
            CommonEvents.SHOW_FRAGMENT,
            bundleOf(CommonParams.FRAGMENT_NAME to screenLogName)
        )
    }

}

abstract class BaseFragmentVDB<VDB : ViewDataBinding>(
    @LayoutRes private val layoutId: Int,
    screenLogName: String,
) : BaseFragment(
    layoutId,
    screenLogName
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
        setupObservers()
    }

    abstract fun setupViewModel()

    abstract fun setupObservers()
}