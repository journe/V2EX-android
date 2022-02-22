package com.journey.android.v2ex.module.profile

import android.view.View
import androidx.core.view.isGone
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import coil.load
import com.journey.android.v2ex.base.BaseFragment
import com.journey.android.v2ex.databinding.FragmentProfileBinding
import com.journey.android.v2ex.libs.extension.launchCoroutine
import com.journey.android.v2ex.model.api.MemberBean
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import org.jetbrains.anko.support.v4.toast

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding, ProfileViewModel>() {
	override val mViewModel: ProfileViewModel by viewModels()

	private val safeArgs: ProfileFragmentArgs by navArgs()
	private var username: String = ""

	override fun FragmentProfileBinding.initView() {
		username = safeArgs.username
		if (username.isEmpty()) {
			toast("未知问题，无法访问用户信息")
			return
		}

		mBinding.tvUsername.text = username

		toast(username)

		mBinding.tvTagline.visibility = View.GONE
		mBinding.tvIntro.visibility = View.GONE
		mBinding.ivLocation.visibility = View.GONE
		mBinding.ivBitcoin.visibility = View.GONE
		mBinding.ivGithub.visibility = View.GONE
		mBinding.ivTwitter.visibility = View.GONE
		mBinding.tvWebsite.visibility = View.GONE
		mBinding.llInfo.visibility = View.GONE

		mBinding.viewpager.adapter = MemberViewpagerAdapter(childFragmentManager)
		mBinding.tlMember.setupWithViewPager(mBinding.viewpager)
	}

	override fun initObserve() {
	}

	override fun initRequestData() {
		launchCoroutine {
			mViewModel.requestMemberBean(username).collect {
				if (it == null) {
					mViewModel.refresh(username)
				} else {
					showUser(it)
				}
			}
		}
	}

	private fun showUser(member: MemberBean) {

		mBinding.ivAvatarProfile.load(member.avatar_large)
		mBinding.tvTagline.text = member.tagline
		mBinding.tvIntro.text = member.bio
		mBinding.tvPrefixCreated.text = "加入于${member.created},${member.id}"

		mBinding.ivBitcoin.isGone = member.btc.isNullOrEmpty()
		mBinding.ivGithub.isGone = member.github.isNullOrEmpty()
		mBinding.ivLocation.isGone = member.location.isNullOrEmpty()
		mBinding.ivTwitter.isGone = member.twitter.isNullOrEmpty()
		mBinding.tvWebsite.isGone = member.website.isNullOrEmpty()

		mBinding.tvTagline.isGone = member.tagline.isNullOrEmpty()
		mBinding.tvIntro.isGone = member.bio.isNullOrEmpty()

		mBinding.llInfo.isGone = member.btc.isNullOrEmpty()
				&& member.github.isNullOrEmpty()
				&& member.location.isNullOrEmpty()
				&& member.twitter.isNullOrEmpty()
				&& member.website.isNullOrEmpty()
	}

	inner class MemberViewpagerAdapter(fm: androidx.fragment.app.FragmentManager) :
		FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

		private val titles = arrayOf("主题", "评论")

		override fun getItem(position: Int) = when (position) {
			0 -> MemberTopicListFragment(username)
			else -> MemberTopicReplyFragment(username)
		}

		override fun getCount() = titles.size
		override fun getPageTitle(position: Int) = titles[position]
	}
}