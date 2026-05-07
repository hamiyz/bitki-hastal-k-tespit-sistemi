# 🌿 Bitki Hastalık Tespit Sistemi

![Python](https://img.shields.io/badge/Python-3.x-blue?style=for-the-badge&logo=python)
![TensorFlow](https://img.shields.io/badge/TensorFlow-Keras-orange?style=for-the-badge&logo=tensorflow)
![TensorFlow Lite](https://img.shields.io/badge/TensorFlow-Lite-FF6F00?style=for-the-badge&logo=tensorflow)
![Android](https://img.shields.io/badge/Android-Kotlin-green?style=for-the-badge&logo=android)
![Project](https://img.shields.io/badge/Project-Bitirme%20Projesi-181717?style=for-the-badge&logo=github)

Derin öğrenme tabanlı mobil bitki hastalık tespit sistemi, bitki yapraklarında görülen hastalıkları görüntü üzerinden tahmin eden Android tabanlı bir mobil uygulamadır.

Kullanıcı, uygulama üzerinden kamera veya galeri aracılığıyla bitki yaprağı görüntüsü seçerek hastalık tahmini alabilir. Eğitilen model TensorFlow Lite formatına dönüştürülerek Android uygulamaya entegre edilmiştir.

---

## 📑 İçindekiler

- [Proje Hakkında](#proje-hakkında)
- [Projenin Amacı](#projenin-amacı)
- [Kullanılan Teknolojiler](#kullanılan-teknolojiler)
- [Proje Özellikleri](#proje-özellikleri)
- [Sistem Mimarisi](#sistem-mimarisi)
- [Proje Yapısı](#proje-yapısı)
- [Model Bilgisi](#model-bilgisi)
- [Veri Seti](#veri-seti)
- [Desteklenen Sınıflar](#desteklenen-sınıflar)
- [Mobil Uygulama](#mobil-uygulama)
- [Test Videosu](#test-videosu)
- [Kurulum](#kurulum)
- [Android Studio ile Çalıştırma](#android-studio-ile-çalıştırma)
- [Çalışma Mantığı](#çalışma-mantığı)
- [Proje Çıktıları](#proje-çıktıları)
- [Gelecek Geliştirmeler](#gelecek-geliştirmeler)
- [Geliştirici](#geliştirici)
- [Lisans](#lisans)

---

## Proje Hakkında

Bu proje, bitki yaprağı görüntülerini analiz ederek hastalık tahmini yapan mobil tabanlı bir yapay zekâ sistemidir.

Proje iki ana bölümden oluşmaktadır:

1. Derin öğrenme tabanlı görüntü sınıflandırma modeli
2. Android mobil uygulama

Model, bitki yaprağı görüntüsünü sınıflandırarak ilgili hastalık sınıfını tahmin eder. Android uygulama ise kullanıcının seçtiği görüntüyü modele gönderir ve tahmin sonucunu kullanıcıya anlaşılır şekilde gösterir.

---

## Projenin Amacı

Tarımsal üretimde bitki hastalıklarının erken fark edilmesi, ürün kayıplarının azaltılması açısından önemlidir.

Bu projenin amacı, kullanıcıların bitki yaprağı görüntüsü üzerinden hızlı ve pratik şekilde hastalık tahmini almasını sağlayan bir mobil sistem geliştirmektir.

Bu sistem ile kullanıcıya:

- Hastalık tahmini
- Güven oranı
- Türkçe hastalık adı
- Hastalık açıklaması
- Çözüm ve yönetim önerileri

sunulmaktadır.

Ayrıca `No_Leaf` sınıfı sayesinde yaprak olmayan görüntülerin tespit edilmesi amaçlanmıştır.

---

## Kullanılan Teknolojiler

| Alan | Teknoloji |
|---|---|
| Programlama Dili | Python, Kotlin |
| Derin Öğrenme | TensorFlow, Keras |
| Mobil Model | TensorFlow Lite |
| Mobil Uygulama | Android Studio |
| Eğitim Ortamı | Google Colab |
| Versiyon Kontrol | Git, GitHub |

---

## Proje Özellikleri

- Android mobil uygulama üzerinden görüntü seçme
- Kamera veya galeri desteği
- Bitki hastalığı tahmini
- TensorFlow Lite model entegrasyonu
- Tahmin güven oranı gösterimi
- Türkçe hastalık adı gösterimi
- Hastalık açıklaması ve çözüm önerisi sunma
- Yaprak olmayan görseller için `No_Leaf` kontrolü
- Kullanıcı dostu mobil arayüz
- Test videosu ve model dosyalarının proje içinde tutulması

---

## Sistem Mimarisi

Sistem, kullanıcıdan alınan görüntünün ön işlemden geçirilmesi, TensorFlow Lite modeli ile analiz edilmesi ve tahmin sonucunun kullanıcıya gösterilmesi adımlarından oluşur.

```text
Kullanıcı
   ↓
Android Mobil Uygulama
   ↓
Kamera / Galeri Üzerinden Görüntü Seçimi
   ↓
Görüntü Ön İşleme
   ↓
TensorFlow Lite Modeli
   ↓
Hastalık Tahmini
   ↓
Sonuç, Güven Oranı ve Öneri Gösterimi
```

---

## Proje Yapısı

```text
bitki-hastal-k-tespit-sistemi/
│
├── app/                     # Android uygulama kaynak dosyaları
├── gradle/                  # Gradle yapılandırma dosyaları
├── models/                  # Eğitilmiş model dosyaları
│   ├── model.keras           # Eğitim sonrası saklanan model dosyası
│   └── model.tflite          # Android uygulamada kullanılan model dosyası
│
├── notebooks/               # Google Colab eğitim dosyaları
├── outputs/                 # Çıktılar ve yardımcı dosyalar
├── test_video/              # Uygulama test videosu ve GIF önizlemesi
│   ├── test_video.mp4
│   └── test_video.gif
│
├── README.md                # Proje açıklama dosyası
├── .gitignore               # GitHub'a gönderilmeyecek dosyalar
├── build.gradle             # Android Gradle dosyası
└── settings.gradle          # Android Studio ayar dosyası
```

---

## Model Bilgisi

Model, bitki yaprağı görüntülerini sınıflandırmak için eğitilmiştir. Eğitim işlemleri Google Colab ortamında gerçekleştirilmiştir.

Eğitilen model, Android uygulamada kullanılabilmesi için TensorFlow Lite formatına dönüştürülmüştür.

Android uygulamada kullanılan model dosyası:

```text
models/model.tflite
```

Eğitim sonrası saklanan model dosyası:

```text
models/model.keras
```

Model, bitki hastalık sınıflarına ek olarak yaprak olmayan görüntüleri ayırt edebilmek için `No_Leaf` sınıfını da içermektedir.

---

## Veri Seti

Projede bitki yapraklarına ait sağlıklı ve hastalıklı görüntülerden oluşan veri seti kullanılmıştır.

Veri seti üzerinde eğitim ve doğrulama ayrımı yapılmış, model bu görüntüler üzerinden eğitilmiştir.

Yaprak olmayan görüntüleri ayırt edebilmek için veri setine ayrıca `No_Leaf` sınıfı eklenmiştir.

---

## Desteklenen Sınıflar

Projede farklı bitki türlerine ait sağlıklı ve hastalıklı yaprak sınıfları bulunmaktadır.

Örnek sınıflar:

```text
Apple___Apple_scab
Apple___Black_rot
Apple___Cedar_apple_rust
Apple___healthy
Blueberry___healthy
Cherry_(including_sour)___healthy
Cherry_(including_sour)___Powdery_mildew
Grape___Black_rot
Grape___Esca_(Black_Measles)
Grape___Leaf_blight_(Isariopsis_Leaf_Spot)
Grape___healthy
Orange___Haunglongbing_(Citrus_greening)
Peach___Bacterial_spot
Peach___healthy
Pepper,_bell___Bacterial_spot
Pepper,_bell___healthy
Potato___Early_blight
Potato___Late_blight
Potato___healthy
Raspberry___healthy
Soybean___healthy
Squash___Powdery_mildew
Strawberry___Leaf_scorch
Strawberry___healthy
Tomato___Bacterial_spot
Tomato___Early_blight
Tomato___Late_blight
Tomato___Leaf_Mold
Tomato___Septoria_leaf_spot
Tomato___Spider_mites Two-spotted_spider_mite
Tomato___Target_Spot
Tomato___Tomato_Yellow_Leaf_Curl_Virus
Tomato___Tomato_mosaic_virus
Tomato___healthy
No_Leaf
```

---

## Mobil Uygulama

Android uygulama, kullanıcının kamera veya galeri üzerinden bitki yaprağı görüntüsü seçmesini sağlar.

Seçilen görüntü model tarafından analiz edilir ve sonuç ekranda gösterilir.

Uygulama çıktısında:

- Tahmin edilen sınıf
- Türkçe hastalık adı
- Güven oranı
- Hastalık açıklaması
- Çözüm / yönetim önerisi

yer almaktadır.

---

## Test Videosu

Videonun tam halini izlemek için:

[![Test Videosunu İzle](https://img.shields.io/badge/▶%20Test%20Videosunu%20İzle-28a745?style=for-the-badge)](test_video/testt.mp4)


---

## Kurulum

Projeyi bilgisayara indirmek için:

```bash
git clone https://github.com/hamiyz/bitki-hastal-k-tespit-sistemi.git
```

Proje klasörüne girin:

```bash
cd bitki-hastal-k-tespit-sistemi
```

Ardından proje Android Studio ile açılabilir.

---

## Android Studio ile Çalıştırma

1. Android Studio açılır.
2. Proje klasörü seçilir.
3. Gradle senkronizasyonu tamamlanır.
4. Uygulama emülatör veya fiziksel Android cihaz üzerinde çalıştırılır.
5. Kamera veya galeri üzerinden bitki yaprağı görüntüsü seçilir.
6. Görüntü analiz edilir.
7. Tahmin sonucu ekranda gösterilir.

---

## Çalışma Mantığı

Sistem, seçilen görüntüyü modelin giriş boyutuna uygun hale getirir. Daha sonra görüntü TensorFlow Lite modeli ile analiz edilir.

Model çıktısında en yüksek olasılığa sahip sınıf seçilir ve uygulama bu sonucu kullanıcıya gösterir.

```text
Görüntü Seçimi
      ↓
Görüntü Ön İşleme
      ↓
TensorFlow Lite Modeli
      ↓
Sınıflandırma
      ↓
Tahmin Sonucu
      ↓
Açıklama ve Öneri Gösterimi
```

---

## Proje Çıktıları

Bu proje kapsamında aşağıdaki çıktılar elde edilmiştir:

- Eğitilmiş derin öğrenme modeli
- TensorFlow Lite mobil model dosyası
- Android mobil uygulama
- Hastalık açıklama ve öneri sistemi
- Test videosu
- GitHub proje deposu

---

## Gelecek Geliştirmeler

İlerleyen aşamalarda sisteme şu özellikler eklenebilir:

- Daha geniş veri seti ile model başarımının artırılması
- Daha fazla bitki ve hastalık sınıfı desteği
- Geçmiş analizlerin detaylı saklanması
- Kullanıcıya bakım / ilaçlama hatırlatmaları
- Konum ve hava durumu bilgisine göre hastalık risk analizi
- İnternet destekli uzman öneri sistemi

---

## Geliştirici

**Hami Çolak**

---

## Lisans

Bu proje eğitim ve bitirme projesi amacıyla hazırlanmıştır.
