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
        )
    }
}