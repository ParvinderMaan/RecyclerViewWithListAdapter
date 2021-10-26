package com.maan.expert

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
import androidx.recyclerview.widget.LinearLayoutManager
import com.maan.expert.databinding.FragmentHomeBinding
import com.maan.expert.model.CargoBox
import com.maan.expert.model.Product
import java.util.*

class HomeFragment : Fragment() {

     private lateinit var binder:FragmentHomeBinding

    companion object {
        fun newInstance() = HomeFragment()
    }

    private lateinit var viewModel: HomeViewModel

    private val cargoAdapter by lazy {
        CargoAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binder=FragmentHomeBinding.inflate(layoutInflater,container,false)
        return binder.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        viewModel.fetchCargo()

        viewModel.getCargos().observe(viewLifecycleOwner,{ lstOfCargo: List<CargoBox> ->
            cargoAdapter.submitList(lstOfCargo)
        })
        val layoutManager=GridLayoutManager(requireActivity(), 2)
        layoutManager.spanSizeLookup = object : SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return when (cargoAdapter.getItemViewType(position)) {
                    R.layout.list_item_product -> 1
                    R.layout.list_item_categories -> 2
                    else -> 2
                }
            }
        }

        binder.rvCargo.layoutManager= layoutManager
        binder.rvCargo.adapter=cargoAdapter


        binder.btnAdd.setOnClickListener {
            viewModel.mergeNewList()
        }

        binder.btnUpdate.setOnClickListener {
            var pos=Random().nextInt(cargoAdapter.itemCount-1)
            if(pos ==0) pos=1
            binder.btnUpdate.text = "UPDATED pos $pos"
            viewModel.updateRandomItem(pos)
        }

    }



}