// package com.jaws.challengeappilham.data.dummy
//
// import com.jaws.challengeappilham.R
// import com.jaws.challengeappilham.model.Menu
//
// interface DummyMenuDataSource {
//    fun getMenuData() : List<Menu>
// }
//
// class DummyMenuDataSourceImpl() : DummyMenuDataSource {
//    override fun getMenuData(): List<Menu> {
//        return  mutableListOf(
//            Menu(
//                id = 1,
//                menuName = "Ayam Geprek",
//                menuPrice = 35000.0,
//                menuImg = "https://raw.githubusercontent.com/yudiatmoko/Asset_Challenge_App_Ilham/main/img_smashed_chicken.webp",
//                menuDesc = "Ayam geprek adalah hidangan dari potongan ayam goreng yang digeprek atau dihancurkan dengan ulekan, lalu dilumuri dengan saus sambal pedas."
//            ),
//            Menu(
//                id = 2,
//                menuName = "Ayam Goreng",
//                menuPrice = 40000.0,
//                menuImg = "https://raw.githubusercontent.com/yudiatmoko/Asset_Challenge_App_Ilham/main/img_fried_chicken.webp",
//                menuDesc = "Ayam goreng adalah hidangan ayam yang digoreng dalam minyak panas hingga kulitnya menjadi renyah dan dagingnya matang sempurna. Biasanya, ayam ini dibumbui dengan campuran rempah-rempah seperti garam, merica, kunyit, bawang putih, dan bawang merah untuk memberikan rasa yang gurih dan lezat."
//            ),
//            Menu(
//                id = 3,
//                menuName = "Ayam Bakar",
//                menuPrice = 50000.0,
//                menuImg = "https://raw.githubusercontent.com/yudiatmoko/Asset_Challenge_App_Ilham/main/img_grilled_chicken.webp",
//                menuDesc = "Ayam bakar adalah hidangan dari potongan ayam dipanggang atau dibakar hingga matang dan memiliki cita rasa manis, pedas, dan beraroma rempah."
//            ),
//            Menu(
//                id = 4,
//                menuName = "Sate Ayam",
//                menuPrice = 30000.0,
//                menuImg = "https://raw.githubusercontent.com/yudiatmoko/Asset_Challenge_App_Ilham/main/img_chicken_satay.webp",
//                menuDesc = "Sate ayam adalah hidangan yang terbuat dari potongan daging ayam yang ditusuk dengan tusukan bambu atau sate, kemudian dipanggang di atas bara api atau gril. Daging ayam biasanya sebelumnya telah dibumbui dengan campuran kecap manis, bawang putih, bawang merah, dan rempah-rempah lainnya."
//            ),
//            Menu(
//                id = 5,
//                menuName = "Kopi Cappuccino",
//                menuPrice = 25000.0,
//                menuImg = "https://raw.githubusercontent.com/yudiatmoko/Asset_Challenge_App_Ilham/main/img_cappuccino.webp",
//                menuDesc = "feugiat pretium nibh ipsum consequat nisl vel pretium lectus quam id leo in vitae turpis massa sed elementum tempus egestas sed sed risus pretium quam vulputate dignissim suspendisse in est ante in nibh mauris cursus mattis molestie a iaculis at erat pellentesque adipiscing commodo elit at imperdiet dui accumsan sit"
//            ),
//            Menu(
//                id = 6,
//                menuName = "Cheese Burger",
//                menuPrice = 38000.0,
//                menuImg = "https://raw.githubusercontent.com/yudiatmoko/Asset_Challenge_App_Ilham/main/img_cheese_burger.webp",
//                menuDesc = "feugiat pretium nibh ipsum consequat nisl vel pretium lectus quam id leo in vitae turpis massa sed elementum tempus egestas sed sed risus pretium quam vulputate dignissim suspendisse in est ante in nibh mauris cursus mattis molestie a iaculis at erat pellentesque adipiscing commodo elit at imperdiet dui accumsan sit"
//            ),
//            Menu(
//                id = 7,
//                menuName = "Kopi Latte",
//                menuPrice = 28000.0,
//                menuImg = "https://raw.githubusercontent.com/yudiatmoko/Asset_Challenge_App_Ilham/main/img_coffee_latte.webp",
//                menuDesc = "feugiat pretium nibh ipsum consequat nisl vel pretium lectus quam id leo in vitae turpis massa sed elementum tempus egestas sed sed risus pretium quam vulputate dignissim suspendisse in est ante in nibh mauris cursus mattis molestie a iaculis at erat pellentesque adipiscing commodo elit at imperdiet dui accumsan sit"
//            ),
//            Menu(
//                id = 8,
//                menuName = "Dim Sum",
//                menuPrice = 20000.0,
//                menuImg = "https://raw.githubusercontent.com/yudiatmoko/Asset_Challenge_App_Ilham/main/img_dimsum.webp",
//                menuDesc = "feugiat pretium nibh ipsum consequat nisl vel pretium lectus quam id leo in vitae turpis massa sed elementum tempus egestas sed sed risus pretium quam vulputate dignissim suspendisse in est ante in nibh mauris cursus mattis molestie a iaculis at erat pellentesque adipiscing commodo elit at imperdiet dui accumsan sit"
//            ),
//            Menu(
//                id = 9,
//                menuName = "Stik Kentang",
//                menuPrice = 15000.0,
//                menuImg = "https://raw.githubusercontent.com/yudiatmoko/Asset_Challenge_App_Ilham/main/img_french_fries.webp",
//                menuDesc = "feugiat pretium nibh ipsum consequat nisl vel pretium lectus quam id leo in vitae turpis massa sed elementum tempus egestas sed sed risus pretium quam vulputate dignissim suspendisse in est ante in nibh mauris cursus mattis molestie a iaculis at erat pellentesque adipiscing commodo elit at imperdiet dui accumsan sit"
//            ),
//            Menu(
//                id = 10,
//                menuName = "Spaghetti Original",
//                menuPrice = 40000.0,
//                menuImg = "https://raw.githubusercontent.com/yudiatmoko/Asset_Challenge_App_Ilham/main/img_spaghetti.webp",
//                menuDesc = "feugiat pretium nibh ipsum consequat nisl vel pretium lectus quam id leo in vitae turpis massa sed elementum tempus egestas sed sed risus pretium quam vulputate dignissim suspendisse in est ante in nibh mauris cursus mattis molestie a iaculis at erat pellentesque adipiscing commodo elit at imperdiet dui accumsan sit"
//            ),
//            Menu(
//                id = 11,
//                menuName = "Spaghetti Pedas",
//                menuPrice = 45000.0,
//                menuImg = "https://raw.githubusercontent.com/yudiatmoko/Asset_Challenge_App_Ilham/main/img_spicy_spaghetti.webp",
//                menuDesc = "feugiat pretium nibh ipsum consequat nisl vel pretium lectus quam id leo in vitae turpis massa sed elementum tempus egestas sed sed risus pretium quam vulputate dignissim suspendisse in est ante in nibh mauris cursus mattis molestie a iaculis at erat pellentesque adipiscing commodo elit at imperdiet dui accumsan sit"
//            ),
//            Menu(
//                id = 12,
//                menuName = "Xiao Long Bao",
//                menuPrice = 35000.0,
//                menuImg = "https://raw.githubusercontent.com/yudiatmoko/Asset_Challenge_App_Ilham/main/img_xiao_long_bao.webp",
//                menuDesc = "feugiat pretium nibh ipsum consequat nisl vel pretium lectus quam id leo in vitae turpis massa sed elementum tempus egestas sed sed risus pretium quam vulputate dignissim suspendisse in est ante in nibh mauris cursus mattis molestie a iaculis at erat pellentesque adipiscing commodo elit at imperdiet dui accumsan sit"
//            ),
//            Menu(
//                id = 13,
//                menuName = "Es Susu Stroberi",
//                menuPrice = 30000.0,
//                menuImg = "https://raw.githubusercontent.com/yudiatmoko/Asset_Challenge_App_Ilham/main/img_strawberry_milk.webp",
//                menuDesc = "feugiat pretium nibh ipsum consequat nisl vel pretium lectus quam id leo in vitae turpis massa sed elementum tempus egestas sed sed risus pretium quam vulputate dignissim suspendisse in est ante in nibh mauris cursus mattis molestie a iaculis at erat pellentesque adipiscing commodo elit at imperdiet dui accumsan sit"
//            ),
//        )
//    }
// }
