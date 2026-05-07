# Bitki Hastalık Tespit Sistemi

Bu proje, derin öğrenme tabanlı mobil bitki hastalık tespit sistemidir. Sistem, bitki yaprağı görüntülerini analiz ederek yaprakta hastalık olup olmadığını tespit eder ve kullanıcıya tahmin sonucunu Android uygulama üzerinden gösterir.

## Projenin Amacı

Bu projenin amacı, tarımsal üretimde bitki hastalıklarının erken tespit edilmesine yardımcı olacak bir mobil uygulama geliştirmektir. Kullanıcı, bitki yaprağının fotoğrafını uygulamaya yükleyerek hastalık tahmini alabilir. Böylece hastalıkların daha erken fark edilmesi ve gerekli önlemlerin alınması hedeflenmektedir.

## Kullanılan Teknolojiler

- Python
- TensorFlow / Keras
- TensorFlow Lite
- Google Colab
- Android Studio
- Kotlin
- GitHub

## Sistem Özellikleri

- Bitki yaprağı görüntüsünden hastalık tahmini
- Sağlıklı ve hastalıklı yaprak sınıflandırması
- Yaprak olmayan görüntüler için `No_Leaf` sınıfı
- Android mobil uygulama üzerinden analiz
- Tahmin sonucu ve güven oranı gösterimi
- Hastalık açıklaması ve çözüm önerisi sunma
- Kullanıcı dostu mobil arayüz

## Proje Yapısı

```text
FarmerFriendApp/
├── app/                    # Android uygulama dosyaları
├── gradle/                 # Gradle yapılandırma dosyaları
├── models/                 # Eğitilmiş model dosyaları
│   ├── model.keras
│   └── model.tflite
├── notebooks/              # Google Colab eğitim dosyaları
├── screenshots/            # Uygulama ekran görüntüleri
├── test_videos/                 # Test videosu
├── README.md
├── build.gradle
└── settings.gradle
