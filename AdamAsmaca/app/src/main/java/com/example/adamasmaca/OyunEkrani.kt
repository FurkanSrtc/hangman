package com.example.adamasmaca

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import kotlinx.android.synthetic.main.activity_oyun_ekrani.*


class OyunEkrani : AppCompatActivity() {
    val db by lazy { DBHelper.DBHelper(this) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_oyun_ekrani)

        var gizliKelime = ""


        imageView2.setImageResource(R.drawable.resim0)

        var kelimeList = db.KelimeleriGetir()// veritabanımdaki tüm kelimeleri kelimeList Değişkenine aktardım
        var rnd = (0..kelimeList.count()).random()  //listeden rastgele bir kelime aldım
        var kelime = kelimeList[rnd].kelime.toString()  //kelime listesinden randomla gelen indeksteki kelimeyi çektim
        var kontrol = false //girilen karakter kelime içerisinde olup olmadığını kontrol ederken kullanıyorum
        var girilenKarakterList = mutableListOf<Char>()  // Girilen karakter list.
        var dogruHarfler= mutableListOf<Char>() //kelimede bulunan doğru harfleri burada tutuyorum
        var yanlisTahminSayisi = 0 // yanlış tahmin girildiğinde burası artar

        for (k in kelime) {  //gelen kelimenin uzunluğu kadar _ oluşturur ve gizliKelime değişkenine aktardım
            gizliKelime += "_ "
        }
        txtViewKelime.text = gizliKelime  // _ leri textviewime yazdırdım

        var tahminYanlisMi = false;  //textboxa yazılan kelimenin doğruluğunu kontrol ederken kullanıyorum
        var ayniHarfMi = false  //textboxa yazılan kelimenin daha önceden girilip girilmediğini kontrol ederken kullanıyorum

        btnEkle.setOnClickListener {  //Ekle butonuna tıklandığı zaman
            ayniHarfMi = false //aşağıda girilen harfi kontrol ederken. eğer aynı harfse bu değişkeni true olarak değiştireceğim.

            if (txtHarf.text.length > 1)  //TEXTBOXA KAÇ KARAKTER GİRDİĞİNİ KONTROL EDİYORUM. 1 karakterden fazla girdiyse hata veriyorum
            {Toast.makeText(applicationContext, "LÜTFEN TEK HARF GİRİN", Toast.LENGTH_SHORT)
                    .show()}
            else {

                for (i in girilenKarakterList)  //Aynı harfin girilip girilmediğini for döngüsüyle kontrol ediyorum
                {
                    if (i == txtHarf.text.single())
                    { ayniHarfMi = true
                    Toast.makeText(
                        applicationContext,
                        "AYNI HARFİ TEKRAR GİREMEZSİNİZ",
                        Toast.LENGTH_SHORT
                    ).show()}
                }
                if (ayniHarfMi == false) { // aynı harf girilmediyse çalışır


                    girilenKarakterList.add(txtHarf.text.single()) //girilen karakteri girilenKarakterlist'ime aktardım
                   // txtTest.text=girilenKarakterList.toString()

                    gizliKelime = "" //gizli kelimemi aşağıda tekrar oluşturacağım için içini boşaltıyorum

                    tahminYanlisMi = true;  // varsayılan olarak tahmini yanlış olarak belirtiyorum. Aşağıda for döngüsüyle kontrol ettikten sonra tahminin doğru olduğunu anlarsam bu değer false olcak;
                    for (k in kelime) { //kelime değişkenimdeki her harfi tek tek kontrol ediyorum
                        kontrol = false //girilen karakter kelime içerisinde olup olmadığını kontrol ederken kullanıyorum
                        for (l in girilenKarakterList) { //kullanıcının girdiği tüm harfleri, kelimemdeki harflerle uyuşup uyuşmadığını kontrol ediyorum


                            if (k == l && kontrol == false) { //eğer kelimemdeki harf, girilenkarakterlerden biriyle uyuşuyorsa. O harfin görünümünü açıyorum.
                                gizliKelime += k + " " //açılan karakteri gizlikelimeme ekliyorum
                                kontrol = true
                                dogruHarfler.add(k) //girilen karakter doğru olduğu için dogruharfler listeme ekliyorum.
                                if (gizliKelime.replace(" ", "") == kelime) { //gizli kelimemin arasındaki boşlukları kaldırınca. Hafızadaki kelime ile aynı oluyorsa. Oyunu kazanmış oluyorum
                                    Toast.makeText(
                                        applicationContext,
                                        "TEBRİKLER OYUNU KAZANDINIZ",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                                break
                            }
                        }
                        if (kontrol == false) { //eğer girilen harf, kelimem içerisinde yoksa gizliKelime değişkenimin o kısmına "_ " ekliyorum
                            gizliKelime += "_ "

                        }
                    }
                    for (l in dogruHarfler)  //girilen harf, doğru harfler listemde var mı diye kontrol ediyorum. Eğer yoksa, adamasmaca karakterinin 1 canı azalmış olacak.
                    {
                        if(l==txtHarf.text.single())
                            tahminYanlisMi=false
                    }
                    if (tahminYanlisMi == true) { //eğer kullanıcının tahmini yanlışsa, her yanlış hak için ayrı fotoğraf tanımladım. 10 kere yanlış yaparsa kaybediyor.
                        yanlisTahminSayisi++
                        when (yanlisTahminSayisi) {
                            1 -> imageView2.setImageResource(R.drawable.resim1)
                            2 -> imageView2.setImageResource(R.drawable.resim2)
                            3 -> imageView2.setImageResource(R.drawable.resim3)
                            4 -> imageView2.setImageResource(R.drawable.resim4)
                            5 -> imageView2.setImageResource(R.drawable.resim5)
                            6 -> imageView2.setImageResource(R.drawable.resim6)
                            7 -> imageView2.setImageResource(R.drawable.resim7)
                            8 -> imageView2.setImageResource(R.drawable.resim8)
                            9 -> imageView2.setImageResource(R.drawable.resim9)
                            10 -> {
                                imageView2.setImageResource(R.drawable.resim10)
                                Toast.makeText(
                                    applicationContext,
                                    "KAYBETTİNİZ",
                                    Toast.LENGTH_SHORT
                                ).show()
                                txtHarf.visibility = View.INVISIBLE
                            }

                        }
                    }
                    txtViewKelime.text = gizliKelime


                }
            }
        }

        btnYonetim.setOnClickListener {
            val intent = Intent(this,MainActivity::class.java) //diğer sayfaya geçiş kodu
            startActivity(intent)
        }
    }

}