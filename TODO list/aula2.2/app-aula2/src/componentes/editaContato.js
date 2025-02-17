import { useEffect, useState } from "react"
import { useNavigate, useParams } from 'react-router-dom'

function EditaContato(){
    const [contato, setContato] = useState({id:0, nome:'', email:'', telefone: ''})
    const { id } = useParams();    
    const navigate = useNavigate()

    function handleChange(event){
        const{name, value} = event.target
        setContato((prevContato) => ({
           ...prevContato,
           [name]: value
        }))
    }
    useEffect(()=>{
        fetch(`http://localhost:3000/contatos/${id}`)  
        .then(resp => resp.json())
        .then(data => setContato(data))
    },[id])

    function SaveContato(){        
       
        fetch(`http://localhost:3000/contatos/${id}`,
        {
            method: 'PUT',
            headers:{'ContentType':'application/json'},
            body:JSON.stringify(contato)
        }
       )
       navigate('/consulta')
    }

    const limparCampos = () => {
        setContato({id:0, nome:'', email:'', telefone:''})
    }

    return(
        <div className="container">
            <h2 className="text=center m-4">Editando Contato</h2>
            <form>
                <label className="control-form">Informe seu nome</label>
                <input className="form-control mb-2"
                        type="text"
                        placeholder="Informe seu nome"
                        name="nome"
                        value={contato.nome}
                        onChange={handleChange} 
                        />
                
                <label>Informe seu Email</label>
                <input className="form-control mb-2"
                        type="email"
                        placeholder="Informe seu email"
                        name="email"
                        value={contato.email}
                        onChange={handleChange}
                    />

                <label>Informe seu Fone</label>
                <input className="form-control mb-2"
                        type="tel"
                        placeholder="Infome telefone"
                        name="telefone"
                        value={contato.telefone}
                        onChange={handleChange}
                        />
                
                <div className="mt-4 d-flex justify-content-beetween">
                    <button
                        className="btn
                            btn-outline-primary"
                            onClick={SaveContato}
                    >Gravar</button>
                    <button className="btn
                            btn-outline-warning"
                            onClick={limparCampos}>
                        Cancelar
                    </button>
                </div>
            </form>
        </div>
    )
}

export default EditaContato