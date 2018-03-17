import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import registerServiceWorker from './registerServiceWorker';
import EEPSystem from './EEPSystem';

ReactDOM.render(
    <EEPSystem gauthId={process.env.REACT_APP_GOOGLE_OAUTH_TOKEN}/>,
    document.getElementById('root')
);
registerServiceWorker();
