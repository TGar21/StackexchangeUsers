package com.example.stackexchange.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.stackexchange.R
import com.example.stackexchange.data.model.UserData
import com.example.stackexchange.databinding.FragmentMainBinding
import com.example.stackexchange.ui.main.MainViewModel
import com.example.stackexchange.ui.main.UsersAdapter
import com.example.stackexchange.util.State

private const val ARG_PARAM1 = "param1"

/**
 * A simple [Fragment] subclass.
 * Use the [MainFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private val viewModel = MainViewModel()
    private lateinit var adapter: UsersAdapter

    private var param1: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        viewModel.usersLiveData.observe(viewLifecycleOwner) {
            adapter.insertData(it)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdapter()
        setupObservers()
        setupSearch()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun setupAdapter(){
        adapter = UsersAdapter(requireContext())
        binding.listUsers.adapter = adapter
        binding.listUsers.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        adapter.onItemClick = { user ->
            val bundle = bundleOf("user" to user.display_name)
            findNavController().navigate(R.id.action_main_to_detail, bundle)
        }
    }

    private fun setupSearch() {
        binding.search.apply {
            isSubmitButtonEnabled = true
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    viewModel.fetchData(query?:"")
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    return false
                }
            })
        }
    }

    private fun setupObservers() {
        viewModel.usersLiveData.observe(viewLifecycleOwner) { userItems ->
            setRecycler(userItems)
        }
        viewModel.state.observe(viewLifecycleOwner) { state ->
            if (state == State.IN_PROGRESS) binding.progress.visibility = View.VISIBLE
            else binding.progress.visibility = View.GONE
        }
    }

    private fun setRecycler(userItems: UserData) {
        binding.apply {
            when (viewModel.state.value) {
                State.LOADED -> {
                    listUsers.visibility = View.VISIBLE
                    progress.visibility = View.GONE
                    retrieveUsers(userItems)
                }
                State.FAILED -> {
                    binding.listUsers.visibility = View.GONE
                    progress.visibility = View.GONE
                }
                State.IN_PROGRESS -> {
                    progress.visibility = View.VISIBLE
                    binding.listUsers.visibility = View.GONE
                }
                State.INIT -> throw IllegalStateException("${this.javaClass.name}: init state not possible here")
                null -> throw IllegalStateException("${this.javaClass.name}: null state not possible here")
            }
        }
    }

    private fun retrieveUsers(users: UserData) {
        adapter.insertData(users)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 2.
         * @return A new instance of fragment MainScreenFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String) =
            MainFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                }
            }
    }
}