import os  
from dotenv import load_dotenv
from langchain_azure_ai.chat_models import AzureAIChatCompletionsModel
from langchain_core.prompts import ChatPromptTemplate
from langchain.prompts import PromptTemplate
from openai import AzureOpenAI
from langchain_core.output_parsers import StrOutputParser 
import azure.cognitiveservices.speech as speechsdk
from langchain_core.messages import HumanMessage, SystemMessage

if __name__ == "__main__":
    
    load_dotenv()

    # Without Langchain
    # llm = AzureOpenAI(
    # azure_endpoint = os.getenv("AZURE_OPENAI_ENDPOINT"), 
    # api_key=os.getenv("AZURE_OPENAI_API_KEY"),  
    # api_version="2024-02-01"
    # )

    llm = AzureAIChatCompletionsModel(
    endpoint=os.environ["AZURE_OPENAI_ENDPOINT"],
    credential=os.environ["AZURE_OPENAI_API_KEY"]
    )

    system_template = "Translate the following into {language}:"
    prompt_template = ChatPromptTemplate.from_messages(
        [("system", system_template), ("user", "{text}")]
    )

    parser = StrOutputParser()

    chain = prompt_template | llm | parser

    chain.invoke({"language": "italian", "text": "hi"})

    #ask(prompt="what is the color of the sky", llm=llm)


def ask(prompt, llm): 
    messages = [
    SystemMessage(content="You are a helpful assistant"),
    HumanMessage(content=prompt),
    ]

    llm.invoke(messages)

    # Without Langchain
    # response = llm.chat.completions.create(
    #     model="gpt-4o", # model = "deployment_name".
    #     messages=[
    #         {"role": "system", "content": "You are a helpful assistant. Keep your answers short and to the point"},
    #         {"role": "user", "content": prompt}
    #     ]
    # )
    # print(response.choices[0].message.content)

def listen():
    # This example requires environment variables named "SPEECH_KEY" and "SPEECH_REGION"
    speech_config = speechsdk.SpeechConfig(subscription=os.getenv("SPEECH_KEY"), region=os.getenv("SPEECH_REGION"))
    speech_config.speech_recognition_language="en-US"

    audio_config = speechsdk.audio.AudioConfig(use_default_microphone=True)
    speech_recognizer = speechsdk.SpeechRecognizer(speech_config=speech_config, audio_config=audio_config)

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
