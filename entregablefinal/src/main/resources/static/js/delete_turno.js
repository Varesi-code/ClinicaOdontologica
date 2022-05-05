function deleteBy(id)
{
          const url = './turnos/id='+ id;
          const settings = {
              method: 'DELETE'
          }
          fetch(url,settings)
          .then(response => response.json())

          //borrar la fila del estudiante eliminado
          let row_id = "#tr_" + id;
          document.querySelector(row_id).remove();

}