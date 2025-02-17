import { useEffect, useState } from "react";
import { useNavigate, useParams } from 'react-router-dom';

function EditaLista() {
    const [lista, setLista] = useState({ id: 0, valor: '' });
    const { id } = useParams(); 
    const [error, setError] = useState(false);

    const navigate = useNavigate();
    // Função para buscar o item com base no ID
    useEffect(() => {
        fetch(`http://localhost:3000/itens/${id}`)
            .then(res => {
                if (!res.ok) throw new Error('Erro ao carregar o item');
                return res.json();
            })
            .then(data => setLista(data))
            .catch(err => console.error(err));
    }, [id]);

    // Função para atualizar o item
    function handleChange(event) {
        const { name, value } = event.target;
        setLista((prevLista) => ({
            ...prevLista,
            [name]: value
        }));
    }

    // Função para salvar as alterações
    function saveLista(e) {
        e.preventDefault(); // Evita o recarregamento da página
        
        if(lista.valor == ''){
            setError(true)
        }else {
            fetch(`http://localhost:3000/itens/${id}`, {
                method: 'PUT',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(lista)
            })
            .then(res => {
                if (!res.ok) throw new Error('Erro ao atualizar o item');
                navigate('/lista'); // Redireciona para a lista após salvar
            }).then(() => {
                setError(false)
            })
            .catch(err => console.error(err));
        }
    }

    // Função para limpar os campos
    const limparCampos = () => {
        setLista({ id: 0, valor: '' });
        setError(false)
        navigate('/lista');
    };

    return (
        <div className="container">
            <h2 className="text-center m-4">Editando item</h2>
            <form onSubmit={saveLista}>
                <label className="control-form">Informe o valor:</label>
                <input
                    className="form-control mb-2"
                    type="text"
                    placeholder="Informe o valor"
                    name="valor"
                    value={lista.valor}
                    onChange={handleChange}
                />

{error && <p className="text-danger">Por favor, preencha o campo antes de salvar.</p>}

                <div className="mt-4 d-flex justify-content-between">
                    <button
                        type="submit"
                        className="btn btn-outline-primary"
                    >
                        Gravar
                    </button>
                    <button
                        type="button"
                        className="btn btn-outline-warning"
                        onClick={limparCampos}
                    >
                        Cancelar
                    </button>
                </div>
            </form>
        </div>
    );
}

export default EditaLista;