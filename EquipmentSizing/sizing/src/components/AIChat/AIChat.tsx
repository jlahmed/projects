import React from 'react';
import { Box, Typography, Paper, TextField, Button } from '@mui/material';

const AIChat: React.FC = () => {
  return (
    <Box sx={{ p: 3 }}>
      <Typography variant="h4" gutterBottom>
        AI Chat & Search
      </Typography>
      <Paper sx={{ p: 2, mt: 2 }}>
        <Typography variant="body1" gutterBottom>
          Chat with AI and search through your uploads.
        </Typography>
        <TextField
          fullWidth
          label="Search uploads..."
          variant="outlined"
          sx={{ mb: 2 }}
        />
        <TextField
          fullWidth
          multiline
          rows={4}
          label="Chat with AI..."
          variant="outlined"
          sx={{ mb: 2 }}
        />
        <Button variant="contained" color="primary">
          Send Message
        </Button>
      </Paper>
    </Box>
  );
};

export default AIChat; 