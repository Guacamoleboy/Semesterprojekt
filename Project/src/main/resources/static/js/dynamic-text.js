/*

    Dynamic Text Hero Section
    Written by Guacamoleboy

    Last Updated:
    07/11-2025

*/

// Attributes
const words = ["mål", "materiale", "stil", "størrelse", "budet"];
const dynamicWord = document.getElementById("dynamic-word");
let currentWord = 0;
let currentLetter = 0;
let typingSpeed = 100;
let pauseBetweenWords = 1500;

// ________________________________________________________

function typeWord() {
    const word = words[currentWord];
    dynamicWord.textContent = word.slice(0, currentLetter + 1);
    currentLetter++;

    if (currentLetter < word.length) {
        setTimeout(typeWord, typingSpeed);
    } else {
        setTimeout(() => {
            eraseWord();
        }, pauseBetweenWords);
    }
}

// ________________________________________________________

function eraseWord() {
    const word = words[currentWord];
    dynamicWord.textContent = word.slice(0, currentLetter - 1);
    currentLetter--;

    if (currentLetter > 0) {
        setTimeout(eraseWord, typingSpeed / 2);
    } else {
        currentWord = (currentWord + 1) % words.length;
        setTimeout(typeWord, typingSpeed);
    }
}

typeWord();