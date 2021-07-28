package br.com.biblioteca.domains.locacao.validator;

import br.com.biblioteca.domains.locacao.dto.LocacaoCriarDTO;
import br.com.biblioteca.exception_handler.exception.WeekendDayException;
import br.com.biblioteca.util.DateUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataPrevistaDevolucaoImpl implements LocacoesValidator {

    private static final String FINAL_DE_SEMANA = "DEVOLUÇÕES APENAS EM DIAS ÚTEIS";

    @Override
    public void validar(LocacaoCriarDTO locacaoCriarDTO) {
        if (DateUtil.isWeekend(locacaoCriarDTO.getDataPrevistaDevolucao().getDayOfWeek())) {
            throw new WeekendDayException(FINAL_DE_SEMANA);
        }
    }

}
