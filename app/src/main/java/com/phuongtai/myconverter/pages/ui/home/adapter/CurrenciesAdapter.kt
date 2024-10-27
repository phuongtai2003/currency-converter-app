package com.phuongtai.myconverter.pages.ui.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.phuongtai.myconverter.currency_data.CurrencyData
import com.phuongtai.myconverter.databinding.CountryCurrencySpinnerItemBinding

class CurrenciesAdapter(private val currencies: List<CurrencyData>, private val mContext: Context) : BaseAdapter() {
    override fun getCount(): Int {
        return currencies.size
    }

    override fun getItem(position: Int): CurrencyData {
        return currencies[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val binding = if (convertView == null) {
            CountryCurrencySpinnerItemBinding.inflate(LayoutInflater.from(mContext), parent, false)
        } else {
            CountryCurrencySpinnerItemBinding.bind(convertView)
        }

        val currencyData = getItem(position)
        binding.currencyCode.text = currencyData.currencyValue
        val drawable = ContextCompat.getDrawable(mContext, currencyData.countryFlagDrawable)
        binding.currencyCode.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null)

        return binding.root
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup?): View {
        return getView(position, convertView, parent)
    }
}