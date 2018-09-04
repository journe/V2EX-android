package com.journey.android.v2ex.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import com.journey.android.v2ex.R
import com.journey.android.v2ex.bean.api.MembersShowBean
import com.journey.android.v2ex.net.GetAPIService
import com.journey.android.v2ex.utils.ImageLoader
import kotlinx.android.synthetic.main.activity_member_info.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MemberInfoActivity : BaseActivity() {

    private lateinit var mMembersShowBean: MembersShowBean
    private var mUserId = 0
    private lateinit var mView: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mView = this.layoutInflater.inflate(R.layout.activity_member_info, null as ViewGroup?, false)
        setContentView(R.layout.activity_member_info)
//        mMembersShowBean = intent.extras[MemberInfoActivity.MEMBERBEAN] as MembersShowBean
        mUserId = intent.extras[MemberInfoActivity.USERID] as Int
        getMemberInfo()
    }

    private fun initView() {
        ImageLoader.displayImage(mView, mMembersShowBean.avatar_large,
                member_info_avatar_iv, R.mipmap.ic_launcher_round, 4)
        member_info_username_tv.text = mMembersShowBean.username
        member_info_tagline_tv.text = mMembersShowBean.tagline
    }

    private fun getMemberInfo() {
        val call = GetAPIService.getInstance().getMemberInfo(mUserId)
        call.enqueue(object : Callback<MembersShowBean> {
            override fun onFailure(call: Call<MembersShowBean>?, t: Throwable?) {
            }

            override fun onResponse(call: Call<MembersShowBean>?, response: Response<MembersShowBean>?) {
                mMembersShowBean = response!!.body()!!
                initView()
            }
        })
    }


    companion object {
        val MEMBERBEAN = "memberbean"
        val USERID = "userid"
        fun start(memberDetail: MembersShowBean, context: Context) {
            val intent = Intent(context, MemberInfoActivity::class.java)
//            intent.putExtra(MEMBERBEAN, memberDetail)
            context.startActivity(intent)
        }

        fun start(id: Int, context: Context) {
            val intent = Intent(context, MemberInfoActivity::class.java)
            intent.putExtra(USERID, id)
            context.startActivity(intent)
        }
    }

}
