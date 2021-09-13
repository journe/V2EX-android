package com.journey.android.v2ex.module.login

import android.text.Editable
import android.text.TextWatcher
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import androidx.annotation.StringRes
import androidx.fragment.app.viewModels
import coil.load
import com.journey.android.v2ex.R
import com.journey.android.v2ex.base.BaseFragment
import com.journey.android.v2ex.databinding.FragmentLoginBinding
import com.journey.android.v2ex.libs.ToastUtils
import com.journey.android.v2ex.model.Result
import com.journey.android.v2ex.module.login.data.LoggedInUser
import com.journey.android.v2ex.utils.PrefStore
import dagger.hilt.android.AndroidEntryPoint

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

  private fun loginSuccess(model: LoggedInUser) {
    val welcome = getString(R.string.toast_login_success)
    val displayName = model.displayName
    ToastUtils.showLongToast("$welcome $displayName")
  }

  private fun showLoginFailed(@StringRes errorString: Int) {
    ToastUtils.showLongToast(errorString)
  }

  /**
   * Shows the progress UI and hides the login form.
   */
  private fun showProgress(show: Boolean) {
    mBinding.loginRefresh.isRefreshing = show
  }

  override fun FragmentLoginBinding.initView() {

    mBinding.loginCaptchaIv.setOnClickListener {
      mViewModel.getCaptcha(captchaUrl)
    }
    mBinding.loginRefresh.setOnRefreshListener {
      mViewModel.loadSignPage()
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
    mViewModel.captchaBitmap.observe(viewLifecycleOwner, {
//      Glide.with(this@LoginFragment)
//          .load(it)
//          .placeholder(R.drawable.ic_sync_white_24dp)
//          .error(R.drawable.ic_sync_problem_white_24dp)
//          .into(mBinding.loginCaptchaIv)
      mBinding.loginCaptchaIv.load(it)
    })

    mViewModel.signInFormData.observe(viewLifecycleOwner) {
      captchaUrl = it.genCaptcha()
      mViewModel.getCaptcha(captchaUrl)
    }

    mViewModel.loginFormState.observe(viewLifecycleOwner, {
      val loginState = it
      // disable login button unless both username / password is valid
      mBinding.signInButton.isEnabled = loginState.isDataValid

      if (loginState.usernameError != null) {
        mBinding.loginAccount.error = getString(loginState.usernameError)
      }
      if (loginState.passwordError != null) {
        mBinding.loginPassword.error = getString(loginState.passwordError)
      }
    })

    mViewModel.loginResult.observe(viewLifecycleOwner, {
      showProgress(false)
      when (it) {
        is Result.Success -> {
          loginSuccess(it.data)
        }
        else -> {
          showLoginFailed(R.string.toast_remote_exception)
        }
      }
    })

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
