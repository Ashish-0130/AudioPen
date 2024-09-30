import speech_recognition as sr
import spacy

# Load the spaCy model
nlp = spacy.load("en_core_web_sm")

# Predefined topics and keywords
topics = {
    "Technology": ["computer", "technology", "software", "hardware"],
    "Health": ["health", "medicine", "doctor", "hospital"],
    "Finance": ["money", "finance", "investment", "bank"]
}

def detect_topic(text):
    doc = nlp(text)
    detected_topics = []
    
    # Check for keywords in the text
    for topic, keywords in topics.items():
        for token in doc:
            if token.text.lower() in keywords:
                detected_topics.append(topic)
                break
    
    if detected_topics:
        return detected_topics[0]  # Return the first detected topic for simplicity
    else:
        return "General"  # Default topic if no keywords are found

def recognize_speech_from_mic():
    r = sr.Recognizer()  # Initialize the Recognizer
    with sr.Microphone() as source:
        print("Adjusting for ambient noise... Please wait.")
        r.adjust_for_ambient_noise(source, duration=1)
        r.pause_threshold = 1.0
        print("Listening...")

        current_topic = None
        topic_text = {topic: "" for topic in topics.keys()}
        
        while True:
            try:
                # Listen for audio input
                audio = r.listen(source, timeout=10)
                print("Recognizing...")
                # Recognize speech using Google Speech Recognition
                text = r.recognize_google(audio)
                print("You said: " + text)
                
                # Detect the topic from the text
                detected_topic = detect_topic(text)
                
                # If the topic changes, print and start a new segment
                if detected_topic != current_topic:
                    if current_topic:
                        print(f"End of topic: {current_topic}")
                        print(f"Text for {current_topic}: {topic_text[current_topic]}")
                    print(f"Start of new topic: {detected_topic}")
                    current_topic = detected_topic
                    topic_text[current_topic] = ""

                # Append text to the current topic
                topic_text[current_topic] += text + " "
                print(f"Text for {current_topic}: {topic_text[current_topic]}")

            except sr.UnknownValueError:
                print("Google Speech Recognition could not understand audio")
            except sr.RequestError:
                print("Could not request results from Google Speech Recognition service")
            except sr.WaitTimeoutError:
                print("Listening timed out while waiting for phrase to start")

if __name__ == "__main__":
    recognize_speech_from_mic()