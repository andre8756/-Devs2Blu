import { useEffect, useState } from "react";
import { Link } from "react-router-dom";

function ConsultaContato() {
  const [contatos, setContatos] = useState([]);
  function load() {
    fetch("http://localhost:3000/contatos")
      .then((response) => response.json())
      .then((data) => setContatos(data));
  }

  useEffect(() => {
    load();
  }, []);

  return (
    <div className="container">
      <h2 className="text-center m-4">Lista de contatos</h2>
      <table className="table table-striped">
        <thead>
          <tr>
            <th>Nome</th>
            <th>Email</th>
            <th>Telefone</th>
          </tr>
        </thead>
        <tbody>
          {contatos.map((c) => {
            return (
              <tr>
                <td>{c.nome}</td>
                <td>{c.email}</td>
                <td>{c.telefone}</td>
                <td>
                  <Link to={`/contato/${c.id}`}>Editar</Link>
                </td>
              </tr>
            );
          })}
        </tbody>
      </table>
    </div>
  );
}

export default ConsultaContato;
