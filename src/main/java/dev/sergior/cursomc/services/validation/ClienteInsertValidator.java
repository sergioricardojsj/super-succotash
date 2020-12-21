package dev.sergior.cursomc.services.validation;

import dev.sergior.cursomc.domain.Cliente;
import dev.sergior.cursomc.domain.TipoCliente;
import dev.sergior.cursomc.dtos.ClienteNewDTO;
import dev.sergior.cursomc.repositories.ClienteRepository;
import dev.sergior.cursomc.resources.exception.FieldMessage;
import dev.sergior.cursomc.services.validation.utils.BR;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;

public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, ClienteNewDTO> {

    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    public void initialize(ClienteInsert constraintAnnotation) {

    }

    @Override
    public boolean isValid(ClienteNewDTO clienteNewDTO, ConstraintValidatorContext constraintValidatorContext) {
        List<FieldMessage> list = new ArrayList<>();

        if (clienteNewDTO.getTipo().equals(TipoCliente.PESSOA_FISICA.getCod()) && !BR.isValidCPF(clienteNewDTO.getCpfOuCnpj())) {
            list.add(new FieldMessage("cpfOuCnpj", "CPF Inválido"));
        } else if (clienteNewDTO.getTipo().equals(TipoCliente.PESSOA_JURIDICA.getCod()) && !BR.isValidCNPJ(clienteNewDTO.getCpfOuCnpj())) {
            list.add(new FieldMessage("cpfOuCnpj", "CNPJ Inválido"));
        }

        Cliente cliente = clienteRepository.findByEmail(clienteNewDTO.getEmail());

        if (cliente != null) {
            list.add(new FieldMessage("email", "email já existente!"));
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
