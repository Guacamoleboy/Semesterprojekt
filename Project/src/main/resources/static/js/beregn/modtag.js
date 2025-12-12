/*

    Not sure what this is. Ebou made it before leaving us.

*/

document.addEventListener("DOMContentLoaded", () => {

    const urlParams = new URLSearchParams(window.location.search);
    const length = urlParams.get("length") || localStorage.getItem("carport_length") || "600";
    const width = urlParams.get("width") || localStorage.getItem("carport_width") || "320";
    const height = urlParams.get("width") || localStorage.getItem("carport_height") || "210";
    const angle = localStorage.getItem("carport_angle") || "Nej";
    const roof = urlParams.get("roof") || localStorage.getItem("carport_roof") || "Plast";
    const toolShed = localStorage.getItem("carport_toolShed") || "Ja";
    const toolShedWidth = localStorage.getItem("carport_toolShed_width") || "320";
    const toolShedHeight = localStorage.getItem("carport_toolShed_height") || "510";
    const toolShedLength = localStorage.getItem("carport_toolShed_length") || "210";
    const resume = document.querySelectorAll(".modtag-resume-item");

    if (resume.length >= 5) {
        resume[0].querySelector(".modtag-resume-value").textContent = `${width} x ${length} x ${height} cm`;
        resume[1].querySelector(".modtag-resume-value").textContent = angle;
        resume[2].querySelector(".modtag-resume-value").textContent = roof;
        resume[3].querySelector(".modtag-resume-value").textContent = toolShed;
        resume[4].querySelector(".modtag-resume-value").textContent = `${toolShedWidth} x ${toolShedLength} x ${toolShedHeight} cm`;
    }

});