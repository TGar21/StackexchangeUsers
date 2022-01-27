package com.example.stackexchange.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.stackexchange.data.model.TagData
import com.example.stackexchange.data.model.UserDataItem
import com.example.stackexchange.databinding.FragmentDetailBinding
import com.example.stackexchange.ui.main.DetailViewModel
import com.example.stackexchange.util.State

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    private val viewModel = DetailViewModel()
    private var tagNames = emptyList<String>()
    private val user = getUserFromArguments()

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

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
        viewModel.fetchTags(user?.user_id ?: 0)
        setUserDetail()
    }

    /*
    *
    *   newUserName: String,
            newReputation: String,
            newTags: String,
            newBadges: String,
            newLocation: String,
            newCreationDate: String*/
    private fun setUserDetail() {
        if (user != null) {
            binding.userDetail.setAllTexts(
                user.display_name,
                user.reputation.toString(),
                tagNames.toString(),
                user.badge_counts,
                user.location,
                user.creation_date
            )
        }
    }

    private fun getUserFromArguments(): UserDataItem? =
        arguments?.getParcelable("user_data_item")

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
        tagNames = tags?.items?.map { it.tag_name } ?: emptyList()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment DetailFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            DetailFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}