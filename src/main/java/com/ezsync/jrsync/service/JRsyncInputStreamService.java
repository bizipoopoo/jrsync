package com.ezsync.jrsync.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface JRsyncInputStreamService {

    Map<String, Map<String, Long>> calculateAdl32AndMd5(File file) throws IOException;

    List<Object> diff(File file, Map<String, Map<String, Long>> compareTable) throws IOException;
}
