package com.dramtar.dogfreinds.presenter.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.dramtar.dogfreinds.R
import com.dramtar.dogfreinds.databinding.FragmentUserBinding

/**
 * Created by Dramtar on 19.03.2022
 */
class UserFragment : Fragment(R.layout.fragment_user) {
    private val vm: MainViewModel by activityViewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentUserBinding.bind(view)
        binding.apply {
            vm.selectedUser.observe(viewLifecycleOwner) { user ->

                Glide.with(humanAvatar)
                    .load(user.pictureLarge)
                    .placeholder(R.drawable.ic_misissing_avatar)
                    .transform(CircleCrop())
                    .into(humanAvatar)

                firstNameTextView.text = user.firstName
                lastNameTextView.text = user.lastName
                ageTextView.text = user.age.toString()
                genderTextView.text = user.gender
                idView.text = user.id.toString()
                emailView.text = user.email
            }

            vm.tempDog.observe(viewLifecycleOwner) {
                Glide.with(doggyAvatarView)
                    .load(it.dogPic)
                    .placeholder(R.drawable.ic_misissing_avatar)
                    .transform(CircleCrop())
                    .into(doggyAvatarView)

                dogName.text = it.dogName
            }

            humanAvatar.setOnClickListener {
                vm.changeUser()
            }

            vm.isDogVisible.observe(viewLifecycleOwner) {
                doggyAvatarView.visibility = if (it) View.VISIBLE else View.INVISIBLE
                dogName.visibility = if (it) View.VISIBLE else View.INVISIBLE
            }
        }
    }
}