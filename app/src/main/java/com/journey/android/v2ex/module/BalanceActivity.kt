package com.journey.android.v2ex.module

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import com.journey.android.v2ex.R
import com.journey.android.v2ex.net.RetrofitService
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.activity_balance.balance_rv
import kotlinx.android.synthetic.main.activity_balance.balance_toolbar
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class BalanceActivity : AppCompatActivity() {

  @Inject
  lateinit var apiService: RetrofitService

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_balance)
    balance_rv.addItemDecoration(
        DividerItemDecoration(
            this,
            DividerItemDecoration.VERTICAL
        )
    )
    setSupportActionBar(balance_toolbar)
    supportActionBar?.setDisplayHomeAsUpEnabled(true)
    balance_toolbar.setNavigationOnClickListener { finish() }

    doGetBalance()
  }

  private fun doGetBalance() {
    apiService
        .getBalance()
        .enqueue(object : Callback<ResponseBody> {
          override fun onFailure(
            call: Call<ResponseBody>,
            t: Throwable
          ) {
            Logger.d(t.stackTrace)
          }

          override fun onResponse(
            call: Call<ResponseBody>,
            response: Response<ResponseBody>
          ) {
            val headView = layoutInflater.inflate(
                R.layout.activity_balance_list_head, balance_toolbar, false
            )
//            setHeadView(
//                genAdapter(BalanceParser.parseBalance(Jsoup.parse(response.body()!!.string()))),
//                headView
//            )
          }
        })

  }

//  private fun setHeadView(
//    adapter: CommonAdapter<BalanceBean>,
//    headView: View
//  ) {
//    val mHeaderAndFooterWrapper =
//      HeaderAndFooterWrapper<Adapter<RecyclerView.ViewHolder>>(adapter)
//    mHeaderAndFooterWrapper.addHeaderView(headView)
//    mHeaderAndFooterWrapper.addFootView(
//        layoutInflater.inflate(
//            R.layout.fragment_topic_detail_foot,
//            balance_toolbar, false
//        )
//    )
//    balance_rv.adapter = mHeaderAndFooterWrapper
//  }
//
//  private fun genAdapter(topicListItem: MutableList<BalanceBean>): CommonAdapter<BalanceBean> {
//    return object : CommonAdapter<BalanceBean>(
//        this,
//        R.layout.activity_balance_list_item, topicListItem
//    ) {
//      override fun convert(
//        holder: ViewHolder?,
//        t: BalanceBean,
//        position: Int
//      ) {
//        holder?.setText(R.id.balance_type_tv, t.balanceType)
//        holder?.setText(R.id.balance_count_tv, t.balanceCount)
//        holder?.setText(R.id.balance_tv, t.balance)
//        holder?.setText(R.id.balance_desc_tv, t.balanceDesc)
//      }
//    }
//  }
}
