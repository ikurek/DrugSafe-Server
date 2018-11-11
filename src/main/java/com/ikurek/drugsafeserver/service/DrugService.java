package com.ikurek.drugsafeserver.service;

import com.ikurek.drugsafeserver.model.Drug;

import java.util.Set;

interface DrugService {

    void merge(Set<Drug> drug);

    Long getDrugCount();

    Drug getDrugWithId(Long id);

    Set<Drug> getDrugsWhereCommonNameIs(String commonName);

    Set<Drug> getDrugsWhereNameOrSubstanceContains(String contained);
}
