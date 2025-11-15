document.querySelectorAll('.dropdown-toggle').forEach(btn => {
    btn.addEventListener('click', () => {
        const parent = btn.closest('.dropdown');
        parent.classList.toggle('open');
    });
});