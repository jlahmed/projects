
var express = require('express');
var mongoose = require('mongoose');
var bodyParser = require('body-parser');

var app = express();
app.use(bodyParser.json());
app.use('/public', express.static(__dirname + '/public'));
app.use('/scripts', express.static(__dirname + '/scripts'));
app.use('/images', express.static(__dirname + '/images'));

// Connect to MongoDB using Mongoose
mongoose.connect('mongodb://admin:password@localhost:27017/mailing-list', {
    authSource: 'admin' // Specify the authentication database
});

// Define a mongoose model for the 'users' collection
var User = mongoose.model('User', {
    name: String,
    email: String
});

app.get('/', function (req, res) {
    res.sendFile(__dirname + "/views/index.html");
});

app.post('/SU', function (req, res) {
    console.log('Received user data:', req.body);

    // Create a new User instance
    var newUser = new User(req.body);

    // Save the user data to the 'users' collection
    newUser.save()
        .then(savedUser => {
            console.log('User data inserted successfully:', savedUser);
            res.status(200).json({ success: true, message: 'User data inserted successfully' });
        })
        .catch(err => {
            console.error('Error inserting user data:', err);
            res.status(500).json({ success: false, message: 'Error inserting user data' });
        });
});

//This will be the default page website is directed to.
//It is important to place after the get request block of code.
app.use((req, res) => {
    res.status(404).send('<h1>Error 404 - page not found</h1>')
});

app.listen(3000, function () {
    console.log("App listening on port 3000!");
});
