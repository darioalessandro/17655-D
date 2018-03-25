import React from "react";
import { withStyles } from "material-ui/styles";
import Table, {
  TableBody,
  TableCell,
  TableHead,
  TableRow
} from "material-ui/Table";
import Paper from "material-ui/Paper";

const styles = theme => ({
  root: {
    width: "100%",
    marginTop: theme.spacing.unit * 3,
    overflowX: "auto"
  },
  table: {
    minWidth: 700
  }
});

class SessionLogs extends React.Component {
  constructor(props) {
    super();
    this.state = { results: [] };
  }

  async componentDidMount() {
    const results = await this.fetchProducts();
    this.setState({ results: results });
  }

  fetchProducts() {
    return fetch(`${this.props.backendURL}/auth/logs`).then(result =>
      result.json()
    );
  }

  render() {
    const { classes } = this.props;
    const { results } = this.state;

    return (
      <Paper className={classes.root}>
        <Table className={classes.table}>
          <TableHead>
            <TableRow>
              <TableCell>email</TableCell>
              <TableCell>name</TableCell>
              <TableCell>date</TableCell>
              <TableCell>event</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {results.map(n => {
              return (
                <TableRow key={n.id}>
                  <TableCell>{n.email}</TableCell>
                  <TableCell>{n.name}</TableCell>
                  <TableCell>{n.created_at}</TableCell>
                  <TableCell>{n.event}</TableCell>
                </TableRow>
              );
            })}
          </TableBody>
        </Table>
      </Paper>
    );
  }
}

export default withStyles(styles)(SessionLogs);
