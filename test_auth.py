#!/usr/bin/env python3

import requests
import json

# Test the authentication endpoint to verify the response structure


def test_auth():
    url = "https://caresphere.ekddigital.com/auth/login"
    payload = {
        "email": "admin@caresphere.com",
        "password": "admin123"
    }

    print(f"Testing authentication at: {url}")
    print(f"Payload: {json.dumps(payload, indent=2)}")

    try:
        response = requests.post(url, json=payload, headers={
                                 "Content-Type": "application/json"})
        print(f"\nResponse Status: {response.status_code}")
        print(f"Response Headers: {dict(response.headers)}")
        print(f"Response Body: {response.text}")

        if response.status_code == 200:
            data = response.json()
            print(f"\nParsed JSON: {json.dumps(data, indent=2)}")
        else:
            print(f"\nError: {response.status_code} - {response.text}")

    except Exception as e:
        print(f"Exception: {e}")
        
        # jjhknjkl;',kpm


if __name__ == "__main__":
    test_auth()
