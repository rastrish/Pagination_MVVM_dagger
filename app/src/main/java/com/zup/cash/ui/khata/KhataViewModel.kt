package com.zup.cash.ui.khata

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PageKeyedDataSource
import androidx.paging.PagedList
import com.zup.cash.ZupRepository
import com.zup.cash.ui.base.BaseViewModel
import com.zup.cash.utils.rx.SchedulerProvider
import com.zup.merchant.data.model.khata.Data
import com.zup.merchant.data.model.verifyOtp.EncryptedRequest
import com.zup.merchant.utils.encryption.AESCiper
import com.zup.merchant.utils.encryption.RSACiper
import com.zup.merchant.utils.network.NetworkHelper
import io.reactivex.disposables.CompositeDisposable
import org.json.JSONObject

class KhataViewModel(
    schedulerProvider: SchedulerProvider,
    compositeDisposable: CompositeDisposable,
    networkHelper: NetworkHelper,
    aesCiper: AESCiper,
    rsaCiper: RSACiper,
    private val zupRepository: ZupRepository
) :
    BaseViewModel(schedulerProvider, compositeDisposable, networkHelper) {

    var itemPagedList  : LiveData<PagedList<Data>>
    var liveDataSource = MutableLiveData<PageKeyedDataSource<Int, Data>>()

    init {
        val itemDataSourceFactory = ItemDataSourceFactory(
            compositeDisposable,
            aesCiper,
            rsaCiper,
            schedulerProvider,
            zupRepository
        )
        liveDataSource = itemDataSourceFactory.getItemLiveDataSouce()

        val pagedListConfig = PagedList.Config.Builder()
            .setEnablePlaceholders(true)
            .setPageSize(ItemDataSource.PAGE_SIZE).build()
        itemPagedList = LivePagedListBuilder(itemDataSourceFactory, pagedListConfig).build()
    }

    override fun onCreate() {



    }


}
