package com.dramtar.dogfreinds.presenter.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.dramtar.dogfreinds.databinding.FragmentUserBinding
import com.dramtar.dogfreinds.utils.*
import com.google.android.material.appbar.AppBarLayout

/**
 * Created by Dramtar on 19.03.2022
 */
class UserFragment : Fragment() {
    private val vm: MainViewModel by activityViewModels()
    private var _binding: FragmentUserBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        val navController = findNavController()
        val appBarConfiguration = AppBarConfiguration(navController.graph)

        binding.apply {
            viewModel = vm
            lifecycleOwner = viewLifecycleOwner

            collapsingToolbarView.setupWithNavController(
                binding.toolbarView,
                navController,
                appBarConfiguration
            )

            appBarView.addOnOffsetChangedListener(object : AppBarStateChangedListener() {
                override fun onStateChanged(appBarLayout: AppBarLayout?, state: State?) {
                    binding.doggyAvatarSmallView.animateAlpha(state == State.COLLAPSED)
                }
            })

            humanAvatar.setOnClickListener {
                vm.changeUser()
            }
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}