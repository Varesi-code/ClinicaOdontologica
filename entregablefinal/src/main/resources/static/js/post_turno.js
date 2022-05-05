window.addEventListener('load', function () {

    const formulario = document.querySelector('#add_turno');
    formulario.addEventListener('submit', function (event) {
        event.preventDefault();

        const formData = {
            fecha: document.querySelector('#fecha').value,
            hora: document.querySelector('#hora').value,
            paciente: { id : document.querySelector('#id-pac').value,},
            odontologo : {id: document.querySelector('#id-odo').value,},
        };

        const url = './turnos';
        const settings = {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(formData)
        }

        fetch(url, settings)
            .then(response => response.json())
            .then(data => {
                 let successAlert = '<div class="alert alert-success alert-dismissible">' +
                     '<button type="button" class="close" data-dismiss="alert">&times;</button>' +
                     '<strong></strong> Turno registrado </div>'

                 document.querySelector('#response').innerHTML = successAlert;
                 document.querySelector('#response').style.display = "block";
                 resetUploadForm();

            })
            .catch(error => {
                    let errorAlert = '<div class="alert alert-danger alert-dismissible">' +
                                     '<button type="button" class="close" data-dismiss="alert">&times;</button>' +
                                     '<strong> Error intente nuevamente</strong> </div>'

                      document.querySelector('#response').innerHTML = errorAlert;
                      document.querySelector('#response').style.display = "block";
                     resetUploadForm();})
    });

    function resetUploadForm(){
        document.querySelector('#fecha').value = "";
        document.querySelector('#hora').value = "";
        document.querySelector('#id-pac').value = "";
        document.querySelector('#id-odo').value = "";

    }

    (function(){
        let pathname = window.location.pathname;
        if(pathname === "/"){
            document.querySelector(".nav .nav-item a:first").addClass("active");
        } else if (pathname == "/turnosLista.html") {
            document.querySelector(".nav .nav-item a:last").addClass("active");
        }
    })();
});