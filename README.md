BBC News Reader - Android Technical Challenge

A modern Android application that fetches and displays top headlines from BBC News using a clean, scalable architecture. 


🚀 Features

Secure Access: Implementation of Biometric (Fingerprint/Face) authentication before accessing news content.

Dynamic Headlines: Fetches real-time news headlines using the NewsAPI.

Chronological Sorting: Headlines are automatically sorted to show the newest articles first.

Article Details: Dedicated detail screen showing images, descriptions, and full content with a "Read More" toggle.

Multi-Flavor Support: Configured with build flavors (bbc and abc) to support different news providers.

⚙️ Setup & Installation
Clone the repository:

Bash

git clone https://github.com/mondheress/Critical-TechWorks-Test.git

Open the project in Android Studio (Ladybug or newer).

Add your API_KEY to the local.properties file:

Properties

API_KEY="YOUR_KEY_HERE"
Sync Gradle and run the app using either the bbc or abc build flavor.
