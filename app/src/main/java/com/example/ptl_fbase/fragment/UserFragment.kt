package com.example.ptl_fbase.fragment

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.ptl_fbase.LoginActivity

import com.example.ptl_fbase.databinding.FragmentUserBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import java.io.ByteArrayOutputStream

class UserFragment : Fragment() {

    private var _binding: FragmentUserBinding? = null
    lateinit var auth: FirebaseAuth
    private lateinit var imgUri: Uri
    private val binding get() = _binding!!

    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = FirebaseAuth.getInstance()
        val user = auth.currentUser

        if (user != null) {
            binding.edtName.setText(user.displayName)
            binding.edtEmail.setText(user.email)
            if (user.isEmailVerified) {
                binding.iconNotVerify.visibility = View.GONE
                binding.iconVerify.visibility = View.VISIBLE
            } else {
                binding.iconNotVerify.visibility = View.VISIBLE
                binding.iconVerify.visibility = View.GONE
            }
        }

        binding.cviUser.setOnClickListener {
            goToCamera()
        }

        binding.btnLogout.setOnClickListener {
            btnLogout()
        }


    }

    private fun goToCamera() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { intent ->
            activity?.packageManager?.let {
                intent?.resolveActivity(it).also {
                    startActivityForResult(intent, REQ_CAM)
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQ_CAM && resultCode == Activity.RESULT_OK) {
            val imgBitmap = data?.extras?.get("data") as Bitmap
            uploadImgFirebase(imgBitmap)
        }
    }

    private fun uploadImgFirebase(imgBitmap: Bitmap) {
        val baos = ByteArrayOutputStream()
        val ref =
            FirebaseStorage.getInstance().reference.child("img_user/${FirebaseAuth.getInstance().currentUser?.email}")
        imgBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val img = baos.toByteArray()
        ref.putBytes(img).addOnCompleteListener {
                if (it.isSuccessful) {
                    ref.downloadUrl.addOnCompleteListener { Task ->
                        Task.result.let { Uri ->
                            imgUri = Uri
                            binding.cviUser.setImageBitmap(imgBitmap)
                        }
                    }
                }
            }
    }

    private fun btnLogout() {
        auth = FirebaseAuth.getInstance()
        auth.signOut()
        val intent = Intent(context, LoginActivity::class.java)
        startActivity(intent)
        activity?.finish()
    }

    companion object {
        const val REQ_CAM = 100

    }

}