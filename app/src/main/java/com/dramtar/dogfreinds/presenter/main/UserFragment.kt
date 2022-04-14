package com.dramtar.dogfreinds.presenter.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.dramtar.dogfreinds.R
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
        initUserObserver()
        initDogObserver()
    }

    private fun initView() {
        binding.humanAvatar.setOnClickListener {
            vm.changeUser()
        }

        requireActivity().setActionBar(binding.toolbarView)
        requireActivity().actionBar?.setDisplayHomeAsUpEnabled(true)
        requireActivity().actionBar?.setHomeButtonEnabled(true)

        binding.toolbarView.setNavigationOnClickListener {
            requireActivity().onBackPressed()
        }

        binding.appBarView.addOnOffsetChangedListener(object : AppBarStateChangedListener() {
            override fun onStateChanged(appBarLayout: AppBarLayout?, state: State?) {
                binding.doggyAvatarSmallView.animateAlpha(state == State.COLLAPSED)
            }
        })
    }

    private fun initUserObserver() {
        binding.apply {
            vm.selectedUser.observe(viewLifecycleOwner) { user ->
                humanAvatar.loadCircle(user.pictureMedium)

                fullNameTextView.text = getString(R.string.full_name, user.firstName, user.lastName)
                ageTextView.text = getString(R.string.age, user.age)
                birthdayTextView.text = getString(R.string.birthday, user.date)
                emailTextView.text = getString(R.string.email, user.email)
                lastUpdateTextView.text = getString(R.string.last_update, user.lastUpdate.toDate())

                collapsingToolbarView.setContentScrimColor(user.bgColor)
                collapsingToolbarView.setExpandedTitleColor(user.nameColor)
                collapsingToolbarView.setCollapsedTitleTextColor(user.nameColor)
                appBarView.setBackgroundColor(user.bgColor)
            }
        }
    }

    private fun initDogObserver() {
        binding.apply {
            vm.tempDog.observe(viewLifecycleOwner) {
                doggyAvatarView.load(it.dogPic)
                doggyAvatarSmallView.loadCircle(it.dogPic)
                collapsingToolbarView.title = it.dogName
            }
        }
    }
}