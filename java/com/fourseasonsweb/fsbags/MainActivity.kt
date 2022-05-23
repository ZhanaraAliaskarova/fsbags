package com.fourseasonsweb.fsbags

import android.arch.persistence.room.Room
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.TypedValue
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import com.fourseasonsweb.fsbags.adapter.ProductsAdapter
import com.fourseasonsweb.fsbags.data.Product
import com.fourseasonsweb.fsbags.data.room.BagsDatabase
import com.fourseasonsweb.fsbags.data.room.models.ProductEntity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    //Статический объект для того чтобы была возможность получить доступ из других активити
    companion object {
        var database: BagsDatabase? = null
        var userName: String? = ""
        var FLName: String? =""
    }

    private var recyclerView: RecyclerView? = null
    private var adapter: ProductsAdapter? = null //Адаптер для списка товара
    private var productList: MutableList<Product> = ArrayList() //список товара

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            val intent = Intent(this, ShoppingCartActivity::class.java)
            startActivity(intent)
        }

        nav_view.setNavigationItemSelectedListener(this)
        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        init()
    }

    /*
    Инициализация необходимых элементов:
    Создание базы данных
    Инициализация RecyclerView
    Адаптера для RecyclerView
     */
    private fun init() {
        MainActivity.database =  Room.databaseBuilder(this,BagsDatabase::class.java,"bagsDatabase")
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()

        recyclerView = findViewById(R.id.recycler_view) as RecyclerView

        productList = ArrayList()
        adapter = ProductsAdapter(this, productList)

        val mLayoutManager = GridLayoutManager(this, 2)
        recyclerView!!.layoutManager = mLayoutManager
        recyclerView!!.addItemDecoration(GridSpacingItemDecoration(2, dpToPx(10), true))
        recyclerView!!.itemAnimator = DefaultItemAnimator()
        recyclerView!!.adapter = adapter

        prepareProducts()
        setHeaderInfo(FLName ?: "")
    }

    /*
    Установить текст на боковом меню (Имя пользователя)
     */
    private fun setHeaderInfo(name: String) {
        val navigationView = findViewById(R.id.nav_view) as NavigationView
        val hView = navigationView.getHeaderView(0)
        val navUser = hView.findViewById(R.id.tv_headerInfo) as TextView
        navUser.text = name
    }

    private fun dpToPx(dp: Int): Int {
        val r = resources
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp.toFloat(), r.displayMetrics))
    }

    /*
    Подготовить список товара
    Сохраняем в базу список товара - наименование, описание, цены, и фотографии
    Оповещаем адаптер о новых данных
     */
    private fun prepareProducts() {
        val covers = intArrayOf(R.drawable.ic_prada,
            R.drawable.ic_prada_2,
            R.drawable.ic_miumiu_1,
            R.drawable.ic_miumiu_2,
            R.drawable.ic_alex_1,
            R.drawable.ic_chanel_1,
            R.drawable.ic_chanel_2,
            R.drawable.ic_chole_1,
            R.drawable.ic_chloe_2,
            R.drawable.ic_giv_1,
            R.drawable.ic_giv_2,
            R.drawable.ic_gucci_1,
            R.drawable.ic_gucci_2,
            R.drawable.ic_hermes_1,
            R.drawable.ic_hermes_2,
            R.drawable.ic_hermes_3,
            R.drawable.ic_hermes_4,
            R.drawable.ic_mal_1,
            R.drawable.ic_mal_2,
            R.drawable.ic_stella_1,
            R.drawable.ic_stella_2,
            R.drawable.ic_val_1,
            R.drawable.ic_val_2)

        MainActivity.database!!.productDao().insert(ProductEntity(1, "Prada Milano", "Новинка!\uD83D\uDE0D\n" +
                "Стильная и удобная женская сумка среднего размера. Держит форму. Имеет три отделения, центральное закрывается на молнию, боковые на магнитных кнопках. Внутри центрального отделения карман на молнии и два открытых кармана для телефона и мелких вещей. Подкладка - шелковая, с фирменным логотипом. Снаружи на обратной стороне сумки карман на молнии. Носится на сгибе руки. Высота ручек - 7 см. В комплекте регулируемый плечевой ремень. На дне есть металлические ножки.❤", 10.0, covers[0]))
        MainActivity.database!!.productDao().insert(ProductEntity(2, "Prada", "Хочешь поднять всему миру настроение? \n" +
                "Смело сочетай фисташковый цвет с остальными оттенками гардероба! \n" +
                "Фисташковый цвет- очень неизбитый, поэтому его сложно встретить в масс-маркетах, за то обязательно увидишь у нас, в @four_seasons_official ❤️\n" +
                "Нежный оттенок, качественная фурнитура! \uD83C\uDF48\n" +
                "Идеальная модель на тёплую весну!", 10.0, covers[1]))
        MainActivity.database!!.productDao().insert(ProductEntity(3, "Alex", "Маленькая красная сумочка – символ изысканности и элегантности для многих красавиц. Компактность модели позволяет вмещать необходимые мелкие предметы на торжестве или легкой прогулке по парку: ключи от квартиры, кошелек, кредитные карты❤", 10.0, covers[2]))
        MainActivity.database!!.productDao().insert(ProductEntity(4, "Chanel", "Строгие и одновременно милые, элегантные и самую малость забавные, круглые сумочки самых разных фасонов покорили модниц всего мира. ", 10.0, covers[3]))
        MainActivity.database!!.productDao().insert(ProductEntity(5, "Chanel", "Нежные новинки на Весну!!! \uD83D\uDE0D\uD83D\uDE0D\uD83D\uDE0D\uD83D\uDE0D\n" +
                "Турция!\uD83C\uDDF9\uD83C\uDDF7\n" +
                "Натуральная кожа\uD83D\uDE0D\n" +
                "Отличная подкладочная ткань\uD83D\uDE31\uD83D\uDE0D\n" +
                "Стильная, компактная и очень нежная модель украсит гардероб каждой красавицы\uD83D\uDE0F❤️", 10.0, covers[4]))
        MainActivity.database!!.productDao().insert(ProductEntity(6, "Chloe", "Яркая модель\uD83D\uDE0D\n" +
                "Насыщенный фиолетовый цвет- отлично дополнит и впишется в Ваш гардероб, подчеркнёт все достоинства образа\uD83D\uDC9C", 10.0, covers[5]))
        MainActivity.database!!.productDao().insert(ProductEntity(7, "Chloe", "Для посещения вечерних и светских мероприятий девушки и женщины с особой тщательностью продумывают свой образ и, в том числе, подбирают наряд и аксессуары. Нередко выбор представительниц прекрасного пола падает на синий клатч, который выглядит роскошно и изысканно. ", 10.0, covers[6]))
        MainActivity.database!!.productDao().insert(ProductEntity(8, "Givenchy", "Многофункциональное портмоне Givenchy\uD83D\uDE0D\n" +
                "Из натуральной кожи. 4 отделения для купюр. Кармашки для банковских карт или визиток. Одно отделение на молнии. Ремешок (темляк) в комплекте. Упакован в картонную коробку. Имеется пыльник\uD83D\uDC4C\uD83C\uDFFC\n" +
                "Модель унисекс\uD83D\uDE0D\n" +
                "Отлично подойдёт в качестве подарка! Успейте приобрести перед предстоящими праздниками\uD83D\uDE0D♥️\n", 10.0, covers[7]))
        MainActivity.database!!.productDao().insert(ProductEntity(9, "Givenchy", "Комбинированная модель (натуральная кожа в лаковом покрытии, натуральная замша) \n" +
                "Оригинальное цветовое сочетание♥️\n" +
                "Качественная фурнитура\uD83D\uDE0D\n" +
                "Плечевой ремешок✅\n" +
                "Удобство и элегантность\uD83D\uDC60", 10.0, covers[8]))
        MainActivity.database!!.productDao().insert(ProductEntity(10, "Gucci", "Красная сумка является одним из лучших аксессуаров, способных дополнить самые разнообразные образы. В 2019 году подобные сумки заняли почетное место в показах многих дизайнеров. Впрочем, красная сумка может подчеркнуть достоинства, преимущества образа и сделать его более ярким❤️", 10.0, covers[9]))
        MainActivity.database!!.productDao().insert(ProductEntity(11, "Gucci", "Стильная✅\n" +
                "Вместительная✅\n" +
                "Качество✅\n" +
                "Удобная и объемная дорожная сумка из водонепроницаемого материала\uD83D\uDE0D \n" +
                "Цвет: тёмно-зеленый❤️ ", 10.0, covers[10]))
        MainActivity.database!!.productDao().insert(ProductEntity(12, "Hermes", "Стильный и вместительный рюкзак из кожи отлично подойдет, как для городского использования, так и для занятий спортом. Удобные лямки, уплотненная спинка, внешний карман на молнии.", 10.0, covers[11]))
        MainActivity.database!!.productDao().insert(ProductEntity(13, "Hermes", "Новинка!❤️\n" +
                "Хищный принт\uD83D\uDC06\n" +
                "Для дерзких и ярких образов!\n" +
                "Качество-\uD83D\uDE0D ", 10.0, covers[12]))
        MainActivity.database!!.productDao().insert(ProductEntity(14, "Hermes", "Поясная сумка “Bright life”\uD83D\uDE0D\n" +
                "——————————————————————————\n" +
                "Яркая поясная сумка с пайетками. Один объемный отдел. Есть внешний карман для мелочи. Закрывается на замок-молнию. Есть ремешок, который регулируется по длине.\n", 10.0, covers[13]))
        MainActivity.database!!.productDao().insert(ProductEntity(15, "Hermes", "Новинка!\uD83D\uDE0D\n" +
                "Очень элегантная и нежная модель ❤️ ——————————————————————————\n" +
                "Действует дисконтная система, подробности на кассе\uD83D\uDCB3 ——————————————————————————\n", 10.0, covers[14]))
        MainActivity.database!!.productDao().insert(ProductEntity(16, "Mulberry", "Прекрасная модель\uD83D\uDE0D\n" +
                "Женская кожаная сумка на подкладке. Внутри карман на молнии. Закрывается на молнию. Две ручки средней длины, модель ребристая (см.фото) \n" +
                "Фурнитура отличного качества, золотистого цвета. ", 10.0, covers[15]))
        MainActivity.database!!.productDao().insert(ProductEntity(17, "Mulberry", "Наряду с синим, зеленый цвет – один из самых модных в этом сезоне. \uD83C\uDF3F\n" +
                "—————————————————————————— Это один из любимейших природных цветов в психологии – он успокаивает, дарит хорошее настроение и заряжает энергией на целый день\uD83E\uDD38\uD83C\uDFFB\u200D♂️ —————————————————————————— А если его еще и использовать в аксессуарах, то можно получить прекрасный свежий образ. ❄️\n" +
                "Зеленая сумка – как раз та вещь, которая поможет вам всегда выглядеть стильно и элегантно.✅ ", 10.0, covers[16]))
        MainActivity.database!!.productDao().insert(ProductEntity(18, "Stella", "Правильно подобранная сумочка с лёгкостью подчеркнёт вашу индивидуальность и неповторимый характер. Однако не секрет, что всё в вашем образе должно быть гармоничным и сочетаемым, но, к сожалению, это нереально при наличии лишь одной сумки.\n" +
                "\n" +
                "Отличное качество, стильный дизайн, практичность, цветовое решение и приемлемая стоимость \uD83D\uDE0D❤️ ", 10.0, covers[17]))
        MainActivity.database!!.productDao().insert(ProductEntity(19, "Stella", "Дорожная сумка в современной жизни необходима практически каждому человеку. Ведь часто мы путешествуем, ездим в командировки и в гости к дальним родственникам. И, конечно, большинство покупателей хотят приобрести сумку именно на колесах, ведь это намного удобней и практичней. ——————————————————————————\n" +
                "Данная модель из водонепроницаемого материала, дно жесткое и не поддающееся деформации, а так же имеет телескопическая ручку — еще одна важная составляющая всех сумок на колесах. Она беспрепятственно фиксируется в двух положениях – выдвинутом и закрытом.", 10.0, covers[18]))
        MainActivity.database!!.productDao().insert(ProductEntity(20, "Valentino", "Сумочки от известного во всем мире дома моды Chloe каждый год на неделях моды вызывают настоящий фурор. Они считаются яркими образцами моды и стиля, многие начинающие кутюрье ориентируются по трендам, создаваемым сумками под этим лейблом.✅ ", 10.0, covers[19]))
        MainActivity.database!!.productDao().insert(ProductEntity(21, "Valentino", "Модным вариантом является обработка материала лазером. Он сразу же приобретает новую структуру и смотрится необычно. Для этого при производстве сумок буквально «выжигаются» узоры, рисунки. Можно сделать единственный в своём роде именной аксессуар на заказ. Такая деталь лука – новый тренд в мире моды. Она требует к себе внимания и несёт в себе практичность❤️ ", 10.0, covers[20]))

        for (item in MainActivity.database!!.productDao().getAllProducts())
        {
            productList.add(Product(item.Id, item.Name, item.Description, item.Image, item.price))
        }

        adapter!!.notifyDataSetChanged()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    /*
    Устанавливаем события на меню правое верхнее
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            //Событие на кнопку settings
            R.id.action_settings -> {
                true
            }
            //Событие на кнопку logout
            R.id.action_logout -> {
                LoginActivity.mGoogleSignInClient!!.signOut()
                    .addOnCompleteListener(this) {
                        val intent = Intent(this, LoginActivity::class.java)
                        finish()
                        startActivity(intent)
                    }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    /*
    Устанавливаем события на левое боковое меню
     */
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            //Событие для кнопки Shopping cart
            R.id.nav_addcard -> {
                val intent = Intent(this, ShoppingCartActivity::class.java)
                startActivity(intent)
            }

            R.id.nav_share -> {

            }
            R.id.nav_send -> {

            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
}
