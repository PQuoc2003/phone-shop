function eventHandler(event) {
    event.preventDefault(); // Prevent form submission
    const quantityInput = document.getElementById('quantity');
    const errorMessage = document.getElementById('errorMessage');
    const quantity = parseFloat(quantityInput.value);

    // Clear previous error message
    errorMessage.textContent = '';

    // Validate input
    if (isNaN(quantity) || quantity <= 0) {
        errorMessage.textContent = 'Please enter a number greater than 0.';
        quantityInput.focus(); // Focus back on the input
    } else {
        this.submit();
    }
}

document.getElementById('add-product').addEventListener('submit', eventHandler);

document.getElementById('edit-product').addEventListener('submit', eventHandler);




