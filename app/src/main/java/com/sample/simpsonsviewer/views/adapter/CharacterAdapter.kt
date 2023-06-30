package com.sample.simpsonsviewer.views.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sample.simpsonsviewer.R
import com.sample.simpsonsviewer.databinding.CharacterItemBinding
import com.sample.simpsonsviewer.model.localmodel.CharacterModel
import java.util.regex.Pattern

private const val TAG = "CharacterAdapter"
class CharacterAdapter(
   private val itemSet: MutableSet<CharacterModel> = mutableSetOf(),
   private val onItemClick: (previewCharacterCard: CharacterModel) -> Unit
): RecyclerView.Adapter<CharacterViewHolder>() {
   fun updateItems(newItems: MutableSet<CharacterModel>) {
      if (itemSet!=newItems) {
         itemSet.clear()
         itemSet.addAll(newItems)
         Log.d(TAG, "updateItems: $itemSet, newItems: $newItems")
         notifyDataSetChanged()
      }
   }

   override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
      return CharacterViewHolder(
         CharacterItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
         )
      )
   }

   override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) =
      holder.bind(itemSet.elementAt(position), onItemClick)



   override fun getItemCount(): Int = itemSet.size


}

class CharacterViewHolder(
   private val binding: CharacterItemBinding
): RecyclerView.ViewHolder(binding.root) {
   fun bind(item: CharacterModel, onItemClick: (previewCharacterCard: CharacterModel) -> Unit) {

      binding.characterName.text = item.name

      itemView.setOnClickListener {
         onItemClick(item)
      }

   }
}