import React from 'react';
import Typography from 'material-ui/Typography';
import PropTypes from 'prop-types';
import { withStyles } from 'material-ui/styles';
import Button from 'material-ui/Button';
import MenuItem from 'material-ui/Menu/MenuItem';
import TextField from 'material-ui/TextField';

import Input, { InputLabel, InputAdornment } from 'material-ui/Input';
import { Menu } from 'material-ui/Menu';
import { FormControl } from 'material-ui/Form';
import { ListItemText } from 'material-ui/List';
import Select from 'material-ui/Select';
import Checkbox from 'material-ui/Checkbox';
import Chip from 'material-ui/Chip';
import Grid from 'material-ui/Grid';
import Divider from 'material-ui/Divider';
import Table, {
  TableBody,
  TableCell,
  TableFooter,
  TableHead,
  TablePagination,
  TableRow,
  TableSortLabel,
} from 'material-ui/Table';
import Toolbar from 'material-ui/Toolbar';
import Paper from 'material-ui/Paper';
import IconButton from 'material-ui/IconButton';
import Tooltip from 'material-ui/Tooltip';
import DeleteIcon from 'material-ui-icons/Delete';
import FilterListIcon from 'material-ui-icons/FilterList';
import { lighten } from 'material-ui/styles/colorManipulator';

const styles = theme => ({
  button: {
    margin: theme.spacing.unit,
    margin: 10,
  },
  fullbutton: {
    margin: theme.spacing.unit,
  },
  margin: {
    margin: theme.spacing.unit,
  },
  container: {
    display: 'flex',
    flexWrap: 'wrap',
  },
  textField: {
    marginLeft: theme.spacing.unit,
    marginRight: theme.spacing.unit,
    width: 200,
  },
  menu: {
    width: 200,
  },
  formControl: {
    margin: theme.spacing.unit,
    minWidth: 120,
    maxWidth: 300,
  },
  chips: {
    display: 'flex',
    flexWrap: 'wrap',
  },
  chip: {
    margin: theme.spacing.unit / 4,
  },
  paper: {
    padding: theme.spacing.unit * 2,
    color: theme.palette.text.secondary,

  },
});

const ITEM_HEIGHT = 48;
const ITEM_PADDING_TOP = 8;
const MenuProps = {
  PaperProps: {
    style: {
      maxHeight: ITEM_HEIGHT * 4.5 + ITEM_PADDING_TOP,
      width: 250,
    },
  },
};

let id = 0;
function createData(productID, productDesc, productPrice, productStock) {
  id += 1;
  return { id, productID, productDesc, productPrice, productStock };
}

const tableData = [
  createData('12345', 'test1', '$4.00', 24),
  createData('23456', 'test2', '$8.00', 37),
  createData('34567', 'test3', '$16.00', 24),
];

class Shipping extends React.Component {
      constructor(props) {
          super();
          this.state={products:[], checked: [0],
          category: '',};
      }

      async componentDidMount() {
          const products = await this.fetchProducts();
          console.log('got products ', JSON.stringify(products));
          this.setState({ products: products });
      }

      fetchProducts() {
          return fetch(`http://localhost:3001/products`).then(result=>result.json());
      }

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
          checked: newChecked,
        });
      };

      handleChange = event => {
        this.setState({ [event.target.name]: event.target.value });
      };

      handleAmount = prop => event => {
        this.setState({ [prop]: event.target.value });
      };


    render() {
      const { classes, theme, onSelectAllClick, order, orderBy, numSelected, rowCount } = this.props;
      const { data, selected } = this.state;


      return (
        <div className={classes.root}>
        <Typography variant="headline" gutterBottom>Shipping</Typography>
         <Grid container spacing={24}>
         <Grid item xs={6}>
          <Paper className={classes.paper}>
          <Typography variant="subheading" gutterBottom>Pending Orders</Typography>

          <Table className={classes.table}>
            <TableHead>
              <TableRow>
                <TableCell padding="checkbox">
                </TableCell>
                <TableCell>Order Number</TableCell>
                <TableCell>Order Date & Time</TableCell>
                <TableCell>Customer Name</TableCell>
              </TableRow>
            </TableHead>
            <TableBody>

            {
               (this.state.products.length === 0) ?
               <TableRow
                 hover
               >
                <TableCell colSpan={4}>No products found! this is most likely a db error</TableCell>
                </TableRow>
                   : this.state.products.map(o => {
                       const key = `${o.orderNum}-${o.orderDate}-${o.customerName}`;
                       return (
                         <TableRow
                           hover
                           key={key}
                         >
                           <TableCell padding="checkbox">
                             <Checkbox
                               onChange={this.handleToggle(o.orderNum)}
                               checked={this.state.checked.indexOf(o.orderNum) !== -1}
                             />
                           </TableCell>
                           <TableCell>{o.orderNum}</TableCell>
                           <TableCell numeric>{o.orderDate}</TableCell>
                           <TableCell numeric>{o.customerName}</TableCell>
                         </TableRow>
                       );
                   })}
            </TableBody>
          </Table>
          <Button variant="raised" color="primary" className={classes.fullbutton} fullWidth="true">Select Pending Order</Button>
          </Paper>
        </Grid>
        <Grid item xs={6}>
          <Paper className={classes.paper}>
          <Typography variant="subheading" gutterBottom>Shipped Orders</Typography>

          <Table className={classes.table}>
            <TableHead>
              <TableRow>
                <TableCell padding="checkbox">
                </TableCell>
                <TableCell>Order Number</TableCell>
                <TableCell>Order Date & Time</TableCell>
                <TableCell>Customer Name</TableCell>
              </TableRow>
            </TableHead>
            <TableBody>

            {
               (this.state.products.length === 0) ?
               <TableRow
                 hover
               >
                <TableCell colSpan={4}>No products found! this is most likely a db error</TableCell>
                </TableRow>
                   : this.state.products.map(o => {
                       const key = `${o.orderNum}-${o.orderDate}-${o.customerName}`;
                       return (
                         <TableRow
                           hover
                           key={key}
                         >
                           <TableCell padding="checkbox">
                             <Checkbox
                               onChange={this.handleToggle(o.orderNum)}
                               checked={this.state.checked.indexOf(o.orderNum) !== -1}
                             />
                           </TableCell>
                           <TableCell>{o.orderNum}</TableCell>
                           <TableCell numeric>{o.orderDate}</TableCell>
                           <TableCell numeric>{o.customerName}</TableCell>
                         </TableRow>
                       );
                   })}
            </TableBody>
          </Table>
          <Button variant="raised" color="primary" className={classes.fullbutton} fullWidth="true">Select Shipped Order</Button>
          </Paper>
        </Grid>

        <Grid item xs={12}>
          <Paper className={classes.paper}>
          <Typography variant="subheading" gutterBottom>Order Information</Typography>

          <form className={classes.container} noValidate autoComplete="off">
              <TextField

                label="First Name"
                className={classes.textField}
                margin="normal"
                disabled = "true"
              />
              <TextField
                label="Last Name"
                className={classes.textField}
                margin="normal"
                disabled = "true"

              />

              <TextField
                label="Phone Number"
                className={classes.textField}
                margin="normal"
                disabled = "true"

              />
              <TextField
                label="Order Date"
                className={classes.textField}
                margin="normal"
                disabled = "true"

              />

              <TextField
                label="Address"
                margin="normal"
                disabled = "true"
                multiline
                fullWidth
                rowsMax="4"
              />

            </form>
          <Table className={classes.table}>
            <TableHead>
              <TableRow>
                <TableCell padding="checkbox">
                </TableCell>
                <TableCell>Order Number</TableCell>
                <TableCell>Order Date & Time</TableCell>
              </TableRow>
            </TableHead>
            <TableBody>

            {
               (this.state.products.length === 0) ?
               <TableRow
                 hover
               >
                <TableCell colSpan={4}>No products found! this is most likely a db error</TableCell>
                </TableRow>
                   : this.state.products.map(o => {
                       const key = `${o.orderNum}-${o.orderDate}-${o.customerName}`;
                       return (
                         <TableRow
                           hover
                           key={key}
                         >
                           <TableCell padding="checkbox">
                             <Checkbox
                               onChange={this.handleToggle(o.orderNum)}
                               checked={this.state.checked.indexOf(o.orderNum) !== -1}
                             />
                           </TableCell>
                           <TableCell>{o.orderNum}</TableCell>
                           <TableCell numeric>{o.orderDate}</TableCell>
                         </TableRow>
                       );
                   })}
            </TableBody>
          </Table>
          <TextField
            label="Messages"
            margin="normal"
            multiline
            fullWidth
            rowsMax="4"
          />
          <Button variant="raised" color="primary" className={classes.fullbutton} fullWidth="true">Mark As Shipped</Button>
          </Paper>
        </Grid>
      </Grid>



        </div>

      );
  }
}



export default withStyles(styles, { withTheme: true })(Shipping);
