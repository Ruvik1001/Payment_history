package com.example.paymenthistory.presentation.fragment.auth

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.paymenthistory.R
import org.koin.androidx.viewmodel.ext.android.viewModel

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

private const val MY_PERMISSIONS_INTERNET = 10001

/**
 * A simple [Fragment] subclass for user authentication.
 *
 * @property viewModel The [AuthViewModel] for managing authentication logic.
 * @property login The TextView for user login input.
 * @property password The TextView for user password input.
 * @property loginButton The Button for login action.
 * @property progressBar The ProgressBar for indicating the authentication progress.
 * @property param1 A parameter for the fragment (unused).
 * @property param2 A parameter for the fragment (unused).
 */
class Auth : Fragment() {

    private val viewModel by viewModel<AuthViewModel>()

    private lateinit var login: TextView
    private lateinit var password: TextView
    private lateinit var loginButton: Button
    private lateinit var progressBar: ProgressBar

    private var param1: String? = null
    private var param2: String? = null

    /**
     * Called when the fragment is created. Responsible for initializing the fragment.
     *
     * @param savedInstanceState If the fragment is being re-initialized after previously being shut down,
     * this Bundle contains the data it most recently supplied in [onSaveInstanceState].
     * Note: Otherwise, it is null.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    /**
     * Called to have the fragment instantiate its user interface view.
     *
     * @param inflater The LayoutInflater object that can be used to inflate any views in the fragment.
     * @param container If non-null, this is the parent view that the fragment's UI should be attached to.
     * The fragment should not add the view itself, but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state as given here.
     * @return Return the View for the fragment's UI, or null.
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_auth, container, false)

        // Check and request INTERNET permission if not granted
        if (ContextCompat.checkSelfPermission(
                this.requireContext(),
                Manifest.permission.INTERNET
            ) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(
                this.requireActivity(),
                arrayOf(Manifest.permission.INTERNET),
                MY_PERMISSIONS_INTERNET
            )
        }

        // Initialize views
        login = view.findViewById(R.id.editTextUsername)
        password = view.findViewById(R.id.editTextPassword)
        loginButton = view.findViewById(R.id.buttonLogin)
        progressBar = view.findViewById(R.id.progressBar)

        // Set user data if available
        login.text = viewModel.getUserData().login
        password.text = viewModel.getUserData().password

        // Set click listener for login button
        loginButton.setOnClickListener {
            progressBar.visibility = ProgressBar.VISIBLE
            viewModel.setUserData(login = login.text.toString(), password = password.text.toString())
            viewModel.auth()
            loginButton.isEnabled = false
        }

        // Observe the authentication response and handle accordingly
        viewModel.responseLiveData.observe(this.requireActivity()) { authResponse ->
            progressBar.visibility = ProgressBar.GONE
            loginButton.isEnabled = true
            try {
                if (authResponse.success) {
                    view.findNavController().navigate(R.id.action_auth_to_history, Bundle().apply {
                        putString(resources.getString(R.string.KEY_USER_TOKEN),
                            authResponse.response!!.token
                        )
                    })
                } else {
                    Toast.makeText(
                        this.requireContext(),
                        "${authResponse.error!!.error_code}: ${authResponse.error!!.error_msg}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            } catch (e: Exception) {
                Toast.makeText(
                    this.requireContext(),
                    resources.getString(R.string.undefined_error),
                    Toast.LENGTH_LONG
                ).show()
                Log.e("PAY_LOGIN", e.message.toString())
            }
        }

        return view
    }
}
