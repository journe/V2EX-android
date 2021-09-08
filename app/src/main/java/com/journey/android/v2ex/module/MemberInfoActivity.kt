package com.journey.android.v2ex.module

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import coil.load
import com.journey.android.v2ex.R
import com.journey.android.v2ex.base.BaseActivity
import com.journey.android.v2ex.libs.extension.largeAvatar
import com.journey.android.v2ex.model.api.MemberBean
import com.journey.android.v2ex.net.RetrofitService
import com.orhanobut.logger.Logger
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_member_info.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@AndroidEntryPoint
class MemberInfoActivity : BaseActivity() {

    private lateinit var mMemberBean: MemberBean
    private var mUserName = ""
    private lateinit var mView: View

    @Inject
    lateinit var apiService: RetrofitService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mView = this.layoutInflater.inflate(R.layout.activity_member_info, null as ViewGroup?, false)
        setContentView(R.layout.activity_member_info)
//        mMembersShowBean = intent.extras[MemberInfoActivity.MEMBERBEAN] as MembersShowBean
        mUserName = intent.extras?.get(USERNAME) as String
        getMemberInfo()
    }

    private fun initView() {
        member_info_avatar_iv.load(mMemberBean.avatar_large.largeAvatar())
        member_info_username_tv.text = mMemberBean.username
        member_info_tagline_tv.text = mMemberBean.tagline
    }

    private fun getMemberInfo() {
        val call = apiService.getMemberInfo(mUserName)
        call.enqueue(object : Callback<MemberBean> {
            override fun onFailure(call: Call<MemberBean>?, t: Throwable?) {
                Logger.d(t?.message)
            }

            override fun onResponse(call: Call<MemberBean>?, response: Response<MemberBean>?) {
                mMemberBean = response!!.body()!!
                initView()
            }
        })
    }


    companion object {
        val MEMBERBEAN = "memberbean"
        val USERID = "userid"
        val USERNAME = "username"
        fun start(memberDetail: MemberBean, context: Context) {
            val intent = Intent(context, MemberInfoActivity::class.java)
//            intent.putExtra(MEMBERBEAN, memberDetail)
            context.startActivity(intent)
        }

        fun start(id: Int, context: Context) {
            val intent = Intent(context, MemberInfoActivity::class.java)
            intent.putExtra(USERID, id)
            context.startActivity(intent)
        }

        fun start(name: String, context: Context) {
            val intent = Intent(context, MemberInfoActivity::class.java)
            intent.putExtra(USERNAME, name)
            context.startActivity(intent)
        }
    }

}
