import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { Link } from "react-router-dom"

function ListaToDo(){
    const [valor, setValor] = useState('');
    const navigate = useNavigate()

    const [lista, setLista] = useState([])

    function Load(){
        fetch('http://localhost:3000/itens')
        .then(res => res.json())
        .then(data => setLista(data))
    }
    
    useEffect(() =>{
        Load()
    },[])

    function addItem(e){
        const lista = {valor}
        fetch("http://localhost:3000/itens",
        {
            method: 'POST',
            headers: {'ContentType': 'application/json'},
            body: JSON.stringify(lista)
        })
        navigate('/lista')
    }

    const CancelarTarefa = () => {
        setValor('')
    }

    const removeItem = (id) => {
        if(window.confirm("Tem certeza que deseja excluir este item?")) {
            fetch(`http://localhost:3000/itens/${id}`, {
                method: 'DELETE'
            })
            .then(() => Load()) // Recarrega a lista após exclusão
            .catch(err => console.error(err));
        }
    };

    return(
        <div className="container">
            <div>
                <h2 className="text-center m-4">Lista</h2>
                <form className="container">
                    <label className="control-form">Escreva um valor: </label>
                    <input className="form-control mb-2" 
                        type="text" 
                        placeholder="Estou pensando em..."
                        value={valor}
                        onChange={(e)=> setValor(e.target.value)}/>
    
                    <div className="mt-4 d-flex justify-content-between">
                        <button className="btn btn-primary px-4" onClick={addItem}>
                            Gravar
                        </button>
                        <button className="btn btn-outline-secondary" type="button" onClick={CancelarTarefa}>
                            Cancelar
                        </button>
                    </div>
                </form>
            </div>
    
            <div className="mt-4">
                <div className="row justify-content-center">
                    {/* Aumente a largura máxima ajustando estas classes */}
                    <div className="col-12 col-md-10 col-xl-8"> {/* Alterado para col-md-10 (tablets) e col-xl-8 (telas grandes)*/}
                        {lista.map((li, index) => (
                            <div key={index} className="card mb-3 mx-auto">
                                <div className="card-body d-flex justify-content-between align-items-center">
                                    <div 
                                        className="flex-grow-1 me-3" >
                                        {li.valor}
                                    </div>
                                    <div className="d-flex flex-column gap-2 flex-shrink-0">
                                        <Link 
                                            to={`/lista/${li.id}`}
                                            className="btn btn-sm btn-outline-primary px-3"> {/* Botões mais largos */}
                                            Editar
                                        </Link>
                                        <button
                                            className="btn btn-sm btn-outline-danger px-3"
                                            onClick={() => removeItem(li.id)}>
                                            Remover
                                        </button>
                                    </div>
                                </div>
                            </div>
                        ))}
                    </div>
                </div>
            </div>
        </div>
    )
}

export default ListaToDo