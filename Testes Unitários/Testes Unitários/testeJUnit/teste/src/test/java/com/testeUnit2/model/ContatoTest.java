package com.testeUnit2.model;

import com.testeUnit2.repository.ContatoRepository;
import com.testeUnit2.services.ContatoServices;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ContatoTest {

    private Contato contato;

    @InjectMocks
    private ContatoServices contatoServices;

    @Mock
    private ContatoRepository contatoRepository;

    @BeforeEach
    void setup(){
        contato = new Contato();
    }

    // Testes com Mockito ---------------------------------------------------------

    @Test
    public void deveRetornarListaDeContatos(){
        Contato contato = new Contato("Andre", "teste@gmail.com", "123456");
        when(contatoRepository.findAll()).thenReturn(Collections.singletonList(contato));
        List<Contato> contatos = contatoServices.listarContatos();
        System.out.println(contatos);

        Assertions.assertEquals(1, contatos.size()); // testa o número de contatos esperados (1)
    }


    @Test
    @DisplayName("Lançar exeção quando o contato tem menos de tres letras")
    public void deveLancarExcessaoQndContatoTiverNomeMenorTressLetras() {
        Contato contato = new Contato("zé", "teste@gmail.com", "123456");

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            contatoServices.salvar(contato);
        });
        assertEquals("O nome precisa ter no mínimo 3 letras", ex.getMessage());
        assertNotNull(ex);

}

    @Test
    @DisplayName("Deve Retornar email inválido")
    public void deveRetornarOEmail() {
        Contato contato = new Contato("André", "blablabla", "123456");

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            this.contatoServices.salvar(contato);
        });

        System.out.println(contatoServices.listarContatos());

        Assertions.assertEquals("Email Inválido!",ex.getMessage());
    }

    @Test
    @DisplayName("Deve retornar que a senha precisa ter no mínio 5 caracteres")
    public void deveRetornarMinimoCaracteres() {
        Contato contato = new Contato("André", "teste@gmail.com", "123");

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            this.contatoServices.salvar(contato);
        });
        Assertions.assertEquals("A senha deve ter no mínimo 5 caracteres",ex.getMessage());
    }

    @Test
    @DisplayName("Deve retornar o contato buscado pelo id")
    public void deveRetornarOContatoBuscadoPeloId() {
        // Arrange
        Long id = 1L;
        Contato contatoMock = new Contato("André", "teste@gmail.com", "123456");
        when(contatoRepository.findById(id)).thenReturn(Optional.of(contatoMock));

        // Act
        Optional<Contato> resultado = contatoServices.buscarPorId(id);

        // Assert
        assertTrue(resultado.isPresent()); // Verifica se retornou um contato
        assertEquals(contatoMock.getNome(), resultado.get().getNome()); // Verifica o nome
        assertEquals(contatoMock.getEmail(), resultado.get().getEmail()); // Verifica o email

        // Verifica se o repository foi chamado corretamente
        verify(contatoRepository).findById(id);
    }

    @Test
    @DisplayName("Deve atualizar um contato existente")
    public void deveAtualizarContato() {
        // Arrange
        Long id = 1L;
        Contato contatoExistente = new Contato("Antigo", "antigo@email.com", "senha123");
        Contato contatoAtualizado = new Contato("Novo", "novo@email.com", "novaSenha");

        when(contatoRepository.findById(id)).thenReturn(Optional.of(contatoExistente));
        when(contatoRepository.save(any(Contato.class))).thenReturn(contatoAtualizado);

        // Act
        contatoServices.atualizarContato(id, contatoAtualizado);

        // Assert
        verify(contatoRepository).findById(id);
        verify(contatoRepository).save(contatoExistente); // Verifica se salvou o contato existente com os novos dados
        assertEquals("Novo", contatoExistente.getNome()); // Verifica se o nome foi atualizado
        assertEquals("novo@email.com", contatoExistente.getEmail()); // Verifica se o email foi atualizado
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar atualizar contato inexistente")
    public void deveLancarExcecaoAoAtualizarContatoInexistente() {
        // Arrange
        Long id = 999L;
        Contato contatoAtualizado = new Contato("Novo", "novo@email.com", "novaSenha");

        when(contatoRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            contatoServices.atualizarContato(id, contatoAtualizado);
        });

        verify(contatoRepository).findById(id);
        verify(contatoRepository, never()).save(any()); // Verifica que não tentou salvar
    }

    @Test
    @DisplayName("Deve excluir contato existente com sucesso - Fluxo completo")
    public void deveExcluirContatoExistente() {
        // Arrange
        Contato novoContato = new Contato("André", "andre@teste.com", "senha123");
        Contato contatoSalvo = new Contato("André", "andre@teste.com", "senha123");
        contatoSalvo.setId(1L); // ID gerado

        // Configuração dos mocks CORRIGIDA
        when(contatoRepository.save(any(Contato.class))).thenReturn(contatoSalvo);
        when(contatoRepository.findById(1L)).thenReturn(Optional.of(contatoSalvo));
        doNothing().when(contatoRepository).delete(contatoSalvo); // Mudado para delete()

        // Act - Fluxo completo
        Contato salvo = contatoServices.salvar(novoContato);
        contatoServices.removerPorId(salvo.getId());

        // Assert CORRIGIDO
        verify(contatoRepository).save(novoContato);
        verify(contatoRepository).delete(contatoSalvo); // Verifica delete() em vez de deleteById()
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar excluir contato inexistente")
    public void deveLancarExcecaoAoExcluirContatoInexistente() {
        // Arrange
        Long idInexistente = 999L;

        // Configura o mock para retornar Optional.empty() quando buscar pelo ID
        when(contatoRepository.findById(idInexistente))
                .thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            contatoServices.removerPorId(idInexistente);
        });

        // Verifica a mensagem da exceção
        assertEquals("Contato não encontrado com ID: " + idInexistente, exception.getMessage());

        // Verifica que NÃO tentou chamar o delete
        verify(contatoRepository, never()).delete(any());
        verify(contatoRepository, never()).deleteById(any());

        // Verifica que tentou buscar o contato
        verify(contatoRepository).findById(idInexistente);
    }

}