package com.bjtu.redis;

import com.bjtu.redis.Test;
import org.apache.commons.io.monitor.FileAlterationListenerAdaptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.text.MessageFormat;

public class FileListener extends FileAlterationListenerAdaptor {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void onFileChange(File file) {
        Test.lock.compareAndSet(false, true);
        Test.loadConfigJson();
        Test.lock.set(false);
    }
}
