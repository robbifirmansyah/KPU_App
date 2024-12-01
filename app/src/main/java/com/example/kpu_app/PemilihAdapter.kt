package com.example.kpu_app

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.kpu_app.database.Pemilih
import com.example.kpu_app.databinding.ItemPemilihBinding

class PemilihAdapter(
    private val onEditClick: (Pemilih) -> Unit,
    private val onDeleteClick: (Pemilih) -> Unit,
    private val onShowClick: (Pemilih) -> Unit
) : ListAdapter<Pemilih, PemilihAdapter.PemilihViewHolder>(PemilihDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PemilihViewHolder {
        val binding = ItemPemilihBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PemilihViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PemilihViewHolder, position: Int) {
        val pemilih = getItem(position)
        holder.bind(pemilih, onEditClick, onDeleteClick, onShowClick)
    }

    class PemilihViewHolder(private val binding: ItemPemilihBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(
            pemilih: Pemilih,
            onEditClick: (Pemilih) -> Unit,
            onDeleteClick: (Pemilih) -> Unit,
            onShowClick: (Pemilih) -> Unit
        ) {
            binding.nomorPemilih.text = pemilih.id.toString()
            binding.namaPemilih.text = pemilih.nama

            // Set click listeners for each icon
            binding.hapusPemilih.setOnClickListener {
                onDeleteClick(pemilih) // Call delete action
            }

            binding.editPemilih.setOnClickListener {
                onEditClick(pemilih) // Call edit action
            }

            binding.showPemilih.setOnClickListener {
                onShowClick(pemilih) // Call show action
            }
        }
    }

    class PemilihDiffCallback : DiffUtil.ItemCallback<Pemilih>() {
        override fun areItemsTheSame(oldItem: Pemilih, newItem: Pemilih): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Pemilih, newItem: Pemilih): Boolean {
            return oldItem == newItem
        }
    }
}