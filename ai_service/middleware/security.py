import re
import bleach

def sanitize_input(text):
    """
    Remove HTML tags and dangerous scripts
    """

    if not text:
        return ""

    # Remove HTML tags
    cleaned = bleach.clean(text, tags=[], strip=True)

    return cleaned.strip()

def detect_prompt_injection(text):
    if not text:
        return False

    text = text.lower()

    suspicious_keywords = [
        "ignore",
        "bypass",
        "hack",
        "override",
        "disable",
        "forget",
        "instructions",
        "system"
    ]

    # If multiple suspicious words appear → treat as attack
    match_count = sum(1 for word in suspicious_keywords if word in text)

    return match_count >= 2

def remove_pii(text):
    text = re.sub(r'\b\d{10}\b', '[REDACTED_PHONE]', text)
    text = re.sub(r'\S+@\S+', '[REDACTED_EMAIL]', text)
    return text

def detect_prompt_injection(text):
    patterns = [
        "ignore all instructions",
        "bypass",
        "hack",
        "override system",
        "act as"
    ]

    text = text.lower()

    for p in patterns:
        if p in text:
            return True
    return False