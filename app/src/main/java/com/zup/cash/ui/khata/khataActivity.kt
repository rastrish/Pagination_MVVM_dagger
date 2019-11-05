package com.zup.cash.ui.khata

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zup.cash.R
import com.zup.cash.di.component.ActivityComponent
import com.zup.cash.ui.base.BaseActivity
import com.zup.merchant.data.model.khata.Data
import kotlinx.android.synthetic.main.activity_khata.*

class KhataActivity : BaseActivity<KhataViewModel>() {

   lateinit var itemAdapter: ItemAdapter
    override fun provideLayoutId(): Int = R.layout.activity_khata
    override fun injectDependencies(activityComponent: ActivityComponent) {
        activityComponent.inject(this)
    }

    override fun setupView(savedInstanceState: Bundle?) {
        itemAdapter = ItemAdapter(this)
        val layoutManager: RecyclerView.LayoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true)
        var khataRecyclerView: RecyclerView = findViewById(R.id.recyler)
        khataRecyclerView.layoutManager = layoutManager
        khataRecyclerView.setHasFixedSize(true)

        progressBar2.visibility = VISIBLE
        viewModel.itemPagedList.observe(this,
            Observer<PagedList<Data>> { items ->

                progressBar2.visibility = GONE
                Log.e("items",items.toString())
                itemAdapter.submitList(items)
            })
        khataRecyclerView.adapter = itemAdapter


    }

}