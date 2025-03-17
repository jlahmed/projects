from flask import Flask, request, jsonify
from flask_cors import CORS
import os
import jwt
import time
from dotenv import load_dotenv

app = Flask(__name__)
CORS(app)

load_dotenv()

@app.route('/', methods=['GET'])
def test():
    return jsonify({"status": "Token server is running"})

def create_token(room_name, username):
    api_key = os.getenv('LIVEKIT_API_KEY')
    api_secret = os.getenv('LIVEKIT_API_SECRET')
    
    now = int(time.time())
    exp = now + 24 * 60 * 60  # 24 hours

    grant = {
        "room": room_name,
        "roomJoin": True,
        "canPublish": True,
        "canSubscribe": True
    }

    claim = {
        "iss": api_key,
        "nbf": now,
        "exp": exp,
        "sub": username,
        "video": grant
    }

    token = jwt.encode(claim, api_secret, algorithm='HS256')
    return token

@app.route('/get_token', methods=['POST'])
def get_token():
    try:
        # Get data from the request
        data = request.get_json()
        
        # Check if room and username are provided, otherwise return an error
        room_name = data.get('room')
        username = data.get('username')
        
        if not room_name or not username:
            return jsonify({"error": "Room name and username are required"}), 400

        print("\n" + "="*50)
        print("Received token request:")
        print(f"Room: {room_name}")
        print(f"Username: {username}")

        # Generate the token
        token = create_token(room_name, username)
        
        print("\nGENERATED TOKEN:")
        print("="*50)
        print(token)
        print("="*50 + "\n")
        
        return jsonify({"token": token})
    
    except Exception as e:
        print("\nERROR GENERATING TOKEN:")
        print("="*50)
        print(str(e))
        print("="*50 + "\n")
        return jsonify({"error": "Failed to generate token"}), 500


if __name__ == '__main__':
    app.run(port=5000, debug=True) 