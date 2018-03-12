var express = require('express'),
    app = express(),
    port = process.env.PORT || 3000;

app.listen(port);

app.get('/', function (req, res) {
    res.send('Backend is Alive')
})

console.log('todo list RESTful API server started on: ' + port);