var express = require('express')
var AWS = require('aws-sdk')
var s3 = new AWS.S3()
var fs = require('fs')


var myBucket = 'chho-0'
var app = express()


app.use(function(req, res, next) {
  res.header("Access-Control-Allow-Origin", "*");
  res.header("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
  next();
});

app.get('/', function (req, res) {
  // res.send('Hello World!')
  res.sendfile('index.html')
})

app.get('/list', function (req, res) {
    var params = {
      Bucket: myBucket
    };
    s3.listObjects(params, function (err, data) {
        for(var i = 0; i < data.Contents.length; i++){
          data.Contents[i].Url = 'https://s3-us-west-1.amazonaws.com/' + data.Name + '/' + data.Contents[i].Key;
        }
        res.send(data.Contents);
    })
})

app.get('/pic', function (req, res) {
  res.sendfile('DatFaceIcon.png');
  uploadFileToS3('DatFaceIcon.png')
    // if (req.query.url){
    //   var fileNameA = "Image_" + "0" + ".jpeg";
    // }


})


app.get('/ping', function (req, res) {
  // res.send('Hello World!')
  res.send('<h1>Pong!</h1>')
})

app.get('/user/:id', function(request, response){
    response.send('user ' + request.params.id);
});

app.get('/users', function (req, res) {

  res.contentType('application/json');

    var people = [
        {Fname: 'John', Lname: 'Doe', Major: 'CS'},
        {Fname: 'Bob', Lname: 'Lee', Major: 'CIS'},
        {Fname: 'Tarry', Lname: 'Connor', Major: 'CE'}
    ];

    var userJSON = JSON.stringify(people);
    res.send(userJSON);
})


function uploadFileToS3(imageFilePath) {
    fs.readFile(imageFilePath, function (err, data) {
        params = {Bucket: myBucket, Key: imageFilePath, Body: data, ACL: "public-read", ContentType: "image/png"};
        s3.putObject(params, function(err, data) {
            if (err) {
                console.log(err)
            } else {
                console.log("Successfully uploaded data to " + myBucket, data);
                fs.unlink(imageFilePath);
            }
        });
    });
}

app.listen(3000, function () {
  console.log('Example app listening on port 3000!')
})