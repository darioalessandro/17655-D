import React from "react";
import Typography from "material-ui/Typography";
import { withStyles } from "material-ui/styles";
import Button from "material-ui/Button";
import TextField from "material-ui/TextField";
import Select from "material-ui/Select";
import MenuItem from "material-ui/Menu/MenuItem";
//import RaisedButton from 'material-ui/RaisedButton';

import Input, { InputAdornment, InputLabel } from "material-ui/Input";
import { FormControl } from "material-ui/Form";
import Checkbox from "material-ui/Checkbox";
import Grid from "material-ui/Grid";
import Divider from "material-ui/Divider";
import Table, {
  TableBody,
  TableCell,
  TableHead,
  TableRow
} from "material-ui/Table";
import Paper from "material-ui/Paper";

const styles = theme => ({
  button: {
    margin: theme.spacing.unit,
    width: 220,
    margin: 10
  },
  fullbutton: {
    margin: theme.spacing.unit
  },
  margin: {
    margin: theme.spacing.unit
  },
  container: {
    display: "flex",
    flexWrap: "wrap",
    margin: 40,
    minWidth: 320,
    maxWidth: 500
  },
  ordersContainer: {
    height: "100%",
    overflowY: "scroll"
  },
  textField: {
    marginLeft: theme.spacing.unit,
    marginRight: theme.spacing.unit,
    width: 200
  },
  menu: {
    width: 200
  },
  formControl: {
    margin: theme.spacing.unit,
    minWidth: 120,
    maxWidth: 300
  },
  chips: {
    display: "flex",
    flexWrap: "wrap"
  },
  chip: {
    margin: theme.spacing.unit / 4
  },
  paper: {
    padding: theme.spacing.unit * 2,
    color: theme.palette.text.secondary
  }
});

const ITEM_HEIGHT = 24;
const ITEM_PADDING_TOP = 4;
const MenuProps = {
  PaperProps: {
    style: {
      maxHeight: ITEM_HEIGHT * 4.5 + ITEM_PADDING_TOP,
      width: 250
    }
  }
};

let id = 0;

function createData(productID, productDesc, productPrice, productStock) {
  id += 1;
  return { id, productID, productDesc, productPrice, productStock };
}

const tableData = [
  createData("12345", "test1", "4.00", 24),
  createData("23456", "test2", "8.00", 37),
  createData("34567", "test3", "16.00", 24)
];

class Orders extends React.Component {
  handleToggle = id => () => {
    const { checked } = this.state;
    const { calculateCost } = this.state;
    const { amount } = this.state;

    const currentIndex = checked.indexOf(id);
    const newChecked = [...checked];
    const newCost = [...calculateCost];

    if (currentIndex === -1) {
      newChecked.push(id);
      newCost.push(this.value);
    } else {
      newChecked.splice(currentIndex, 1);
    }

    this.setState({
      checked: newChecked,
      amount: this.value
    });
  };
  submitOrder = () => {
    alert("clicked");
  };

  constructor(props) {
    super();
    this.state = {
      products: [],
      checked: [0],
      category: "",
      firstName: "",
      lastName: "",
      address: "",
      phoneNumber: "",
      calculateCost: [0],
      amount: 0
    };
  }

  async componentDidMount() {
    //const products = await this.fetchAllProducts();
    //this.setState({ products: products });
  };

  fetchFilteredProducts(event) {
    return fetch(`${this.props.backendURL}/products?category_filter=${encodeURIComponent(event)}`).then(result =>
      result.json()
    );
  };

  fetchAllProducts() {
    return fetch(`${this.props.backendURL}/products`).then(result =>
      result.json()
    );
  };

  handleChange(event) {
    this.setState({ firstName: event.target.value });
  };

  async productCategoryChange(event) {
   //const products = this.fetchAllProducts();//this.fetchFilteredProducts(event.target.value);
   this.setState({ category: event.target.value });
   const products = await fetch(`${this.props.backendURL}/products?category_filter=${encodeURIComponent(event.target.value)}`).then(result =>
    result.json()
    );

    console.log("goit filtered products", products);
   this.setState({products: products});
  };

  render() {
    const {
      classes,
      theme,
      onSelectAllClick,
      order,
      orderBy,
      numSelected,
      rowCount
    } = this.props;
    const { data, selected } = this.state;

    return (
      <div style={styles.ordersContainer} className={classes.root}>
        <Typography variant="headline" gutterBottom>
          Orders
        </Typography>
        <Grid container spacing={24}>
          <Grid item xs={4}>
            <Paper className={classes.paper}>
              <Typography variant="subheading" gutterBottom>
                Customer Information
              </Typography>

              <form className={classes.container} noValidate autoComplete="off">
                <TextField
                  required
                  id="firstName"
                  label="First Name"
                  className={classes.textField}
                  onChange={this.handleChange}
                />
                <TextField
                  required
                  id="required"
                  label="Last Name"
                  className={classes.textField}
                  value={this.state.lastName}
                  onChange={this.handleChange}
                />
                <TextField
                  required
                  id="required"
                  label="Address"
                  className={classes.textField}
                  value={this.state.address}
                  onChange={this.handleChange}
                />
                <TextField
                  required
                  id="required"
                  label="Phone Number"
                  className={classes.textField}
                  value={this.state.phoneNumber}
                  onChange={this.handleChange}
                />
              </form>
            </Paper>
          </Grid>
          <Grid item xs={8}>
            <Paper className={classes.paper}>
              <Typography variant="subheading" gutterBottom>
                Product Category:{" "}
              </Typography>

              <Select
                  value={this.state.category}
                  onChange={this.productCategoryChange.bind(this)}
                  inputProps={{
                    name: "category",
                    id: "productCategory"
                  }}
                  className={classes.textField}
                >
                  <MenuItem value={"trees"}>Trees</MenuItem>
                  <MenuItem value={"seeds"}>Seeds</MenuItem>
                  <MenuItem value={"shrubs"}>Shrubs</MenuItem>
                  <MenuItem value={"cultureboxes"}>Culture Boxes</MenuItem>
                  <MenuItem value={"genomics"}>Genomics</MenuItem>
                  <MenuItem value={"referencematerials"}>Reference Materials</MenuItem>
              </Select>

              <Table className={classes.table}>
                <TableHead>
                  <TableRow>
                    <TableCell padding="checkbox" />
                    <TableCell>Product Code</TableCell>
                    <TableCell>Product Desc</TableCell>
                    <TableCell>Price ($)</TableCell>
                    <TableCell numeric>Items In Stock</TableCell>
                  </TableRow>
                </TableHead>
                <TableBody>
                  {this.state.products.map(n => {
                    return (
                      <TableRow hover key={n.product_code}>
                        <TableCell padding="checkbox">
                          <Checkbox
                            onChange={this.handleToggle(n.product_code)}
                            checked={this.state.checked.indexOf(n.product_code) !== -1}
                            value={n.productPrice}
                          />
                        </TableCell>
                        <TableCell>{n.product_code}</TableCell>
                        <TableCell>{n.description}</TableCell>
                        <TableCell numeric>{n.price}</TableCell>
                        <TableCell numeric>{n.quantity}</TableCell>
                      </TableRow>
                    );
                  })}
                </TableBody>
              </Table>

              <Divider light />
              <FormControl fullWidth className={classes.margin}>
                <InputLabel htmlFor="adornment-amount">
                  Calculated Total Cost
                </InputLabel>
                <Input
                  disabled
                  id="adornment-amount"
                  value={this.state.amount}
                  startAdornment={
                    <InputAdornment position="start">$</InputAdornment>
                  }
                />
              </FormControl>
            </Paper>
            <Button
              variant="raised"
              color="primary"
              className={classes.fullbutton}
              fullWidth
              onClick={this.submitOrder}
            >
              Submit Order
            </Button>
          </Grid>
        </Grid>
      </div>
    );
  }
}

export default withStyles(styles, { withTheme: true })(Orders);
