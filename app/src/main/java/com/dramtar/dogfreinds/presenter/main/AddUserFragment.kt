package com.dramtar.dogfreinds.presenter.main

import android.net.Uri
import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.MenuRes
import androidx.appcompat.widget.PopupMenu
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.dramtar.dogfreinds.R
import com.dramtar.dogfreinds.databinding.AddUserFragmentBinding
import com.dramtar.dogfreinds.utils.loadCircle
import com.dramtar.dogfreinds.utils.toDate
import com.google.android.material.datepicker.MaterialDatePicker
import kotlinx.coroutines.flow.collectLatest

/**
 * Created by Dramtar on 05.05.2022
 */
private const val IMAGE_REQUEST = "image/*"

class AddUserFragment : Fragment(R.layout.add_user_fragment) {
    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var binding: AddUserFragmentBinding

    private val navigationArgs: AddUserFragmentArgs by navArgs()
    private var imgPickLaunch: ActivityResultLauncher<String>? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel.editUserById(navigationArgs.id)
        initImgPickerLauncher()
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = AddUserFragmentBinding.bind(view)
        binding.apply {
            val navController = findNavController()
            toolbarView.setupWithNavController(
                navController = navController,
                AppBarConfiguration(navGraph = navController.graph)
            )
            toolbarView.setTitle(R.string.edit_user_title)
        }
        bindObservers()
        bindListeners()
        bindErrorObservers()
    }

    private fun bindObservers() {
        lifecycleScope.launchWhenStarted {
            viewModel.selectedUser.collectLatest {
                it?.let { user ->
                    binding.firstNameEditText.setText(user.firstName, TextView.BufferType.SPANNABLE)
                    binding.lastNameEditText.setText(user.lastName, TextView.BufferType.SPANNABLE)
                    binding.emailEditText.setText(user.email, TextView.BufferType.SPANNABLE)
                    binding.dateButton.text = user.dateFormatted
                }
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.state.collectLatest {
                binding.genderButton.text = it.gender
                binding.dateButton.text = it.date.toDate()
                if (it.avatar.isBlank()) {
                    binding.humanAvatar.setImageResource(R.drawable.ic_misissing_avatar)
                } else {
                    binding.humanAvatar.loadCircle(it.avatar)
                }
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.validationEvents.collectLatest {
                when (it) {
                    is MainViewModel.ValidationEvent.Success -> {
                        findNavController().popBackStack()
                    }
                }
            }
        }
    }

    private fun bindListeners() {
        binding.apply {
            emailEditText.addTextChangedListener {
                viewModel.onEvent(
                    AddingNewUserFromEvent.EmailChanged(
                        it.toString().trim()
                    )
                )
            }
            firstNameEditText.addTextChangedListener {
                viewModel.onEvent(
                    AddingNewUserFromEvent.FirstNameChanged(
                        it.toString()
                    )
                )
            }
            lastNameEditText.addTextChangedListener {
                viewModel.onEvent(
                    AddingNewUserFromEvent.LastNameChanged(
                        it.toString()
                    )
                )
            }
            genderButton.setOnClickListener {
                showMenu(it, R.menu.gender_menu)
            }
            humanAvatar.setOnClickListener {
                imgPickLaunch?.launch(IMAGE_REQUEST)
            }
            dateButton.setOnClickListener {
                showDatePicker()
            }
            saveButton.setOnClickListener {
                viewModel.onEvent(AddingNewUserFromEvent.Adding)
            }
        }
    }

    private fun bindErrorObservers() {
        lifecycleScope.launchWhenStarted {
            viewModel.state.collectLatest {
                binding.emailContainer.error = it.emailError
                binding.firstNameContainer.error = it.firstNameError
                binding.lastNameContainer.error = it.lastNameError
            }
        }
    }

    private fun initImgPickerLauncher() {
        imgPickLaunch =
            registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
                uri?.let {
                    viewModel.onEvent(AddingNewUserFromEvent.AvatarChanged(avatar = it.toString()))
                }
            }
    }

    private fun showDatePicker() {
        val date = viewModel.state.value.date
        val datePicker = MaterialDatePicker.Builder.datePicker().setSelection(date).build()
        datePicker.show(requireActivity().supportFragmentManager, "DatePicker")
        datePicker.addOnPositiveButtonClickListener {
            viewModel.onEvent(AddingNewUserFromEvent.DateChanged(it))
        }
    }

    private fun showMenu(view: View, @MenuRes menuRes: Int) {
        val popup = PopupMenu(requireContext(), view, Gravity.CENTER_HORIZONTAL)
        popup.menuInflater.inflate(menuRes, popup.menu)
        popup.setOnMenuItemClickListener { menuItem: MenuItem ->
            viewModel.onEvent(AddingNewUserFromEvent.GenderChanged(menuItem.title.toString()))
            true
        }
        popup.show()
    }
}