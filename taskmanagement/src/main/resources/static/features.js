const features = {

    task: {
        title: "Task Assignment",
        text: "Managers can assign tasks to team members, set priorities, and track progress easily within the dashboard."
    },

    role: {
        title: "Role Based Access",
        text: "Different access levels such as Admin, Editor, and Viewer ensure proper control and security."
    },

    deadline: {
        title: "Deadline Tracking",
        text: "Set deadlines for tasks and receive notifications so the team stays on schedule."
    },

    analytics: {
        title: "Progress Analytics",
        text: "Visual reports and insights help teams understand productivity and project status."
    },

    collab: {
        title: "Real-Time Collaboration",
        text: "Team members can comment, upload files, and discuss tasks directly inside the platform."
    },

    security: {
        title: "Secure Authentication",
        text: "User accounts are protected with encrypted passwords and secure login verification."
    }

};

function showFeature(key) {

    document.getElementById("featureModal").style.display = "flex";
    document.getElementById("featureTitle").innerText = features[key].title;
    document.getElementById("featureText").innerText = features[key].text;

}

function closeModal() {
    document.getElementById("featureModal").style.display = "none";
}