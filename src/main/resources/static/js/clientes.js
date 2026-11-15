
const API_CLIENTES = "/api/clientes";

const formulario = document.getElementById("formularioCliente");
const tablaClientes = document.getElementById("tablaClientes");
const mensaje = document.getElementById("mensaje");

let clienteEditando = null;

// Mostrar mensajes al usuario
function mostrarMensaje(texto, error = false) {
    mensaje.textContent = texto;
    mensaje.style.color = error ? "red" : "green";
}

// Consultar clientes activos
async function cargarClientes() {
    try {
        const respuesta = await fetch(API_CLIENTES);

        if (!respuesta.ok) {
            throw new Error("No fue posible consultar los clientes.");
        }

        const clientes = await respuesta.json();

        tablaClientes.innerHTML = "";

        if (clientes.length === 0) {
            tablaClientes.innerHTML = `
                <tr>
                    <td colspan="8">No hay clientes registrados.</td>
                </tr>
            `;
            return;
        }

        clientes.forEach(cliente => {
            const fila = document.createElement("tr");

            const datos = [
                cliente.idCliente,
                cliente.nombres,
                cliente.apellidos,
                cliente.documento,
                cliente.correo || "No registrado",
                cliente.telefono || "No registrado",
                cliente.direccion || "No registrada"
            ];

            datos.forEach(dato => {
                const celda = document.createElement("td");
                celda.textContent = dato ?? "";
                fila.appendChild(celda);
            });

            const celdaAcciones = document.createElement("td");

            const botonEditar = document.createElement("button");
            botonEditar.textContent = "Editar";
            botonEditar.type = "button";
            botonEditar.addEventListener("click", () => {
                editarCliente(cliente);
            });

            const botonEliminar = document.createElement("button");
            botonEliminar.textContent = "Desactivar";
            botonEliminar.type = "button";
            botonEliminar.addEventListener("click", () => {
                eliminarCliente(cliente.idCliente);
            });

            celdaAcciones.appendChild(botonEditar);
            celdaAcciones.appendChild(botonEliminar);

            fila.appendChild(celdaAcciones);
            tablaClientes.appendChild(fila);
        });

    } catch (error) {
        mostrarMensaje(error.message, true);
        tablaClientes.innerHTML = `
            <tr>
                <td colspan="8">Error al cargar los clientes.</td>
            </tr>
        `;
    }
}

// Registrar o actualizar un cliente
formulario.addEventListener("submit", async function(evento) {
    evento.preventDefault();

    const cliente = {
        nombres: document.getElementById("nombres").value.trim(),
        apellidos: document.getElementById("apellidos").value.trim(),
        documento: document.getElementById("documento").value.trim(),
        correo: document.getElementById("correo").value.trim() || null,
        telefono: document.getElementById("telefono").value.trim() || null,
        direccion: document.getElementById("direccion").value.trim() || null
    };

    const metodo = clienteEditando ? "PUT" : "POST";

    const url = clienteEditando
        ? `${API_CLIENTES}/${clienteEditando}`
        : API_CLIENTES;

    try {
        const respuesta = await fetch(url, {
            method: metodo,
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(cliente)
        });

        if (!respuesta.ok) {
            const detalle = await respuesta.text();
            throw new Error(detalle || "No fue posible guardar el cliente.");
        }

        mostrarMensaje(
            clienteEditando
                ? "Cliente actualizado correctamente."
                : "Cliente registrado correctamente."
        );

        formulario.reset();
        clienteEditando = null;

        formulario.querySelector("button[type='submit']").textContent =
            "Registrar cliente";

        await cargarClientes();

    } catch (error) {
        mostrarMensaje(error.message, true);
    }
});

// Cargar información de un cliente en el formulario
function editarCliente(cliente) {
    clienteEditando = cliente.idCliente;

    document.getElementById("nombres").value = cliente.nombres;
    document.getElementById("apellidos").value = cliente.apellidos;
    document.getElementById("documento").value = cliente.documento;
    document.getElementById("correo").value = cliente.correo || "";
    document.getElementById("telefono").value = cliente.telefono || "";
    document.getElementById("direccion").value = cliente.direccion || "";

    formulario.querySelector("button[type='submit']").textContent =
        "Actualizar cliente";

    mostrarMensaje("Editando cliente seleccionado.");

    formulario.scrollIntoView({ behavior: "smooth" });
}

// Desactivar un cliente (eliminación lógica)
async function eliminarCliente(id) {
    const confirmar = confirm(
        "¿Estás seguro de que deseas desactivar este cliente?"
    );

    if (!confirmar) {
        return;
    }

    try {
        const respuesta = await fetch(`${API_CLIENTES}/${id}`, {
            method: "DELETE"
        });

        if (!respuesta.ok) {
            throw new Error("No fue posible desactivar el cliente.");
        }

        mostrarMensaje("Cliente desactivado correctamente.");

        if (clienteEditando === id) {
            formulario.reset();
            clienteEditando = null;

            formulario.querySelector("button[type='submit']").textContent =
                "Registrar cliente";
        }

        await cargarClientes();

    } catch (error) {
        mostrarMensaje(error.message, true);
    }
}

// Cargar clientes al abrir la página
cargarClientes();
