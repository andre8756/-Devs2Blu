import { useEffect, useState } from "react";
import { useNavigate, useParams } from 'react-router-dom';

function EditaContato() {
    const [contato, setContato] = useState({ id: 0, valor: '' }); // Alterado para "valor"
    const { id } = useParams(); // Extrai o ID da URL
    const navigate = useNavigate();

    // Função para buscar o contato com base no ID
    useEffect(() => {
        fetch(`http://localhost:3000/itens/${id}`)
            .then(res => {
                if (!res.ok) throw new Error('Erro ao carregar o item');
                return res.json();
            })
            .then(data => setContato(data))
            .catch(err => console.error(err));
    }, [id]);

    // Função para atualizar o contato
    function handleChange(event) {
        const { name, value } = event.target;
        setContato((prevContato) => ({
            ...prevContato,
            [name]: value
        }));
    }

    // Função para salvar as alterações
    function saveContato(e) {
        e.preventDefault(); // Evita o recarregamento da página

        fetch(`http://localhost:3000/itens/${id}`, {
            method: 'PUT',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(contato)
        })
        .then(res => {
            if (!res.ok) throw new Error('Erro ao atualizar o item');
            navigate('/lista'); // Redireciona para a lista após salvar
        })
        .catch(err => console.error(err));
    }

    // Função para limpar os campos
    const limparCampos = () => {
        setContato({ id: 0, valor: '' });
        navigate('/lista');
    };

    return (
        <div className="container">
            <h2 className="text-center m-4">Editando item</h2>
            <form onSubmit={saveContato}>
                <label className="control-form">Informe o valor:</label>
                <input
                    className="form-control mb-2"
                    type="text"
                    placeholder="Informe o valor"
                    name="valor"
                    value={contato.valor}
                    onChange={handleChange}
                />

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

export default EditaContato;