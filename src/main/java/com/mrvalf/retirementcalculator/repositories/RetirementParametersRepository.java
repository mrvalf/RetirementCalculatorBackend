package com.mrvalf.retirementcalculator.repositories;

import com.mrvalf.retirementcalculator.entities.RetirementParametersEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RetirementParametersRepository  extends
    JpaRepository<RetirementParametersEntity, Integer> {
    List<RetirementParametersEntity> findAllByUsername(String username);
}
