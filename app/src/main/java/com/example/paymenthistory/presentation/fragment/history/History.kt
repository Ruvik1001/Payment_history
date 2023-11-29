package com.example.paymenthistory.presentation.fragment.history

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.widget.AppCompatImageButton
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.paymenthistory.R
import com.example.paymenthistory.adapter.PaymentRecyclerAdapter
import com.example.paymenthistory.presentation.fragment.auth.AuthViewModel

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * Fragment for displaying payment history.
 *
 * @property param1 Unused parameter.
 * @property param2 Unused parameter.
 * @property viewModel The [HistoryViewModel] for managing authentication logic.
 * @property progressBar The ProgressBar for indicating the downloading progress.
 * @property buttonLogout The Button for logout from account.
 * @property recycler The Recycler for show history of payment.
 */
class History : Fragment() {

    private var param1: String? = null
    private var param2: String? = null

    private lateinit var viewModel: HistoryViewModel

    private lateinit var progressBar: ProgressBar
    private lateinit var buttonLogout: AppCompatImageButton
    private lateinit var recycler: RecyclerView

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
        val view = inflater.inflate(R.layout.fragment_history, container, false)

        viewModel = ViewModelProvider(this, HistoryViewModelFactory(this.requireContext()))
            .get(HistoryViewModel::class.java)

        progressBar = view.findViewById(R.id.progressBar)
        buttonLogout = view.findViewById(R.id.buttonLogout)
        recycler = view.findViewById(R.id.rv_payment)
        recycler.layoutManager = LinearLayoutManager(this.requireContext())

        // Retrieve user token from arguments
        val userToken = requireArguments().getString(resources.getString(R.string.KEY_USER_TOKEN))
        viewModel.load(userToken ?: "")

        // Set click listener for logout button
        buttonLogout.setOnClickListener {
            view.findNavController().popBackStack()
        }

        // Observe the payment response and update UI accordingly
        viewModel.paymentResponseLiveData.observe(this.requireActivity()) { paymentResponse ->
            progressBar.visibility = ProgressBar.GONE
            try {
                if (paymentResponse.success) {
                    Toast.makeText(
                        this.requireContext(),
                        "Data successfully loaded!",
                        Toast.LENGTH_LONG
                    ).show()
                    recycler.adapter = PaymentRecyclerAdapter(paymentResponse.response!!.toMutableList())
                } else {
                    Toast.makeText(
                        this.requireContext(),
                        "${paymentResponse.error!!.error_code}: ${paymentResponse.error.error_msg}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            } catch (e: Exception) {
                Toast.makeText(
                    this.requireContext(),
                    resources.getString(R.string.undefined_error),
                    Toast.LENGTH_LONG
                ).show()
                Log.e("PAY_HISTORY", e.message.toString())
            }
        }

        return view
    }
}
