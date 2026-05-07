# 🌿 Bitki Hastalık Tespit Sistemi

![Python](https://img.shields.io/badge/Python-3.x-blue?style=for-the-badge&logo=python)
![TensorFlow](https://img.shields.io/badge/TensorFlow-Keras-orange?style=for-the-badge&logo=tensorflow)
![Android](https://img.shields.io/badge/Android-Kotlin-green?style=for-the-badge&logo=android)
![TensorFlow Lite](https://img.shields.io/badge/TensorFlow-Lite-FF6F00?style=for-the-badge&logo=tensorflow)
![Project](https://img.shields.io/badge/Project-Bitirme%20Projesi-181717?style=for-the-badge&logo=github)

Derin öğrenme tabanlı mobil bitki hastalık tespit sistemi, bitki yapraklarında görülen hastalıkları görüntü üzerinden tahmin eden Android tabanlı bir mobil uygulamadır.

Kullanıcı, uygulama üzerinden kamera veya galeri aracılığıyla bitki yaprağı görüntüsü seçerek hastalık tahmini alabilir. Eğitilen model TensorFlow Lite formatına dönüştürülerek Android uygulamaya entegre edilmiştir.

---

## 📑 İçindekiler

- [Proje Hakkında](#-proje-hakkında)
- [Projenin Amacı](#-projenin-amacı)
- [Kullanılan Teknolojiler](#-kullanılan-teknolojiler)
- [Proje Özellikleri](#-proje-özellikleri)
- [Sistem Mimarisi](#-sistem-mimarisi)
- [Proje Yapısı](#-proje-yapısı)
- [Model Bilgisi](#-model-bilgisi)
- [Veri Seti](#-veri-seti)
- [Desteklenen Sınıflar](#-desteklenen-sınıflar)
- [Mobil Uygulama](#-mobil-uygulama)
- [Test Videosu](#-test-videosu)
- [Kurulum](#-kurulum)
- [Android Studio ile Çalıştırma](#-android-studio-ile-çalıştırma)
- [Çalışma Mantığı](#-çalışma-mantığı)
- [Proje Çıktıları](#-proje-çıktıları)
- [Gelecek Geliştirmeler](#-gelecek-geliştirmeler)
- [Geliştirici](#-geliştirici)
- [Lisans](#-lisans)

---

## 📌 Proje Hakkında

Bu proje, bitki yaprağı görüntülerini analiz ederek hastalık tahmini yapan mobil tabanlı bir yapay zekâ sistemidir.

Proje iki temel bölümden oluşmaktadır:

1. Derin öğrenme tabanlı görüntü sınıflandırma modeli
2. Android mobil uygulama

Model, bitki yaprağı görüntüsünü sınıflandırarak ilgili hastalık sınıfını tahmin eder. Android uygulama ise kullanıcının seçtiği görüntüyü modele gönderir ve tahmin sonucunu kullanıcıya anlaşılır şekilde gösterir.

---

## 🎯 Projenin Amacı

Tarımsal üretimde bitki hastalıklarının erken fark edilmesi, ürün kayıplarının azaltılması açısından önemlidir. Bu projenin amacı, kullanıcıların bitki yaprağı görüntüsü üzerinden hızlı ve pratik şekilde hastalık tahmini almasını sağlayan bir mobil sistem geliştirmektir.

Bu sistem ile kullanıcıya:

- Hastalık tahmini,
- Güven oranı,
- Türkçe hastalık adı,
- Hastalık açıklaması,
- Çözüm ve yönetim önerileri

sunulmaktadır.

Ayrıca `No_Leaf` sınıfı sayesinde yaprak olmayan görüntülerin tespit edilmesi amaçlanmıştır.

---

## 🧰 Kullanılan Teknolojiler

| Alan | Teknoloji |
|---|---|
| Programlama Dili | Python, Kotlin |
| Derin Öğrenme | TensorFlow, Keras |
| Mobil Model | TensorFlow Lite |
| Mobil Uygulama | Android Studio |
| Eğitim Ortamı | Google Colab |
| Versiyon Kontrol | Git, GitHub |

---

## 🚀 Proje Özellikleri

- Android mobil uygulama üzerinden görüntü seçme
- Kamera veya galeri desteği
- Bitki hastalığı tahmini
- TensorFlow Lite model entegrasyonu
- Tahmin güven oranı gösterimi
- Türkçe hastalık adı gösterimi
- Hastalık açıklaması ve çözüm önerisi sunma
- Yaprak olmayan görseller için `No_Leaf` kontrolü
- Kullanıcı dostu arayüz
- Test videosu ve model dosyalarının proje içinde tutulması

---

## 🏗️ Sistem Mimarisi

Sistem genel olarak kullanıcıdan alınan görüntünün ön işlemden geçirilmesi, TensorFlow Lite modeli ile analiz edilmesi ve tahmin sonucunun kullanıcıya gösterilmesi adımlarından oluşur.

```text
Kullanıcı
   ↓
Android Mobil Uygulama
   ↓
Görüntü Seçimi / Kamera
   ↓
Görüntü Ön İşleme
   ↓
TensorFlow Lite Modeli
   ↓
Hastalık Tahmini
   ↓
Sonuç, Güven Oranı ve Öneri Gösterimi
