package com.mykotiln

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import com.google.android.material.navigation.NavigationView
import com.mykotiln.activity.ActivityJava
import com.mykotiln.activity.ActivityKotlin
import com.mykotiln.activity.ActivityKotlinClass
import com.mykotiln.activity.ActivityKotlinUse
import kotlinx.android.synthetic.main.content_main.*


class KotlinMainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.kotlin_activity_main)

        initView()

    }

    internal fun onListItemClick(index: Int) {
        val intent: Intent
        /** context 使用 this@MainActivity  方式 */
        intent = Intent(this@KotlinMainActivity, DEMOS[index].demoClass)
        this.startActivity(intent)
    }


    /**内部类 内部类使用 inner 关键字来表示。 */
    inner class DemoInfo(
            var title: Int,
            var desc: Int,
            var demoClass: Class<out Activity>
    )

    private val DEMOS = arrayOf(
            DemoInfo(R.string.tab_item, R.string.tab_item_desc, ActivityKotlin::class.java),
            DemoInfo(R.string.tab_item_2, R.string.tab_item_2_desc, ActivityJava::class.java),
            DemoInfo(R.string.tab_item_3, R.string.tab_item_3_desc, ActivityKotlinClass::class.java),
            DemoInfo(R.string.tab_item_4, R.string.tab_item_4_desc, ActivityKotlinUse::class.java)
    )


    inner class DemoListAdapter : BaseAdapter() {

        override fun getView(index: Int, convertView: View?, parent: ViewGroup): View {
            var convertView = convertView
            val myViewHolder: MyViewHolder
            if (convertView == null) {
                convertView = View.inflate(this@KotlinMainActivity, R.layout.list_info_item, null)
                myViewHolder = MyViewHolder(convertView)
                convertView!!.tag = myViewHolder
            } else {
                myViewHolder = convertView.tag as MyViewHolder
            }
            myViewHolder.title!!.setText(DEMOS[index].title)
            myViewHolder.desc!!.setText(DEMOS[index].desc)
            if (index >= 25) {
                myViewHolder.title!!.setTextColor(Color.YELLOW)
            }
            return convertView
        }

        override fun getCount(): Int {
            return DEMOS.size
        }

        override fun getItem(index: Int): Any {
            return DEMOS[index]
        }

        override fun getItemId(id: Int): Long {
            return id.toLong()
        }

        internal inner class MyViewHolder(view: View) {

            var title: TextView? = null

            var desc: TextView? = null

            init {
                title = view.findViewById<TextView>(R.id.title) as TextView?
                desc = view.findViewById<TextView>(R.id.desc) as TextView?
            }
        }
    }


    fun initView() {
        listView_content.setAdapter(DemoListAdapter())
        listView_content.setOnItemClickListener(AdapterView.OnItemClickListener { arg0, v, index, arg3 -> onListItemClick(index) })

//        val toolbar = findViewById<android.widget.Toolbar>(R.id.toolbar)
//        setSupportActionBar(toolbar)

//        val fab = findViewById<FloatingActionButton>(R.id.fab)
//        fab.setOnClickListener { view ->
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                    .setAction("Action", null).show()
//        }
//
//        val drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
//        val toggle = ActionBarDrawerToggle(
//                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
//        drawer.setDrawerListener(toggle)
//        toggle.syncState()
//
//        val navigationView = findViewById<NavigationView>(R.id.nav_view)
//        navigationView.setNavigationItemSelectedListener(this)

    }

    override fun onBackPressed() {
        val drawer = findViewById<androidx.drawerlayout.widget.DrawerLayout>(R.id.drawer_layout)
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId


        if (id == R.id.action_settings) {
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        val id = item.itemId

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        val drawer = findViewById<androidx.drawerlayout.widget.DrawerLayout>(R.id.drawer_layout)
        drawer.closeDrawer(GravityCompat.START)
        return true
    }
}
