package com.mykotiln

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.view.View
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.BaseAdapter
import android.widget.TextView
import com.mykotiln.activity.KotlinActivity
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()

    }

    internal fun onListItemClick(index: Int) {
        val intent: Intent
        /** context 使用 this@MainActivity  方式 */
        intent = Intent( this@MainActivity  , DEMOS[index].demoClass )
        this.startActivity(intent)
    }


    /**内部类 内部类使用 inner 关键字来表示。 */
    inner class DemoInfo(
    var title: Int ,
    var desc: Int ,
    var demoClass: Class<out Activity>
    )

    private val DEMOS = arrayOf(
        DemoInfo(R.string.tab_item, R.string.tab_item_desc, KotlinActivity::class.java)
    )


    inner class DemoListAdapter : BaseAdapter() {

        override fun getView(index: Int, convertView: View?, parent: ViewGroup): View {
            var convertView = convertView
            val myViewHolder: MyViewHolder
            if (convertView == null) {
                convertView = View.inflate(this@MainActivity , R.layout.list_info_item, null)
                myViewHolder = MyViewHolder(convertView)
                convertView!!.tag = myViewHolder
            } else {
                myViewHolder = convertView.tag as MyViewHolder
            }
            myViewHolder.title!!.setText(DEMOS[index].title )
            myViewHolder.desc!!.setText(DEMOS[index].desc )
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
                title = view.findViewById(R.id.title) as TextView?
                desc = view.findViewById(R.id.desc) as TextView?
            }
        }
    }



    fun initView(){
        listView_content.setAdapter(DemoListAdapter())
        listView_content.setOnItemClickListener(AdapterView.OnItemClickListener { arg0, v, index, arg3 -> onListItemClick(index) })

        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        val fab = findViewById(R.id.fab) as FloatingActionButton
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        val drawer = findViewById(R.id.drawer_layout) as DrawerLayout
        val toggle = ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer.setDrawerListener(toggle)
        toggle.syncState()

        val navigationView = findViewById(R.id.nav_view) as NavigationView
        navigationView.setNavigationItemSelectedListener(this)

    }

    override fun onBackPressed() {
        val drawer = findViewById(R.id.drawer_layout) as DrawerLayout
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

        val drawer = findViewById(R.id.drawer_layout) as DrawerLayout
        drawer.closeDrawer(GravityCompat.START)
        return true
    }
}
