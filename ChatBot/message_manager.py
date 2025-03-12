class MessageManager:
    def __init__(self):
        self.messages = [
            {
                "role": "system",
                "content": [
                    {
                        "type": "text",
                        "text": "You are an AI assistant that helps people find information. Keep your answers short and to the point"
                    }
                ]
            }
        ]

    def add_user_message(self, content):
        self.messages.append({"role": "user", "content": content})

    def add_assistant_message(self, content):
        self.messages.append({"role": "assistant", "content": content})

    def get_messages(self):
        return self.messages

    def clear_messages(self):
        self.messages = [self.messages[0]]  # Keep the system message 