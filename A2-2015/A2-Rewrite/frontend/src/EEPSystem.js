import React from 'react';
import PropTypes from 'prop-types';
import { withStyles } from 'material-ui/styles';
import Drawer from 'material-ui/Drawer';
import Card, { CardHeader, CardContent } from 'material-ui/Card';
import AppBar from 'material-ui/AppBar';
import Toolbar from 'material-ui/Toolbar';
import Typography from 'material-ui/Typography';
import IconButton from 'material-ui/IconButton';
import Button from 'material-ui/Button';
import Hidden from 'material-ui/Hidden';
import Divider from 'material-ui/Divider';
import MenuIcon from 'material-ui-icons/Menu';
import GoogleLogin from 'react-google-login';
import { ListItem, ListItemIcon, ListItemText } from 'material-ui/List';
import OrdersIcon from 'material-ui-icons/LocalGroceryStore';
import InventoryIcon from 'material-ui-icons/Work';
import ShippingIcon from 'material-ui-icons/LocalShipping';
import LockIcon from 'material-ui-icons/Lock';

import Orders from './Orders'
import Inventory from './Inventory'
import Shipping from './Shipping'
import SessionLogs from './SessionLogs'

const drawerWidth = 240;

const styles = theme => ({
    googleButton: {
        display: 'inline-block',
        background: 'rgb(209, 72, 54)',
        color: 'rgb(255, 255, 255)',
        width: '100%',
        paddingTop: '10px',
        paddingBottom: '10px',
        borderRadius: '2px',
        border: '1px solid transparent',
        fontSize: '16px',
        fontWeight: 'bold',
        fontFamily: 'Roboto',
    },
    login: {
        display: 'flex',
        justifyContent: 'center',
        alignItems: 'center',
        height: '100%',
        background: '#e5e8e5',
    },

    card: {
        alignSelf: 'center',
        minWidth: 275,
        alignItems:'center',
        justifyContent:'center',
    },
    root: {
        flexGrow: 1,
        height: '100%',
        zIndex: 1,
        overflow: 'hidden',
        position: 'relative',
        display: 'flex',
        width: '100%',
        background: '#e5e8e5',
    },
    appBar: {
        position: 'absolute',
        marginLeft: drawerWidth,
        [theme.breakpoints.up('md')]: {
            width: `calc(100% - ${drawerWidth}px)`,
        },
    },
    navIconHide: {
        [theme.breakpoints.up('md')]: {
            display: 'none',
        },
    },
    toolbar: theme.mixins.toolbar,
    drawerPaper: {
        width: drawerWidth,
        [theme.breakpoints.up('md')]: {
            position: 'relative',
        },
    },
    content: {
        flexGrow: 1,
        backgroundColor: theme.palette.background.default,
        padding: theme.spacing.unit * 3,
    },
    flex: {
        flex: 1,
    },
});

const InventorySection = 'Inventory';
const OrdersSection = 'Orders';
const ShippingSection = 'Shipping';
const SessionsSection = 'Session Logs';

class EEPSystem extends React.Component {
    state = {
        mobileOpen: false,
        section: InventorySection,
        authToken: null,
    };

    handleDrawerToggle = () => {
        this.setState({ mobileOpen: !this.state.mobileOpen });
    };

    render() {
        const { classes, theme } = this.props;

        const drawer = (
            <div>
                <div className={classes.toolbar} />
                <Divider />
                <ListItem button onClick={() => this.setState({ section: OrdersSection})}>
                    <ListItemIcon>
                        <OrdersIcon />
                    </ListItemIcon>
                    <ListItemText primary="Orders" />
                </ListItem>
                <ListItem button onClick={() => this.setState({ section: InventorySection})}>
                    <ListItemIcon>
                        <InventoryIcon />
                    </ListItemIcon>
                    <ListItemText primary="Inventory" />
                </ListItem>
                <ListItem button onClick={() => this.setState({ section: ShippingSection})}>
                    <ListItemIcon>
                        <ShippingIcon />
                    </ListItemIcon>
                    <ListItemText primary="Shipping" />
                <ListItem button onClick={() => this.setState({ section: SessionsSection})}>
                    <ListItemIcon>
                        <LockIcon />
                    </ListItemIcon>
                    <ListItemText primary="Session Logs" />
                </ListItem>
                <Divider />
            </div>
        );

        var selectedSession;

        switch (this.state.section) {
            case InventorySection:
                selectedSession = <Inventory backendURL={this.props.backendURL}/>;
                break;
            case OrdersSection:
                selectedSession = <Orders backendURL={this.props.backendURL}/>;
                break;
            case SessionsSection:
                selectedSession = <SessionLogs backendURL={this.props.backendURL}/>;
                break;
            default:
                selectedSession = <Typography> Unknown Section </Typography>;
        }

        const mainMenu = (
            <div className={classes.root}>
                <AppBar className={classes.appBar}>
                    <Toolbar>
                        <IconButton
                            color="inherit"
                            aria-label="open drawer"
                            onClick={this.handleDrawerToggle}
                            className={classes.navIconHide}
                        >
                            <MenuIcon />
                        </IconButton>
                        <Typography variant="title" color="inherit" className={classes.flex}>
                            EEP Leaf Tech system
                        </Typography>
                        <Typography color="inherit">
                            {this.state.name}
                        </Typography>
                        <Button color="inherit" onClick={this.logout.bind(this)}>Logout</Button>
                    </Toolbar>
                </AppBar>
                <Hidden mdUp>
                    <Drawer
                        variant="temporary"
                        anchor={theme.direction === 'rtl' ? 'right' : 'left'}
                        open={this.state.mobileOpen}
                        onClose={this.handleDrawerToggle}
                        classes={{
                            paper: classes.drawerPaper,
                        }}
                        ModalProps={{
                            keepMounted: true, // Better open performance on mobile.
                        }}
                    >
                        {drawer}
                    </Drawer>
                </Hidden>
                <Hidden smDown implementation="css">
                    <Drawer
                        variant="permanent"
                        open
                        classes={{
                            paper: classes.drawerPaper,
                        }}
                    >
                        {drawer}
                    </Drawer>
                </Hidden>
                <main className={classes.content}>
                    <div className={classes.toolbar} />
                    { (this.state.section === 'Inventory') ? <Inventory/> : null }
                    { (this.state.section === 'Orders') ? <Orders/> : null }
                    { (this.state.section === 'Shipping') ? <Shipping/> : null }
                    { selectedSession}
                </main>
            </div>
        );

        const auth = <div className={classes.login}>
            <Card className={classes.card}>
                <CardHeader
                    title="EEP-Leaf Tech System"
                />
                <CardContent>
                    <GoogleLogin
                        className={classes.googleButton}
                        clientId={this.props.gauthId}
                        buttonText='Login to EEP'
                        onSuccess={this.loginSuccess.bind(this)}
                        onFailure={this.loginFailure.bind(this)}
                    />
                </CardContent>
            </Card>
        </div>;

        return (!this.state.authToken) ? auth : mainMenu;
    }



    loginSuccess(response) {
        console.log(JSON.stringify(response));
        this.setState({ authToken: response.tokenId });
        this.setState({ name: response.profileObj.name });
        this.setState({ email: response.profileObj.email });
        fetch(`${this.props.backendURL}/auth/log/login`, {
            method: 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                name: this.state.name,
                email: this.state.email,
                token: this.state.authToken,
            })
        });
    }

    loginFailure() {
        this.setState({ authToken: null });
    }

    logout() {
        fetch(`${this.props.backendURL}/auth/log/logout`, {
            method: 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                name: this.state.name,
                email: this.state.email,
                token: this.state.authToken,
            })
        });
        window.open('https://accounts.google.com/logout', '_newtab');
        this.setState({ authToken: null });
    }
}

EEPSystem.propTypes = {
    classes: PropTypes.object.isRequired,
    theme: PropTypes.object.isRequired,
};

export default withStyles(styles, { withTheme: true })(EEPSystem);
