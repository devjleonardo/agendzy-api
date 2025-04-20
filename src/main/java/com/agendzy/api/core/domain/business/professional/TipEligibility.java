package com.agendzy.api.core.domain.business.professional;

public enum TipEligibility {

    DISABLED,        // Não pode receber gorjeta
    OPTIONAL,        // Cliente decide no momento do pagamento
    MANDATORY,       // Valor fixo obrigatório
    SHARED           // Gorjeta é coletiva

}
