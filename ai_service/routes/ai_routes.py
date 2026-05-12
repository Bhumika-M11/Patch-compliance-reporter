from flask import Blueprint, request, jsonify
from middleware.security import sanitize_input, detect_prompt_injection
from services.groq_service import generate_ai_response

ai_routes = Blueprint("ai_routes", __name__)

from flask import request, jsonify
import jwt

SECRET_KEY = "your_secret_key"

def verify_token():
    token = request.headers.get("Authorization")

    if not token:
        return None

    try:
        decoded = jwt.decode(token, SECRET_KEY, algorithms=["HS256"])
        return decoded
    except:
        return None

@ai_routes.route("/generate-report", methods=["POST"])
def generate_report():
    data = request.get_json()

    if not data or "input" not in data:
        return jsonify({"error": "Missing input"}), 400

    user_input = data["input"]

    # Empty input check
    if not user_input.strip():
        return jsonify({"error": "Empty input"}), 400

    # Injection check
    if detect_prompt_injection(user_input):
        return jsonify({"error": "Prompt injection detected"}), 400

    cleaned_input = sanitize_input(user_input)

    report = generate_ai_response(cleaned_input)

    return jsonify({
        "message": "Report generated successfully",
        "cleaned_input": cleaned_input,
        "report": report
    })