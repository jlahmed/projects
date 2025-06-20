import logging
import asyncio
from dotenv import load_dotenv
from livekit.agents import AutoSubscribe, JobContext, WorkerOptions, cli, llm
from livekit.agents.voice_assistant import VoiceAssistant
from livekit.plugins import openai, silero
from typing import Annotated

load_dotenv()

logger = logging.getLogger("temperature-control")
logger.setLevel(logging.INFO)

class AssistantFnc(llm.FunctionContext):
    def __init__(self) -> None:
        super().__init__()
        self._temperature = {
            "butter chicken": 35,
            "pizza": 40,
        }

    @llm.ai_callable(description="get the temperature of a selected meal")
    def get_temperature(
        self, meal: Annotated[str, llm.TypeInfo(description="The selected meal (butter chicken or pizza)")]
    ):
        try:
            meal = meal.lower()
            if meal not in self._temperature:
                logger.error("Meal %s not found", meal)
                return f"I'm sorry, I don't have temperature information for {meal}"
            
            temp = self._temperature[meal]
            logger.info("Retrieved temperature %s for meal: %s", temp, meal)
            return f"The temperature of the {meal} is {temp}C"
        except Exception as e:
            logger.error("Error getting temperature: %s", str(e))
            return f"I'm sorry, I encountered an error while retrieving the temperature"

    @llm.ai_callable(description="set the temperature of the selected meal")
    def set_temperature(
        self,
        meal: Annotated[str, llm.TypeInfo(description="The selected meal (butter chicken or pizza)")],
        temp: Annotated[int, llm.TypeInfo(description="The temperature to set")]
    ):
        try:
            meal = meal.lower()
            if meal not in self._temperature:
                logger.error("Meal %s not found", meal)
                return f"I'm sorry, I don't recognize {meal} as a valid meal option"
            
            self._temperature[meal] = temp
            logger.info("Set temperature %s for meal: %s", temp, meal)
            return f"The temperature of the {meal} is now {temp}C"
        except Exception as e:
            logger.error("Error setting temperature: %s", str(e))
            return f"I'm sorry, I encountered an error while setting the temperature"

async def entrypoint(ctx: JobContext):
    initial_ctx = llm.ChatContext().append(
        role="system",
        text=(
            "You are a food critic who reviews food in detail and gives tips to improve recipes."
            "You can also accurately tell what temperature is the food and can heat it up to the desired temperature."
            "Speak as much as a human would speak, not like a robot. For example font start listing bullet points"
            "Only give temperatures in degrees celcius; do not mention fahrenheit"
        ),
    )
    await ctx.connect(auto_subscribe=AutoSubscribe.AUDIO_ONLY)
    fnc_ctx = AssistantFnc()

    assistant = VoiceAssistant(
        vad=silero.VAD.load(),
        stt=openai.STT(),
        llm=openai.LLM(),
        tts=openai.TTS(voice="nova"),
        chat_ctx=initial_ctx,
        fnc_ctx=fnc_ctx,
    )
    assistant.start(ctx.room)

    await asyncio.sleep(1)
    await assistant.say("Hey, how can I help you today!")

if __name__ == "__main__":
    cli.run_app(WorkerOptions(entrypoint_fnc=entrypoint))