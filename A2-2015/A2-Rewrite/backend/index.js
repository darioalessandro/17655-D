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

/*      AuthLogs table      */
const AuthLogs = sequelize.define('auth_logs', {
    name: Sequelize.STRING,
    email: Sequelize.STRING,
    token: Sequelize.INTEGER,
    event: Sequelize.INTEGER
},{
    underscored: true,
    freezeTableName: true,
    tableName: 'auth_logs'
});

/*      Prooduct Category table      */
const ProductCategory = sequelize.define('product_category', {
    id: {
       type: Sequelize.STRING,
       primaryKey: true
    },
    created_at: Sequelize.TIME,
    created_at: Sequelize.TIME
},{
    underscored: true,
    freezeTableName: true,
    tableName: 'product_category'
});

/*      Product table      */
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
    underscored: true,
    freezeTableName: true,
    tableName: 'product'
});

/*      Order table      */
const Orders = sequelize.define('order', {
    createdAt: Sequelize.TIME,
    customerFirstName: Sequelize.STRING,
    customerLastName: Sequelize.STRING,
    customerAddress: Sequelize.STRING,
    customerPhone: Sequelize.STRING,
    price: Sequelize.DOUBLE,
    shippedFlag: Sequelize.BOOLEAN
},{
    underscored: true,
    freezeTableName: true,
    tableName: 'order'
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

    // authorization routes ----------------------

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

    // dimension routes
app.get('/product_categories', async function(req, res) {
    res.json(await ProductCategory.findAll({}));
});

app.get('/products',async  function (req, res) {
    res.json(await Product.findAll({}));
});

console.log('RESTful API server started on: ' + port);