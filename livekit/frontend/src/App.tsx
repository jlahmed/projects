import { useState, useEffect, useRef } from 'react'
import {
  Room,
  RoomEvent,
  RemoteParticipant,
  RemoteTrack,
  RoomOptions
} from 'livekit-client'
import './App.css'

interface ChatMessage {
  text: string;
  sender: string;
  timestamp: Date;
}

function App() {
  const [isChatStarted, setIsChatStarted] = useState(false)
  const [messages, setMessages] = useState<ChatMessage[]>([])
  const [room, setRoom] = useState<Room | null>(null)
  const [error, setError] = useState<string>('')
  const messagesEndRef = useRef<HTMLDivElement>(null)

  const scrollToBottom = () => {
    messagesEndRef.current?.scrollIntoView({ behavior: "smooth" })
  }

  useEffect(() => {
    scrollToBottom()
  }, [messages])

  const getToken = async () => {
    try {
      const response = await fetch('http://localhost:5000/get_token', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({
          room: 'ai-chat-room',
          username: 'user'
        })
      });
      
      if (!response.ok) {
        throw new Error('Failed to get token');
      }
      
      const data = await response.json();
      return data.token;
    } catch (err) {
      console.error('Error getting token:', err);
      throw err;
    }
  }

  const connectToRoom = async () => {
    try {
      const token = await getToken();
      const url = 'wss://bot-6ohqqmrh.livekit.cloud'
      
      const roomOptions: RoomOptions = {
        adaptiveStream: true,
        dynacast: true
      }
      
      const room = new Room(roomOptions)

      room.on(RoomEvent.TrackSubscribed, (track: RemoteTrack) => {
        if (track.kind === 'audio') {
          // Handle incoming audio
          track.attach()
        }
      })

      room.on(RoomEvent.DataReceived, (payload: Uint8Array, participant?: RemoteParticipant) => {
        const message = JSON.parse(new TextDecoder().decode(payload))
        setMessages(prev => [...prev, {
          text: message.text,
          sender: participant?.identity || 'AI',
          timestamp: new Date()
        }])
      })

      await room.connect(url, token)
      await room.localParticipant.setMicrophoneEnabled(true)
      
      setRoom(room)
      setMessages(prev => [...prev, {
        text: "Connected to room. AI assistant is ready to chat.",
        sender: 'System',
        timestamp: new Date()
      }])
    } catch (err) {
      console.error('Failed to connect to room:', err)
      setError('Failed to connect to room. Please try again.')
    }
  }

  const disconnectFromRoom = async () => {
    if (room) {
      room.disconnect()
      setRoom(null)
      setMessages(prev => [...prev, {
        text: "Disconnected from room.",
        sender: 'System',
        timestamp: new Date()
      }])
    }
  }

  const handleChatToggle = async () => {
    if (!isChatStarted) {
      await connectToRoom()
    } else {
      await disconnectFromRoom()
    }
    setIsChatStarted(!isChatStarted)
  }

  return (
    <div className="app-container">
      <div className="chat-window">
        {error ? (
          <div className="error-message">{error}</div>
        ) : isChatStarted ? (
          <div className="messages-container">
            {messages.map((message, index) => (
              <div 
                key={index} 
                className={`message ${message.sender.toLowerCase()}`}
              >
                <div className="message-header">
                  <span className="sender">{message.sender}</span>
                  <span className="timestamp">
                    {message.timestamp.toLocaleTimeString()}
                  </span>
                </div>
                <div className="message-content">{message.text}</div>
              </div>
            ))}
            <div ref={messagesEndRef} />
          </div>
        ) : (
          <div className="placeholder-text">
            Start a conversation to begin chatting
          </div>
        )}
      </div>
      <button 
        className={`start-button ${isChatStarted ? 'stop' : ''}`}
        onClick={handleChatToggle}
      >
        {isChatStarted ? 'Stop Conversation' : 'Start Conversation'}
      </button>
    </div>
  )
}

export default App
