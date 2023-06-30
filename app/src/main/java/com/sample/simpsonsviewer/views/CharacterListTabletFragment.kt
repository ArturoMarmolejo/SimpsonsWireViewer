package com.sample.simpsonsviewer.views

import android.os.Bundle
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
import com.sample.simpsonsviewer.views.adapter.CharacterAdapterTablet


/**
 * A simple [Fragment] subclass.
 * Use the [CharacterListTabletFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
private const val TAG = "CharacterListTabletFrag"
class CharacterListTabletFragment : BaseFragment() {

    private val binding by lazy {
        FragmentCharacterListTabletBinding.inflate(layoutInflater)
    }

    private val characterListAdapterTablet: CharacterAdapterTablet by lazy {
        CharacterAdapterTablet {
            simpsonsViewModel.selectedSimpsonsCharacter = it
        }
    }
    private val characterList = mutableSetOf<CharacterModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        binding.characterListRvTablet.apply {
            layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.VERTICAL,
                false
            )
            setHasFixedSize(true)
            adapter = characterListAdapterTablet
        }

        simpsonsViewModel.allCharacters.observe(viewLifecycleOwner) {state ->
            when(state) {
                is UIState.LOADING -> {}
                is UIState.SUCCESS -> {
                    characterList.clear()
                    characterList.addAll(state.response)
//                    characterList.distinct().toList()
                    characterListAdapterTablet.updateItems(characterList)
                }
                is UIState.ERROR -> {
                    showError(state.error.localizedMessage) {
                        Log.d(TAG, "onCreateView: UIState Error: $state")
                    }
                }
            }
        }

        binding.svSimpsonsCharactersTablet.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                val filteredList = mutableSetOf<CharacterModel>()
                if (newText.isNullOrBlank()) {
                    filteredList.addAll(characterList)
                } else {
                    characterList.forEach{character ->
                        if(character.text.contains(newText.toString(), true)) {
                            filteredList.add(character)

//                            filteredList.toMutableSet().toMutableList()
                        }
                    }
                }
                characterListAdapterTablet.updateItems(filteredList)
                return true
            }
        })

        return binding.root
    }
    override fun onDestroyView() {
        super.onDestroyView()
        characterList.clear()
    }

}