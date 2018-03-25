import React from "react";
import Typography from "material-ui/Typography";
import { withStyles } from "material-ui/styles";
import Button from "material-ui/Button";
import TextField from "material-ui/TextField";
import Checkbox from "material-ui/Checkbox";
import { FormGroup, FormControlLabel } from "material-ui/Form";
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
    flexWrap: "wrap"
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
    color: theme.palette.text.secondary,
    overflowX: "auto"
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

let id = 0;

function createData(productID, productDesc, productPrice, productStock) {
  id += 1;
  return { id, productID, productDesc, productPrice, productStock };
}

class Shipping extends React.Component {
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

  handleChange = name => event => {
    this.setState({
      [name]: event.target.value
    });
  };
  handleAmount = prop => event => {
    this.setState({ [prop]: event.target.value });
  };

  handleClick = async (
    event,
    id,
    first,
    last,
    address,
    phone,
    date,
    shipped
  ) => {
    const orderDetails = await this.fetchOrderDetails(id);
    this.setState({
      selectedOrder: id,
      orderFirst: first,
      orderLast: last,
      orderAddress: address,
      orderPhone: phone,
      orderItems: orderDetails,
      orderDate: date,
      orderShipped: shipped
    });
  };

  isSelected = id => this.state.selectedOrder == id;

  async markAsShipped() {
    const a = await this.updateOrderShipped(this.state.selectedOrder);
    this.setState({ orderShipped: true });
    const orders = await this.fetchOrders(
      this.state.showPending,
      this.state.showShipped
    );
    this.setState({ orders: orders });
  }

  constructor(props) {
    super();
    this.state = {
      orders: [],
      checked: [0],
      category: "",
      selectedOrder: null,
      orderFirst: "",
      orderLast: "",
      orderAddress: "",
      orderPhone: "",
      orderItems: [],
      orderDate: null,
      orderShipped: true,
      showPending: true,
      showShipped: true
    };
  }

  async componentDidMount() {
    // do nothing for now

    const orders = await this.fetchOrders(
      this.state.showPending,
      this.state.showShipped
    );
    this.setState({ orders: orders });
  }

  fetchOrders(showPending, showShipped) {
    console.log("fetching ...");
    return fetch(
      `${this.props.backendURL}/orders?show_pending=${encodeURIComponent(
        showPending
      )}&show_shipped=${encodeURIComponent(showShipped)}`
    ).then(result => result.json());
  }
  fetchOrderDetails(orderId) {
    return fetch(
      `${this.props.backendURL}/order_item?order_id=${encodeURIComponent(
        orderId
      )}`
    ).then(result => result.json());
  }
  updateOrderShipped(orderId) {
    return fetch(
      `${
        this.props.backendURL
      }/mark_order_shipped?order_id=${encodeURIComponent(orderId)}`
    ).then(result => result);
  }

  async toggleShowPending() {
    this.setState({ showPending: !this.state.showPending });
    const orders = await this.fetchOrders(
      !this.state.showPending,
      this.state.showShipped
    );
    this.setState({ orders: orders });
  }
  async toggleShowShipped() {
    this.setState({ showShipped: !this.state.showShipped });
    const orders = await this.fetchOrders(
      this.state.showPending,
      !this.state.showShipped
    );
    this.setState({ orders: orders });
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
    const isShipped = this.state.orderShipped == true;
    return (
      <div className={classes.root}>
        <Typography variant="headline" gutterBottom>
          Shipping
        </Typography>
        <Grid container spacing={24}>
          <Grid item xs={6}>
            <Paper className={classes.paper}>
              <Typography variant="subheading" gutterBottom>
                Orders
              </Typography>
              <FormGroup row>
                <FormControlLabel
                  control={
                    <Checkbox
                      checked={this.state.showPending}
                      onChange={this.toggleShowPending.bind(this)}
                      value="checkedA"
                    />
                  }
                  label="Show Pending Orders"
                />
                <FormControlLabel
                  control={
                    <Checkbox
                      checked={this.state.showShipped}
                      onChange={this.toggleShowShipped.bind(this)}
                      value="checkedB"
                      color="primary"
                    />
                  }
                  label="Show Shipped Orders"
                />
              </FormGroup>

              <Table className={classes.table}>
                <TableHead>
                  <TableRow>
                    <TableCell>Order Number</TableCell>
                    <TableCell>Order Date & Time</TableCell>
                    <TableCell>Customer First Name</TableCell>
                    <TableCell>Customer Last Name</TableCell>
                    <TableCell>Order Shipped?</TableCell>
                  </TableRow>
                </TableHead>
                <TableBody>
                  {this.state.orders.length === 0 ? (
                    <TableRow hover>
                      <TableCell colSpan={4} />
                    </TableRow>
                  ) : (
                    this.state.orders.map(o => {
                      const isSelected = this.isSelected(o.id);
                      return (
                        <TableRow
                          hover
                          key={o.id}
                          onClick={event =>
                            this.handleClick(
                              event,
                              o.id,
                              o.customer_first_name,
                              o.customer_last_name,
                              o.customer_address,
                              o.customer_phone,
                              o.created_at,
                              o.shipped_flag
                            )
                          }
                          selected={isSelected}
                        >
                          <TableCell>{o.id}</TableCell>
                          <TableCell numeric>{o.created_at}</TableCell>
                          <TableCell numeric>{o.customer_first_name}</TableCell>
                          <TableCell numeric>{o.customer_last_name}</TableCell>
                          <TableCell>{o.shipped_flag.toString()}</TableCell>
                        </TableRow>
                      );
                    })
                  )}
                </TableBody>
              </Table>
            </Paper>
          </Grid>

          <Grid item xs={6}>
            <Paper className={classes.paper}>
              <Typography variant="subheading" gutterBottom>
                Order Information
              </Typography>

              <form className={classes.container} noValidate autoComplete="off">
                <TextField
                  label="Order ID"
                  type="number"
                  className={classes.textField}
                  value={this.state.selectedOrder}
                  disabled="true"
                />
                <TextField
                  label="Order Shipped?"
                  className={classes.textField}
                  value={this.state.orderShipped}
                  disabled="true"
                />
                <TextField
                  label="First Name"
                  className={classes.textField}
                  value={this.state.orderFirst}
                  disabled="true"
                />
                <TextField
                  label="Last Name"
                  className={classes.textField}
                  value={this.state.orderLast}
                  disabled="true"
                />

                <TextField
                  label="Phone Number"
                  className={classes.textField}
                  value={this.state.orderPhone}
                  disabled="true"
                />
                <TextField
                  numeric
                  label="Order Date"
                  className={classes.textField}
                  value={this.state.orderDate}
                  disabled="true"
                />

                <TextField
                  label="Address"
                  disabled="true"
                  value={this.state.orderAddress}
                  multiline
                  fullWidth
                  rowsMax="4"
                />
              </form>
              <Table className={classes.table}>
                <TableHead>
                  <TableRow>
                    <TableCell>Product Category</TableCell>
                    <TableCell>Product Code</TableCell>
                    <TableCell>Quantity</TableCell>
                  </TableRow>
                </TableHead>
                <TableBody>
                  {this.state.orderItems.length === 0 ? (
                    <TableRow hover>
                      <TableCell colSpan={4} />
                    </TableRow>
                  ) : (
                    this.state.orderItems.map(o => {
                      const key = `${o.order_id}-${o.product_company_id}-${
                        o.product_category
                      }-${o.product_code}`;
                      return (
                        <TableRow hover key={key}>
                          <TableCell>{o.product_category}</TableCell>
                          <TableCell>{o.product_code}</TableCell>
                          <TableCell numeric>{o.quantity}</TableCell>
                        </TableRow>
                      );
                    })
                  )}
                </TableBody>
              </Table>
              <Button
                variant="raised"
                color="primary"
                disabled={isShipped}
                className={classes.fullbutton}
                onClick={this.markAsShipped.bind(this)}
                fullWidth
              >
                Mark As Shipped
              </Button>
            </Paper>
          </Grid>
        </Grid>
      </div>
    );
  }
}

export default withStyles(styles, { withTheme: true })(Shipping);
