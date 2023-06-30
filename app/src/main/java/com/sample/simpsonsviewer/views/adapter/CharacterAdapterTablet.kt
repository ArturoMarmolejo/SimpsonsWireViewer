package com.sample.simpsonsviewer.views.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sample.simpsonsviewer.R
import com.sample.simpsonsviewer.databinding.CharacterItemBinding
import com.sample.simpsonsviewer.databinding.CharacterItemTabletBinding
import com.sample.simpsonsviewer.model.localmodel.CharacterModel
import java.util.regex.Pattern

private const val TAG = "CharacterAdapterTablet"
class CharacterAdapterTablet(
    private val itemSet: MutableSet<CharacterModel> = mutableSetOf(),
    private val onItemClick: (previewCharacterCard: CharacterModel) -> Unit
): RecyclerView.Adapter<CharacterTabletViewHolder>() {
    fun updateItems(newItems: MutableSet<CharacterModel>) {
        if (itemSet!=newItems) {
            itemSet.clear()
            itemSet.addAll(newItems)
            Log.d(TAG, "updateItems: $itemSet, newItems: $newItems")
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterTabletViewHolder {
        return CharacterTabletViewHolder(
            CharacterItemTabletBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CharacterTabletViewHolder, position: Int) =
        holder.bind(itemSet.elementAt(position), onItemClick)



    override fun getItemCount(): Int = itemSet.size


}

class CharacterTabletViewHolder(
    private val binding: CharacterItemTabletBinding
): RecyclerView.ViewHolder(binding.root) {
    fun bind(item: CharacterModel, onItemClick: (previewCharacterCard: CharacterModel) -> Unit) {

        binding.characterName.text = item.name

        val characterTextString = item.text// converts text to a string
        val splitCharacterText = characterTextString.split("-") // takes the second element and removes any leading/trailing whitespace
        val characterTextResult = splitCharacterText[1].trim()

        binding.characterDescription.text = characterTextResult


        Glide
            .with(binding.root)
            .load("https://duckduckgo.com" + item.icon.uRL)
            .centerCrop()
            .placeholder(R.drawable.baseline_person_24)
            .error(R.drawable.baseline_person_off_24)
            .into(binding.characterThumbnail)
    }
}