package com.example.budayakita.ui.explore

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import com.example.budayakita.MainActivity
import com.example.budayakita.R
import com.example.budayakita.data.model.UserPreference
import com.example.budayakita.data.model.dataStore
import com.example.budayakita.databinding.ActivityExploreBinding
import com.example.budayakita.ui.ViewModelFactory
import com.example.budayakita.ui.create.CreateActivity
import com.example.budayakita.ui.glossary.GlossaryActivity
import com.example.budayakita.ui.profile.ProfileActivity
import com.qamar.curvedbottomnaviagtion.CurvedBottomNavigation
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import androidx.lifecycle.lifecycleScope
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date

class ExploreActivity : AppCompatActivity() {

    private lateinit var binding: ActivityExploreBinding
    private var selectedImageUri: Uri? = null
    private lateinit var currentUserId: String

    private val exploreViewModel: ExploreViewModel by viewModels {
        ViewModelFactory.getInstance(applicationContext)
    }

    private val userPreference: UserPreference by lazy {
        UserPreference.getInstance(applicationContext.dataStore)
    }

    private val galleryLauncher = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            selectedImageUri = it
            binding.previewImage.setImageURI(it)
        }
    }

    private val cameraLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            @Suppress("DEPRECATION") val photo = result.data?.extras?.get("data") as? Bitmap
            photo?.let {
                binding.previewImage.setImageBitmap(it)
                // Convert bitmap to file if needed
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExploreBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Mengambil session atau token dari preference
        lifecycleScope.launch {
            val session = userPreference.getSession().first()
            currentUserId = session.token // atau gunakan ID lainnya yang relevan
        }

        setupBottomNavigation()
        setupImageUploadButtons()
        observeViewModel()
    }

    private fun setupBottomNavigation() {
        val bottomNavigation = binding.bottomNavigation
        bottomNavigation.apply {
            add(CurvedBottomNavigation.Model(1, "Home", R.drawable.ic_home))
            add(CurvedBottomNavigation.Model(2, "Glossary", R.drawable.ic_glossary))
            add(CurvedBottomNavigation.Model(3, "Explore", R.drawable.ic_explore))
            add(CurvedBottomNavigation.Model(4, "Create", R.drawable.ic_create))
            add(CurvedBottomNavigation.Model(5, "Profile", R.drawable.ic_profile))

            show(3, true)

            setOnClickMenuListener {
                when (it.id) {
                    1 -> startActivity(Intent(this@ExploreActivity, MainActivity::class.java))
                    2 -> startActivity(Intent(this@ExploreActivity, GlossaryActivity::class.java))
                    3 -> {}
                    4 -> startActivity(Intent(this@ExploreActivity, CreateActivity::class.java))
                    5 -> startActivity(Intent(this@ExploreActivity, ProfileActivity::class.java))
                }
            }
        }

        binding.btnHistory.setOnClickListener {
            replaceFragment(HistoryExploreFragment())
        }
    }

    private fun setupImageUploadButtons() {
        binding.btnGallery.setOnClickListener {
            galleryLauncher.launch("image/*")
        }

        binding.btnCamera.setOnClickListener {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            val photoUri = createImageFileUri()  // Membuat file image URI
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
            cameraLauncher.launch(intent)
        }

        binding.btnUpload.setOnClickListener {
            selectedImageUri?.let { uri ->
                val file = File(getRealPathFromURI(uri))
                exploreViewModel.predictImage(file.absolutePath, currentUserId)
                replaceFragment(LoadingExploreFragment())
            } ?: run {
                Toast.makeText(this, "Please select an image first", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun observeViewModel() {
        exploreViewModel.predictionResponse.observe(this) { response ->
            if (response != null) {
                val successFragment = SuccessExploreFragment.newInstance(
                    response.file_url,
                    response.prediction,
                    response.deskripsi
                )
                replaceFragment(successFragment)
            } else {
                Toast.makeText(this, "Prediction failed", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()  // Commit menggunakan commit() untuk menjaga lifecycle fragment
    }

    private fun getRealPathFromURI(uri: Uri): String {
        val projection = arrayOf(MediaStore.Images.Media.DATA)
        contentResolver.query(uri, projection, null, null, null)?.use { cursor ->
            val columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            cursor.moveToFirst()
            return cursor.getString(columnIndex)
        }
        return uri.path ?: ""
    }

    @SuppressLint("SimpleDateFormat")
    private fun createImageFileUri(): Uri {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File = getExternalFilesDir(Environment.DIRECTORY_PICTURES)!!
        val imageFile = File(storageDir, "JPEG_${timeStamp}_")
        return FileProvider.getUriForFile(this, "com.example.budayakita.fileprovider", imageFile)
    }
}
