package dev.sergior.cursomc.services.validation;

import dev.sergior.cursomc.domain.Cliente;
import dev.sergior.cursomc.domain.TipoCliente;
import dev.sergior.cursomc.dtos.ClienteDTO;
import dev.sergior.cursomc.dtos.ClienteNewDTO;
import dev.sergior.cursomc.repositories.ClienteRepository;
import dev.sergior.cursomc.resources.exception.FieldMessage;
import dev.sergior.cursomc.services.validation.utils.BR;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.devtools.remote.server.HandlerMapper;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.*;

public class ClienteUpdateValidator implements ConstraintValidator<ClienteUpdate, ClienteDTO> {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    public void initialize(ClienteUpdate constraintAnnotation) {

    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean isValid(ClienteDTO clienteDto, ConstraintValidatorContext constraintValidatorContext) {
        Map<String, String> map = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        Integer uriId = Integer.parseInt(map.get("id"));

        List<FieldMessage> list = new ArrayList<>();

        Cliente cliente = clienteRepository.findByEmail(clienteDto.getEmail());

        if (cliente != null && !cliente.getId().equals(uriId)) {
            list.add(new FieldMessage("email", "email já existente"));
        }

        list.forEach(element -> {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate(element.getMessage())
                    .addPropertyNode(element.getFieldName())
                    .addConstraintViolation();
        });

        return list.isEmpty();
    }

}
