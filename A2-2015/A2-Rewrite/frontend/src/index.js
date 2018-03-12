import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import registerServiceWorker from './registerServiceWorker';
import EEPSystem from './EEPSystem';

ReactDOM.render(
    <EEPSystem />,
    document.getElementById('root')
);
registerServiceWorker();
