import os
from groq import Groq
from dotenv import load_dotenv

load_dotenv()

client = Groq(api_key=os.getenv("GROQ_API_KEY"))

def generate_ai_response(user_input):

    prompt = f"""
    You are a cybersecurity AI assistant.

    Analyze the following vulnerability scan result
    and generate a professional patch compliance report.

    Input:
    {user_input}

    Include:
    - Risk summary
    - Severity
    - Recommendations
    - Patch actions
    """

    try:
        response = client.chat.completions.create(
            model="llama-3.1-8b-instant",
            messages=[
                {
                    "role": "user",
                    "content": prompt
                }
            ],
            temperature=0.3
        )

        return response.choices[0].message.content

    except Exception as e:
        return f"Error generating AI response: {str(e)}"