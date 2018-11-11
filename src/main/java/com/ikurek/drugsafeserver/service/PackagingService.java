package com.ikurek.drugsafeserver.service;

import com.ikurek.drugsafeserver.model.Drug;
import com.ikurek.drugsafeserver.model.Packaging;

interface PackagingService {

    Packaging findPackagingByEan(Long ean);

    Drug findDrugByPackaging(Packaging packaging);


}
