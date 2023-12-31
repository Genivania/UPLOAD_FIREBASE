package br.senai.sp.jandira.upload_firebase

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
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
    //FUNCAO DE TGARTAMENTO DE CLICK
    private fun registerClickEvents(){

        //TRATA O EVENTO DE CLICK DO COMPONENTE IMAGEVIEW
        binding.imageView.setOnClickListener{
            resultLauncher.launch("image/*")
        }
        //TRATA O EVENTO DE CLICK DO BOTAO DE UPLOAD
        binding.uploadBtn.setOnClickListener {
            uploadImage()
        }

        binding.showAllBtn.setOnClickListener {
            startActivity(Intent(this, ImageFeed::class.java))
        }

    }

    //FUNCAO UPLOAD
    private fun uploadImage(){

        binding.progressBar.visibility = View.VISIBLE

        //DEFINE UM NOME UNICO PARA A IMAGEM COM USO DE UM VALOR TIMESSTAMP
        storageRef = storageRef.child(System.currentTimeMillis().toString())

        //EXECUTA O PROCESSO DE UPLOAD DA IMAGEM
//        imageUri?.let {
//            storageRef.putFile(it).addOnCompleteListener{
//                task->
//                    if (task.isSuccessful){
//                        Toast.makeText(this,
//                                                "UPLOAD CONCLUIDO!",
//                                                Toast.LENGTH_LONG).show()
//                    }else{
//                        Toast.makeText(this,
//                            "ERRO AO REALIZAR O UPLOAD!",
//                            Toast.LENGTH_LONG).show()
//                    }
//
//                binding.progressBar.visibility = View.VISIBLE
//                binding.imageView.setImageResource(R.drawable.upload)
//            }
//        }

        //EXECUTA O PROCESSO DE UPLOAD DA IMAGEM - V2 - UPLOAD NO STORAGE E GRAVCAO NO FIREBASE
        imageUri?.let {
            storageRef.putFile(it).addOnCompleteListener { task->

                if (task.isSuccessful) {

                    storageRef.downloadUrl.addOnSuccessListener { uri ->

                        val map = HashMap<String, Any>()
                        map["pic"] = uri.toString()

                        firebaseFireStore.collection("images").add(map).addOnCompleteListener { firestoreTask ->

                            if (firestoreTask.isSuccessful){
                                Toast.makeText(this, "Uploaded Successfully", Toast.LENGTH_SHORT).show()

                            }else{
                                Toast.makeText(this, firestoreTask.exception?.message, Toast.LENGTH_SHORT).show()

                            }
                            binding.progressBar.visibility = View.GONE
                            binding.imageView.setImageResource(R.drawable.upload)

                        }
                    }

                }else{

                    Toast.makeText(this,  task.exception?.message, Toast.LENGTH_SHORT).show()

                }

                //BARRA DE PROGRESSO DO UPLOAD
                binding.progressBar.visibility = View.GONE

                //TROCA A IMAGEM PARA A IMAGEM PADRÃO
                binding.imageView.setImageResource(R.drawable.upload)

            }
        }

    }
}



















