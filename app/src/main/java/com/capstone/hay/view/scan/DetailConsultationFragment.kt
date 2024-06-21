package com.capstone.hay.view.scan

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.navArgs
import com.capstone.hay.R
import com.capstone.hay.data.model.ConsultModel
import com.capstone.hay.databinding.FragmentDetailConsultationBinding
import com.google.android.material.appbar.MaterialToolbar
import java.net.URLEncoder

class DetailConsultationFragment : Fragment() {
    private var _binding: FragmentDetailConsultationBinding? = null
    private val binding get() = _binding!!

    private val args: DetailConsultationFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailConsultationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupData(args.consult)
        setupActionBack()
    }

    private fun setupData(consult : ConsultModel) {
        binding.imgProfile.setImageResource(consult.photo)
        binding.nameProfile.text = consult.name
        binding.descAboutConsult.text = consult.about
        binding.scheduleTimeConsult.text = consult.schedule
        binding.infoProfile.text = getString(R.string.info_consult, consult.specialist, consult.place, consult.year.toString())
        binding.cardSchedule.setOnClickListener {
            openWhatsApp(requireContext(), consult.contact, consult.name, consult.schedule)
        }
    }

    private fun setupActionBack() {
        val topAppBar: MaterialToolbar = binding.topAppBar
        (requireActivity() as AppCompatActivity).setSupportActionBar(topAppBar)
        (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        topAppBar.setNavigationOnClickListener {
            @Suppress("DEPRECATION")
            requireActivity().onBackPressed()
        }
    }

    private fun openWhatsApp(context: Context, phoneNumber: String, name: String, schedule: String) {
        try {
            val strippedPhoneNumber = phoneNumber.replace("[^0-9]".toRegex(), "")
            val message = "Halo, saya ingin berkonsultasi mengenai masalah rambut saya dengan $name pada jadwal berikut: $schedule. Bisakah Anda membantu saya?"
            val encodedMessage = URLEncoder.encode(message, "UTF-8")
            val uri = Uri.parse("https://wa.me/$strippedPhoneNumber?text=$encodedMessage")

            val intent = Intent(Intent.ACTION_VIEW, uri)
            intent.setPackage("com.whatsapp")

            context.startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(context, "WhatsApp tidak tersedia.", Toast.LENGTH_SHORT).show()
            e.printStackTrace()
        }
    }


}