package org.king2.blogs.service;

import org.king2.blogs.result.SystemResult;

import javax.servlet.http.HttpServletRequest;

public interface BLogsIndexService {

    SystemResult index(HttpServletRequest request) throws Exception;

    SystemResult get(String id, HttpServletRequest request) throws Exception;
}
