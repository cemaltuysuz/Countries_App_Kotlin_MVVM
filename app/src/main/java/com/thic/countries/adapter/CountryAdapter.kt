package com.thic.countries.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.thic.countries.utils.ImageUploader
import com.thic.countries.utils.ImageUploaderI
import com.thic.countries.R
import com.thic.countries.model.Model
import com.thic.countries.view.CountriesFragmentDirections

class CountryAdapter (val countryList:ArrayList<Model>,val context:Context) : RecyclerView.Adapter<CountryAdapter.ViewHolderC>() {

    private val uploader: ImageUploaderI = ImageUploader()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderC {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.countries_recyclerview_layout,parent,false)
    return ViewHolderC(view)
    }

    override fun onBindViewHolder(holder: ViewHolderC, position: Int) {
        val current = countryList[position]
        uploader.imageUploadWithGlide(context,holder.cFlag!!, current.countryFlag)
        holder.cTitle!!.text =  current.countryName

        holder.itemView.setOnClickListener(View.OnClickListener {
            val action = CountriesFragmentDirections.actionCountriesFragmentToDetailFragment(countryList[position].uuid)
            Navigation.findNavController(it).navigate(action)
        })
    }

    override fun getItemCount(): Int {
        return countryList.size
    }

    class ViewHolderC(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var cFlag:AppCompatImageView? = null
        var cTitle:AppCompatTextView? = null

        init {
            cFlag = itemView.findViewById(R.id.countryFlagImagePrivate)
            cTitle = itemView.findViewById(R.id.countryPrivate)
        }
    }
    fun updateAdapter(newList:List<Model>){
        this.countryList.clear()
        this.countryList.addAll(newList)
        notifyDataSetChanged()
    }
}