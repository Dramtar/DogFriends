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
        initUserObserver()
        initDogObserver()
    }

    private fun initView() {
        val navController = findNavController()
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        binding.collapsingToolbarView.setupWithNavController(
            binding.toolbarView,
            navController,
            appBarConfiguration
        )

        binding.appBarView.addOnOffsetChangedListener(object : AppBarStateChangedListener() {
            override fun onStateChanged(appBarLayout: AppBarLayout?, state: State?) {
                binding.doggyAvatarSmallView.animateAlpha(state == State.COLLAPSED)
            }
        })

        binding.humanAvatar.setOnClickListener {
            vm.changeUser()
        }
    }

    private fun initUserObserver() {
        binding.apply {
            vm.selectedUser.observe(viewLifecycleOwner) { user ->
                user?.let {
                    humanAvatar.loadCircle(it.pictureMedium)

                    fullNameTextView.text = getString(R.string.full_name, it.firstName, it.lastName)
                    ageTextView.text = getString(R.string.age, it.age)
                    birthdayTextView.text = getString(R.string.birthday, it.date)
                    emailTextView.text = getString(R.string.email, it.email)
                    lastUpdateTextView.text = getString(R.string.last_update, it.lastUpdate.toDate())

                    collapsingToolbarView.setContentScrimColor(it.bgColor)
                    collapsingToolbarView.setExpandedTitleColor(it.nameColor)
                    collapsingToolbarView.setCollapsedTitleTextColor(it.nameColor)
                    appBarView.setBackgroundColor(it.bgColor)
                }
            }
        }
    }

    private fun initDogObserver() {
        binding.apply {
            vm.tempDog.observe(viewLifecycleOwner) { dog ->
                dog?.let {
                    doggyAvatarView.load(it.dogPic)
                    doggyAvatarSmallView.loadCircle(it.dogPic)
                    collapsingToolbarView.title = it.dogName
                }
            }
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}