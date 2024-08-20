package com.mrvalf.retirementcalculator.configurations;

import com.mrvalf.retirementcalculator.taxes.TaxBracketFederal;
import com.mrvalf.retirementcalculator.taxes.TaxBracketVirginia;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TaxesConfig {
    @Bean
    public TaxBracketVirginia taxBracketVirginia () {
        return new TaxBracketVirginia();
    }
    @Bean
    public TaxBracketFederal taxBracketFederal () {
        return new TaxBracketFederal();
    }
}
