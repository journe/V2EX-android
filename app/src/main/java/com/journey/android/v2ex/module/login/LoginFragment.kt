package com.journey.android.v2ex.module.login

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import androidx.annotation.StringRes
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.journey.android.v2ex.R
import com.journey.android.v2ex.base.BaseFragment
import com.journey.android.v2ex.libs.ToastUtils
import com.journey.android.v2ex.module.login.data.LoggedInUser
import com.journey.android.v2ex.utils.PrefStore
import kotlinx.android.synthetic.main.fragment_login.login_account
import kotlinx.android.synthetic.main.fragment_login.login_captcha
import kotlinx.android.synthetic.main.fragment_login.login_captcha_iv
import kotlinx.android.synthetic.main.fragment_login.login_password
import kotlinx.android.synthetic.main.fragment_login.login_refresh
import kotlinx.android.synthetic.main.fragment_login.sign_in_button

class LoginFragment : BaseFragment() {

  private val viewModel: LoginViewModel by viewModels {
    object : ViewModelProvider.Factory {
      override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return LoginViewModel(LoginRepository(LoginDataSource())) as T
      }
    }
  }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    return inflater.inflate(R.layout.fragment_login, container, false)

  }

  override fun onViewCreated(
    view: View,
    savedInstanceState: Bundle?
  ) {
    super.onViewCreated(view, savedInstanceState)

    login_captcha_iv.setOnClickListener {
//      getCaptcha(mLoginBean.genCaptcha())
    }
    login_refresh.setOnRefreshListener {
//      doGetLoginTask()
    }
    login_account.setText(PrefStore.instance.userName)
    login_password.setText(PrefStore.instance.userPass)

    login_account.afterTextChanged {
      viewModel.loginDataChanged(
          login_account.text.toString(),
          login_password.text.toString()
      )
    }

    login_password.apply {
      afterTextChanged {
        viewModel.loginDataChanged(
            login_account.text.toString(),
            login_password.text.toString()
        )
      }

      setOnEditorActionListener { _, actionId, _ ->
        when (actionId) {
          EditorInfo.IME_ACTION_DONE ->
            viewModel.login(
                login_account.text.toString(),
                login_password.text.toString(),
                login_captcha.text.toString()
            )
        }
        false
      }

      sign_in_button.setOnClickListener {
        showProgress(true)
        viewModel.login(
            login_account.text.toString(),
            login_password.text.toString(),
            login_captcha.text.toString()
        )
      }
    }
    observe()
  }

  private fun observe() {
    viewModel.captchaBitmap.observe(viewLifecycleOwner, {
      Glide.with(this@LoginFragment)
          .load(it)
          .placeholder(R.drawable.ic_sync_white_24dp)
          .error(R.drawable.ic_sync_problem_white_24dp)
          .into(login_captcha_iv)
    })

    viewModel.loginFormState.observe(viewLifecycleOwner, {
      val loginState = it ?: return@observe

      // disable login button unless both username / password is valid
      sign_in_button.isEnabled = loginState.isDataValid

      if (loginState.usernameError != null) {
        login_account.error = getString(loginState.usernameError)
      }
      if (loginState.passwordError != null) {
        login_password.error = getString(loginState.passwordError)
      }
    })

    viewModel.loginResult.observe(viewLifecycleOwner, {
      val loginResult = it ?: return@observe

      showProgress(false)
      if (loginResult.error != null) {
        showLoginFailed(loginResult.error)
      }
      if (loginResult.success != null) {
        updateUiWithUser(loginResult.success)
      }
    })
  }

  private fun updateUiWithUser(model: LoggedInUser) {
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
    login_refresh.isRefreshing = show
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
