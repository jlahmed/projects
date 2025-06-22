import React from 'react';
import { Box, Typography, Paper, List, ListItem, ListItemText } from '@mui/material';

const ResultsList: React.FC = () => {
  return (
    <Box sx={{ p: 3 }}>
      <Typography variant="h4" gutterBottom>
        Generated Excel Results
      </Typography>
      <Paper sx={{ p: 2, mt: 2 }}>
        <Typography variant="body1" gutterBottom>
          Review and download AI-generated Excel files.
        </Typography>
        <List>
          <ListItem>
            <ListItemText
              primary="Sample Result 1"
              secondary="Generated on: 2024-01-01"
            />
          </ListItem>
          <ListItem>
            <ListItemText
              primary="Sample Result 2"
              secondary="Generated on: 2024-01-02"
            />
          </ListItem>
        </List>
      </Paper>
    </Box>
  );
};

export default ResultsList; 