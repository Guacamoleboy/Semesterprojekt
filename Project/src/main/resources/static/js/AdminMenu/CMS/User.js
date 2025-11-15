window.addEventListener("DOMContentLoaded", () => {
    const CMSuserSearch = document.getElementById("CMSuserSearch");
    const CMScreateUser = document.getElementById("CMScreateUser");
    const CMSsearchUserDiv = document.getElementById("CMSsearchUser");

    // _________________________________________________

    const PageSize = 5;
    let CMSUsers = [];
    let userPage = 0;

    // _________________________________________________

    CMSuserSearch.addEventListener("submit", async (e) => {

        e.preventDefault();
        const form = new FormData(e.target);

        const res = await fetch("/searchUsers", {
            method: "POST",
            body: form
        });

        CMSUsers = await res.json();
        userPage = 0;
        renderUsers();

    });

    // _________________________________________________

    CMScreateUser.addEventListener("submit", async (e) => {

        e.preventDefault();
        const formData = new FormData(e.target);
        const res = await fetch("/createUser", {
            method: "POST",
            body: formData
        });

        if (res.ok){

            e.target.reset();

        }

    });

    // _________________________________________________

    function renderUsers() {

        CMSsearchUserDiv.style.display = "flex";
        CMSsearchUserDiv.innerHTML = "";

        if (CMSUsers.length === 0) {

            CMSsearchUserDiv.innerHTML = "<p>Ingen brugere fundet.</p>";
            togglePageShift(false);
            addHideButton();
            return;

        }

        const start = userPage * PageSize;
        const pageItems = CMSUsers.slice(start, start + PageSize);

        pageItems.forEach(u => {

            const div = document.createElement("div");
            div.classList.add("user-item");

            div.innerHTML = `

                <div class="user-view" id="user-view-${u.id}">
                    <p>Navn: ${u.username} — Rolle: ${u.roleID}</p>
                    <button class="action-btn edit-user-btn" data-id="${u.id}">Rediger</button>
                    <button class="action-btn delete-user-btn" data-id="${u.id}">Slet</button>
                </div>
                
                <div class="user-edit" id="user-edit-${u.id}" style="display:none;">
                    <p>Navn: <span contenteditable="true" id="edit-username-${u.id}" class="editable">${u.username}</span> — 
                       Rolle: <span contenteditable="true" id="edit-role-${u.id}" class="editable">${u.roleID}</span></p>
                    <button class="action-btn save-user-btn" data-id="${u.id}">Gem</button>
                    <button class="action-btn cancel-user-btn" data-id="${u.id}">Annuller</button>
                </div>
                
            `;

            CMSsearchUserDiv.appendChild(div);

        });

        setupEditButtons();
        setupDeleteButtons();
        togglePageShift(true);
        addHideButton();

    }

    // _________________________________________________

    function setupEditButtons() {

        document.querySelectorAll(".edit-user-btn").forEach(btn => {

            btn.addEventListener("click", () => {

                const id = btn.dataset.id;
                document.getElementById(`user-view-${id}`).style.display = "none";
                document.getElementById(`user-edit-${id}`).style.display = "block";

            });

        });

        document.querySelectorAll(".cancel-user-btn").forEach(btn => {

            btn.addEventListener("click", () => {

                const id = btn.dataset.id;
                document.getElementById(`user-view-${id}`).style.display = "block";
                document.getElementById(`user-edit-${id}`).style.display = "none";

            });

        });

        document.querySelectorAll(".save-user-btn").forEach(btn => {

            btn.addEventListener("click", async () => {

                const id = btn.dataset.id;
                const username = document.getElementById(`edit-username-${id}`).textContent;
                const roleID = document.getElementById(`edit-role-${id}`).textContent;

                const formData = new FormData();
                formData.append("id", id);
                formData.append("username", username);
                formData.append("role", roleID);

                const res = await fetch("/updateUser", {
                    method: "POST",
                    body: formData
                });

                if (res.ok) {

                    const i = CMSUsers.findIndex(u => u.id == id);
                    if (i !== -1) CMSUsers[i] = { ...CMSUsers[i], ...updatedUser };
                    renderUsers();

                }

            });

        });

    }

    // _________________________________________________

    function setupDeleteButtons() {

        document.querySelectorAll(".delete-user-btn").forEach(btn => {

            btn.addEventListener("click", async () => {

                const id = btn.dataset.id;

                const formData = new FormData();
                formData.append("id", id);

                const res = await fetch("/deleteUser", {
                    method: "POST",
                    body: formData
                });

                if (res.ok) {

                    CMSUsers = CMSUsers.filter(u => u.id != id);
                    renderUsers();

                } else {

                    console.log("Noget gik galt. Prøv igen.");

                }

            });

        });

    }

    // _________________________________________________

    function togglePageShift(show) {

        const prevBtn = document.getElementById("prevCMSUsers");
        const nextBtn = document.getElementById("nextCMSUsers");

        prevBtn.classList.add("action-btn");
        nextBtn.classList.add("action-btn");

        if (!show || CMSUsers.length <= PageSize) {

            prevBtn.style.display = "none";
            nextBtn.style.display = "none";

        } else {

            prevBtn.style.display = userPage > 0 ? "inline-block" : "none";
            nextBtn.style.display = (userPage + 1) * PageSize < CMSUsers.length ? "inline-block" : "none";

        }

        prevBtn.onclick = () => {

            if (userPage > 0) {

                userPage--;
                renderUsers();

            }

        };

        nextBtn.onclick = () => {

            if ((userPage + 1) * PageSize < CMSUsers.length) {

                userPage++;
                renderUsers();

            }

        };

    }

    // _________________________________________________

    function addHideButton() {

        const container = document.querySelector("#CMSroles .cms-btn-container");
        const id = "hideUsers";

        if (document.getElementById(id)) return;

        const hideBtn = document.createElement("button");
        hideBtn.textContent = "Skjul";
        hideBtn.id = id;
        hideBtn.classList.add("action-btn");
        hideBtn.addEventListener("click", () => {

            CMSsearchUserDiv.innerHTML = "";
            CMSsearchUserDiv.style.display = "none";
            togglePageShift(false);
            hideBtn.remove();

        });

        container.prepend(hideBtn);

    }
});
