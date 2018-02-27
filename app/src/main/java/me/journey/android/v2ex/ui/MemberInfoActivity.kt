package me.journey.android.v2ex.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.activity_member_info.*
import me.journey.android.v2ex.R
import me.journey.android.v2ex.bean.MemberInfoBean
import me.journey.android.v2ex.utils.Constants
import me.journey.android.v2ex.utils.GetAPIService
import me.journey.android.v2ex.utils.ImageLoader
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MemberInfoActivity : AppCompatActivity() {

    private lateinit var mMemberInfoBean: MemberInfoBean
    private var mUserId = 0
    private lateinit var mView: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mView = this.layoutInflater.inflate(R.layout.activity_member_info, null as ViewGroup?, false)
        setContentView(R.layout.activity_member_info)
//        mMemberInfoBean = intent.extras[MemberInfoActivity.MEMBERBEAN] as MemberInfoBean
        mUserId = intent.extras[MemberInfoActivity.USERID] as Int
        getMemberInfo()
    }

    private fun initView() {
        ImageLoader.displayImage(mView, mMemberInfoBean?.avatar_large,
                member_info_avatar_iv, R.mipmap.ic_launcher_round, 4)
        member_info_username_tv.text = mMemberInfoBean.username
        member_info_tagline_tv.text = mMemberInfoBean.tagline
    }

    fun getMemberInfo() {
        val retrofit = Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        val service = retrofit.create(GetAPIService::class.java)

        val call = service.getMemberInfo(mUserId)
        call.enqueue(object : Callback<MemberInfoBean> {
            override fun onFailure(call: Call<MemberInfoBean>?, t: Throwable?) {
            }

            override fun onResponse(call: Call<MemberInfoBean>?, response: Response<MemberInfoBean>?) {
                mMemberInfoBean = response!!.body()!!
                initView()
            }
        })
    }


    companion object {
        val MEMBERBEAN = "memberbean"
        val USERID = "userid"
        fun start(member: MemberInfoBean, context: Context) {
            val intent = Intent(context, MemberInfoActivity::class.java)
            intent.putExtra(MEMBERBEAN, member)
            context.startActivity(intent)
        }

        fun start(id: Int, context: Context) {
            val intent = Intent(context, MemberInfoActivity::class.java)
            intent.putExtra(USERID, id)
            context.startActivity(intent)
        }
    }

}
