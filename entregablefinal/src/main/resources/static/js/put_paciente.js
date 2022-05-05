window.addEventListener('load', function () {
    const formulario = document.querySelector('#update_paciente_form');
    formulario.addEventListener('submit', function (event) {
        let pacienteId = document.querySelector('#id').value;
        const formData = {
            id: document.querySelector('#id').value,
            nombre: document.querySelector('#nombre').value,
            apellido: document.querySelector('#apellido').value,
            dni: document.querySelector('#dni').value,
            fechaIngreso : document.querySelector('#fecha').value,


        };
        const url = './pacientes
        const settings = {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(formData)
        }
          fetch(url,settings)
          .then(response => response.json())

    })
 })

    function findBy(id) {
          const url = '/pacientes'+"/id="+id;
          const settings = {
              method: 'GET'
          }
          fetch(url,settings)
          .then(response => response.json())
          .then(data => {
              let paciente = data;
                document.querySelector('#id').value = paciente.id;
                document.querySelector('#nombre').value = paciente.nombre;
                document.querySelector('#apellido').value = paciente.apellido;
                document.querySelector('#dni').value = paciente.dni;
                document.querySelector('#fecha').value = paciente.fechaIngreso;

              document.querySelector('#div_paciente_updating').style.display = "block";
          }).catch(error => {
              alert("Error: " + error);
          })
      }