import streamlit as st 
from dotenv import load_dotenv
from openai import AzureOpenAI
import azure.cognitiveservices.speech as speechsdk

#st block to use with streamlit env variables instead of os
SPEECH_KEY = st.secrets["SPEECH_KEY"]
SPEECH_REGION = st.secrets["SPEECH_REGION"]
AZURE_OPENAI_ENDPOINT = st.secrets["AZURE_OPENAI_ENDPOINT"]
AZURE_OPENAI_API_KEY = st.secrets["AZURE_OPENAI_API_KEY"]
AZURE_OPENAI_MODEL_NAME = st.secrets["AZURE_OPENAI_MODEL_NAME"]

##Uconmment for using os instead of st
# import os 
# SPEECH_KEY = os.getenv("SPEECH_KEY")
# SPEECH_REGION = os.getenv("SPEECH_REGION")
# AZURE_OPENAI_ENDPOINT = os.getenv("AZURE_OPENAI_ENDPOINT")
# AZURE_OPENAI_API_KEY = os.getenv("AZURE_OPENAI_API_KEY")
# AZURE_OPENAI_MODEL_NAME = os.getenv("AZURE_OPENAI_MODEL_NAME")
# load_dotenv()

speech_config = speechsdk.SpeechConfig(subscription=SPEECH_KEY, region=SPEECH_REGION)
speech_config.speech_recognition_language="en-US"
audio_config = speechsdk.audio.AudioConfig(use_default_microphone=True)
speech_config.speech_synthesis_voice_name='en-US-AvaMultilingualNeural'
speech_recognizer = speechsdk.SpeechRecognizer(speech_config=speech_config, audio_config=audio_config)
speech_synthesizer = speechsdk.SpeechSynthesizer(speech_config=speech_config, audio_config=audio_config)

model = AzureOpenAI(
azure_endpoint = AZURE_OPENAI_ENDPOINT, 
api_key=AZURE_OPENAI_API_KEY,  
api_version="2024-05-01-preview"
)

messages = [
    {
        "role": "system",
        "content": [
            {
                "type": "text",
                "text": "You are an AI assistant that helps people find information. Keep your answers short and to the point"
            }
        ]
    }
] 

def listen():

    print("Speak into your microphone.")
    speech_recognition_result = speech_recognizer.recognize_once_async().get()

    if speech_recognition_result.reason == speechsdk.ResultReason.RecognizedSpeech:
        promt = speech_recognition_result.text
        print("Recognized: {}".format(promt))
        return promt
    elif speech_recognition_result.reason == speechsdk.ResultReason.NoMatch:
        print("No speech could be recognized: {}".format(speech_recognition_result.no_match_details))
    elif speech_recognition_result.reason == speechsdk.ResultReason.Canceled:
        cancellation_details = speech_recognition_result.cancellation_details
        print("Speech Recognition canceled: {}".format(cancellation_details.reason))
        if cancellation_details.reason == speechsdk.CancellationReason.Error:
            print("Error details: {}".format(cancellation_details.error_details))
            print("Did you set the speech resource key and region values?")

def process():
    response = model.chat.completions.create(
    model=AZURE_OPENAI_MODEL_NAME,
    messages=messages,
    max_tokens=100,  
    temperature=0.7,  
    top_p=0.95,  
    frequency_penalty=0,  
    presence_penalty=0,
    stop=None,  
    stream=False
    )
    
    return response.choices[0].message.content

def speak(text):

    speech_synthesis_result = speech_synthesizer.speak_text_async(text).get()

    if speech_synthesis_result.reason == speechsdk.ResultReason.SynthesizingAudioCompleted:
        print("Speech synthesized for text [{}]".format(text))
    elif speech_synthesis_result.reason == speechsdk.ResultReason.Canceled:
        cancellation_details = speech_synthesis_result.cancellation_details
        print("Speech synthesis canceled: {}".format(cancellation_details.reason))
        if cancellation_details.reason == speechsdk.CancellationReason.Error:
            if cancellation_details.error_details:
                print("Error details: {}".format(cancellation_details.error_details))
                print("Did you set the speech resource key and region values?")

def create_web_interface():
    # Streamlit Web Interface
    st.set_page_config(page_title="AI Assistant", page_icon=":speech_balloon:", layout="wide")

    st.title("AI Assistant with Speech and Text")

    st.sidebar.header("Instructions")
    st.sidebar.write("1. Click on 'Start Bot' to interact with the AI via speech.")
    st.sidebar.write("2. Click on 'Stop Bot' to end the interaction.")
    st.sidebar.write("3. Wait for AI Assistant to stop speaking before asking next question.")

    start_button = st.button("Start Bot")
    stop_button = st.button("Stop Bot")

    return start_button, stop_button

if __name__ == "__main__":
    
    start_button, stop_button = create_web_interface()

    if start_button:
        welcome_message = "Please ask me anything! Note you will have to let me finish speaking before you ask a follow-up question."
        st.text_area("Assistant: ", welcome_message, key=welcome_message)
        speak(welcome_message)

        while True:
            prompt=listen()
            if not prompt:
                continue  # Skip empty inputs

            messages.append({"role": "user", "content": prompt})  
            st.text_area("You: ", prompt, key=prompt)
            response = process()
            st.text_area("Assistant: ", response, key=response)
            messages.append({"role": "assistant", "content": response})  

            speak(response)

    if stop_button:
        goodbye_message = "It has been my pleasure to assist you today, have a great day!"
        st.text_area("Assistant: ", goodbye_message, key=goodbye_message)
        speak(goodbye_message)
        st.stop()
        quit()
