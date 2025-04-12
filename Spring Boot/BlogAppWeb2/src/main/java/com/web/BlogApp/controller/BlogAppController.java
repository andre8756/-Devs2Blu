package com.web.BlogApp.controller;


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.web.BlogApp.dtos.BlogAppRecordPostComentario;
import com.web.BlogApp.model.PostComentarioModel;
import com.web.BlogApp.repository.PostComentarioRepository;
import com.web.BlogApp.service.BlogAppServiceImplComentarios;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;

//import com.web.BlogApp.model.PostComentarioModel;
import com.web.BlogApp.model.PostModel;
import com.web.BlogApp.service.BlogAppService;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.ui.Model;

@Controller
//@RestController //não funciona para returno de string
public class BlogAppController {


	@Autowired
	BlogAppService blogappservice;

  @Autowired
  private BlogAppServiceImplComentarios comentarioService;


	// LISTA TODOS OS POSTS
	@GetMapping(value = "/posts") // posso utilizar assim também chamando pelo navegador
//  @RequestMapping(value = "/posts", method = RequestMethod.GET)
	public ModelAndView  getPosts() {
		ModelAndView mv = new ModelAndView("posts");
		List<PostModel> posts = blogappservice.findAll();
		mv.addObject("post", posts);
		return mv;
	}

	@GetMapping(value = "/posts/{id}")
	public ModelAndView getPostDetails(@PathVariable("id") UUID id, RedirectAttributes attributes) { // Adicione RedirectAttributes
		Optional<PostModel> postOptional = blogappservice.findById(id); // Renomeado para clareza

		if (postOptional.isPresent()) {
			// Post encontrado, proceder normalmente
			ModelAndView mv = new ModelAndView("postDetails"); // <-- CORRIJA O NOME DA VIEW AQUI TAMBÉM!
			PostModel post = postOptional.get();
			mv.addObject("post", post); // Adiciona o objeto post ao modelo

			// IMPORTANTE: Se estiver usando LAZY loading para comentários,
			// você precisa inicializá-los aqui dentro da transação do serviço
			// ou usar JOIN FETCH na sua consulta do repositório.
			// Exemplo de inicialização (se necessário, faça no Service):
			// Hibernate.initialize(post.getPostComentarioModel()); // Ou post.getPostComentarioModel().size();

			mv.addObject("novoComentario", new BlogAppRecordPostComentario(""));
			return mv;
		} else {
			// Post NÃO encontrado
			attributes.addFlashAttribute("erro", "Post com ID " + id + " não encontrado."); // Mensagem de erro
			// Redireciona para a lista de posts
			return new ModelAndView("redirect:/posts");
		}
	}

	@GetMapping(value = "/newpost")
	public String getPostform(){
		return "newpostForm";
	}

	@PostMapping(value = "/newpost")
	public String savePost(@Valid PostModel post, BindingResult result, RedirectAttributes attributes){
		if(result.hasErrors()){
			attributes.addFlashAttribute("mensagem", "Verifique se os campos obrigatórios foram preenchidos!");
			return "redirect:/newpostForm";
		}
		post.setData(LocalDate.now());
		blogappservice.save(post);
		return "redirect:/posts";
	}

	// Método para exibir o formulário de edição preenchido com os dados do post
	@GetMapping(value = "/posts/edit/{id}")
	public ModelAndView editPost(@PathVariable("id") UUID id) {
		ModelAndView mv = new ModelAndView("editPostForm");
		Optional<PostModel> postOptional = blogappservice.findById(id);

		if (postOptional.isPresent()) {
			PostModel post = postOptional.get();
			mv.addObject("post", post);
			return mv;
		} else {
			return new ModelAndView("redirect:/posts");
		}
	}

	// Método para processar a atualização do post
	@PostMapping(value = "/posts/edit/{id}")
	public String updatePost(@PathVariable("id") UUID id,
							 @Valid PostModel postFormData, // Dados APENAS do formulário (Título, Autor, Texto)
							 BindingResult result,
							 RedirectAttributes attributes) {

		if (result.hasErrors()) {
			attributes.addFlashAttribute("mensagem", "Erro na validação. Verifique os campos.");
			// Para retornar erros específicos ao form, você precisaria adicionar
			// attributes.addFlashAttribute("org.springframework.validation.BindingResult.postFormData", result);
			// attributes.addFlashAttribute("postFormData", postFormData); // E o objeto com erro
			return "redirect:/posts/edit/" + id; // Retorna para o form GET
		}

		// 1. BUSCAR a entidade EXISTENTE do banco de dados
		Optional<PostModel> existingPostOptional = blogappservice.findById(id);

		if (existingPostOptional.isPresent()) {
			// 2. Obter a instância GERENCIADA pelo Hibernate/JPA
			PostModel existingPost = existingPostOptional.get();
			// existingPost AGORA CONTÉM a referência correta para a lista de comentários original

			// 3. MERGE: Copiar APENAS os campos modificáveis do formulário para a entidade existente
			existingPost.setTitulo(postFormData.getTitulo());
			existingPost.setAutor(postFormData.getAutor());
			existingPost.setTexto(postFormData.getTexto());
			// IMPORTANTE: NÃO fazer existingPost.setPostComentarioModel(postFormData.getPostComentarioModel())
			// pois postFormData.getPostComentarioModel() será null ou vazio!
			// A lista de comentários original em existingPost deve ser preservada.

			// 4. SALVAR a entidade existente (agora modificada).
			// O Hibernate verá que a coleção de comentários ainda está referenciada.
			blogappservice.save(existingPost); // Linha 123 do seu stack trace está aqui ou dentro deste save

			attributes.addFlashAttribute("mensagem", "Post atualizado com sucesso!");
			return "redirect:/posts"; // Redireciona para a lista após sucesso

		} else {
			// Post não encontrado para atualizar
			attributes.addFlashAttribute("mensagem", "Erro: Post não encontrado para atualização.");
			return "redirect:/posts"; // Redireciona para a lista
		}
	}

	// Método para excluir um post - CORRIGIDO
	@GetMapping(value = "/posts/delete/{id}")
	public String deletePost(@PathVariable("id") UUID id, RedirectAttributes attributes) {
		Optional<PostModel> postOptional = blogappservice.findById(id);

		if (postOptional.isPresent()) {
			// Usa o método delete disponível na interface BlogAppService
			blogappservice.delete(postOptional.get());
			attributes.addFlashAttribute("mensagem", "Post excluído com sucesso!");
		} else {
			attributes.addFlashAttribute("mensagem", "Post não encontrado!");
		}

		return "redirect:/posts";
	}

	//-------------------Comentários-------------------------

	/**
	 * Salva um novo comentário para um post específico.
	 */
	@PostMapping("/posts/{postId}/comments")
	public String saveComment(@PathVariable("postId") UUID postId,
							  @Valid @ModelAttribute("novoComentario") BlogAppRecordPostComentario comentarioDto, // Pega o objeto do form
							  BindingResult result,
							  RedirectAttributes attributes,
							  Model model) { // Adicionado Model para caso de erro

		Optional<PostModel> postOptional = blogappservice.findById(postId);

		if (!postOptional.isPresent()) {
			attributes.addFlashAttribute("erroComentario", "Post não encontrado para adicionar comentário.");
			return "redirect:/posts"; // Redireciona para lista geral se o post sumiu
		}
		try {
			// Chama o serviço para salvar o comentário (ele já trata a busca do post internamente)
			comentarioService.saveComentario(postId, comentarioDto);
			attributes.addFlashAttribute("mensagemComentario", "Comentário adicionado com sucesso!");
		} catch (ResponseStatusException e) { // Captura erro se o post não for encontrado no serviço
			attributes.addFlashAttribute("erroComentario", e.getReason());
		} catch (Exception e) { // Captura outros erros inesperados
			attributes.addFlashAttribute("erroComentario", "Erro ao salvar comentário.");
			// Logar o erro e.printStackTrace(); ou usar um logger
		}

		// Redireciona de volta para a página de detalhes do post
		return "redirect:/posts/" + postId;
	}


	/**
	 * Deleta um comentário específico de um post.
	 */
	@GetMapping("/posts/{postId}/comments/delete/{commentId}")
	public String deleteComment(@PathVariable("postId") UUID postId,
								@PathVariable("commentId") UUID commentId,
								RedirectAttributes attributes) {

		Optional<PostComentarioModel> commentOptional = comentarioService.findById(commentId);

		if (commentOptional.isPresent()) {
			// Verifica se o comentário realmente pertence ao post (segurança extra opcional)
			if (commentOptional.get().getPostModel().getId().equals(postId)) {
				comentarioService.delete(commentOptional.get());
				attributes.addFlashAttribute("mensagemComentario", "Comentário excluído com sucesso!");
			} else {
				// Tentativa de excluir comentário de outro post
				attributes.addFlashAttribute("erroComentario", "Erro: Comentário não pertence a este post.");
			}
		} else {
			attributes.addFlashAttribute("erroComentario", "Comentário não encontrado.");
		}

		// Redireciona de volta para a página de detalhes do post
		return "redirect:/posts/" + postId;
	}


	/**
	 * Exibe o formulário para editar um comentário existente.
	 * Precisaremos de uma nova view para isso: "editCommentForm.html"
	 */
	@GetMapping("/posts/{postId}/comments/edit/{commentId}")
	public String showEditCommentForm(@PathVariable("postId") UUID postId,
									  @PathVariable("commentId") UUID commentId,
									  Model model, // Usar Model
									  RedirectAttributes attributes) {

		Optional<PostComentarioModel> commentOptional = comentarioService.findById(commentId);
		Optional<PostModel> postOptional = blogappservice.findById(postId); // Verifica se o post existe

		if (commentOptional.isPresent() && postOptional.isPresent()) {
			PostComentarioModel comment = commentOptional.get();
			// Verifica se o comentário pertence ao post correto
			if (!comment.getPostModel().getId().equals(postId)) {
				attributes.addFlashAttribute("erroComentario", "Erro: Comentário não pertence a este post.");
				return "redirect:/posts/" + postId;
			}

			// Cria um DTO a partir do comentário existente para preencher o formulário
			BlogAppRecordPostComentario comentarioDto = new BlogAppRecordPostComentario(comment.getComentario());

			model.addAttribute("comentarioEditDto", comentarioDto); // Objeto para o form th:object
			model.addAttribute("postId", postId);
			model.addAttribute("commentId", commentId);
			model.addAttribute("postTitulo", postOptional.get().getTitulo()); // Para exibir no form

			return "editCommentForm"; // Nome da nova view HTML

		} else {
			if (!postOptional.isPresent()) {
				attributes.addFlashAttribute("erroComentario", "Post não encontrado.");
				return "redirect:/posts";
			} else {
				attributes.addFlashAttribute("erroComentario", "Comentário não encontrado.");
				return "redirect:/posts/" + postId;
			}
		}
	}


	/**
	 * Processa a atualização de um comentário existente.
	 */
	@PostMapping("/posts/{postId}/comments/update/{commentId}")
	public String updateComment(@PathVariable("postId") UUID postId,
								@PathVariable("commentId") UUID commentId,
								@Valid @ModelAttribute("comentarioEditDto") BlogAppRecordPostComentario comentarioDto,
								BindingResult result,
								RedirectAttributes attributes,
								Model model) { // Model para caso de erro de validação

		Optional<PostComentarioModel> commentOptional = comentarioService.findById(commentId);

		// Verifica se o post ao qual o comentário pertence ainda existe
		Optional<PostModel> postOptional = blogappservice.findById(postId);
		if (!postOptional.isPresent()) {
			attributes.addFlashAttribute("erroComentario", "Post associado não encontrado.");
			return "redirect:/posts";
		}

		// Se a validação do DTO falhar
		if (result.hasErrors()) {
			// Redisplay o formulário de edição com os erros
			model.addAttribute("postId", postId);
			model.addAttribute("commentId", commentId);
			model.addAttribute("postTitulo", postOptional.get().getTitulo());
			// O DTO com erro (`comentarioEditDto`) já está no model via @ModelAttribute
			return "editCommentForm"; // Retorna para o formulário de edição
		}

		if (commentOptional.isPresent()) {
			PostComentarioModel comment = commentOptional.get();

			// Verifica se o comentário pertence ao post correto
			if (!comment.getPostModel().getId().equals(postId)) {
				attributes.addFlashAttribute("erroComentario", "Erro: Comentário não pertence a este post.");
				return "redirect:/posts/" + postId;
			}

			// Atualiza o texto do comentário
			comment.setComentario(comentarioDto.comentario());
			// Opcional: Atualizar a data do comentário para data da edição
			// comment.setData(LocalDate.now(ZoneId.of("UTC")));

			comentarioService.save(comment); // Salva a entidade atualizada
			attributes.addFlashAttribute("mensagemComentario", "Comentário atualizado com sucesso!");

		} else {
			attributes.addFlashAttribute("erroComentario", "Comentário não encontrado para atualizar.");
		}

		// Redireciona de volta para a página de detalhes do post
		return "redirect:/posts/" + postId;
	}




	//------------------- FIM: Métodos para Comentários -------------------------



}