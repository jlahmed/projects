import { useState, useEffect, useRef } from 'react'
import {
  Room,
  RoomEvent,
  RemoteTrack
} from 'livekit-client'
import * as speechsdk from 'microsoft-cognitiveservices-speech-sdk'
import './App.css'
const LIVEKIT_URL = import.meta.env.VITE_LIVEKIT_URL;
const SPEECH_KEY = import.meta.env.VITE_SPEECH_KEY;
const SPEECH_REGION = import.meta.env.VITE_SPEECH_REGION;

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
  const [isListening, setIsListening] = useState(false)
  const messagesEndRef = useRef<HTMLDivElement>(null)
  const recognizerRef = useRef<speechsdk.SpeechRecognizer | null>(null)

  useEffect(() => {
    document.title = 'VoiceAssistant';
  }, []);

  const scrollToBottom = () => {
    messagesEndRef.current?.scrollIntoView({ behavior: "smooth" })
  }

  useEffect(() => {
    scrollToBottom()
  }, [messages])

  const initializeSpeechRecognizer = () => {
    const speechConfig = speechsdk.SpeechConfig.fromSubscription(
      SPEECH_KEY,
      SPEECH_REGION
    );
    speechConfig.speechRecognitionLanguage = 'en-US';
    
    const audioConfig = speechsdk.AudioConfig.fromDefaultMicrophoneInput();
    const recognizer = new speechsdk.SpeechRecognizer(speechConfig, audioConfig);

    recognizer.recognized = (s, e) => {
      if (e.result.text && e.result.text.trim() !== '') {
        setMessages(prev => [...prev, {
          text: e.result.text,
          sender: 'You',
          timestamp: new Date()
        }]);
      }
    };

    recognizerRef.current = recognizer;
  };

  const startListening = () => {
    if (recognizerRef.current) {
      recognizerRef.current.startContinuousRecognitionAsync(
        () => {
          setIsListening(true);
          console.log('Speech recognition started');
        },
        (error) => {
          console.error('Error starting recognition:', error);
        }
      );
    }
  };

  const stopListening = () => {
    if (recognizerRef.current) {
      recognizerRef.current.stopContinuousRecognitionAsync(
        () => {
          setIsListening(false);
          console.log('Speech recognition stopped');
        },
        (error) => {
          console.error('Error stopping recognition:', error);
        }
      );
    }
  };

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
      const url = LIVEKIT_URL;
      
      const room = new Room()

      room.on(RoomEvent.DataReceived, (payload: Uint8Array) => {
        try {
          const text = new TextDecoder().decode(payload);
          const message = JSON.parse(text);
          console.log(message.text);
          
          setMessages(prev => [...prev, {
            text: message.text,
            sender: message.type === 'user_speech' ? 'You' : 'AI',
            timestamp: new Date()
          }]);
        } catch (error) {
          console.error('Error processing message:', error);
        }
      });

      room.on(RoomEvent.TrackSubscribed, async (track: RemoteTrack) => {
        if (track.kind === 'audio') {
          try {
            const mediaTrack = track.mediaStreamTrack;
            const stream = new MediaStream([mediaTrack]);

            const audioElement = document.createElement('audio');
            audioElement.srcObject = stream;
            audioElement.autoplay = true;
            document.body.appendChild(audioElement);

            const speechConfig = speechsdk.SpeechConfig.fromSubscription(
              "FslrsjPFv9EBwVBC1XM6z4nKIMIuO9moOTnSRVYm2zjkDUUrq6AZJQQJ99BCAC4f1cMXJ3w3AAAAACOGYBVG",
              "westus"
            );
            speechConfig.speechRecognitionLanguage = 'en-US';

            const audioConfig = speechsdk.AudioConfig.fromStreamInput(stream);
            const recognizer = new speechsdk.SpeechRecognizer(speechConfig, audioConfig);

            recognizer.recognized = (s, e) => {
              if (e.result.text && e.result.text.trim() !== '') {
                console.log('AI Speech recognized:', e.result.text);
                setMessages(prev => [...prev, {
                  text: e.result.text,
                  sender: 'Assistant',
                  timestamp: new Date()
                }]);
              }
            };

            await recognizer.startContinuousRecognitionAsync();
            console.log('AI audio recognition started');

          } catch (err) {
            console.error('Error setting up audio handling:', err);
          }
        }
      });

      await room.connect(url, token);
      await room.localParticipant.setMicrophoneEnabled(true);
      
      setRoom(room);
      initializeSpeechRecognizer();
      startListening();
      
      setMessages([{
        text: "Connected to room. AI assistant is ready to chat.",
        sender: 'System',
        timestamp: new Date()
      }]);
    } catch (err) {
      console.error('Failed to connect to room:', err);
      setError('Failed to connect to room. Please try again.');
    }
  }

  const disconnectFromRoom = async () => {
    if (room) {
      stopListening();
      room.disconnect()
      setRoom(null)
      setMessages([])
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
          <>
            <div className={`listening-indicator ${isListening ? 'active' : ''}`}>
              {isListening ? 'Listening...' : 'Not listening'}
            </div>
            <div className="messages-container">
              {[...messages]
                .sort((a, b) => a.timestamp.getTime() - b.timestamp.getTime())
                .map((message, index) => (
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
          </>
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
  );
}

export default App;