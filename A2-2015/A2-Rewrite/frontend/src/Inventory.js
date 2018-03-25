import React from "react";
import Typography from "material-ui/Typography";
import { withStyles } from "material-ui/styles";
import Button from "material-ui/Button";
import MenuItem from "material-ui/Menu/MenuItem";
import TextField from "material-ui/TextField";

import Input, { InputAdornment, InputLabel } from "material-ui/Input";
import Select from "material-ui/Select";
import Checkbox from "material-ui/Checkbox";
import Grid from "material-ui/Grid";
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

const ITEM_HEIGHT = 48;
const ITEM_PADDING_TOP = 8;
const MenuProps = {
  PaperProps: {
    style: {
      maxHeight: ITEM_HEIGHT * 4.5 + ITEM_PADDING_TOP,
      width: 250
    }
  }
};

class Inventory extends React.Component {
  handleToggle = value => () => {
    const { checked } = this.state;
    const currentIndex = checked.indexOf(value);
    const newChecked = [...checked];

    if (currentIndex === -1) {
      newChecked.push(value);
    } else {
      newChecked.splice(currentIndex, 1);
    }

    this.setState({
      checked: newChecked
    });
  };
  handleChange = event => {
    this.setState({ [event.target.name]: event.target.value });
  };
  handleAmount = prop => event => {
    this.setState({ [prop]: event.target.value });
  };

  constructor(props) {
    super();
    this.state = {
      products: [],
      checked: [0],
      category: ""
    };
  }

  async componentDidMount() {
    const products = await this.fetchProducts();
    this.setState({ products: products });
  }

  fetchProducts() {
    return fetch(`${this.props.backendURL}/products`).then(result =>
      result.json()
    );
  }

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
      <div className={classes.root}>
        <Typography variant="headline" gutterBottom>
          Inventory Management
        </Typography>
        <Grid container spacing={24}>
          <Grid item xs={4}>
            <Paper className={classes.paper}>
              <Typography variant="subheading" gutterBottom>
                Product Information
              </Typography>

              <form className={classes.container} noValidate autoComplete="off">
                <InputLabel
                  htmlFor="productCategory"
                  className={classes.textField}
                >
                  Product Category
                </InputLabel>
                <Select
                  value={this.state.category}
                  onChange={this.handleChange}
                  inputProps={{
                    name: "category",
                    id: "productCategory"
                  }}
                  className={classes.textField}
                >
                  <MenuItem value={"trees"}>Trees</MenuItem>
                  <MenuItem value={"seeds"}>Seeds</MenuItem>
                  <MenuItem value={"shrubs"}>Shrubs</MenuItem>
                  <MenuItem value={"culture"}>Culture Boxes</MenuItem>
                  <MenuItem value={"genomics"}>Genomics</MenuItem>
                  <MenuItem value={"processing"}>Processing</MenuItem>
                  <MenuItem value={"refrence"}>Reference Materials</MenuItem>
                </Select>

                <TextField
                  required
                  id="required"
                  label="Product ID"
                  className={classes.textField}
                />
                <TextField
                  required
                  id="required"
                  label="Quantity"
                  className={classes.textField}
                />
                <TextField
                  required
                  id="required"
                  label="Product Desc"
                  className={classes.textField}
                />
                <InputLabel
                  htmlFor="adornment-amount"
                  className={classes.textField}
                >
                  Retail Price *
                </InputLabel>
                <Input
                  required
                  id="adornment-amount"
                  className={classes.textField}
                  value={this.state.amount}
                  onChange={this.handleAmount("amount")}
                  startAdornment={
                    <InputAdornment position="start">$</InputAdornment>
                  }
                />
              </form>
              <Button
                variant="raised"
                color="primary"
                className={classes.fullbutton}
                fullWidth
              >
                Add Item
              </Button>
            </Paper>
          </Grid>
          <Grid item xs={8}>
            <Paper className={classes.paper}>
              <Typography variant="subheading" gutterBottom>
                Current Inventory:{" "}
              </Typography>

              <Table className={classes.table}>
                <TableHead>
                  <TableRow>
                    <TableCell padding="checkbox" />
                    <TableCell>Company ID</TableCell>
                    <TableCell>Category</TableCell>
                    <TableCell>Product Code</TableCell>
                    <TableCell>Description</TableCell>
                    <TableCell>Price ($)</TableCell>
                  </TableRow>
                </TableHead>
                <TableBody>
                  {this.state.products.length === 0 ? (
                    <TableRow hover>
                      <TableCell colSpan={6}>
                        No products found! this is most likely a db error
                      </TableCell>
                    </TableRow>
                  ) : (
                    this.state.products.map(p => {
                      const key = `${p.company_id}-${p.category}-${
                        p.product_code
                      }`;
                      return (
                        <TableRow hover key={key}>
                          <TableCell padding="checkbox">
                            <Checkbox
                              onChange={this.handleToggle(p.product_code)}
                              checked={
                                this.state.checked.indexOf(p.product_code) !==
                                -1
                              }
                            />
                          </TableCell>
                          <TableCell>{p.company_id}</TableCell>
                          <TableCell numeric>{p.category}</TableCell>
                          <TableCell numeric>{p.product_code}</TableCell>
                          <TableCell numeric>{p.description}</TableCell>
                          <TableCell numeric>{p.price}</TableCell>
                        </TableRow>
                      );
                    })
                  )}
                </TableBody>
              </Table>
              <Button
                variant="raised"
                color="primary"
                className={classes.button}
              >
                Delete Selected Items
              </Button>
              <Button
                variant="raised"
                color="primary"
                className={classes.button}
              >
                Decrement Selcted Items
              </Button>
            </Paper>
          </Grid>
        </Grid>
      </div>
    );
  }
}

export default withStyles(styles, { withTheme: true })(Inventory);
