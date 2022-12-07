package com.mx.rockstar.marsroversgallery.binding

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mx.rockstar.marsroversgallery.ui.detail.RoverPhotoViewModel
import com.skydoves.baserecyclerviewadapter.RecyclerViewPaginator
import com.skydoves.bindables.BindingListAdapter
import com.skydoves.whatif.whatIfNotNullAs

object RecyclerViewBinding {

    @JvmStatic
    @BindingAdapter("adapter")
    fun bindAdapter(view: RecyclerView, adapter: RecyclerView.Adapter<*>) {
        view.adapter = adapter.apply {
            stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
        }
    }

    @JvmStatic
    @BindingAdapter("submitList")
    fun bindSubmitList(view: RecyclerView, itemList: List<Any>?) {
        view.adapter.whatIfNotNullAs<BindingListAdapter<Any, *>> { adapter ->
            adapter.submitList(itemList)
        }
    }

    @JvmStatic
    @BindingAdapter("paginationList")
    fun paginationList(view: RecyclerView, viewModel: RoverPhotoViewModel) {
        RecyclerViewPaginator(
            recyclerView = view,
            isLoading = { viewModel.isLoading },
            loadMore = { viewModel.fetchNextPhotoList() },
            onLast = { viewModel.isLast }
        ).run {
            threshold = 0
        }
    }

}