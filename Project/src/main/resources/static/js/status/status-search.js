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
        const response = await fetch(`/status/${id}/status`);
        const data = await response.json();

        if (data.status === null) {
            showNotification("ID eksisterer ikke", "fog");
            return;
        }

        window.location.href = `/status/${id}?status=${data.status}`;
    } catch (err) {
        showNotification("Fejl | " + err, "fog");
    }
});