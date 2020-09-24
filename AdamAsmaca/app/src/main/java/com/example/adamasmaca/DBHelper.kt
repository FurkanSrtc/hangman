package com.example.adamasmaca

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast


class DBHelper(mainActivity: MainActivity) {
    class DBHelper(val context: Context) : SQLiteOpenHelper(context,DBHelper.DATABASE_NAME,null,DBHelper.DATABASE_VERSION) {
        private val TABLE_NAME="Kelimeler"  // KELİMELER TABLOSU OLUŞTURDUM
        private val COL_ID = "id"           //KELİMELER TABLOSUNA ID SÜTUNU EKLİCEM
        private val COL_KELIME = "kelime"     //KELİMELER TABLOSUNDA KELİMELERİ TUTACAĞIM

        companion object {
            private val DATABASE_NAME = "SQLITE_DATABASE"//database adı
            private val DATABASE_VERSION = 1
        }

        override fun onCreate(db: SQLiteDatabase?) {  //EĞER VERİTABANINDA 'Kelimeler' ADINDA TABLO YOKSA AŞAĞIDAKİ TABLO OLUŞTURMA KODU ÇALIŞACAK
            val createTable = "CREATE TABLE $TABLE_NAME ($COL_ID INTEGER PRIMARY KEY AUTOINCREMENT, $COL_KELIME  VARCHAR(256))"
            db?.execSQL(createTable)
        }

        override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        }

        fun KelimeEkle(kelime: Kelimeler){   //Veritabanına Ekleme Komutu
            val sqliteDB = this.writableDatabase
            val contentValues = ContentValues()
            contentValues.put(COL_KELIME , kelime.kelime) //Metoduma kelen kelime sınıfının içindeki kelimeyi sql ime kaydettim (id yi almadım çünkü otomatik artıyor)

            val result = sqliteDB.insert(TABLE_NAME,null,contentValues)

            Toast.makeText(context,if(result != -1L) "Kayıt Başarılı" else "Kayıt yapılamadı.", Toast.LENGTH_SHORT).show()
        }


        fun KelimeleriGetir():MutableList<Kelimeler>{  // veritabanındaki tüm kelimeleri çekme komutu
            val kelimeList = mutableListOf<Kelimeler>() //kelineleri bu listede tutuyorum
            val sqliteDB = this.readableDatabase
            val query = "SELECT * FROM $TABLE_NAME"
            val result = sqliteDB.rawQuery(query,null)
            if(result.moveToFirst()){
                do {
                    val kelime = Kelimeler()
                    kelime.id = result.getString(result.getColumnIndex(COL_ID)).toInt()
                    kelime.kelime = result.getString(result.getColumnIndex(COL_KELIME))
                    kelimeList.add(kelime)
                }while (result.moveToNext())
            }
            result.close()
            sqliteDB.close()
            return kelimeList
        }



        fun KelimeleriSil(){  //Tüm verileri silme komutu
            val sqliteDB = this.writableDatabase
            sqliteDB.delete(TABLE_NAME,null,null)
            sqliteDB.close()

        }
    }
}