function saveTasks() {
    localStorage.setItem("tasks", JSON.stringify(tasks));
    renderTasks();
}
const user = JSON.parse(localStorage.getItem("user"));

async function addTask() {

    const title = document.getElementById("taskTitle").value;
    const status = document.getElementById("taskStatus").value;
    const deadline = document.getElementById("taskReminder").value;

    const user = JSON.parse(localStorage.getItem("user"));
    if (!title) {
        alert("Enter task title");
        return;
    }
    const res = await fetch("http://localhost:8080/api/tasks", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            title: title,
            description: "",
            priority: "MEDIUM",
            status: status.toUpperCase(),
            deadline: deadline,
            userId: user.id
        })
    });

    loadTasks();
}
async function loadTasks() {

    const user = JSON.parse(localStorage.getItem("user"));
    if (!user || !user.id) {
        alert("Login again");
        window.location.href = "login.html";
    }
    const res = await fetch(`http://localhost:8080/api/tasks/user/${user.id}`);

    const tasks = await res.json();
    document.getElementById("todoList").innerHTML = "";
    document.getElementById("progressList").innerHTML = "";
    document.getElementById("doneList").innerHTML = "";

    tasks.forEach(task => {

        const div = document.createElement("div");
        div.className = "task";

        div.innerHTML =
            `
        <b>${task.title}</b>
        <p>Reminder: ${task.deadline}</p>
        <button onclick="move(${task.id},'TODO')">Todo</button>
        <button onclick="move(${task.id},'PROGRESS')">Progress</button>
        <button onclick="move(${task.id},'DONE')">Done</button>
        <button onclick="deleteTask(${task.id})">Delete</button>
        `;

        if (task.status === "TODO")
            document.getElementById("todoList").appendChild(div);

        if (task.status === "PROGRESS")
            document.getElementById("progressList").appendChild(div);

        if (task.status === "DONE")
            document.getElementById("doneList").appendChild(div);

    });
}

async function deleteTask(id) {

    await fetch(`http://localhost:8080/api/tasks/${id}`, {
        method: "DELETE"
    });

    loadTasks();
}
async function move(id, status) {

    await fetch(`http://localhost:8080/api/tasks/${id}/${status}`, {
        method: "PUT"
    });

    loadTasks();
}
window.onload = loadTasks;
