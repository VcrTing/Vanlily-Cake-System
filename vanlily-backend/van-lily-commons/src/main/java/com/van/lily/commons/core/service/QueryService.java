package com.van.lily.commons.core.service;

import com.van.lily.commons.core.AjaxResult;

public interface QueryService {

    <T> AjaxResult pag(T param);

    AjaxResult one(Long id);
}
