document.getElementById("status-search-btn").addEventListener("click", async() => {
    const id = document.getElementById("status-id").value.trim();
    if(!id) {
        showNotification("Indtast venligst et ID", "fog");
        return;
    }
    if(!/^\d+$/.test(id)) {
        showNotification("Indtast venligst kun tal", "fog");
        return;
    }
    try {
        const response = await fetch(`/status/id/${id}/status`);
        const data = await response.json();
        window.location.href = `/status/id/${id}?status=${data.status}`;
    } catch (err) {
        showNotification("ID eksisterer ikke", "fog");
    }
});