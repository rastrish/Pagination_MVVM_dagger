package com.zup.cash.ui.khata

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import com.zup.cash.ZupRepository
import com.zup.cash.utils.rx.RxSchedulerProvider
import com.zup.cash.utils.rx.SchedulerProvider
import com.zup.merchant.data.model.khata.Data
import com.zup.merchant.utils.encryption.AESCiper
import com.zup.merchant.utils.encryption.RSACiper
import io.reactivex.disposables.CompositeDisposable

class ItemDataSourceFactory(
    val compositeDisposable: CompositeDisposable,
    val aesCiper: AESCiper,
    val rsaCiper: RSACiper,
    val schedulerProvider: SchedulerProvider,
    val zupRepository: ZupRepository
) : DataSource.Factory<Int, Data>() {

   private val itemLiveDataSource: MutableLiveData<PageKeyedDataSource<Int, Data>> = MutableLiveData()


    override fun create(): DataSource<Int, Data> {
        val itemDataSource = ItemDataSource(compositeDisposable, rsaCiper, aesCiper, schedulerProvider,zupRepository)
        itemLiveDataSource.postValue(itemDataSource)
        return itemDataSource
    }

    fun getItemLiveDataSouce(): MutableLiveData<PageKeyedDataSource<Int, Data>> {
        return itemLiveDataSource
    }


}
