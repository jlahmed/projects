document.getElementById("Submit").addEventListener("click", SignUpClicked);
let full_name, email;

// Define a function that will handle the click event
function SignUpClicked(event) {
    event.preventDefault(); // Prevents the default form submission behavior

    full_name = document.getElementById("name").value;
    email = document.getElementById("email").value;

    console.log("Name:", full_name);
    console.log("Email:", email);

    // Trigger backend interaction (replace with your backend endpoint)
    sendSignUpDataToBackend(full_name, email);
}

// Function to send sign-up data to the backend
function sendSignUpDataToBackend(name, email) {
    // Replace the URL with your actual backend endpoint
    const backendEndpoint = 'http://localhost:3000/SU';

    // Make a POST request to the backend with the sign-up data
    fetch(backendEndpoint, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({ name, email }),
    })
    .then(response => {
        console.log('Raw server response:', response);
        return response.text();
    })
    .then(data => {
        console.log('Backend response:', data);
    })
    .catch(error => {
        console.error('Error sending data to backend:', error);
    });    
    
}
