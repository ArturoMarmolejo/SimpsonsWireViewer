package com.sample.simpsonsviewer.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.sample.simpsonsviewer.R
import com.sample.simpsonsviewer.databinding.FragmentDetailsBinding
import com.sample.simpsonsviewer.model.localmodel.CharacterModel
import com.sample.simpsonsviewer.utils.BaseFragment
import java.util.regex.Pattern

/**
 * A simple [Fragment] subclass.
 * Use the [DetailsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DetailsFragment : BaseFragment() {
    private val binding by lazy {
        FragmentDetailsBinding.inflate(layoutInflater)
    }

    private lateinit var selectedCharacter: CharacterModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        selectedCharacter = simpsonsViewModel.selectedSimpsonsCharacter

        val pattern = Pattern.compile("<.*?>(.*?)</a>")
        val matcher = pattern.matcher(selectedCharacter.name)
        if(matcher.find()) {
            val name = matcher.group(1)
            binding.characterName.text = name
        } else {
            binding.characterName.text = selectedCharacter.name
        }

        val characterTextString = selectedCharacter.text // converts text to a string
        val splitCharacterText = characterTextString.split("-") // takes the second element and removes any leading/trailing whitespace
        val characterTextResult = splitCharacterText[1].trim()

        binding.tvCharacterDescription.text = characterTextResult

        Glide
            .with(binding.root)
            .load("https://duckduckgo.com" + selectedCharacter.icon.uRL)
            .centerCrop()
            .override(400, 600)
            .placeholder(R.drawable.baseline_person_24)
            .error(R.drawable.baseline_person_off_24)
            .into(binding.characterThumbnail)

        return binding.root
    }


}