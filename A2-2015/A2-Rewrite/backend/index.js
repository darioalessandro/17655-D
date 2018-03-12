const express = require('express'),
    app = express(),
    port = process.env.PORT || 3001;

const Sequelize = require('sequelize');
const sequelize = new Sequelize('inventory_v2', 'remote', 'remote_pass', {
    host: 'localhost',
    dialect: 'mysql',

    pool: {
        max: 5,
        min: 0,
        acquire: 30000,
        idle: 10000
    },

    // http://docs.sequelizejs.com/manual/tutorial/querying.html#operators
    operatorsAliases: false
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


app.listen(port);

app.get('/', function (req, res) {
    res.send('Backend is Alive')
});

app.get('/products',async  function (req, res) {
    res.json(await Product.findAll({}));
});

console.log('todo list RESTful API server started on: ' + port);