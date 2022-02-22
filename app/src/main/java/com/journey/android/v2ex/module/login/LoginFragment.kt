package com.journey.android.v2ex.module.login

import android.text.Editable
import android.text.TextWatcher
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import androidx.fragment.app.viewModels
import coil.load
import com.aries.ui.widget.alert.UIAlertDialog
import com.journey.android.v2ex.BuildConfig
import com.journey.android.v2ex.R
import com.journey.android.v2ex.base.BaseFragment
import com.journey.android.v2ex.databinding.FragmentLoginBinding
import com.journey.android.v2ex.libs.ToastUtils
import com.journey.android.v2ex.libs.extension.gone
import com.journey.android.v2ex.libs.extension.visible
import com.journey.android.v2ex.model.Result
import com.journey.android.v2ex.module.login.data.LoginResult
import com.journey.android.v2ex.utils.PrefStore
import dagger.hilt.android.AndroidEntryPoint
import org.jetbrains.anko.support.v4.toast

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding, LoginViewModel>() {

	private lateinit var captchaUrl: String

	override val mViewModel: LoginViewModel by viewModels()

	private fun doLogin() {
		showProgress(true)
		mViewModel.login(
			mBinding.loginAccount.text.toString(),
			mBinding.loginPassword.text.toString(),
			mBinding.loginCaptcha.text.toString()
		)
	}

	private fun loginSuccess(model: LoginResult) {
		val welcome = getString(R.string.toast_login_success)
		val displayName = welcome + model.username
		toast(displayName)
	}

	/**
	 * Shows the progress UI and hides the login form.
	 */
	private fun showProgress(show: Boolean) {
		mBinding.loginRefresh.isRefreshing = show
	}

	override fun FragmentLoginBinding.initView() {

		if (BuildConfig.DEBUG) {
			mBinding.logoutTv.visible()
			mBinding.logoutTv.setOnClickListener {
				mViewModel.logout()
			}
		} else {
			mBinding.logoutTv.gone()
		}
		mBinding.loginCaptchaIv.setOnClickListener {
			mViewModel.getCaptcha(captchaUrl)
		}
		mBinding.loginRefresh.setOnRefreshListener {
			initRequestData()
		}
		mBinding.loginAccount.setText(PrefStore.instance.userName)
		mBinding.loginPassword.setText(PrefStore.instance.userPass)

		mBinding.loginAccount.afterTextChanged {
			mViewModel.loginDataChanged(
				mBinding.loginAccount.text.toString(),
				mBinding.loginPassword.text.toString()
			)
		}

		mBinding.loginPassword.afterTextChanged {
			mViewModel.loginDataChanged(
				mBinding.loginAccount.text.toString(),
				mBinding.loginPassword.text.toString()
			)
		}

		mBinding.loginCaptcha.setOnEditorActionListener { _, actionId, _ ->
			when (actionId) {
				EditorInfo.IME_ACTION_DONE ->
					doLogin()
			}
			false
		}

		mBinding.signInButton.setOnClickListener {
			doLogin()
		}
	}

	override fun initObserve() {
		mViewModel.captchaBitmap.observe(viewLifecycleOwner) {
			mBinding.loginCaptchaIv.load(it)
		}

		mViewModel.errorMessage.observe(viewLifecycleOwner) {
			if (it.isNotEmpty()) {
				toast(it)
				mBinding.loginRefresh.isRefreshing = false
			}
		}

		//获取校验码
		mViewModel.signInFormData.observe(viewLifecycleOwner) {
			captchaUrl = it.genCaptcha()
			mViewModel.getCaptcha(captchaUrl)
		}

		mViewModel.loginFormState.observe(viewLifecycleOwner) {
			val loginState = it
			// disable login button unless both username / password is valid
			mBinding.signInButton.isEnabled = loginState.isDataValid

			if (loginState.usernameError != null) {
				mBinding.loginAccount.error = getString(loginState.usernameError)
			}
			if (loginState.passwordError != null) {
				mBinding.loginPassword.error = getString(loginState.passwordError)
			}
		}

		//登录结果处理
		mViewModel.loginResult.observe(viewLifecycleOwner) {
			showProgress(false)
			when (it) {
				is Result.Success -> {
					loginSuccess(it.data)
				}
				is Result.Error -> {
					mViewModel.logout()
					UIAlertDialog.DividerIOSBuilder(context)
						.setMessage(it.exception.message ?: "")
						.setPositiveButton("知道了") { dialog, which ->
							dialog?.dismiss()
							initRequestData()
						}
						.create().setDimAmount(0.6f).show()
				}
			}
		}

		mViewModel.loadingState.observe(viewLifecycleOwner) {
			showProgress(it)
		}
	}

	override fun initRequestData() {
		mViewModel.loadSignPage()
	}

}

fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
	this.addTextChangedListener(object : TextWatcher {
		override fun afterTextChanged(editable: Editable?) {
			afterTextChanged.invoke(editable.toString())
		}

		override fun beforeTextChanged(
			s: CharSequence,
			start: Int,
			count: Int,
			after: Int
		) {
		}

		override fun onTextChanged(
			s: CharSequence,
			start: Int,
			before: Int,
			count: Int
		) {
		}
	})
}
