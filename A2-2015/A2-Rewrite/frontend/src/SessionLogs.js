import React from 'react';
import Typography from 'material-ui/Typography';

class SessionLogs extends React.Component {
    constructor(props) {
        super();
        this.state={products:[]};
    }

    async componentDidMount() {
        const products = await this.fetchProducts();
        console.log('got products ', JSON.stringify(products));
        this.setState({ products: products });
    }

    fetchProducts() {
        return fetch(`${this.props.backendURL}/auth/logs`).then(result=>result.json());
    }

    render() {
        return <div>
            <Typography>Session Logs</Typography>
            {
               (this.state.products.length === 0) ?
                   <Typography>No login data found! this is most likely a db error</Typography>
                   : this.state.products.map(p => {
                       const key = `${p.email}${p.id}`;
                       return <Typography key={key}>{p.email} {p.name} {p.created_at} {p.event} </Typography>
                   })
            }
        </div>
    }
}

export default SessionLogs;