from flask import Flask
from flask_limiter import Limiter
from flask_limiter.util import get_remote_address
from flask_jwt_extended import JWTManager
from routes.ai_routes import ai_routes
from routes.auth_routes import auth_routes

app = Flask(__name__)


app.config["JWT_SECRET_KEY"] = "super-secret-key"
jwt = JWTManager(app)
app.register_blueprint(auth_routes)

# 🚦 Rate limit
limiter = Limiter(
    key_func=get_remote_address,
    default_limits=["30 per minute"]
)

limiter.init_app(app)

# Register routes
app.register_blueprint(ai_routes)

@app.route("/health")
def health():
    return {"status": "ok"}

@app.route("/")
def home():
    return "AI Service Running"

if __name__ == "__main__":
    app.run(host="0.0.0.0", port=5000, debug=True)