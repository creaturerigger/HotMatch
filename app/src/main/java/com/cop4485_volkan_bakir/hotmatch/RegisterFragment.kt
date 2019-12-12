package com.cop4485_volkan_bakir.hotmatch

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import de.hdodenhof.circleimageview.CircleImageView
import java.io.ByteArrayOutputStream
import java.io.File
import java.util.*

private const val TAG = "RegisterFragment"
private const val PROCEED_LOGIN = 0
private const val PROFILE_IMAGE_DIALOG = 1
private const val REQUEST_PHOTO = 2
private const val REQUEST_GALLERY = 3
private const val AVATAR_IMAGE_DIALOG = 4

class RegisterFragment : Fragment(), ProfileImageDialogFragment.Callbacks, AvatarImageDialogFragment.Callbacks {

    private val cameraViewModel: CameraViewModel by lazy {
        ViewModelProviders.of(this).get(CameraViewModel::class.java)
    }

    private val firebaseStorage = FirebaseStorage.getInstance()

    private lateinit var userEmail: EditText
    private lateinit var userPassword1: EditText
    private lateinit var userPassword2: EditText
    private lateinit var registerButton: Button
    private lateinit var profileImage: CircleImageView
    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference

    private lateinit var photoFile: File
    private lateinit var photoUri: Uri
    private lateinit var galleryUri: Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.hide()
        auth = FirebaseAuth.getInstance()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_register, container, false)
        userEmail = view.findViewById(R.id.rg_text_edit_email) as EditText
        userPassword1 = view.findViewById(R.id.rg_text_edit_password) as EditText
        userPassword2 = view.findViewById(R.id.rg_text_edit_password2) as EditText
        registerButton = view.findViewById(R.id.rg_register_button) as Button
        registerButton.setOnClickListener {
            val userEmailText = userEmail.text.toString()
            val userPasssword1Text = userPassword1.text.toString()
            val userPassword2Text = userPassword2.text.toString()
            if (validateFields(userEmailText, userPasssword1Text, userPassword2Text)) {
                val profileImageConstantState = profileImage.drawable.constantState
                val defaultImageConstantState =
                    resources.getDrawable(R.drawable.profile_sample).constantState
                if (profileImageConstantState == defaultImageConstantState) {
                    Toast.makeText(context, "You have to add a profile image.", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    val isOk = createUser(userEmailText, userPasssword1Text)
                }
            }
        }
        profileImage = view.findViewById(R.id.rg_profile_image) as CircleImageView
        profileImage.setOnClickListener {
            val profileImageDialog = ProfileImageDialogFragment()
            profileImageDialog.apply {
                setTargetFragment(this@RegisterFragment, PROFILE_IMAGE_DIALOG)
                show(this@RegisterFragment.requireFragmentManager(), TAG)
            }
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val imageFileName = UUID.randomUUID()
        photoFile = cameraViewModel.getImageFile("$imageFileName.jpg")
        photoUri = FileProvider.getUriForFile(
            requireActivity(),
            "com.cop4485_volkan_bakir.hotmatch.fileprovider",
            photoFile
        )
    }

    override fun onCameraButtonClicked() {
        val packageManager: PackageManager = requireActivity().packageManager
        val captureImage = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        val resolvedActivity: ResolveInfo? =
            packageManager.resolveActivity(captureImage, PackageManager.MATCH_DEFAULT_ONLY)
        if (resolvedActivity == null) {
            Toast.makeText(context, "There is no available camera service", Toast.LENGTH_SHORT)
                .show()
        } else {

            captureImage.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)

            val cameraActivities: List<ResolveInfo> = packageManager
                .queryIntentActivities(captureImage, PackageManager.MATCH_DEFAULT_ONLY)

            for (cameraActivity in cameraActivities) {
                requireActivity().grantUriPermission(
                    cameraActivity.activityInfo.packageName,
                    photoUri,
                    Intent.FLAG_GRANT_WRITE_URI_PERMISSION
                )
            }
            startActivityForResult(captureImage, REQUEST_PHOTO)
        }
    }

    override fun onAvatarsButtonClicked() {
        val avatarImageDialog = AvatarImageDialogFragment()
        avatarImageDialog.apply {
            setTargetFragment(this@RegisterFragment, AVATAR_IMAGE_DIALOG)
            show(this@RegisterFragment.requireFragmentManager(), TAG)
        }
    }

    override fun onAvatarClicked(avatarImageId: Int) {
        profileImage.setImageResource(avatarImageId)
    }

    override fun onGalleryButtonClicked() {
        val galleryIntent = Intent(Intent.ACTION_PICK)
        galleryIntent.type = "image/*"
        startActivityForResult(galleryIntent, REQUEST_GALLERY)
    }

    override fun onDetach() {
        super.onDetach()
        requireActivity().revokeUriPermission(photoUri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when {
            resultCode != Activity.RESULT_OK -> return
            requestCode == REQUEST_PHOTO -> {
                requireActivity().revokeUriPermission(
                    photoUri,
                    Intent.FLAG_GRANT_WRITE_URI_PERMISSION
                )
                updateImageView()
            }
            requestCode == REQUEST_GALLERY && data != null -> {
                galleryUri = data.data!!
                val galleryImageBitmap = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                        val gallerySource =
                            ImageDecoder.createSource(requireActivity().contentResolver, galleryUri)
                        ImageDecoder.decodeBitmap(gallerySource)
                    } else {
                        MediaStore.Images.Media.getBitmap(
                            requireActivity().contentResolver,
                            galleryUri
                        )
                    }

                profileImage.setImageBitmap(galleryImageBitmap)

            }
        }
    }

    private fun validateFields(email: String, password1: String, password2: String): Boolean {
        return if (email.isEmpty() || password1.isEmpty() || password2.isEmpty()) {
            Toast.makeText(context, R.string.register_empty_fields, Toast.LENGTH_SHORT).show()
            false
        } else {
            if (!isEmailValid(email)) {
                Toast.makeText(context, R.string.is_not_valid_email, Toast.LENGTH_SHORT).show()
                false
            } else {
                if (password1 != password2) {
                    Toast.makeText(context, R.string.passwords_must_match, Toast.LENGTH_SHORT)
                        .show()
                    false
                } else {
                    true
                }
            }
        }
    }

    private fun isEmailValid(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun createUser(email: String, password: String): Boolean {
        var isUserCreated: Boolean = false
        val mActivity = MainActivity.newInstance()
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(mActivity) { task ->
                isUserCreated = if (task.isSuccessful) {
                    Log.d(TAG, "createUserWithEmail:success")
                    val user = auth.currentUser
                    updateUI(user)
                    val dialogFragment = RegisteredDialogFragment()
                    val imm = ContextCompat.getSystemService(
                        view!!.context,
                        InputMethodManager::class.java
                    )
                    imm?.hideSoftInputFromWindow(view!!.windowToken, 0)

                    val userProfileImageBitmap = imageView2Bitmap(profileImage)
                    uploadUserImage(userProfileImageBitmap, user)
                    addUserToFirebaseRTDB(user!!.uid)
                    dialogFragment.apply {
                        setTargetFragment(this@RegisterFragment, PROCEED_LOGIN)
                        show(this@RegisterFragment.requireFragmentManager(), TAG)
                    }
                    true
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(
                        context, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                    updateUI(null)
                    false
                }
            }
        return isUserCreated
    }

    private fun addUserToFirebaseRTDB(userId: String) {
        database = FirebaseDatabase.getInstance().getReference("users")
        val gameUser = GameUser(userId, 0, 0, 1)
        database.child(userId).setValue(gameUser)
            .addOnSuccessListener {
                Toast.makeText(context, "User has been created successfully.", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Log.d(TAG, "Operation failed! -> $it")
            }
    }

    private fun uploadUserImage(bitmap: Bitmap, user: FirebaseUser?) {
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
        val data = byteArrayOutputStream.toByteArray()
        val firebaseStorageReference = firebaseStorage.reference
        val profileImageReference =
            firebaseStorageReference.child("profile_images/${user?.uid}.jpg")
        val uploadTask = profileImageReference.putBytes(data)
        uploadTask.addOnFailureListener {
            Toast.makeText(context, "Image couldn't be uploaded!", Toast.LENGTH_SHORT).show()
        }
        uploadTask.addOnSuccessListener {
            Toast.makeText(context, "Image hass been uploaded successfully", Toast.LENGTH_SHORT)
                .show()
        }
        uploadTask.continueWithTask { task ->
            if (task.isSuccessful) {
                task.exception?.let { throw it }
            }
            profileImageReference.downloadUrl
        }.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val downloadUri = task.result
                val profileUpdates = UserProfileChangeRequest.Builder()
                    .setPhotoUri(downloadUri)
                    .build()
                user?.updateProfile(profileUpdates)?.addOnCompleteListener {
                    if (it.isSuccessful) {
                        Log.d(TAG, "User photo Uri has been set.")
                    }
                }
            } else {
                Toast.makeText(context, "An error has been occurred!", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun updateImageView() {
        if (photoFile.exists()) {
            val bitmap = getSquareCroppedBitmap(photoFile.path, requireActivity())
            profileImage.setImageBitmap(bitmap)
        }
    }

    private fun updateUI(user: FirebaseUser?) {

    }

    private fun imageView2Bitmap(view: ImageView): Bitmap {
        return (view.drawable as BitmapDrawable).bitmap
    }

}