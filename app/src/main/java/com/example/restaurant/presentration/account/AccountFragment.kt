package com.example.restaurant.presentration.account


import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.restaurant.databinding.FragmentAccountBinding
import com.example.restaurant.usecase.autoCleared
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*


@AndroidEntryPoint
class AccountFragment : Fragment() {
    var binding:FragmentAccountBinding by autoCleared()
    lateinit var sharedpreferences: SharedPreferences
    val myCalendar: Calendar = Calendar.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
       binding = FragmentAccountBinding .inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val spinner = binding.genderSpinner
        spinner.setItems("Select your Gender","Male","Female","Others")
        spinner.setOnItemSelectedListener { view, position, id, item ->
            Snackbar.make(
                view,
                "Clicked $item",
                Snackbar.LENGTH_LONG
            ).show()
        }
        sharedpreferences = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        binding.saveSettingSettingsBtn.setOnClickListener {
            val editor = sharedpreferences.edit()
            editor.putString("first_name", binding.firstName.editText?.text.toString())
            editor.putString("last_name", binding.lastName.editText?.text.toString())
            editor.putString("phone_number", binding.phoneNumber.editText?.text.toString())
            editor.putString("email", binding.email.editText?.text.toString())
            editor.putString("gender", spinner.text.toString())
            editor.putString("date_of_birth", binding.dateofbirth.editText?.text.toString())
            editor.putString("nid", binding.nid.editText?.text.toString())
            editor.putString("occupation", binding.occupation.editText?.text.toString())
            editor.commit()
            binding.firstName.editText?.setText("")
            binding.lastName.editText?.setText("")
            binding.phoneNumber.editText?.setText("")
            binding.email.editText?.setText("")
            spinner.setText("")
            binding.dateofbirth.editText?.setText("")
            binding.nid.editText?.setText("")
            binding.occupation.editText?.setText("")

            Toast.makeText(
                requireContext(),
                " Personal information save successfully ",
                Toast.LENGTH_LONG
            ).show()
        }

        val date =
            OnDateSetListener { view, year, month, day ->
                myCalendar.set(Calendar.YEAR, year)
                myCalendar.set(Calendar.MONTH, month)
                myCalendar.set(Calendar.DAY_OF_MONTH, day)
                updateLabel()
            }
        binding.birthdayEt.setOnClickListener(View.OnClickListener {
            DatePickerDialog(
                requireContext(),
                date,
                myCalendar.get(Calendar.YEAR),
                myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        })

        binding.firstName.editText?.setText(sharedpreferences.getString("first_name", ""))
        binding.lastName.editText?.setText(sharedpreferences.getString("last_name", ""))
        binding.phoneNumber.editText?.setText(sharedpreferences.getString("phone_number", ""))
        binding.email.editText?.setText(sharedpreferences.getString("email", ""))
        spinner.setText(sharedpreferences.getString("gender", ""))
        binding.dateofbirth.editText?.setText(sharedpreferences.getString("date_of_birth", ""))
        binding.nid.editText?.setText(sharedpreferences.getString("nid", ""))
        binding.occupation.editText?.setText(sharedpreferences.getString("occupation", ""))

    }

    private fun updateLabel() {
        val myFormat = "MM/dd/yyyy"
        val dateFormat = SimpleDateFormat(myFormat, Locale.US)
        binding.birthdayEt.setText(dateFormat.format(myCalendar.time))
    }


}