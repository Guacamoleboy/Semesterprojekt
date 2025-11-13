/*

    Notification Box Bottom Right
    Written by Guacamoleboy
    Date: 01/11-2025

*/

// Attributes

// _______________________________________________________________________

function showNotification(message, color = "green") {

    const container = document.getElementById('guac-notification-container');

    if (!container) {
        console.log("Tilføj guac-notification-container <div> til din fil..")
        return;
    }

    const notificationBox = document.createElement('div');
    const rootStyles = getComputedStyle(document.documentElement);
    let bgColor;

    switch(color.toLowerCase()) {
        case "green":
            bgColor = rootStyles.getPropertyValue('--project-green');
            break;
        case "orange":
        case "warning":
            bgColor = rootStyles.getPropertyValue('--project-orange');
            break;
        case "red":
        default:
            bgColor = rootStyles.getPropertyValue('--project-red');
            break;
    }

    /* guacamoleboy-framework.css */
    notificationBox.className = 'guac-notification';
    notificationBox.innerText = message;
    notificationBox.style.backgroundColor = bgColor.trim();

    container.appendChild(notificationBox);

    requestAnimationFrame(() => {
        notificationBox.style.opacity = '1';
        notificationBox.style.transform = 'translateX(0)';
    });

    setTimeout(() => {
        notificationBox.style.opacity = '0';
        notificationBox.style.transform = 'translateX(100%)';
        setTimeout(() => container.removeChild(notificationBox), 500);
    }, 5000);

}

// _______________________________________________________________________

document.addEventListener("DOMContentLoaded", function() {
    const urlParams = new URLSearchParams(window.location.search);
    const error = urlParams.get("error");
    const success = urlParams.get("success");
    const msg = urlParams.get("msg");
    const hasWelcomed = sessionStorage.getItem("welcomeMessageShown");

    if (document.getElementById('map') && !hasWelcomed) {
        showNotification("Velkommen tilbage", "green");
        sessionStorage.setItem("welcomeMessageShown", "true");
    }

    /* Show any msg in green */
    if (msg) {
        showNotification(msg, "green");
    }

    /* Pre-determined msgs as ?success redirect */
    if (success) {
        switch(success) {
            case "accountCreated":
                showNotification("Konto oprettet! Log venligst ind.", "green");
                break;
        }
    }

    /* Pre-determined msgs as ?error redirect */
    if (error) {
        switch(error) {
            case "wrongInfo":
                showNotification("Forkert brugernavn eller adgangskode...", "red");
                break;
            case "wrongPassword":
                showNotification("Forkert adgangskode...", "orange");
                break;
            case "wrongPassMatch":
                showNotification("Adgangskoderne matcher ikke...", "red");
                break;
            case "accountExists":
                showNotification("Brugernavnet findes allerede...", "orange");
                break;
            case "userNotFound":
                showNotification("Bruger ikke fundet...", "orange");
                break;
            case "missingFields":
                showNotification("Manglende felter...", "red");
                break;
            case "500":
                showNotification("Serverfejl: 500", "red");
                break;
            case "deleteMissingFields":
                showNotification("Udfyld venligst begge felter.", "red");
                break;
            case "notLoggedIn":
                showNotification("Du skal være logget ind.", "red");
                break;
            case "UsernameError":
            case "contactError":
                showNotification("Der er opstået en fejl!", "red");
                break;
            case "invalidParams":
                showNotification("Ugyldige parametre tilføjet. Prøv igen.", "red");
                break;
            case "dbError":
                showNotification("Database fejl. Prøv igen.", "red");
                break;
        }
    }

});