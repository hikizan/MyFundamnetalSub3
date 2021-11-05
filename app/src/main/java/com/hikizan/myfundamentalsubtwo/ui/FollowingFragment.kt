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
import com.hikizan.myfundamentalsubtwo.databinding.FragmentFollowingBinding
import com.hikizan.myfundamentalsubtwo.model.detail.ResponseDetail
import com.hikizan.myfundamentalsubtwo.model.following.ResponseFollowing
import com.hikizan.myfundamentalsubtwo.presenter.FollowingPresenter

class FollowingFragment : Fragment(), UsersContract.followingView {

    private lateinit var presenterFollowing: UsersContract.followingPresenter
    private lateinit var binding: FragmentFollowingBinding
    private val listDetail: ArrayList<ResponseDetail> = ArrayList<ResponseDetail>()
    private lateinit var adapter: GithubUserAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentFollowingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initPresenter()

        val responseDetail: ResponseDetail? =
            requireActivity().intent.getParcelableExtra(DetailActivity.EXTRA_DATA)

        presenterFollowing.getFollowing(responseDetail?.login)
    }

    private fun initPresenter() {
        showLoading(true)
        presenterFollowing = FollowingPresenter(this)
    }

    private fun getReqDetail(login: String) {
        presenterFollowing.getDetailUser(login)
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.pbFollowing.visibility = View.VISIBLE
        } else {
            binding.pbFollowing.visibility = View.INVISIBLE
        }
    }

    override fun _onSuccessDetail(detailResponse: ResponseDetail?) {
        listDetail.add(detailResponse!!)
        adapter = GithubUserAdapter(listDetail)
        binding.rvFollowing.layoutManager = LinearLayoutManager(requireContext())
        binding.rvFollowing.adapter = adapter
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

    override fun _onSuccessFollowing(followingResponse: List<ResponseFollowing>?) {
        for (following in followingResponse!!) {
            following.login?.let { getReqDetail(it) }
        }
        showLoading(false)
    }

    override fun _onFailedFollowing(message: String?) {
        Toast.makeText(requireContext(), "Message: $message", Toast.LENGTH_SHORT).show()
    }

}