const checkboxes = document.querySelectorAll('.section-3 .check-input');
const submitBtn = document.getElementById('submitBtn');

// _________________________________________________________________

function toggleButton() {

    var allChecked = Array.from(checkboxes).every(function(e) {
        return e.checked;
    });

    // Visuals
    if(allChecked){
        submitBtn.classList.remove('guac-locked');
    } else {
        submitBtn.classList.add('guac-locked');
    }

}

// _________________________________________________________________

checkboxes.forEach(function(e) {
    e.addEventListener('change', toggleButton);
});

toggleButton();