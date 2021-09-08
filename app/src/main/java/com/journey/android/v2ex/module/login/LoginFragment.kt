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
class LoginFragment : BaseFragment() {

  private lateinit var captchaUrl: String

  private val viewModel: LoginViewModel by viewModels()

  override val binding get() = _binding!! as FragmentLoginBinding

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    _binding = FragmentLoginBinding.inflate(inflater, container, false)
    return binding.root

  }

  override fun onViewCreated(
    view: View,
    savedInstanceState: Bundle?
  ) {
    super.onViewCreated(view, savedInstanceState)

    viewModel.loadSignPage()

    binding.loginCaptchaIv.setOnClickListener {
      viewModel.getCaptcha(captchaUrl)
    }
    binding.loginRefresh.setOnRefreshListener {
      viewModel.loadSignPage()
    }
    binding.loginAccount.setText(PrefStore.instance.userName)
    binding.loginPassword.setText(PrefStore.instance.userPass)

    binding.loginAccount.afterTextChanged {
      viewModel.loginDataChanged(
          binding.loginAccount.text.toString(),
          binding.loginPassword.text.toString()
      )
    }

    binding.loginPassword.afterTextChanged {
      viewModel.loginDataChanged(
          binding.loginAccount.text.toString(),
          binding.loginPassword.text.toString()
      )
    }

    binding.loginCaptcha.setOnEditorActionListener { _, actionId, _ ->
      when (actionId) {
        EditorInfo.IME_ACTION_DONE ->
          doLogin()
      }
      false
    }

    binding.signInButton.setOnClickListener {
      doLogin()
    }
    observe()
  }

  private fun doLogin() {
    showProgress(true)
    viewModel.login(
        binding.loginAccount.text.toString(),
        binding.loginPassword.text.toString(),
        binding.loginCaptcha.text.toString()
    )
  }

  private fun observe() {
    viewModel.captchaBitmap.observe(viewLifecycleOwner, {
//      Glide.with(this@LoginFragment)
//          .load(it)
//          .placeholder(R.drawable.ic_sync_white_24dp)
//          .error(R.drawable.ic_sync_problem_white_24dp)
//          .into(binding.loginCaptchaIv)
      binding.loginCaptchaIv.load(it)
    })

    viewModel.signInFormData.observe(viewLifecycleOwner) {
      captchaUrl = it.genCaptcha()
      viewModel.getCaptcha(captchaUrl)
    }

    viewModel.loginFormState.observe(viewLifecycleOwner, {
      val loginState = it
      // disable login button unless both username / password is valid
      binding.signInButton.isEnabled = loginState.isDataValid

      if (loginState.usernameError != null) {
        binding.loginAccount.error = getString(loginState.usernameError)
      }
      if (loginState.passwordError != null) {
        binding.loginPassword.error = getString(loginState.passwordError)
      }
    })

    viewModel.loginResult.observe(viewLifecycleOwner, {
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

    viewModel.loadingState.observe(viewLifecycleOwner) {
      showProgress(it)
    }
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
    binding.loginRefresh.isRefreshing = show
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
