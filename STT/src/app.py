import streamlit as st
import openai
import langchain
from langchain.chat_models import ChatOpenAI
from langchain.schema import HumanMessage
import speech_recognition as sr
from gtts import gTTS
import os
