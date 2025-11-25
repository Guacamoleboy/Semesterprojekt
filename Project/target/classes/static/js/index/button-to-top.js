/*

    Button to previous <section> tag
    Written by: Guacamoleboy

*/

let scrollToTopBtn = document.querySelector(".scroll-btn");
let sections = Array.from(document.querySelectorAll("section"));

// ___________________________________________________________________________________

window.addEventListener("scroll", function () {

    // Initial
    let scrollY = window.scrollY;

    // Show button. In reality 500 isn't needed as we use 100vh but it's fine for now.
    if (scrollY > 500) {
        scrollToTopBtn.classList.add("show");
    } else {
        scrollToTopBtn.classList.remove("show");
    }

});

// ___________________________________________________________________________________

function scrollToSection() {

    // Initial
    let scrollY = window.scrollY;
    let currentIndex = -1;

    // For loop over our section array
    for (let i = 0; i < sections.length; i++) {
        let top = sections[i].offsetTop;
        let bottom = top + sections[i].offsetHeight;
        if (scrollY >= top - 10 && scrollY < bottom - 10) {
            currentIndex = i;
            break;
        }
    }

    // Find previous section tag
    let previousSection = sections[currentIndex - 1];

    // Scroll to previous section - smooth being set in our CSS file
    window.scrollTo({
        top: previousSection.offsetTop
    });
}