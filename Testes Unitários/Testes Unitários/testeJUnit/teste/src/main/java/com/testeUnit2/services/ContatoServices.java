package com.testeUnit2.services;

import com.testeUnit2.model.Contato;
import com.testeUnit2.repository.ContatoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContatoServices {

    @Autowired
    private ContatoRepository contatoRepository;

    public Contato salvar(Contato contato){

        if(contato.getNome().length() < 3){ //Verificação se nome é maior que 2 letras
            throw new RuntimeException("O nome precisa ter no mínimo 3 letras");
        }else {

            if(validarEmail(contato.getEmail())){ //Valida o email
                if(contato.getSenha().length() < 5){ //Valida a senha
                    throw new RuntimeException("A senha deve ter no mínimo 5 caracteres");
                }else {
                    return contatoRepository.save(contato);
                }
            }else {
                throw new RuntimeException("Email Inválido!");
            }
        }
    }

    public List<Contato> listarContatos(){
        return contatoRepository.findAll();
    }

    public Contato atualizarContato(Long id, Contato contatoAtualizado) {
        // Verifica se o contato existe
        Contato contatoExistente = contatoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Contato não encontrado"));

        // Validações (as mesmas do método salvar)
        if(contatoAtualizado.getNome().length() < 3) {
            throw new RuntimeException("O nome precisa ter no mínimo 3 letras");
        }

        if(!validarEmail(contatoAtualizado.getEmail())) {
            throw new RuntimeException("Email Inválido!");
        }

        if(contatoAtualizado.getSenha().length() < 5) {
            throw new RuntimeException("A senha deve ter no mínimo 5 caracteres");
        }

        // Atualiza os campos
        contatoExistente.setNome(contatoAtualizado.getNome());
        contatoExistente.setEmail(contatoAtualizado.getEmail());
        contatoExistente.setSenha(contatoAtualizado.getSenha());

        return contatoRepository.save(contatoExistente);
    }

    public Optional<Contato> buscarPorId(Long id){
        return contatoRepository.findById(id);
    }

    public void removerPorId(Long id) {
        Contato contato = contatoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Contato não encontrado com ID: " + id));

        contatoRepository.delete(contato); // Usando delete() com a entidade
    }

    public boolean validarEmail(String email){
        if (email == null || email.isEmpty()) {
            return false;
        }

        int indexArroba = email.indexOf('@');

        // 1. Verifica se '@' existe
        if (indexArroba == -1) {
            return false; // Não encontrou '@'
        }

        // 2. Verifica se '@' NÃO está no início (precisa de caractere antes)
        if (indexArroba == 0) {
            return false; // '@' está no começo, não tem letra antes
        }

        // 3. Verifica se '@' NÃO está no fim (precisa de caractere depois)
        if (indexArroba == email.length() - 1) {
            return false; // '@' está no fim, não tem letra depois
        }

        // 4. Pega o caractere imediatamente antes e depois do '@'
        char charAntes = email.charAt(indexArroba - 1);
        char charDepois = email.charAt(indexArroba + 1);

        // 5. Verifica se ambos os caracteres são letras
        // Character.isLetter() verifica se é uma letra de qualquer idioma (inclui acentos)
        boolean antesEhLetra = Character.isLetter(charAntes);
        boolean depoisEhLetra = Character.isLetter(charDepois);

        // Retorna true APENAS se ambos forem letras
        return antesEhLetra && depoisEhLetra;
    }

}
