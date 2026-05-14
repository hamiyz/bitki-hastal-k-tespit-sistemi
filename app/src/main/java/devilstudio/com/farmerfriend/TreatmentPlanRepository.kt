package devilstudio.com.farmerfriend

object TreatmentPlanRepository {

    fun getTreatmentPlan(diseaseName: String): TreatmentPlan {
        return when {
            diseaseName.contains("Sağlıklı", ignoreCase = true) -> healthyPlan(diseaseName)

            diseaseName.contains("Bakteriyel", ignoreCase = true) ||
                    diseaseName.contains("Turunçgil Yeşillenme", ignoreCase = true) -> bacterialPlan(diseaseName)

            diseaseName.contains("Virüs", ignoreCase = true) -> viralPlan(diseaseName)

            diseaseName.contains("Kırmızı Örümcek", ignoreCase = true) -> pestPlan(diseaseName)

            diseaseName.contains("Külleme", ignoreCase = true) ||
                    diseaseName.contains("Yanıklık", ignoreCase = true) ||
                    diseaseName.contains("Çürüklük", ignoreCase = true) ||
                    diseaseName.contains("Kara Leke", ignoreCase = true) ||
                    diseaseName.contains("Pas", ignoreCase = true) ||
                    diseaseName.contains("Yaprak Küfü", ignoreCase = true) ||
                    diseaseName.contains("Septorya", ignoreCase = true) ||
                    diseaseName.contains("Hedef Leke", ignoreCase = true) ||
                    diseaseName.contains("Siyah Benek", ignoreCase = true) -> fungalPlan(diseaseName)

            else -> generalPlan(diseaseName)
        }
    }

    private fun fungalPlan(diseaseName: String): TreatmentPlan {
        val steps = arrayListOf(
            "Hastalıklı yaprakları ve bitki artıklarını ortamdan uzaklaştırın.",
            "Bitkinin hava almasını sağlayın, çok sık dikim veya yoğun yapraklanma varsa azaltın.",
            "Sulamayı yaprak üzerine değil, mümkünse kök bölgesine yapın.",
            "Ürün önerileri ekranından ilgili fungisit ve destek ürünlerini inceleyin.",
            "Hastalığın yayılımını 3-5 gün aralıklarla gözlemleyin.",
            "Belirtiler artıyorsa ziraat uzmanından destek alın."
        )

        return TreatmentPlan(
            diseaseName = diseaseName,
            riskLevel = "Orta / Yüksek",
            summary = "$diseaseName mantari kaynaklı bir hastalık olabilir. Erken müdahale edilmezse yaprak, gövde veya meyvede yayılım gösterebilir.",
            steps = steps
        )
    }

    private fun bacterialPlan(diseaseName: String): TreatmentPlan {
        val steps = arrayListOf(
            "Hastalıklı yaprakları ve enfekte olmuş bitki kısımlarını temizleyin.",
            "Budama veya temas sonrası kullanılan ekipmanları dezenfekte edin.",
            "Yaprakların uzun süre ıslak kalmasını önleyin.",
            "Ürün önerileri ekranından bakterisit ve yaprak destek ürünlerini inceleyin.",
            "Aynı alanda hastalık tekrar ediyorsa kayıt oluşturup bölgesel takibi kontrol edin.",
            "Şiddetli belirtilerde ziraat uzmanına danışın."
        )

        return TreatmentPlan(
            diseaseName = diseaseName,
            riskLevel = "Yüksek",
            summary = "$diseaseName bakteriyel kaynaklı olabilir. Bakteriyel hastalıklarda hijyen, erken temizlik ve koruyucu uygulamalar önemlidir.",
            steps = steps
        )
    }

    private fun viralPlan(diseaseName: String): TreatmentPlan {
        val steps = arrayListOf(
            "Virüs belirtisi görülen bitkiyi diğer bitkilerden ayırın.",
            "Yaprak biti, beyaz sinek gibi taşıyıcı zararlıları kontrol edin.",
            "Enfekte bitkilerde doğrudan tedavi sınırlı olduğu için yayılımı engellemeye odaklanın.",
            "Bitki direncini artıracak yaprak gübresi ve organik destek ürünlerini inceleyin.",
            "Aynı bölgede tekrar görülüyorsa bölgesel kayıt oluşturun.",
            "Kesin teşhis ve mücadele için ziraat uzmanından destek alın."
        )

        return TreatmentPlan(
            diseaseName = diseaseName,
            riskLevel = "Yüksek",
            summary = "$diseaseName virüs kaynaklı olabilir. Virüs hastalıklarında asıl amaç yayılımı önlemek ve bitkiyi desteklemektir.",
            steps = steps
        )
    }

    private fun pestPlan(diseaseName: String): TreatmentPlan {
        val steps = arrayListOf(
            "Yaprakların alt yüzeyini kontrol edin.",
            "Zararlı yoğunluğu az ise bitkiyi izole edip yaprak temizliği yapın.",
            "Doğal/organik destek ürünlerini ve yaprak güçlendiricileri inceleyin.",
            "Bitkide stres belirtileri varsa düzenli sulama ve besleme sağlayın.",
            "Yayılım devam ederse uzman desteği alın.",
            "Bölgesel kayıt oluşturarak zararlı yoğunluğunu takip edin."
        )

        return TreatmentPlan(
            diseaseName = diseaseName,
            riskLevel = "Orta",
            summary = "$diseaseName zararlı kaynaklı bir problem olabilir. Erken kontrol edilirse yayılım azaltılabilir.",
            steps = steps
        )
    }

    private fun healthyPlan(diseaseName: String): TreatmentPlan {
        val steps = arrayListOf(
            "Bitki sağlıklı görünüyor, düzenli bakım yapmaya devam edin.",
            "Yaprakları haftalık olarak kontrol edin.",
            "Aşırı sulamadan kaçının.",
            "Bitki gelişimini desteklemek için uygun yaprak gübresi veya organik bakım ürünlerini inceleyin.",
            "Hastalık belirtisi görülürse tekrar analiz yapın.",
            "Sağlıklı bitki kayıtlarını geçmiş analizlerde takip edin."
        )

        return TreatmentPlan(
            diseaseName = diseaseName,
            riskLevel = "Düşük",
            summary = "$diseaseName sonucu bitkide belirgin bir hastalık tespit edilmediğini gösterir. Koruyucu bakım önerilir.",
            steps = steps
        )
    }

    private fun generalPlan(diseaseName: String): TreatmentPlan {
        val steps = arrayListOf(
            "Bitkiyi daha net ışıkta tekrar fotoğraflayın.",
            "Yaprağın tamamının kadrajda olmasına dikkat edin.",
            "Belirti görülen yaprakları yakından inceleyin.",
            "Uygun ürün önerilerini kontrol edin.",
            "Bölgesel kayıt oluşturarak hastalık takibini yapın.",
            "Kesin teşhis için ziraat uzmanına danışın."
        )

        return TreatmentPlan(
            diseaseName = diseaseName,
            riskLevel = "Belirsiz",
            summary = "$diseaseName için genel bakım ve kontrol planı oluşturuldu.",
            steps = steps
        )
    }
}