import React from 'react';
import Typography from 'material-ui/Typography';

class Orders extends React.Component {

    async componentDidMount() {
        const products = await this.fetchProducts();
        console.log('got products ', JSON.stringify(products));
        this.setState({ products: products });
    }

    fetchProducts() {
        return fetch(`${this.props.backendURL}/products`).then(result=>result.json());
    }

    render() {
        return <Typography>Orders</Typography>
    }
}

export default Orders;