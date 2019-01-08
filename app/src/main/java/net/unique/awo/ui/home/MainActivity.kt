package net.unique.awo.ui.home

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.Toast
import net.unique.awo.R
import net.unique.awo.base.BaseActivity
import net.unique.awo.databinding.ActivityMainBinding
import net.unique.awo.model.Post
import net.unique.awo.ui.post.PostAdapter
import net.unique.awo.ui.post.PostPresenter
import net.unique.awo.ui.post.PostView

class MainActivity : BaseActivity<PostPresenter>(), PostView {

    /**
     * DataBinding instance
     */
    private lateinit var binding: ActivityMainBinding

    /**
     * The adapter for the list of posts
     */
    private val postsAdapter = PostAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.adapter = postsAdapter
        binding.layoutManager = LinearLayoutManager(this)
        binding.dividerItemDecoration = DividerItemDecoration(this, LinearLayoutManager.VERTICAL)


        presenter.onViewCreated()
    }

//    private fun initToolbar() {
//        setSupportActionBar(toolbar)
//        supportActionBar?.apply {
//            setDisplayHomeAsUpEnabled(true)
//            val menuDrawable = ContextCompat.getDrawable(this@MainActivity, R.drawable.ic_menu)
//            menuDrawable?.setTint(ContextCompat.getColor(this@MainActivity, R.color.colorAccent))
//            setHomeAsUpIndicator(R.drawable.ic_menu)
//            title = getString(R.string.movies)
//        }
//    }

    override fun showLoading() {
        binding.progressVisibility = View.VISIBLE
    }

    override fun hideLoading() {
        binding.progressVisibility = View.GONE
    }

    override fun showError(errorMessage: String) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show()
    }

    override fun updatePosts(posts: List<Post>) {
        postsAdapter.updatePosts(posts)
    }

    override fun instantiatePresenter(): PostPresenter {
        return PostPresenter(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onViewDestroyed()
    }


}
