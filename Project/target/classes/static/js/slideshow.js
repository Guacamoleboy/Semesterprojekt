const slideshowImg = document.getElementById('slideshow-img');
const totalImages = 7;
const intervalTime = 3000;
let currentImage = 1;

// _______________________________________________________

setInterval(() => {

    currentImage++;

    if (currentImage > totalImages) {
        currentImage = 1;
    }

    slideshowImg.src = `/images/page-content/beregn-intro/carport-${currentImage}.png`;

}, intervalTime);