# 🌿 Derin Öğrenme Tabanlı Mobil Bitki Hastalık Tespit Sistemi

Bu proje, bitki yapraklarında görülen hastalıkların derin öğrenme yöntemleri kullanılarak tespit edilmesini sağlayan mobil tabanlı bir uygulamadır. Kullanıcı, Android uygulama üzerinden kamera veya galeri aracılığıyla bitki yaprağı görüntüsü seçerek hastalık tahmini alabilir.

Proje kapsamında eğitilen görüntü sınıflandırma modeli, Android uygulamaya TensorFlow Lite formatında entegre edilmiştir. Böylece model, mobil cihaz üzerinde doğrudan çalışarak hızlı ve pratik tahmin sonuçları sunmaktadır.

---

## 📌 Projenin Amacı

Tarımsal üretimde bitki hastalıklarının erken tespit edilmesi, ürün kayıplarını azaltmak açısından büyük önem taşımaktadır. Bu projenin amacı, bitki yaprağı görüntülerini analiz ederek hastalık durumunu tahmin eden kullanıcı dostu bir mobil sistem geliştirmektir.

Sistem sayesinde kullanıcılar:

- Bitki yaprağı görüntüsü üzerinden hastalık tahmini alabilir.
- Tahmin sonucunu ve güven oranını görüntüleyebilir.
- Hastalık hakkında açıklama ve çözüm önerilerine ulaşabilir.
- Yaprak olmayan görüntüler için uyarı alabilir.

---

## 🚀 Temel Özellikler

- Android mobil uygulama üzerinden görüntü seçme
- Kamera veya galeri desteği
- Derin öğrenme tabanlı hastalık tahmini
- TensorFlow Lite model entegrasyonu
- Tahmin güven oranı gösterimi
- Hastalık açıklaması ve öneri metinleri
- `No_Leaf` sınıfı ile yaprak olmayan görüntüleri ayırt etme
- Kullanıcı dostu arayüz
- Test videosu ve model dosyalarının proje içerisinde saklanması

---

## 🎥 Test Videosu

Aşağıdaki video, Android uygulamanın çalışma sürecini göstermektedir.

[▶️ Test Videosunu İzle](test_video/test_video.mp4)

## 🧠 Kullanılan Teknolojiler

| Alan | Teknoloji |
|---|---|
| Model Eğitimi | Python, TensorFlow, Keras |
| Model Dönüştürme | TensorFlow Lite |
| Mobil Uygulama | Android Studio, Kotlin |
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
│   ├── model.keras           # Eğitim sonrası kaydedilen model
│   └── model.tflite          # Android uygulamada kullanılan model
│
├── notebooks/               # Google Colab eğitim dosyaları
├── outputs/                 # Çıktılar ve yardımcı dosyalar
├── test_video/              # Uygulama test videosu
│
├── README.md                # Proje açıklama dosyası
├── .gitignore               # GitHub'a gönderilmeyecek dosyalar
├── build.gradle             # Proje Gradle dosyası
└── settings.gradle          # Android Studio ayar dosyası
