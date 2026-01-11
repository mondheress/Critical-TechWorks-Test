package com.criticaltechwork.bbcnews.presentation.detail

import android.os.Build
import android.os.Bundle
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.criticaltechwork.bbcnews.R
import com.criticaltechwork.bbcnews.data.model.NewsResponse
import com.criticaltechwork.bbcnews.databinding.ActivityDetailBinding
import com.criticaltechwork.bbcnews.presentation.base.BaseActivity

class DetailActivity : BaseActivity<ActivityDetailBinding>() {
    override fun inflateBinding(): ActivityDetailBinding = ActivityDetailBinding.inflate(layoutInflater)

    private var _binding: ActivityDetailBinding? = null
    private var isExpanded = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val article = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getSerializableExtra("EXTRA_ARTICLE", NewsResponse.Article::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getSerializableExtra("EXTRA_ARTICLE") as? NewsResponse.Article
        }

        binding.detailBtnBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
        binding.btnExpand.setOnClickListener {
            toggleContent()
        }
        article?.let { setupUI(it) }
    }

    private fun setupUI(article: NewsResponse.Article) {
        title = article.author
        binding.detailTitle.text = article.title
        binding.detailDescription.text = article.description
        binding.textContent.text = article.content

        Glide.with(this)
            .load(article.urlToImage)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .placeholder(android.R.drawable.ic_menu_report_image)
            .error(android.R.drawable.ic_menu_gallery)
            .centerCrop()
            .into(binding.imageDetail)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
    private fun toggleContent() {
        isExpanded = !isExpanded
        if (isExpanded) {
            binding.textContent.maxLines = Int.MAX_VALUE
            binding.btnExpand.text = getString(R.string.show_less)
        } else {
            binding.textContent.maxLines = 3
            binding.btnExpand.text = getString(R.string.read_more)
        }
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}