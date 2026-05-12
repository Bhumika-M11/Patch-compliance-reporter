import os
import time
import logging
from dotenv import load_dotenv
from groq import Groq

# Load env variables
load_dotenv()

# Logging setup
logging.basicConfig(
    filename="groq_errors.log",
    level=logging.ERROR,
    format="%(asctime)s - %(levelname)s - %(message)s"
)

class GroqClient:
    def __init__(self):
        self.api_key = os.getenv("GROQ_API_KEY")
        self.model = os.getenv("MODEL_NAME", "llama-3.3-70b-versatile")

        if not self.api_key:
            raise ValueError("GROQ_API_KEY missing in .env")

        self.client = Groq(api_key=self.api_key)

    def generate_report(self, user_input):
        """
        Calls Groq API with retry logic
        Returns structured JSON response
        """

        prompt = f"""
        Generate a patch compliance report using the following input:

        {user_input}

        Return JSON format only:
        {{
            "title": "",
            "summary": "",
            "overview": "",
            "key_items": [],
            "recommendations": []
        }}
        """

        max_retries = 3

        for attempt in range(max_retries):
            try:
                response = self.client.chat.completions.create(
                    model=self.model,
                    messages=[
                        {
                            "role": "user",
                            "content": prompt
                        }
                    ],
                    temperature=0.3,
                    max_tokens=500
                )

                result = response.choices[0].message.content

                return {
                    "success": True,
                    "data": result,
                    "retry_count": attempt
                }

            except Exception as e:
                logging.error(f"Attempt {attempt+1} failed: {str(e)}")

                if attempt < max_retries - 1:
                    time.sleep(2 ** attempt)  # Exponential backoff
                else:
                    return {
                        "success": False,
                        "error": str(e),
                        "is_fallback": True,
                        "data": {
                            "title": "Fallback Report",
                            "summary": "AI service temporarily unavailable.",
                            "overview": "Using fallback response.",
                            "key_items": [],
                            "recommendations": []
                        }
                    }