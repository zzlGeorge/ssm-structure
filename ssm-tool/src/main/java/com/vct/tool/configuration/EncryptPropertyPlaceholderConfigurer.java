package com.vct.tool.configuration;

import com.vct.tool.util.DESUtil;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

/**
 * @author vincent
 * @version 1.0.0
 * @ClassName EncryptPropertyPlaceholderConfigurer.java
 * @Description TODO
 * @createTime 2019-08-07 14:24:00
 */
public class EncryptPropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer {
    // 需要解密的字段
    private String[] encryptPropNames = new String[]{"jdbc.user", "jdbc.password"};

    @Override
    protected String convertProperty(String propertyName, String propertyValue) {
        if (isEncryptProp(propertyName)) {
            return DESUtil.getDecryptString(propertyValue);
        } else {
            return propertyValue;
        }
    }

    private boolean isEncryptProp(String propertyName) {
        for (String encryptPropertyName : encryptPropNames) {
            if (encryptPropertyName.equals(propertyName)) {
                return true;
            }
        }
        return false;
    }
}
