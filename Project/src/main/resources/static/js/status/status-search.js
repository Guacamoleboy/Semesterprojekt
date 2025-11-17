document.getElementById("status-search-btn").addEventListener("click", () => {
    const id = document.getElementById("status-id").value.trim();
    if(!id) {
        showNotification("Indtast venligst et ID", "fog");
        return;
    }
    // Validation
    if(!/^\d+$/.test(id)) {
        showNotification("Indtast venligst kun tal", "fog");
        return;
    }
    window.location.href = `/status/id`;
});