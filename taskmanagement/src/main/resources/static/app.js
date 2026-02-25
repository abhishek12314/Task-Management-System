const API = "http://localhost:8080/api";
<script src="app.js"></script>
function toggleDarkMode() {

    document.body.classList.toggle("dark")

    localStorage.setItem(
        "theme",
        document.body.classList.contains("dark")
    )
}

window.onload = () => {

    if (localStorage.getItem("theme") === "true") {
        document.body.classList.add("dark")
    }

}
function register() {

    fetch(API + "/auth/register", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            name: document.getElementById("name").value,
            email: document.getElementById("email").value,
            password: document.getElementById("password").value
        })
    })
        .then(res => res.json())
        .then(data => {
            alert("Registration successful")
            window.location.href = "login.html"
        })
}

function login() {

    fetch(API + "/auth/login", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            email: document.getElementById("email").value,
            password: document.getElementById("password").value
        })
    })
        .then(res => res.json())
        .then(user => {

            localStorage.setItem("user", JSON.stringify(user))

            if (user.role === "ADMIN") {
                window.location.href = "admin.html"
            } else {
                window.location.href = "tasks.html"
            }

        })
}

function createTask() {

    fetch(API + "/tasks", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            title: document.getElementById("title").value,
            deadline: document.getElementById("deadline").value,
            status: "TODO"
        })
    })
        .then(() => loadTasks())

}

function loadTasks() {

    fetch(API + "/tasks")
        .then(res => res.json())
        .then(tasks => {

            const list = document.getElementById("taskList")
            list.innerHTML = ""

            tasks.forEach(task => {

                const div = document.createElement("div")
                div.className = "task"

                div.innerHTML = `
    <div>
    <b>${task.title}</b>
    <br>
    <small>${task.deadline || ""}</small>
    </div>
    
    <div class="task-actions">
    <button onclick="editTask(${task.id})">Edit</button>
    <button onclick="deleteTask(${task.id})">Delete</button>
    </div>
    `

                list.appendChild(div)

            })

        })
}
function deleteTask(id) {

    fetch(API + "/tasks/" + id, {
        method: "DELETE"
    })
        .then(() => loadTasks())

}
function loadAdminStats() {

    fetch(API + "/admin/dashboard")
        .then(res => res.json())
        .then(data => {

            document.getElementById("users").innerText = data.users
            document.getElementById("tasks").innerText = data.tasks
            document.getElementById("done").innerText = data.completed

            renderChart(data)

        })

}
function editTask(id) {

    const title = prompt("New title")

    if (!title) return

    fetch(API + "/tasks/" + id, {
        method: "PUT",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            title: title,
            status: "TODO"
        })
    })
        .then(() => loadTasks())

}
function renderChart(data) {

    const ctx = document.getElementById("chart")

    new Chart(ctx, {
        type: "doughnut",
        data: {
            labels: ["Users", "Tasks", "Completed"],
            datasets: [{
                data: [
                    data.users,
                    data.tasks,
                    data.completed
                ]
            }]
        }
    })

}
const themeBtn = document.getElementById("themeToggle");

function setTheme(mode) {
    document.body.classList.toggle("dark", mode === "dark");
    localStorage.setItem("theme", mode);
}

themeBtn.onclick = () => {
    const dark = document.body.classList.contains("dark");
    setTheme(dark ? "light" : "dark");
};

window.onload = () => {
    const saved = localStorage.getItem("theme") || "light";
    setTheme(saved);
};

const token = localStorage.getItem("token");

fetch("http://localhost:8080/api/tasks", {
    headers: {
        "Authorization": "Bearer " + token
    }
})