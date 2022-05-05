package com.dramtar.dogfreinds.presenter.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

const val TAG = "userListFragment"

class UsersListFragment : Fragment(R.layout.fragment_users_list),
    UserAdapter.OnItemClickListener {

    private val vm: MainViewModel by activityViewModels()
    private val userAdapter = UserAdapter(this)
    private var _binding: FragmentUsersListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUsersListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
        }
        userAdapter.stateRestorationPolicy =
            RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
    }

    @OptIn(ExperimentalPagingApi::class)
    private fun iniObservers() {
        lifecycleScope.launchWhenCreated {
            vm.usersList.collectLatest {
                userAdapter.submitData(lifecycle, it)
                binding.swipeRefresher.isRefreshing = false
            }
        }
    }

    override fun onItemClick(user: User) {
        vm.setSelectedUser(user)
        findNavController().navigate(R.id.action_usersListFragment_to_userFragment)
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}