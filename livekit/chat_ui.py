import streamlit as st

class ChatUI:
    def __init__(self):
        self.setup_page()
        self.container = st.container()
        self.setup_buttons()

    def setup_page(self):
        st.set_page_config(page_title="AI Assistant", page_icon=":speech_balloon:", layout="wide")
        with open('styles.css') as f:
            st.markdown(f'<style>{f.read()}</style>', unsafe_allow_html=True)
        st.title("AI Assistant with Speech and Text")

    def setup_buttons(self):
        col1, col2, col3 = st.columns([1, 1, 10])
        self.start_button = col1.button("Start Bot")
        self.stop_button = col2.button("Stop Bot")

    def display_message(self, role, content):
        with self.container:
            st.chat_message(role).write(content)

    def display_initial_message(self):
        with self.container:
            st.chat_message("assistant").write("Click 'Start Bot' to begin the conversation.")

    @property
    def is_start_pressed(self):
        return self.start_button

    @property
    def is_stop_pressed(self):
        return self.stop_button 