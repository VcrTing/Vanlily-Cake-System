package com.van.lily.commons.core.service;

import com.van.lily.commons.core.AjaxResult;

public interface ModifyService {

    <T> AjaxResult pos(T form);

    <T> AjaxResult upd(Long id, T form);
}
