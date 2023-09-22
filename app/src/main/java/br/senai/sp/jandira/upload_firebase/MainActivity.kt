package br.senai.sp.jandira.upload_firebase

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import br.senai.sp.jandira.upload_firebase.databinding.ActivityMainBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.StorageReference

class MainActivity : AppCompatActivity() {

    //DECLARAÇAO DOS ATRIBUTOS:

    //ACTIVITYMAINBINDING - MANIPULAÇAO DO ELEMENTOS GRÁFICOS DP MATERIAL DESING
    private lateinit var binding: ActivityMainBinding

    //STORAGEREFERENCES - PERMITE A MANIPULAÇAO DO CLOUD STORAGE (ARMAZENA AEQUIVOS)
    private lateinit var storageRef: StorageReference

    //FIREBASEFIRESTORE - PERMITE A MANIPULAÇAO DO BANCO DE DADOS NO SQL
    private lateinit var firebaseFireStore: FirebaseFirestore

    //URI - PERMITE A MANIPULAÇAO DE ARQUIVOS ATRAVES DO SEU ENDEREÇAMENTO
    private var imageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}