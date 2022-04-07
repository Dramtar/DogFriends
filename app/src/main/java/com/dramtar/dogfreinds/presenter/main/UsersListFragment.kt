package com.dramtar.dogfreinds.presenter.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.lifecycle.lifecycleScope
import androidx.paging.ExperimentalPagingApi
import androidx.recyclerview.widget.RecyclerView
import com.dramtar.dogfreinds.R
import com.dramtar.dogfreinds.databinding.FragmentUsersListBinding
import com.dramtar.dogfreinds.domain.model.User
import com.dramtar.dogfreinds.presenter.adapter.UserAdapter
import kotlinx.coroutines.flow.collectLatest

/**
 * Created by Dramtar on 19.03.2022
 */

const val TAG = "userListFragment"

class UsersListFragment : Fragment(R.layout.fragment_users_list),
    UserAdapter.OnItemClickListener {

    private val vm: MainViewModel by activityViewModels()
    private val userAdapter = UserAdapter(this)
    private lateinit var binding: FragmentUsersListBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentUsersListBinding.bind(view)

        initView()
        iniObservers()
    }

    private fun initView() {
        binding.usersRecyclerView.apply {
            adapter = userAdapter
        }
        userAdapter.stateRestorationPolicy =
            RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
    }

    @OptIn(ExperimentalPagingApi::class)
    private fun iniObservers() {
        lifecycleScope.launchWhenCreated {
            vm.usersList.collectLatest {
                userAdapter.submitData(lifecycle, it)
            }
        }
    }

    override fun onItemClick(user: User) {
        vm.setSelectedUser(user)
        requireActivity().supportFragmentManager.commit {
            setReorderingAllowed(true)
            addToBackStack(TAG)
            replace<UserFragment>(R.id.container)
        }
    }
}