var express = require('express')
var app = express()

app.get('/', function (req, res) {
  // res.send('Hello World!')
  res.sendfile('index.html')
})

app.get('/ping', function (req, res) {
  // res.send('Hello World!')
  res.sendfile('<h1>Pong!</h1>')
})

app.listen(3000, function () {
  console.log('Example app listening on port 3000!')
})