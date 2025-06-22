import React, { useState } from 'react';
import { 
  Box, 
  Typography, 
  Paper, 
  TextField, 
  Button,
  List,
  ListItem,
  Divider
} from '@mui/material';

interface ChatMessage {
  id: string;
  text: string;
  isUser: boolean;
  timestamp: Date;
}

const AIChat: React.FC = () => {
  const [chatInput, setChatInput] = useState('');
  const [messages, setMessages] = useState<ChatMessage[]>([]);

  const handleSendMessage = () => {
    if (chatInput.trim()) {
      const userMessage: ChatMessage = {
        id: Date.now().toString(),
        text: chatInput,
        isUser: true,
        timestamp: new Date()
      };

      // Add user message to chat
      setMessages(prev => [...prev, userMessage]);

      // Simulate AI response (replace with actual AI call later)
      setTimeout(() => {
        const aiMessage: ChatMessage = {
          id: (Date.now() + 1).toString(),
          text: `I received your message: "${chatInput}". This is a placeholder response. In the future, I'll analyze your DBM files and sizing sheets to provide equipment sizing recommendations.`,
          isUser: false,
          timestamp: new Date()
        };
        setMessages(prev => [...prev, aiMessage]);
      }, 1000);

      setChatInput('');
    }
  };

  const handleKeyPress = (event: React.KeyboardEvent) => {
    if (event.key === 'Enter' && !event.shiftKey) {
      event.preventDefault();
      handleSendMessage();
    }
  };

  return (
    <Box sx={{ p: 3 }}>
      <Typography variant="h4" gutterBottom>
        AI Chat
      </Typography>
      
      <Paper sx={{ p: 2, mt: 2 }}>
        <Typography variant="body1" gutterBottom>
          Chat with AI to get equipment sizing recommendations.
        </Typography>

        {/* Chat Window */}
        <Paper 
          variant="outlined" 
          sx={{ 
            height: '400px', 
            overflow: 'auto', 
            mb: 2, 
            p: 2,
            backgroundColor: '#f5f5f5'
          }}
        >
          {messages.length === 0 ? (
            <Typography variant="body2" color="text.secondary" sx={{ textAlign: 'center', mt: 10 }}>
              Start a conversation with the AI...
            </Typography>
          ) : (
            <List>
              {messages.map((message, index) => (
                <React.Fragment key={message.id}>
                  <ListItem sx={{ 
                    justifyContent: message.isUser ? 'flex-end' : 'flex-start',
                    px: 0
                  }}>
                    <Paper sx={{ 
                      p: 2, 
                      maxWidth: '70%',
                      backgroundColor: message.isUser ? '#e3f2fd' : '#fff',
                      borderRadius: 2
                    }}>
                      <Typography variant="body2" sx={{ mb: 1 }}>
                        {message.text}
                      </Typography>
                      <Typography variant="caption" color="text.secondary">
                        {message.timestamp.toLocaleTimeString()}
                      </Typography>
                    </Paper>
                  </ListItem>
                  {index < messages.length - 1 && <Divider sx={{ my: 1 }} />}
                </React.Fragment>
              ))}
            </List>
          )}
        </Paper>

        {/* Chat Input */}
        <Box sx={{ display: 'flex', gap: 1 }}>
          <TextField
            fullWidth
            multiline
            rows={2}
            label="Type your message..."
            variant="outlined"
            value={chatInput}
            onChange={(e) => setChatInput(e.target.value)}
            onKeyPress={handleKeyPress}
            placeholder="Press Enter to send, Shift+Enter for new line"
          />
          <Button 
            variant="contained" 
            color="primary"
            onClick={handleSendMessage}
            disabled={!chatInput.trim()}
            sx={{ minWidth: '100px' }}
          >
            Send
          </Button>
        </Box>
      </Paper>
    </Box>
  );
};

export default AIChat; 