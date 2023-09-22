package com.jaws.challengeappilham.data

import com.jaws.challengeappilham.R
import com.jaws.challengeappilham.model.Menu

interface MenuDataSource {
    fun getMenuData() : List<Menu>
}

class MenuDataSourceImpl() : MenuDataSource{
    override fun getMenuData(): List<Menu> {
        return  mutableListOf(
            Menu(
                menuName = "Ayam Geprek",
                menuPrice = 35000.0,
                menuImg = R.drawable.img_smashed_chicken,
                menuDesc = "Ayam geprek adalah hidangan dari potongan ayam goreng yang digeprek atau dihancurkan dengan ulekan, lalu dilumuri dengan saus sambal pedas."
            ),
            Menu(
                menuName = "Ayam Goreng",
                menuPrice = 40000.0,
                menuImg = R.drawable.img_fried_chicken,
                menuDesc = "Ayam goreng adalah hidangan ayam yang digoreng dalam minyak panas hingga kulitnya menjadi renyah dan dagingnya matang sempurna. Biasanya, ayam ini dibumbui dengan campuran rempah-rempah seperti garam, merica, kunyit, bawang putih, dan bawang merah untuk memberikan rasa yang gurih dan lezat."
            ),
            Menu(
                menuName = "Ayam Bakar",
                menuPrice = 50000.0,
                menuImg = R.drawable.img_grilled_chicken,
                menuDesc = "Ayam bakar adalah hidangan dari potongan ayam dipanggang atau dibakar hingga matang dan memiliki cita rasa manis, pedas, dan beraroma rempah."
            ),
            Menu(
                menuName = "Sate Ayam",
                menuPrice = 30000.0,
                menuImg = R.drawable.img_chicken_satay,
                menuDesc = "Sate ayam adalah hidangan yang terbuat dari potongan daging ayam yang ditusuk dengan tusukan bambu atau sate, kemudian dipanggang di atas bara api atau gril. Daging ayam biasanya sebelumnya telah dibumbui dengan campuran kecap manis, bawang putih, bawang merah, dan rempah-rempah lainnya."
            ),
            Menu(
                menuName = "Kopi Cappuccino",
                menuPrice = 25000.0,
                menuImg = R.drawable.img_cappuccino,
                menuDesc = "feugiat pretium nibh ipsum consequat nisl vel pretium lectus quam id leo in vitae turpis massa sed elementum tempus egestas sed sed risus pretium quam vulputate dignissim suspendisse in est ante in nibh mauris cursus mattis molestie a iaculis at erat pellentesque adipiscing commodo elit at imperdiet dui accumsan sit"
            ),
            Menu(
                menuName = "Cheese Burger",
                menuPrice = 38000.0,
                menuImg = R.drawable.img_cheese_burger,
                menuDesc = "feugiat pretium nibh ipsum consequat nisl vel pretium lectus quam id leo in vitae turpis massa sed elementum tempus egestas sed sed risus pretium quam vulputate dignissim suspendisse in est ante in nibh mauris cursus mattis molestie a iaculis at erat pellentesque adipiscing commodo elit at imperdiet dui accumsan sit"
            ),
            Menu(
                menuName = "Kopi Latte",
                menuPrice = 28000.0,
                menuImg = R.drawable.img_coffee_latte,
                menuDesc = "feugiat pretium nibh ipsum consequat nisl vel pretium lectus quam id leo in vitae turpis massa sed elementum tempus egestas sed sed risus pretium quam vulputate dignissim suspendisse in est ante in nibh mauris cursus mattis molestie a iaculis at erat pellentesque adipiscing commodo elit at imperdiet dui accumsan sit"
            ),
            Menu(
                menuName = "Dim Sum",
                menuPrice = 20000.0,
                menuImg = R.drawable.img_dimsum,
                menuDesc = "feugiat pretium nibh ipsum consequat nisl vel pretium lectus quam id leo in vitae turpis massa sed elementum tempus egestas sed sed risus pretium quam vulputate dignissim suspendisse in est ante in nibh mauris cursus mattis molestie a iaculis at erat pellentesque adipiscing commodo elit at imperdiet dui accumsan sit"
            ),
            Menu(
                menuName = "Stik Kentang",
                menuPrice = 15000.0,
                menuImg = R.drawable.img_french_fries,
                menuDesc = "feugiat pretium nibh ipsum consequat nisl vel pretium lectus quam id leo in vitae turpis massa sed elementum tempus egestas sed sed risus pretium quam vulputate dignissim suspendisse in est ante in nibh mauris cursus mattis molestie a iaculis at erat pellentesque adipiscing commodo elit at imperdiet dui accumsan sit"
            ),
            Menu(
                menuName = "Spaghetti Original",
                menuPrice = 40000.0,
                menuImg = R.drawable.img_spaghetti,
                menuDesc = "feugiat pretium nibh ipsum consequat nisl vel pretium lectus quam id leo in vitae turpis massa sed elementum tempus egestas sed sed risus pretium quam vulputate dignissim suspendisse in est ante in nibh mauris cursus mattis molestie a iaculis at erat pellentesque adipiscing commodo elit at imperdiet dui accumsan sit"
            ),
            Menu(
                menuName = "Spaghetti Pedas",
                menuPrice = 45000.0,
                menuImg = R.drawable.img_spicy_spaghetti,
                menuDesc = "feugiat pretium nibh ipsum consequat nisl vel pretium lectus quam id leo in vitae turpis massa sed elementum tempus egestas sed sed risus pretium quam vulputate dignissim suspendisse in est ante in nibh mauris cursus mattis molestie a iaculis at erat pellentesque adipiscing commodo elit at imperdiet dui accumsan sit"
            ),
            Menu(
                menuName = "Xiao Long Bao",
                menuPrice = 35000.0,
                menuImg = R.drawable.img_xiao_long_bao,
                menuDesc = "feugiat pretium nibh ipsum consequat nisl vel pretium lectus quam id leo in vitae turpis massa sed elementum tempus egestas sed sed risus pretium quam vulputate dignissim suspendisse in est ante in nibh mauris cursus mattis molestie a iaculis at erat pellentesque adipiscing commodo elit at imperdiet dui accumsan sit"
            ),
            Menu(
                menuName = "Es Susu Stroberi",
                menuPrice = 30000.0,
                menuImg = R.drawable.img_strawberry_milk,
                menuDesc = "feugiat pretium nibh ipsum consequat nisl vel pretium lectus quam id leo in vitae turpis massa sed elementum tempus egestas sed sed risus pretium quam vulputate dignissim suspendisse in est ante in nibh mauris cursus mattis molestie a iaculis at erat pellentesque adipiscing commodo elit at imperdiet dui accumsan sit"
            ),
        )
    }
}