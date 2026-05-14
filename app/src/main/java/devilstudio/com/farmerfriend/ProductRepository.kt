package devilstudio.com.farmerfriend

object ProductRepository {

    private data class ProductPlan(
        val diseaseName: String,
        val productBaseName: String,
        val diseaseType: String
    )

    fun getAllProducts(): ArrayList<Product> {
        val products = ArrayList<Product>()

        val productPlans = listOf(
            ProductPlan("Elma - Kara Leke", "Elma Kara Leke", "fungal"),
            ProductPlan("Elma - Siyah Çürüklük", "Elma Siyah Çürüklük", "fungal"),
            ProductPlan("Elma - Sedir Elma Pası", "Elma Pas", "fungal"),
            ProductPlan("Elma - Sağlıklı", "Elma Sağlıklı Bakım", "healthy"),

            ProductPlan("Yaban Mersini - Sağlıklı", "Yaban Mersini Sağlıklı Bakım", "healthy"),

            ProductPlan("Kiraz - Külleme", "Kiraz Külleme", "fungal"),
            ProductPlan("Kiraz - Sağlıklı", "Kiraz Sağlıklı Bakım", "healthy"),

            ProductPlan("Üzüm - Siyah Çürüklük", "Üzüm Siyah Çürüklük", "fungal"),
            ProductPlan("Üzüm - Siyah Benek", "Üzüm Siyah Benek", "fungal"),
            ProductPlan("Üzüm - Yaprak Yanıklığı", "Üzüm Yaprak Yanıklığı", "fungal"),
            ProductPlan("Üzüm - Sağlıklı", "Üzüm Sağlıklı Bakım", "healthy"),

            ProductPlan("Portakal - Turunçgil Yeşillenme", "Portakal Yeşillenme", "bacterial"),

            ProductPlan("Şeftali - Bakteriyel Leke", "Şeftali Bakteriyel Leke", "bacterial"),
            ProductPlan("Şeftali - Sağlıklı", "Şeftali Sağlıklı Bakım", "healthy"),

            ProductPlan("Biber - Bakteriyel Leke", "Biber Bakteriyel Leke", "bacterial"),
            ProductPlan("Biber - Sağlıklı", "Biber Sağlıklı Bakım", "healthy"),

            ProductPlan("Patates - Erken Yanıklık", "Patates Erken Yanıklık", "fungal"),
            ProductPlan("Patates - Geç Yanıklık", "Patates Geç Yanıklık", "fungal"),
            ProductPlan("Patates - Sağlıklı", "Patates Sağlıklı Bakım", "healthy"),

            ProductPlan("Ahududu - Sağlıklı", "Ahududu Sağlıklı Bakım", "healthy"),
            ProductPlan("Soya Fasulyesi - Sağlıklı", "Soya Sağlıklı Bakım", "healthy"),

            ProductPlan("Kabak - Külleme", "Kabak Külleme", "fungal"),

            ProductPlan("Çilek - Yaprak Yanıklığı", "Çilek Yaprak Yanıklığı", "fungal"),
            ProductPlan("Çilek - Sağlıklı", "Çilek Sağlıklı Bakım", "healthy"),

            ProductPlan("Domates - Bakteriyel Leke", "Domates Bakteriyel Leke", "bacterial"),
            ProductPlan("Domates - Erken Yanıklık", "Domates Erken Yanıklık", "fungal"),
            ProductPlan("Domates - Geç Yanıklık", "Domates Geç Yanıklık", "fungal"),
            ProductPlan("Domates - Yaprak Küfü", "Domates Yaprak Küfü", "fungal"),
            ProductPlan("Domates - Septorya Yaprak Lekesi", "Domates Septorya", "fungal"),
            ProductPlan("Domates - İki Noktalı Kırmızı Örümcek", "Domates Kırmızı Örümcek", "pest"),
            ProductPlan("Domates - Hedef Leke", "Domates Hedef Leke", "fungal"),
            ProductPlan("Domates - Sarı Yaprak Kıvırcıklık Virüsü", "Domates Sarı Yaprak Virüsü", "viral"),
            ProductPlan("Domates - Mozaik Virüsü", "Domates Mozaik Virüsü", "viral"),
            ProductPlan("Domates - Sağlıklı", "Domates Sağlıklı Bakım", "healthy")
        )

        var id = 1

        for (plan in productPlans) {
            when (plan.diseaseType) {
                "fungal" -> {
                    products.add(
                        createProduct(
                            id++,
                            "${plan.productBaseName} Fungisit Plus",
                            "Fungisit",
                            260,
                            plan.diseaseName,
                            "${plan.diseaseName} için mantari hastalık yönetimine destek amaçlı önerilen demo üründür.",
                            "Yaprak lekesi, yanıklık, çürüklük, pas ve külleme gibi mantari hastalıklar için ürün öneri modülünde gösterilir."
                        )
                    )

                    products.add(
                        createProduct(
                            id++,
                            "${plan.productBaseName} Yaprak Gübresi",
                            "Yaprak Gübresi",
                            165,
                            plan.diseaseName,
                            "Hastalık sonrası bitkinin toparlanmasına ve yaprak gelişimine destek olur.",
                            "Bitkinin besin ihtiyacını desteklemek ve hastalık sonrası gelişimi güçlendirmek amacıyla önerilir."
                        )
                    )

                    products.add(
                        createProduct(
                            id++,
                            "${plan.productBaseName} Organik Destek",
                            "Organik Ürün",
                            190,
                            plan.diseaseName,
                            "Doğal içerikli destek ürünü olarak önerilir.",
                            "Organik üretim tercih eden kullanıcılar için destekleyici ürün olarak gösterilir."
                        )
                    )
                }

                "bacterial" -> {
                    products.add(
                        createProduct(
                            id++,
                            "${plan.productBaseName} Bakterisit Plus",
                            "Bakterisit",
                            285,
                            plan.diseaseName,
                            "${plan.diseaseName} için bakteriyel hastalık yönetimine destek amaçlı önerilen demo üründür.",
                            "Bakteriyel leke ve benzeri hastalık risklerinde ürün öneri modülünde gösterilir."
                        )
                    )

                    products.add(
                        createProduct(
                            id++,
                            "${plan.productBaseName} Bakırlı Destek",
                            "Bakterisit",
                            310,
                            plan.diseaseName,
                            "Bakteriyel hastalıklara karşı koruyucu destek amaçlı önerilir.",
                            "Proje kapsamında örnek bakterisit ürün olarak gösterilmektedir."
                        )
                    )

                    products.add(
                        createProduct(
                            id++,
                            "${plan.productBaseName} Yaprak Gübresi",
                            "Yaprak Gübresi",
                            175,
                            plan.diseaseName,
                            "Hastalık sonrası bitkinin toparlanmasına destek olur.",
                            "Bitkinin güçlenmesi ve yeni yaprak gelişiminin desteklenmesi için önerilir."
                        )
                    )
                }

                "viral" -> {
                    products.add(
                        createProduct(
                            id++,
                            "${plan.productBaseName} Bitki Direnci Desteği",
                            "Yaprak Gübresi",
                            180,
                            plan.diseaseName,
                            "Virüs kaynaklı stres sonrası bitki direncini desteklemek için önerilen demo üründür.",
                            "Virüs hastalıklarında doğrudan tedavi yerine bitkiyi güçlendirme ve zararlı kontrolü önemlidir."
                        )
                    )

                    products.add(
                        createProduct(
                            id++,
                            "${plan.productBaseName} Organik Koruma",
                            "Organik Ürün",
                            205,
                            plan.diseaseName,
                            "Destekleyici ve doğal bakım ürünü olarak önerilir.",
                            "Yaprak sağlığını desteklemek ve bitkinin stres toleransını artırmak amacıyla gösterilir."
                        )
                    )

                    products.add(
                        createProduct(
                            id++,
                            "${plan.productBaseName} Genel Bakım Seti",
                            "Organik Ürün",
                            230,
                            plan.diseaseName,
                            "Bitki bakım ve koruma sürecini destekleyen demo üründür.",
                            "Virüs belirtileri görüldüğünde uzman desteği alınmalı, taşıyıcı zararlılarla mücadele edilmelidir."
                        )
                    )
                }

                "pest" -> {
                    products.add(
                        createProduct(
                            id++,
                            "${plan.productBaseName} Organik Mücadele",
                            "Organik Ürün",
                            220,
                            plan.diseaseName,
                            "Zararlı kaynaklı yaprak hasarlarında doğal destek ürünü olarak önerilir.",
                            "Kırmızı örümcek gibi zararlılarda bitki sağlığını desteklemek için gösterilen demo üründür."
                        )
                    )

                    products.add(
                        createProduct(
                            id++,
                            "${plan.productBaseName} Yaprak Güçlendirici",
                            "Yaprak Gübresi",
                            170,
                            plan.diseaseName,
                            "Zararlı sonrası yaprak gelişimini desteklemek için önerilir.",
                            "Bitkinin toparlanma sürecine destek olmak amacıyla gösterilir."
                        )
                    )

                    products.add(
                        createProduct(
                            id++,
                            "${plan.productBaseName} Doğal Bakım Spreyi",
                            "Organik Ürün",
                            195,
                            plan.diseaseName,
                            "Doğal bakım ve destek amacıyla önerilir.",
                            "Zararlı hasarı sonrası bakım için örnek ürün olarak gösterilmektedir."
                        )
                    )
                }

                "healthy" -> {
                    products.add(
                        createProduct(
                            id++,
                            "${plan.productBaseName} Yaprak Gübresi",
                            "Yaprak Gübresi",
                            145,
                            plan.diseaseName,
                            "Sağlıklı bitkilerde gelişimi ve yaprak kalitesini desteklemek için önerilir.",
                            "Bitki besleme ve düzenli bakım amacıyla gösterilen demo üründür."
                        )
                    )

                    products.add(
                        createProduct(
                            id++,
                            "${plan.productBaseName} Organik Bakım",
                            "Organik Ürün",
                            165,
                            plan.diseaseName,
                            "Sağlıklı bitkiler için doğal bakım desteği sağlar.",
                            "Koruyucu bakım ve düzenli bitki sağlığı desteği için önerilir."
                        )
                    )

                    products.add(
                        createProduct(
                            id++,
                            "${plan.productBaseName} Koruyucu Destek",
                            "Organik Ürün",
                            185,
                            plan.diseaseName,
                            "Bitkinin sağlıklı gelişimini sürdürmesine yardımcı olur.",
                            "Hastalık belirtisi olmayan bitkilerde bakım ve destek amacıyla gösterilir."
                        )
                    )
                }
            }
        }

        return products
    }

    private fun createProduct(
        id: Int,
        name: String,
        category: String,
        basePrice: Int,
        diseaseName: String,
        purpose: String,
        detail: String
    ): Product {
        val finalPrice = basePrice + ((id % 7) * 15)
        val sellerId = ((id - 1) % 10) + 1

        return Product(
            id = id,
            name = name,
            category = category,
            price = "$finalPrice TL",
            diseaseName = diseaseName,
            purpose = purpose,
            detail = "$detail Gerçek kullanım için ürün etiketi, ruhsat bilgisi ve ziraat uzmanı önerisi dikkate alınmalıdır.",
            imageResId = getImageByCategory(category),
            sellerId = sellerId
        )
    }

    private fun getImageByCategory(category: String): Int {
        return when (category) {
            "Fungisit" -> R.drawable.product_fungicide
            "Bakterisit" -> R.drawable.product_bactericide
            "Yaprak Gübresi" -> R.drawable.product_fertilizer
            "Organik Ürün" -> R.drawable.product_organic
            else -> R.drawable.product_organic
        }
    }
}