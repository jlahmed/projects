import React, { useState } from 'react';
import { 
  Box, 
  Tabs, 
  Tab, 
  AppBar, 
  Toolbar, 
  Typography,
  ThemeProvider
} from '@mui/material';
import DBMUpload from './components/DBMUpload/DBMUpload';
import SizingSheetUpload from './components/SizingSheets/SizingSheetUpload';
import AIChat from './components/AIChat/AIChat';
import ResultsList from './components/Results/ResultsList';
import { theme } from './theme';

interface TabPanelProps {
  children?: React.ReactNode;
  index: number;
  value: number;
}

function TabPanel(props: TabPanelProps) {
  const { children, value, index, ...other } = props;

  return (
    <div
      role="tabpanel"
      hidden={value !== index}
      id={`simple-tabpanel-${index}`}
      aria-labelledby={`simple-tab-${index}`}
      {...other}
    >
      {value === index && <Box sx={{ p: 3 }}>{children}</Box>}
    </div>
  );
}

function App() {
  const [tabValue, setTabValue] = useState(0);

  const handleTabChange = (event: React.SyntheticEvent, newValue: number) => {
    setTabValue(newValue);
  };

  return (
    <ThemeProvider theme={theme}>
      <Box sx={{ flexGrow: 1, backgroundColor: '#ffffff', minHeight: '100vh', width: '100%' }}>
        <AppBar position="static">
          <Toolbar>
            <Typography variant="h6" component="div" sx={{ flexGrow: 1 }}>
              Equipment Sizing Bot
            </Typography>
          </Toolbar>
        </AppBar>
        
        <Box sx={{ width: '100%' }}>
          <Box sx={{ borderBottom: 1, borderColor: 'divider', mt: 2, width: '100%' }}>
            <Tabs value={tabValue} onChange={handleTabChange} aria-label="Equipment sizing tabs" sx={{ width: '100%' }}>
              <Tab label="DBM Upload" />
              <Tab label="Sizing Sheets" />
              <Tab label="AI Chat" />
              <Tab label="Generated Results" />
            </Tabs>
          </Box>
          
          <TabPanel value={tabValue} index={0}>
            <DBMUpload />
          </TabPanel>
          <TabPanel value={tabValue} index={1}>
            <SizingSheetUpload />
          </TabPanel>
          <TabPanel value={tabValue} index={2}>
            <AIChat />
          </TabPanel>
          <TabPanel value={tabValue} index={3}>
            <ResultsList />
          </TabPanel>
        </Box>
      </Box>
    </ThemeProvider>
  );
}

export default App; 