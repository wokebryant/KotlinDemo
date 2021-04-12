package com.example.kotlindemo.activity

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.FrameLayout
import android.widget.ListView
import android.widget.Toast
import androidx.core.os.CancellationSignal
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.kotlindemo.R
import com.example.kotlindemo.fragment.AnotherFragment
import kotlinx.android.synthetic.main.activity_first.*
import java.lang.StringBuilder
import kotlin.contracts.Returns

class FirstActivity : AppCompatActivity(), View.OnClickListener {

    var listViews : ListView? = null
    var fragmentContainer : FrameLayout? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_retrofit)
//        initView()
    }

    private fun initView() {
        button1.setOnClickListener {
            Log.i("SecondActivity", "You clicked button1")

            Toast.makeText(this, "You clicked button1", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, SecondActivity::class.java)

//            val intent = Intent("com.example.kotlindemo.ACTION_START")
//            intent.addCategory("com.example.kotlindemo.MY_CATEGORY")
            intent.putExtra("extra_data", "Hello world")

//            val intent = Intent()
//            intent.action = Intent.ACTION_VIEW
//            intent.data = Uri.parse("https://www.baidu.com")
//            startActivity(intent).

//            val dialIntent = Intent(Intent.ACTION_DIAL)
//            dialIntent.data = Uri.parse("tel:15600484760")
            startActivity(intent)
        }
        listViews = ListView(this)
            listViews?.setOnItemClickListener{parent, view, position, id ->

            }

        fragmentContainer = FrameLayout(this)
        replaceFragment(AnotherFragment())

    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.fragmentContainer, fragment)
        transaction.commit()
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.button2 ->
                AlertDialog.Builder(this).apply {
                    setTitle("This is Dialog")
                    setMessage("Something important")
                    setCancelable(true)
                    setPositiveButton("OK", {
                            dialog, which ->
                    })
                    setNegativeButton("Cancel"){
                        dialog, which -> Toast.makeText(this@FirstActivity, "取消提示框", Toast.LENGTH_SHORT).show()
                    }
                    show()
                }
        }
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.add_item -> Toast.makeText(this, "You clicked Add", Toast.LENGTH_SHORT).show()
            R.id.remove_item -> Toast.makeText(this, "You clicked remove", Toast.LENGTH_SHORT).show()
        }
        return true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode) {
            1 -> if (resultCode == Activity.RESULT_OK) {
                val returnData = data?.getStringExtra("data_return")
            }
        }
        val a = getRandomLengthString("apple")
    }

    private fun getRandomLengthString(str: String): String {
        val n = (1..20).random()
        val builder = StringBuilder()
        repeat(n) {
            builder.append(str)
        }
        return builder.toString()
    }

}
