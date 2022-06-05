package com.dramtar.dogfreinds.presenter.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
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

private const val TAG = "userListFragment"

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
        binding.apply {
            usersRecyclerView.apply {
                adapter = userAdapter
            }

            swipeRefresher.setOnRefreshListener {
                userAdapter.refresh()
            }

            addButton.setOnClickListener {
                findNavController().navigate(R.id.action_usersListFragment_to_addUserFragment)
            }

        }
        userAdapter.stateRestorationPolicy =
            RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
    }

    @OptIn(ExperimentalPagingApi::class)
    private fun iniObservers() {
        lifecycleScope.launchWhenStarted {
            vm.usersList.collectLatest {
                userAdapter.submitData(lifecycle, it)
                binding.swipeRefresher.isRefreshing = false
            }
        }

        lifecycleScope.launchWhenStarted {
            vm.isNeedScrollToEnd().collectLatest {
                if (it) {
                    binding.usersRecyclerView.scrollToPosition(userAdapter.itemCount-1)
                }
            }
        }
    }

    override fun onItemClick(user: User) {
        vm.setSelectedUser(user)
        findNavController().navigate(R.id.action_usersListFragment_to_userFragment)
    }
}