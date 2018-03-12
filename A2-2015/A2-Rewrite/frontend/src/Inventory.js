import React from 'react';
import Typography from 'material-ui/Typography';

class Inventory extends React.Component {
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
        return fetch(`http://localhost:3001/products`).then(result=>result.json());
    }

    render() {
        console.log('this.state.products ', JSON.stringify(this.state));
        return <div>
            <Typography>Inventory</Typography>
            {
               (this.state.products.length === 0) ?
                   <Typography>No products found! this is most likely a db error</Typography>
                   : this.state.products.map(p => {
                       const key = `${p.company_id}-${p.category}-${p.product_code}`;
                       return <Typography key={key}>{p.company_id} {p.category} {p.product_code} {p.description} {p.price} </Typography>
                   })
            }
        </div>
    }
}

export default Inventory;