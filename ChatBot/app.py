import os 
import streamlit as st 
from dotenv import load_dotenv
from openai import AzureOpenAI
import azure.cognitiveservices.speech as speechsdk
from message_manager import MessageManager
from chat_ui import ChatUI

# Load environment variables
load_dotenv()

class ChatBot:
    def __init__(self):
        self.setup_azure_services()
        self.message_manager = MessageManager()
        self.ui = ChatUI()
        
        if 'bot_running' not in st.session_state:
            st.session_state.bot_running = False

    def setup_azure_services(self):
        # Azure Speech setup
        speech_config = speechsdk.SpeechConfig(
            subscription=os.getenv("SPEECH_KEY"), 
            region=os.getenv("SPEECH_REGION")
        )
        speech_config.speech_recognition_language = "en-US"
        speech_config.speech_synthesis_voice_name = 'en-US-AvaMultilingualNeural'
        
        audio_config = speechsdk.audio.AudioConfig(use_default_microphone=True)
        
        self.speech_recognizer = speechsdk.SpeechRecognizer(
            speech_config=speech_config, 
            audio_config=audio_config
        )
        self.speech_synthesizer = speechsdk.SpeechSynthesizer(
            speech_config=speech_config, 
            audio_config=audio_config
        )

        # Azure OpenAI setup
        self.model = AzureOpenAI(
            azure_endpoint=os.getenv("AZURE_OPENAI_ENDPOINT"),
            api_key=os.getenv("AZURE_OPENAI_API_KEY"),
            api_version="2024-05-01-preview"
        )

    def listen(self):
        print("Speak into your microphone.")
        result = self.speech_recognizer.recognize_once_async().get()
        
        if result.reason == speechsdk.ResultReason.RecognizedSpeech:
            print("Recognized: {}".format(result.text))
            return result.text
        return None

    def process(self):
        response = self.model.chat.completions.create(
            model=os.getenv("AZURE_OPENAI_MODEL_NAME"),
            messages=self.message_manager.get_messages(),
            max_tokens=100,
            temperature=0.7,
            top_p=0.95,
            frequency_penalty=0,
            presence_penalty=0,
            stop=None,
            stream=False
        )
        return response.choices[0].message.content

    def speak(self, text):
        result = self.speech_synthesizer.speak_text_async(text).get()
        if result.reason == speechsdk.ResultReason.SynthesizingAudioCompleted:
            print("Speech synthesized for text [{}]".format(text))
        elif result.reason == speechsdk.ResultReason.Canceled:
            print("Speech synthesis canceled: {}".format(result.cancellation_details.reason))

    def run(self):
        self.ui.display_initial_message()

        if self.ui.is_start_pressed and not st.session_state.bot_running:
            st.session_state.bot_running = True
            welcome_message = "Please ask me anything!"
            self.ui.display_message("assistant", welcome_message)
            self.speak(welcome_message)

        while st.session_state.bot_running:
            if self.ui.is_stop_pressed:
                st.session_state.bot_running = False
                return

            prompt = self.listen()
            if prompt:
                self.message_manager.add_user_message(prompt)
                self.ui.display_message("user", prompt)
                
                response = self.process()
                self.ui.display_message("assistant", response)
                self.message_manager.add_assistant_message(response)
                self.speak(response)

if __name__ == "__main__":
    bot = ChatBot()
    bot.run()

