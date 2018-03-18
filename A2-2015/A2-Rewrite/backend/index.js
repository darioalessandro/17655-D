const express = require('express'),
    app = express(),
    port = process.env.PORT || 3001;
const jsonParser = require('body-parser').json();
const Sequelize = require('sequelize');
const sequelize = new Sequelize('eep_operations', 'eep_admin', 'eep_password', {
    host: 'eep-operations.c0ucixdvk0wc.us-east-1.rds.amazonaws.com',
    port: 3306,
    dialect: 'mysql',
    dialectOptions: {
        ssl: 'Amazon RDS'
    },
    pool: { maxConnections: 5, maxIdleTime: 30},
    language: 'en'
});

const Product = sequelize.define('product', {
    company_id: {
        type: Sequelize.STRING,
        primaryKey: true,
    },
    category: {
        type: Sequelize.STRING,
        primaryKey: true,
    },
    product_code: {
        type: Sequelize.STRING,
        primaryKey: true,
    },
    description: Sequelize.STRING,
    quantity: Sequelize.INTEGER,
    price: Sequelize.DOUBLE,
    },{
    // don't use camelcase for automatically added attributes but underscore style
    // so updatedAt will be updated_at
    underscored: true,
    // disable the modification of tablenames; By default, sequelize will automatically
    // transform all passed model names (first parameter of define) into plural.
    // if you don't want that, set the following
    freezeTableName: true,
    // define the table's name
    tableName: 'product'
});

const AuthLogs = sequelize.define('auth_logs', {
    name: Sequelize.STRING,
    email: Sequelize.STRING,
    token: Sequelize.INTEGER,
    event: Sequelize.INTEGER,
},{
    // don't use camelcase for automatically added attributes but underscore style
    // so updatedAt will be updated_at
    underscored: true,
    // disable the modification of tablenames; By default, sequelize will automatically
    // transform all passed model names (first parameter of define) into plural.
    // if you don't want that, set the following
    freezeTableName: true,
    // define the table's name
    tableName: 'auth_logs'
});


app.listen(port);

// Enable cors so that we can call the api from the React UI

app.use(function(req, res, next) {
    res.header("Access-Control-Allow-Origin", "*");
    res.header("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
    next();
});

app.get('/', function (req, res) {
    res.send('Backend is Alive')
});

app.get('/auth/logs', async function (req, res) {
    const results = (await AuthLogs.findAll({})).map(logEntry => {
        // We do not want to send the tokens back.
        logEntry.token = '';
        return logEntry;
    });
    res.json(results);
});

app.post('/auth/log/:type',jsonParser,async function (req, res) {
    if (!req.body) return res.sendStatus(400);
    switch (req.params.type) {
        case 'login':
            await AuthLogs.build({
                name: req.body.name,
                email: req.body.email,
                token:req.body.token ,
                event: 'login',
            }).save();
            await res.send("login success");
            break;

        case 'logout':
            await AuthLogs.build({
                name: req.body.name,
                email: req.body.email,
                token:req.body.token ,
                event: 'logout',
            }).save();
            await res.send("logout success");
            break;

        default:
            await res.send('Unknown log type');
    }
});

app.get('/products',async  function (req, res) {
    res.json(await Product.findAll({}));
});

console.log('RESTful API server started on: ' + port);