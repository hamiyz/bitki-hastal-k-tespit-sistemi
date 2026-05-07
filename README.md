# 🌿 Bitki Hastalık Tespit Sistemi

![Python](https://img.shields.io/badge/Python-3.x-blue?style=for-the-badge&logo=python)
![TensorFlow](https://img.shields.io/badge/TensorFlow-Keras-orange?style=for-the-badge&logo=tensorflow)
![Android](https://img.shields.io/badge/Android-Kotlin-green?style=for-the-badge&logo=android)
![TensorFlow Lite](https://img.shields.io/badge/TensorFlow-Lite-ff6f00?style=for-the-badge&logo=tensorflow)
![GitHub](https://img.shields.io/badge/Project-Bitirme%20Projesi-181717?style=for-the-badge&logo=github)

Derin öğrenme tabanlı mobil bitki hastalık tespit sistemi.  
Bu proje, bitki yaprağı görüntülerini analiz ederek hastalık tahmini yapan ve sonucu Android mobil uygulama üzerinden kullanıcıya gösteren bir bitirme projesidir.

---

## 📑 İçindekiler

- [Proje Hakkında](#-proje-hakkında)
- [Projenin Amacı](#-projenin-amacı)
- [Kullanılan Teknolojiler](#-kullanılan-teknolojiler)
- [Proje Yapısı](#-proje-yapısı)
- [Model Bilgisi](#-model-bilgisi)
- [Mobil Uygulama](#-mobil-uygulama)
- [Test Videosu](#-test-videosu)
- [Kurulum](#-kurulum)
- [Çalışma Mantığı](#-çalışma-mantığı)
- [Gelecek Geliştirmeler](#-gelecek-geliştirmeler)
- [Geliştirici](#-geliştirici)

---

## 📌 Proje Hakkında

Bu proje, bitki yapraklarında görülen hastalıkların görüntü işleme ve derin öğrenme yöntemleriyle tespit edilmesini amaçlamaktadır.

Proje iki ana bölümden oluşmaktadır:

1. **Makine Öğrenmesi / Derin Öğrenme Modeli**
2. **Android Mobil Uygulama**

Model, bitki yaprağı görüntülerinden hastalık sınıfını tahmin eder. Android uygulama ise kullanıcının kamera veya galeri üzerinden seçtiği görüntüyü modele gönderir ve tahmin sonucunu ekranda gösterir.

---

## 🎯 Projenin Amacı

Tarımsal üretimde bitki hastalıklarının erken tespiti, ürün kayıplarını azaltmak açısından önemlidir. Bu proje ile kullanıcıların bitki yaprağı fotoğrafı üzerinden hızlı ve pratik şekilde hastalık tahmini alabilmesi hedeflenmiştir.

Sistem kullanıcıya:

- Hastalık tahmini,
- Tahmin güven oranı,
- Türkçe hastalık adı,
- Hastalık açıklaması,
- Çözüm / yönetim önerisi

sunmaktadır.

Ayrıca `No_Leaf` sınıfı ile yaprak olmayan görsellerin tespit edilmesi amaçlanmıştır.

---

## 🧰 Kullanılan Teknolojiler

| Alan | Kullanılan Teknoloji |
|---|---|
| Programlama | Python, Kotlin |
| Derin Öğrenme | TensorFlow, Keras |
| Mobil Model | TensorFlow Lite |
| Mobil Uygulama | Android Studio |
| Eğitim Ortamı | Google Colab |
| Versiyon Kontrol | Git, GitHub |

---

## 📁 Proje Yapısı

```text
bitki-hastal-k-tespit-sistemi/
│
├── app/                     # Android uygulama kaynak dosyaları
├── gradle/                  # Gradle yapılandırma dosyaları
├── models/                  # Eğitilmiş model dosyaları
│   ├── model.keras           # Eğitim sonrası model dosyası
│   └── model.tflite          # Android uygulamada kullanılan model
│
├── notebooks/               # Google Colab eğitim dosyaları
├── outputs/                 # Çıktılar ve yardımcı dosyalar
├── test_video/              # Uygulama test videosu
│
├── README.md                # Proje açıklama dosyası
├── .gitignore               # GitHub'a gönderilmeyecek dosyalar
├── build.gradle             # Android Gradle dosyası
└── settings.gradle          # Android Studio ayar dosyası
