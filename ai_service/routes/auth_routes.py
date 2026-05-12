from flask import Blueprint, request, jsonify
import jwt
import datetime

auth_routes = Blueprint("auth_routes", __name__)

SECRET_KEY = "your_secret_key"  # move to env later

@auth_routes.route("/login", methods=["GET", "POST"])
def login():
    data = request.get_json()

    username = data.get("username")
    password = data.get("password")

    # simple check (for now)
    if username == "admin" and password == "admin":
        token = jwt.encode({
            "user": username,
            "exp": datetime.datetime.utcnow() + datetime.timedelta(hours=1)
        }, SECRET_KEY, algorithm="HS256")

        return jsonify({"token": token})
    if request.method == "GET":
        return "Login endpoint working. Use POST."

    return jsonify({"error": "Invalid credentials"}), 401