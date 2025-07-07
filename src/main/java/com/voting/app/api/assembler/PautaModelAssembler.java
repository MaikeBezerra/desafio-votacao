package com.voting.app.api.assembler;

import com.voting.app.api.model.PautaModel;
import com.voting.app.api.model.ResultadoModel;
import com.voting.app.domain.model.Pauta;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PautaModelAssembler {

    private final ModelMapper modelMapper;

    public PautaModel toPautaModel(Pauta pauta) {
        return modelMapper.map(pauta, PautaModel.class);
    }

    public ResultadoModel toResultadoModel(Pauta pauta) {
        return modelMapper.map(pauta, ResultadoModel.class);
    }

}
