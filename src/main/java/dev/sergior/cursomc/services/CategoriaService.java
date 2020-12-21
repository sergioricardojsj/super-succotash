package dev.sergior.cursomc.services;

import dev.sergior.cursomc.domain.Categoria;
import dev.sergior.cursomc.dtos.CategoriaDTO;
import dev.sergior.cursomc.exceptions.DataIntegrityException;
import dev.sergior.cursomc.exceptions.ObjectNotFoundException;
import dev.sergior.cursomc.repositories.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    public Categoria find(Integer id) throws ObjectNotFoundException {
        Optional<Categoria> optional = categoriaRepository.findById(id);
        return optional.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id: " + id +
                "\nTipo: " + Categoria.class.getName()));
    }

    public List<Categoria> findAll() {
        return categoriaRepository.findAll();
    }

    public Categoria insert(Categoria categoria) {
        return categoriaRepository.save(categoria);
    }

    public Categoria update(Categoria categoria) throws ObjectNotFoundException {
        Categoria novaCategoria = find(categoria.getId());
        updateData(categoria, novaCategoria);

        return categoriaRepository.save(novaCategoria);
    }

    public void delete(Integer id) throws DataIntegrityException {
        try {
            categoriaRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException("Não é possível excluir categoria que possui produtos!");
        }
    }

    public Page<Categoria> findPage(Integer page, Integer linesPerPage, String order, String direction) {
        PageRequest request = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), order);
        return categoriaRepository.findAll(request);
    }

    public Categoria fromDto(CategoriaDTO categoriaDTO) {
        return new Categoria(categoriaDTO.getId(), categoriaDTO.getNome());
    }

    private void updateData(Categoria categoria, Categoria novaCategoria) {
        novaCategoria.setNome(categoria.getNome());
    }

}
