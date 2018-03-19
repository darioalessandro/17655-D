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

const names = [
  'Oliver Hansen',
  'Van Henry',
  'April Tucker',
  'Ralph Hubbard',
  'Omar Alexander',
  'Carlos Abbott',
  'Miriam Wagner',
  'Bradley Wilkerson',
  'Virginia Andrews',
  'Kelly Snyder',
];

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

class Orders extends React.Component {

    handleChange = name => event => {
      this.setState({
        [name]: event.target.value,
      });
    };

    state = {
      name: [],

      };




    render() {
      const { classes, theme, onSelectAllClick, order, orderBy, numSelected, rowCount } = this.props;


      return (

        <div>
        <Typography>Orders</Typography>
        <form className={classes.container} noValidate autoComplete="off">
            <TextField
              required
              id="required"
              label="First Name"
              className={classes.textField}
              margin="normal"
            />
            <TextField
              required
              id="required"
              label="Last Name"
              className={classes.textField}
              margin="normal"
            />
            <TextField
              required
              id="required"
              label="Address"
              className={classes.textField}
              margin="normal"
            />
            <TextField
              required
              id="required"
              label="Phone Number"
              className={classes.textField}
              margin="normal"
            />

          </form>

          <Button variant="raised" color="primary" className={classes.button}>
            Trees
          </Button>
          <Button variant="raised" color="primary" className={classes.button}>
            Seeds
          </Button>
          <Button variant="raised" color="primary" className={classes.button}>
            Shrubs
          </Button>

          <Paper className={classes.root}>
            <Table className={classes.table}>
              <TableHead>
                <TableRow>
                  <TableCell padding="checkbox">
                  </TableCell>
                  <TableCell>Product ID</TableCell>
                  <TableCell>Product Desc</TableCell>
                  <TableCell>Price ($)</TableCell>
                  <TableCell numeric>Items In Stock</TableCell>
                </TableRow>
              </TableHead>
              <TableBody>
                {tableData.map(n => {

                  return (

                    <TableRow key={n.id}>
                    <TableCell padding="checkbox">
                      <Checkbox />
                    </TableCell>
                      <TableCell>{n.productID}</TableCell>
                      <TableCell numeric>{n.productDesc}</TableCell>
                      <TableCell numeric>{n.productPrice}</TableCell>
                      <TableCell numeric>{n.productStock}</TableCell>
                    </TableRow>
                  );
                })}
              </TableBody>
            </Table>
          </Paper>


          <FormControl fullWidth className={classes.margin}>
            <InputLabel htmlFor="adornment-amount">Amount</InputLabel>
            <Input
              id="adornment-amount"
              value={this.state.amount}
              onChange={this.handleChange('amount')}
              startAdornment={<InputAdornment position="start">$</InputAdornment>}
            />
          </FormControl>


          <Button variant="raised" color="primary" className={classes.button}>
            Submit Order
          </Button>


        </div>

      );
  }
}



export default withStyles(styles, { withTheme: true })(Orders);
