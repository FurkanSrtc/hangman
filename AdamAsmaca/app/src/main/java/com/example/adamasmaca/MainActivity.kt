package com.example.adamasmaca

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val db by lazy { DBHelper.DBHelper(this)  } //veritabanına erişim yetkisi veriyorum
        btnEkle.setOnClickListener {

            if (txtKelime.text!=null){
            db.KelimeEkle(kelime = Kelimeler(kelime = txtKelime.text.toString()))
            kelimeleriYazdir(db.KelimeleriGetir())}
       }
btnGiris.setOnClickListener {
    if (txtAd.text.toString()=="beyza" && txtSifre.text.toString()=="123") //kullanıcı adı şifre doğru mu diye kontrol ediyorum
    {
        txtAd.visibility=View.INVISIBLE;
        txtSifre.visibility=View.INVISIBLE;
        btnGiris.visibility=View.INVISIBLE;

        txtKelime.visibility=View.VISIBLE
        btnKelimeSil.visibility=View.VISIBLE
        btnEkle.visibility=View.VISIBLE
        txtViewKelimeler.visibility=View.VISIBLE
    }
    else
        Toast.makeText(applicationContext, "Kullanıcı Adı Veya Parola Hatalı", Toast.LENGTH_SHORT).show()
}
        btnKelimeSil.setOnClickListener {
            db.KelimeleriSil()
        }
btnOyunEkrani.setOnClickListener {
    val intent = Intent(this,OyunEkrani::class.java)

    startActivity(intent)
}

        }
    fun kelimeleriYazdir(list:MutableList<Kelimeler>){ //veritabanında kayıtlı olan kelimeleri çekiyorum
        txtViewKelimeler.text = ""
        list.forEach {
            txtViewKelimeler.text = txtViewKelimeler.text.toString() + "\n" + it.kelime
        }

    }


}