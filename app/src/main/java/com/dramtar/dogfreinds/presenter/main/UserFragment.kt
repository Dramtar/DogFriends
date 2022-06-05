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
import com.dramtar.dogfreinds.R
import com.dramtar.dogfreinds.databinding.AddUserFragmentBinding
import com.dramtar.dogfreinds.databinding.FragmentUserBinding
import com.dramtar.dogfreinds.utils.*
import com.google.android.material.appbar.AppBarLayout

/**
 * Created by Dramtar on 19.03.2022
 */
class UserFragment : Fragment(R.layout.fragment_user) {
    private val vm: MainViewModel by activityViewModels()
    private lateinit var binding: FragmentUserBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentUserBinding.bind(view)
        initView()
    }

    private fun initView() {
        val navController = findNavController()
        binding.apply {
            viewModel = vm
            lifecycleOwner = viewLifecycleOwner
            fragment = this@UserFragment

            collapsingToolbarView.setupWithNavController(
                binding.toolbarView,
                navController,
                AppBarConfiguration(navController.graph)
            )

            appBarView.addOnOffsetChangedListener(object : AppBarStateChangedListener() {
                override fun onStateChanged(appBarLayout: AppBarLayout?, state: State?) {
                    binding.doggyAvatarSmallView.animateAlpha(state == State.COLLAPSED)
                }
            })
        }
    }

    fun editUser() {
        vm.selectedUser.value?.let { user ->
            val action = UserFragmentDirections.actionUserFragmentToAddUserFragment(id = user.id)
            findNavController().navigate(action)
        }
    }
}