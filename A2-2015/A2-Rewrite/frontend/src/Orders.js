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
    width: 220,
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
    margin: 40,
    minWidth: 320,
    maxWidth: 500,
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

class Orders extends React.Component {
    state = {
      checked: [0],
      };

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


    render() {
      const { classes, theme, onSelectAllClick, order, orderBy, numSelected, rowCount } = this.props;
      const { data, selected } = this.state;


      return (
        <div className={classes.root}>
        <Typography variant="headline" gutterBottom>Orders</Typography>
         <Grid container spacing={24}>
         <Grid item xs={4}>
          <Paper className={classes.paper}>
          <Typography variant="subheading" gutterBottom>Customer Information</Typography>

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
          </Paper>
        </Grid>
        <Grid item xs={8}>
          <Paper className={classes.paper}>
            <Typography variant="subheading" gutterBottom>Choose your Inventory: </Typography>

            <Button variant="raised" color="primary" className={classes.button}>Trees</Button>
            <Button variant="raised" color="primary" className={classes.button}>
              Seeds
            </Button>
            <Button variant="raised" color="primary" className={classes.button}>
              Shrubs
            </Button>

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
                    <TableRow
                      hover
                      key={n.id}
                    >
                    <TableCell padding="checkbox">
                    <Checkbox
                      onChange={this.handleToggle(n.id)}
                      checked={this.state.checked.indexOf(n.id) !== -1}
                    />
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

          <Divider light />
          <FormControl fullWidth className={classes.margin}>
            <InputLabel htmlFor="adornment-amount">Calculated Total Cost</InputLabel>
            <Input
              id="adornment-amount"
              value={this.state.amount}
              startAdornment={<InputAdornment position="start">$</InputAdornment>}
            />
          </FormControl>
          </Paper>
          <Button variant="raised" color="primary" className={classes.fullbutton} fullWidth="true">
            Submit Order
          </Button>
        </Grid>
      </Grid>



        </div>

      );
  }
}



export default withStyles(styles, { withTheme: true })(Orders);
