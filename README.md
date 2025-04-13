
# Enhanced Notepad Application 📝

A feature-rich notepad desktop application built using **Java Swing** and integrated with **Python's Vosk API** for real-time speech-to-text functionality. Designed with a clean UI and dark mode support, this application enhances the traditional notepad experience with powerful editing tools and accessibility features.

---

## 🌟 Features

- 🖋️ **Text Editing**
  - Create, open, save, and edit `.txt` files
  - Undo / Redo functionality
  - Word, character, and line counter
  - Find and Replace functionality

- 🎨 **Dark Mode Toggle**
  - Switch between light and dark themes for better readability

- 🧠 **Speech-to-Text Integration**
  - Convert spoken words into text using **Python (Vosk API)**
  - Launches a Python subprocess that listens via microphone and appends recognized text to the editor

- 📋 **Clipboard Operations**
  - Cut, copy, paste, and select all

- 🧼 **Clean UI**
  - Intuitive and minimal interface with responsive components

---

## 📂 Project Structure

```
EnhancedNotepad/
├── Notepad.java            # Main Java application (Swing UI)
├── SpeechToText.py         # Python script using Vosk API
├── model/                  # Vosk speech recognition model folder
├── assets/                 # Icons, styles, and additional resources
└── README.md
```

---

## 🚀 Getting Started

### Prerequisites

- Java (JDK 8+)
- Python 3.x
- Vosk API
- Swing (included in JDK)
- Vosk Model (download from [Vosk Models](https://alphacephei.com/vosk/models))

---

### Installation

1. **Clone the repository:**

```bash
git clone https://github.com/12345harinig/enhanced-notepad.git
cd enhanced-notepad
```

2. **Install Python dependencies:**

```bash
pip install vosk pyaudio
```

3. **Download Vosk Model** and extract it into the `model/` directory  
   (e.g., `model/vosk-model-small-en-us-0.15`)

---

## 🛠️ How to Run

### Step 1: Launch the Java Notepad App

```bash
javac Notepad.java
java Notepad
```

### Step 2: Use Speech-to-Text

Click the microphone button to activate Python-based speech-to-text. Speak clearly and the text will appear in the editor!

---

## 👩‍💻 Author

**Harini G**  
- 🔗 [LinkedIn](https://www.linkedin.com/in/harini-g-842a4525b/)
- 💻 [GitHub](https://github.com/12345harinig)

---

## 📄 License

This project is open-source and available under the [MIT License](LICENSE).
