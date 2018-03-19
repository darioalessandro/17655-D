
const express = require('express'),
    app = express(),
    port = process.env.PORT || 3001;
const jsonParser = require('body-parser').json();
const Sequelize = require('sequelize');

const sequelize = new Sequelize('eep_operations', process.env.EEP_DATABASE_ADMIN_NAME, process.env.EEP_DATABASE_PASSWORD, {
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
    underscored: true,
    freezeTableName: true,
    tableName: 'product'
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

const AuthLogs = sequelize.define('auth_logs', {
        name: Sequelize.STRING,
        email: Sequelize.STRING,
        token: Sequelize.INTEGER,
        event: Sequelize.INTEGER,
    },{
        underscored: true,
        freezeTableName: true,
        tableName: 'auth_logs'
});

/*      Order table      */
const Orders = sequelize.define('order', {
        id: {
            type: Sequelize.INTEGER,
            primaryKey: true
        },
        created_at: Sequelize.TIME,
        customer_first_name: Sequelize.STRING,
        customer_last_name: Sequelize.STRING,
        customer_address: Sequelize.STRING,
        customer_phone: Sequelize.STRING,
        price: Sequelize.DOUBLE,
        shipped_flag: Sequelize.BOOLEAN
    },{
        underscored: true,
        freezeTableName: true,
        tableName: 'order',
        timestamps: false
});

const OrderItems = sequelize.define('order_item', {
        order_id: {
            type: Sequelize.INTEGER,
            primaryKey: true
        },
        product_company_id: {
            type: Sequelize.STRING,
            primaryKey: true
        },
        product_category: {
            type: Sequelize.STRING,
            primaryKey: true
        },
        product_code: {
            type: Sequelize.STRING,
            primaryKey: true
        },
        quantity: Sequelize.INTEGER
    },{
        underscored: true,
        freezeTableName: true,
        tableName: 'order_item',
        timestamps: false
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
    var filter = req.param('category_filter', null);

    if(filter) {
        res.json(await Product.findAll({
            where: {
                category: filter
            }
        }));
    } else {
        res.json(await Product.findAll({}));
    }
});

app.get('/product_categories', async function(req, res) {
    res.json(await ProductCategory.findAll({}));
});

app.get('/orders', async  function (req, res) {
    var showPending = ('false' != req.param('show_pending', true));
    var showShipped = ('false' != req.param('show_shipped', true));

    if(!showPending && !showShipped){
        return res.json([]);
    }else if(showPending != showShipped) {
        res.json(await Orders.findAll({
            where: {
                shipped_flag: showShipped
            }
        }));
    }else {
        res.json(await Orders.findAll({}));
    }
});

app.get('/order_item', async  function (req, res) {
    var order_id = req.param('order_id', null);

    if(order_id){
        res.json(await OrderItems.findAll({
            where: {
                order_id: order_id
            }
        }));  
    } else {
        return res.json([]);
    }   
});

console.log('RESTful API server started on: ' + port);