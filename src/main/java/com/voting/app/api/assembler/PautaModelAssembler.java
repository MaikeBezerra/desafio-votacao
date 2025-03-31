package com.voting.app.api.assembler;

import com.voting.app.api.model.ResultadoModel;
import com.voting.app.domain.model.Pauta;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SessaoModelAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public ResultadoModel toResultadoModel(Pauta pauta) {
        return modelMapper.map(pauta, ResultadoModel.class);
    }

}
