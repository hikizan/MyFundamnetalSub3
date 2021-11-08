package com.hikizan.myfundamentalsubthree.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.hikizan.myfundamentalsubthree.adapter.GithubUserAdapter
import com.hikizan.myfundamentalsubthree.contract.UsersContract
import com.hikizan.myfundamentalsubthree.database.Favorite
import com.hikizan.myfundamentalsubthree.databinding.FragmentFollowersBinding
import com.hikizan.myfundamentalsubthree.model.detail.ResponseDetail
import com.hikizan.myfundamentalsubthree.model.followers.ResponseFollowers
import com.hikizan.myfundamentalsubthree.presenter.FollowersPresenter

class FollowersFragment : Fragment(), UsersContract.followersView {

    private lateinit var presenterFollowers: UsersContract.followersPresenter
    private var _fragmentFollowersBinding: FragmentFollowersBinding? = null
    private val binding get() = _fragmentFollowersBinding
    private val listDetail: ArrayList<ResponseDetail> = ArrayList()
    private lateinit var adapter: GithubUserAdapter
    private var responseDetail: ResponseDetail? = null
    private var favorite: Favorite? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _fragmentFollowersBinding = FragmentFollowersBinding.inflate(inflater, container, false)
        return binding?.root!!

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initPresenter()

        responseDetail =
            requireActivity().intent.getParcelableExtra(DetailActivity.EXTRA_DATA)

        favorite = requireActivity().intent.getParcelableExtra(DetailActivity.EXTRA_FAVORITE)

        if (responseDetail != null) {
            presenterFollowers.getFollowers(responseDetail?.login)
        } else {
            presenterFollowers.getFollowers(favorite?.login)
        }

    }

    private fun initPresenter() {
        showLoading(true)
        presenterFollowers = FollowersPresenter(this)
    }

    private fun getReqDetail(login: String) {
        presenterFollowers.getDetailUser(login)
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding?.pbFollower?.visibility = View.VISIBLE
        } else {
            binding?.pbFollower?.visibility = View.INVISIBLE
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun _onSuccessDetail(detailResponse: ResponseDetail?) {
        listDetail.add(detailResponse!!)
        adapter = GithubUserAdapter(listDetail)
        binding?.rvFollowers?.layoutManager = LinearLayoutManager(requireContext())
        binding?.rvFollowers?.adapter = adapter
        adapter.notifyDataSetChanged()
        adapter.setOnItemClickCallback(object : GithubUserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: ResponseDetail) {
                showSelectedGithubUser(data)
            }
        })
    }

    private fun showSelectedGithubUser(data: ResponseDetail) {
        val moveWithDataParcel = Intent(requireContext(), DetailActivity::class.java)
        moveWithDataParcel.putExtra(DetailActivity.EXTRA_DATA, data)
        startActivity(moveWithDataParcel)
    }

    override fun _onFailedDetail(message: String?) {
        Toast.makeText(requireContext(), "Message: $message", Toast.LENGTH_SHORT).show()
    }

    override fun _onSuccessFollowers(followersResponse: List<ResponseFollowers>?) {
        for (followers in followersResponse!!) {
            followers.login?.let { getReqDetail(it) }
        }
        showLoading(false)
    }

    override fun _onFailedFollowers(message: String?) {
        Toast.makeText(requireContext(), "Message: $message", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        _fragmentFollowersBinding = null
    }
}