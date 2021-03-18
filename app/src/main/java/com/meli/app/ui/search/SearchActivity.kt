package com.meli.app.ui.search

import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.SearchView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.meli.app.databinding.ActivitySearchBinding
import com.meli.app.di.SEARCH_VIEW_MODEL
import com.meli.app.model.SearchHistory
import com.meli.app.utils.extensions.handleState
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.qualifier.named

class SearchActivity : AppCompatActivity() {

    private val searchViewModel: SearchViewModel by viewModel(named(SEARCH_VIEW_MODEL))

    private var binding: ActivitySearchBinding? = null

    private val onItemClickSearchHistory: ((String) -> Unit) = { query ->
        comeBackToSearch(query)
    }

    private val searchHistoryAdapter = SearchHistoryAdapter(onItemClickSearchHistory)

    companion object {
        const val QUERY = "query"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setUpToolbar()
        setUpRecycler()
        getSearchHistory()
        setSearchViewListener()
        setFocusSearch()
    }

    private fun setUpRecycler() {
        binding?.rvSearchHistory?.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = searchHistoryAdapter
        }
    }

    private fun setSearchViewListener() {
        binding?.searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    searchViewModel.saveSearch(it)
                    comeBackToSearch(it)
                }

                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun setUpToolbar() {
        setSupportActionBar(binding?.toolbarSearch)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    private fun setFocusSearch() {
        binding?.searchView?.requestFocus()
        val inputMethodManager =
            this.applicationContext?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.showSoftInput(binding?.searchView, InputMethodManager.SHOW_IMPLICIT)
    }

    private fun getSearchHistory() {
        searchViewModel.getAllSearchHistory()
        searchViewModel.searchHistory.observe(this, { result ->
            result.handleState(onSuccess = { data -> setDataSearchHistory(data) })
        })
    }

    private fun setDataSearchHistory(searchHistory: List<SearchHistory>) {
        searchHistoryAdapter.listSearchHistory = searchHistory.sortedByDescending { item -> item.id }
        searchHistoryAdapter.notifyDataSetChanged()
    }

    private fun comeBackToSearch(query: String) {
        intent.putExtra(QUERY, query)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding?.rvSearchHistory?.adapter = null
        binding = null
    }

}