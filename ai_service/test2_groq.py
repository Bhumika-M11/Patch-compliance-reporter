from services.groq_client import GroqClient

client = GroqClient()

sample_data = {
    "server_name": "Prod Server A",
    "patch_status": "Pending",
    "risk_level": "High",
    "missing_patches": 12
}

result = client.generate_report(sample_data)

print(result)