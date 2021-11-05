package com.hikizan.myfundamentalsubtwo.ui

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.hikizan.myfundamentalsubtwo.adapter.GithubUserAdapter
import com.hikizan.myfundamentalsubtwo.contract.UsersContract
import com.hikizan.myfundamentalsubtwo.databinding.FragmentFollowersBinding
import com.hikizan.myfundamentalsubtwo.model.detail.ResponseDetail
import com.hikizan.myfundamentalsubtwo.model.followers.ResponseFollowers
import com.hikizan.myfundamentalsubtwo.presenter.FollowersPresenter

class FollowersFragment : Fragment(), UsersContract.followersView {

    private lateinit var presenterFollowers: UsersContract.followersPresenter
    private lateinit var binding: FragmentFollowersBinding
    private val listDetail: ArrayList<ResponseDetail> = ArrayList<ResponseDetail>()
    private lateinit var adapter: GithubUserAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentFollowersBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initPresenter()

        val responseDetail: ResponseDetail? =
            requireActivity().intent.getParcelableExtra(DetailActivity.EXTRA_DATA)

        presenterFollowers.getFollowers(responseDetail?.login)
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
            binding.pbFollower.visibility = View.VISIBLE
        } else {
            binding.pbFollower.visibility = View.INVISIBLE
        }
    }

    override fun _onSuccessDetail(detailResponse: ResponseDetail?) {
        listDetail.add(detailResponse!!)
        adapter = GithubUserAdapter(listDetail)
        binding.rvFollowers.layoutManager = LinearLayoutManager(requireContext())
        binding.rvFollowers.adapter = adapter
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

}