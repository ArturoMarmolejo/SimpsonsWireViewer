package com.sample.simpsonsviewer.views

import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.sample.simpsonsviewer.R

import com.sample.simpsonsviewer.databinding.FragmentCharacterListBinding
import com.sample.simpsonsviewer.databinding.FragmentCharacterListTabletBinding
import com.sample.simpsonsviewer.model.localmodel.CharacterModel
import com.sample.simpsonsviewer.utils.BaseFragment
import com.sample.simpsonsviewer.utils.UIState
import com.sample.simpsonsviewer.views.adapter.CharacterAdapter


/**
 * A simple [Fragment] subclass.
 * Use the [CharacterList.newInstance] factory method to
 * create an instance of this fragment.
 */
private const val TAG = "CharacterList"
class CharacterList : BaseFragment() {
    private val binding by lazy {
        FragmentCharacterListBinding.inflate(layoutInflater)
    }
    private val characterListAdapter: CharacterAdapter by lazy {
        CharacterAdapter {
            simpsonsViewModel.selectedSimpsonsCharacter = it
            findNavController().navigate(R.id.action_characterList_to_detailsFragment)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val characterList: MutableSet<CharacterModel> = mutableSetOf()


        binding.characterListRv.apply {
            layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.VERTICAL,
                false
            )
            setHasFixedSize(true)
            adapter = characterListAdapter
        }

        simpsonsViewModel.allCharacters.observe(viewLifecycleOwner) {state ->
            when(state) {
                is UIState.LOADING -> {}
                is UIState.SUCCESS -> {
                    characterList.clear()
                    characterList.addAll(state.response)
                    Log.d(TAG, "onCreateView: ${characterList.size}")
//                    characterList.distinct().toList()
                    characterListAdapter.updateItems(characterList)
                }
                is UIState.ERROR -> {
                    showError(state.error.localizedMessage) {
                        Log.d(TAG, "onCreateView: UIState Error: $state")
                    }
                }
            }
        }

        binding.svSimpsonsCharacters.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                val filteredList = characterList.filter { character ->
                    character.text.contains(newText.toString(), true)
                }.toMutableSet()
                Log.d(TAG, "onQueryTextChange: ${filteredList.size}")
                characterListAdapter.updateItems(filteredList)
                return true
            }
        })

        return binding.root
    }
//    override fun onDestroyView() {
//        super.onDestroyView()
//        characterList.clear()
//    }


}