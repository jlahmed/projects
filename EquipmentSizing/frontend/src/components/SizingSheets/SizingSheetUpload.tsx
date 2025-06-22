import React from 'react';
import { Box, Typography, Paper } from '@mui/material';

const SizingSheetUpload: React.FC = () => {
  return (
    <Box sx={{ p: 3 }}>
      <Typography variant="h4" gutterBottom>
        Sizing Sheets Upload & Management
      </Typography>
      <Paper sx={{ p: 2, mt: 2 }}>
        <Typography variant="body1">
          Upload your Excel sizing sheets here for AI processing.
        </Typography>
        {/* TODO: Add file upload component */}
      </Paper>
    </Box>
  );
};

export default SizingSheetUpload; 