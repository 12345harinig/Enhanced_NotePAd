import sys
import os
import wave
import json
from vosk import Model, KaldiRecognizer
import pyaudio

# Set the path to the Vosk model directory
MODEL_PATH = "C:\\Users\\HARINI\\Downloads\\Notepad-main\\Notepad-main\\vosk-model-small-en-us-0.15"

# Ensure the model directory exists
if not os.path.exists(MODEL_PATH):
    print("Model path is incorrect! Please check it.")
    sys.exit(1)

# Load the Vosk model
model = Model(MODEL_PATH)

# Configure PyAudio
p = pyaudio.PyAudio()
stream = p.open(format=pyaudio.paInt16, channels=1, rate=16000, input=True, frames_per_buffer=8192)
stream.start_stream()

# Initialize recognizer
recognizer = KaldiRecognizer(model, 16000)

print("Listening... Speak into the microphone.")

# This is the file where speech will be saved
output_file = "recognized_text.txt"

# Clear the file at the beginning
open(output_file, "w").close()

while True:
    data = stream.read(4096, exception_on_overflow=False)
    if len(data) == 0:
        break
    if recognizer.AcceptWaveform(data):
        result = json.loads(recognizer.Result())
        text = result["text"]
        print("Recognized:", text)

        # Append the recognized text to the file
        with open(output_file, "a", encoding="utf-8") as f:
            f.write(text + "\n")
