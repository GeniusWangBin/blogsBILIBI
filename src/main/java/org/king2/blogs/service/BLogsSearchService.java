package org.king2.blogs.service;

import org.king2.blogs.result.SystemResult;

public interface BLogsSearchService {
    SystemResult getByQuery(String query);
}
