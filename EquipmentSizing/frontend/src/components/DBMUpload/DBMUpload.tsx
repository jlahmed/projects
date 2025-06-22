import React from 'react';
import { Box, Typography, Paper } from '@mui/material';

const DBMUpload: React.FC = () => {
  return (
    <Box sx={{ p: 3 }}>
      <Typography variant="h4" gutterBottom>
        DBM Upload & Management
      </Typography>
      <Paper sx={{ p: 2, mt: 2 }}>
        <Typography variant="body1">
          Upload your DBM files here for AI parameter extraction.
        </Typography>
        {/* TODO: Add file upload component */}
      </Paper>
    </Box>
  );
};

export default DBMUpload; 