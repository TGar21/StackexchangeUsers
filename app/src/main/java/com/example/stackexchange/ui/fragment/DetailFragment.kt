package com.example.stackexchange.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.stackexchange.data.model.TagData
import com.example.stackexchange.data.model.UserDataItem
import com.example.stackexchange.databinding.FragmentDetailBinding
import com.example.stackexchange.ui.main.DetailViewModel
import com.example.stackexchange.util.State

/**
 * A simple [Fragment] subclass.
 */
class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    private val viewModel = DetailViewModel()
    private var tagNames = emptyList<String>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()
        viewModel.fetchTags(getUserFromArguments()?.user_id ?: 0)
        setUserDetail()
    }

    private fun setUserDetail() {
        val user = getUserFromArguments()
        if (user != null) {
            binding.userDetail.setAllTexts(
                user.display_name,
                user.reputation.toString(),
                user.badge_counts,
                user.location,
                user.creation_date
            )
            binding.userDetail.loadImage(user.profile_image)
        }
    }

    private fun getUserFromArguments(): UserDataItem? {
        return arguments?.getParcelable<UserDataItem>("user_data_item")
    }

    private fun setupObservers() {
        viewModel.tagsLiveData.observe(viewLifecycleOwner) { tags ->
            saveTags(tags)
        }
        viewModel.state.observe(viewLifecycleOwner) { state ->
            if (state == State.IN_PROGRESS) binding.progress.visibility = View.VISIBLE
            else binding.progress.visibility = View.GONE
        }
    }

    private fun saveTags(tags: TagData?) {
        when (viewModel.state.value) {
            State.LOADED -> {
                binding.userDetail.setTags(tags?.items?.map { it.tag_name }.toString())
            }
            State.FAILED -> {
                Toast.makeText(
                    requireContext(),
                    "Tags won't be shown. Loading failed.",
                    Toast.LENGTH_LONG
                ).show()
            }
            State.IN_PROGRESS -> { /* no-op */
            }
            null -> throw IllegalStateException("${this.javaClass.name}: null state not possible here")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}