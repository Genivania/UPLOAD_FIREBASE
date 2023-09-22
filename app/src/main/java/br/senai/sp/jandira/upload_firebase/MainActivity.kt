package br.senai.sp.jandira.upload_firebase

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import br.senai.sp.jandira.upload_firebase.databinding.ActivityMainBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
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


        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        initiVars()
        registerClickEvents()
    }

    //FUNCAO DE INICIALIZAÇÃO DOS RECURSOS DO FIREBASE
    private fun initiVars(){
        storageRef = FirebaseStorage.getInstance().reference.child("images")
        firebaseFireStore = FirebaseFirestore.getInstance()
    }

    //FUNÇÃO PARA O LANÇADOR DE RECUPERAÇÃO DE IMAGENS DA GALERIA
    private val resultLauncher = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ){
        imageUri = it
        binding.imageView.setImageURI(it)
    }

    private fun registerClickEvents(){

        //TRATA O EVENTO DE CLICK DO COMPONENTE IMAGEVIEW
        binding.imageView.setOnClickListener{
            resultLauncher.launch("image/*")
        }
    }
}