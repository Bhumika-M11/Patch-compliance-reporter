import os
from dotenv import load_dotenv
from groq import Groq

#Load environment variables
load_dotenv()

#Get API key
api_key=os.getenv("GROQ_API_KEY")
if not api_key:
    print("ERROR:GROQ_API_KEY not found in .env file")
    exit()
try:
    #Initialize Groq client
    client=Groq(api_key=api_key)
    #Test API call
    response=client.chat.completions.create(
        model="llama-3.3-70b-versatile",
        messages=[
            {
                "role":"user",
                "content":"Explain patch compliance in one sentence."
            }
        ],
        temperature=0.3,
        max_tokens=100
    )
    print("\nAPI CALL SUCCESS\n")
    print(response.choices[0].message.content)
except Exception as e:
    print("\nAPI CALL FAILED\n")
    print(str(e))