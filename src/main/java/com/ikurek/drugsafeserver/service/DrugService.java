package com.ikurek.drugsafeserver.service;

import com.ikurek.drugsafeserver.model.Drug;

import java.util.Set;

interface DrugService {

    void merge(Set<Drug> drug);

    Long getDrugCount();

    Set<Drug> getDrugsWhereNameOrSubstanceContains(String contained);
}
