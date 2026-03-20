const user = JSON.parse(localStorage.getItem("user"));
const token = localStorage.getItem("token");

async function addTask() {

    const title = document.getElementById("taskTitle").value;
    const status = document.getElementById("taskStatus").value;
    const deadline = document.getElementById("taskReminder").value;

    if (!title) {
        alert("Enter task title");
        return;
    }

    await fetch("http://localhost:8080/api/tasks", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
            "Authorization": "Bearer " + token
        },
        body: JSON.stringify({
            title: title,
            status: status,
            deadline: deadline,
            userId: user.id
        })
    });

    document.getElementById("taskTitle").value = "";
    loadTasks();
}

async function loadTasks() {

    
    if (!user || !user.id) {
        alert("Login again");
        window.location.href = "login.html";
        return;
    }

    const res = await fetch(`http://localhost:8080/api/tasks/user/${user.id}`, {
        method: "GET",
        headers: {
            "Authorization": "Bearer " + token
        }
    });

    if (!res.ok) {
        console.error("Request blocked:", res.status);
        return;
    }

    const tasks = await res.json();

    document.getElementById("todoList").innerHTML = "";
    document.getElementById("progressList").innerHTML = "";
    document.getElementById("doneList").innerHTML = "";

    tasks.forEach(task => {

        const div = document.createElement("div");
        div.className = "task";

        div.innerHTML = `
<b>${task.title}</b>
<p>Reminder: ${task.deadline ?? ""}</p>

<button onclick="move(${task.id},'TODO')">Todo</button>
<button onclick="move(${task.id},'IN_PROGRESS')">Progress</button>
<button onclick="move(${task.id},'DONE')">Done</button>

<button onclick="deleteTask(${task.id})">Delete</button>
`;

        if (task.status === "TODO")
            document.getElementById("todoList").appendChild(div);

        if (task.status === "IN_PROGRESS")
            document.getElementById("progressList").appendChild(div);

        if (task.status === "DONE")
            document.getElementById("doneList").appendChild(div);

    });

}

async function deleteTask(id) {

    await fetch(`http://localhost:8080/api/tasks/${id}`, {
        method: "DELETE",
        headers: {
            "Authorization": "Bearer " + token
        }
    });

    loadTasks();
}

async function move(id, status) {

    await fetch(`http://localhost:8080/api/tasks/${id}/${status}`, {
        method: "PUT",
        headers: {
            "Authorization": "Bearer " + token
        }
    });

    loadTasks();
}

window.onload = loadTasks;
