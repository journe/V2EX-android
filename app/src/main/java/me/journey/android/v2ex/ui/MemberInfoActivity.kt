package me.journey.android.v2ex.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.activity_member_info.*
import me.journey.android.v2ex.R
import me.journey.android.v2ex.bean.MemberInfoDetailBean
import me.journey.android.v2ex.net.GetAPIService
import me.journey.android.v2ex.utils.ImageLoader
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MemberInfoActivity : BaseActivity() {

    private lateinit var mMemberInfoDetailBean: MemberInfoDetailBean
    private var mUserId = 0
    private lateinit var mView: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mView = this.layoutInflater.inflate(R.layout.activity_member_info, null as ViewGroup?, false)
        setContentView(R.layout.activity_member_info)
//        mMemberInfoDetailBean = intent.extras[MemberInfoActivity.MEMBERBEAN] as MemberInfoDetailBean
        mUserId = intent.extras[MemberInfoActivity.USERID] as Int
        getMemberInfo()
    }

    private fun initView() {
        ImageLoader.displayImage(mView, mMemberInfoDetailBean.avatar_large,
                member_info_avatar_iv, R.mipmap.ic_launcher_round, 4)
        member_info_username_tv.text = mMemberInfoDetailBean.username
        member_info_tagline_tv.text = mMemberInfoDetailBean.tagline
    }

    fun getMemberInfo() {
        val service = GetAPIService.getInstance()
        val call = service.getMemberInfo(mUserId)
        call.enqueue(object : Callback<MemberInfoDetailBean> {
            override fun onFailure(call: Call<MemberInfoDetailBean>?, t: Throwable?) {
            }

            override fun onResponse(call: Call<MemberInfoDetailBean>?, response: Response<MemberInfoDetailBean>?) {
                mMemberInfoDetailBean = response!!.body()!!
                initView()
            }
        })
    }


    companion object {
        val MEMBERBEAN = "memberbean"
        val USERID = "userid"
        fun start(memberDetail: MemberInfoDetailBean, context: Context) {
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
