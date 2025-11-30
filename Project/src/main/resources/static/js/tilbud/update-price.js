/*

    Used on /tilbud to calculate final price after updating values
    Initial value and price estimate is based on 25% avanace & 2.500 levering.

    - Guac

*/

function calculateFinalPrice() {

    // Initial
    const defaultAvance = 25;
    const defaultLevering = 2500;
    const prisEl = document.getElementById("tilbud-pris");
    const avanceEl = document.getElementById("tilbud-avance");
    const leveringEl = document.getElementById("tilbud-levering");
    const finalEl = document.getElementById("tilbud-final-price");
    let pris = parseFloat(prisEl.value);
    let avance = parseFloat(avanceEl.value);
    let levering = parseFloat(leveringEl.value);

    // Checks
    if (isNaN(avance)) avance = defaultAvance;
    if (isNaN(levering)) levering = defaultLevering;

    // Calculations & Price update
    const base = (pris - defaultLevering) / (1 + defaultAvance / 100);
    const newAvanceAmount = base * (avance / 100);
    const finalPrice = base + newAvanceAmount + levering;

    // Final render in 1 decimal
    finalEl.textContent = finalPrice.toFixed(1);
}

// eventListeners
document.getElementById("tilbud-pris").addEventListener("input", calculateFinalPrice);
document.getElementById("tilbud-avance").addEventListener("change", calculateFinalPrice);
document.getElementById("tilbud-levering").addEventListener("input", calculateFinalPrice);
document.getElementById("tilbud-type").addEventListener("change", calculateFinalPrice);

// Load content without page reload
window.addEventListener("DOMContentLoaded", calculateFinalPrice);